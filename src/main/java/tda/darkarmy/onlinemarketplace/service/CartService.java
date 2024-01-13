package tda.darkarmy.onlinemarketplace.service;


import org.modelmapper.internal.bytebuddy.pool.TypePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tda.darkarmy.onlinemarketplace.dto.CartItemDto;
import tda.darkarmy.onlinemarketplace.exception.ResourceNotFoundException;
import tda.darkarmy.onlinemarketplace.model.Cart;
import tda.darkarmy.onlinemarketplace.model.CartItem;
import tda.darkarmy.onlinemarketplace.model.Product;
import tda.darkarmy.onlinemarketplace.model.User;
import tda.darkarmy.onlinemarketplace.repository.CartItemRepository;
import tda.darkarmy.onlinemarketplace.repository.CartRepository;
import tda.darkarmy.onlinemarketplace.repository.ProductRepository;

import java.util.Arrays;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    public Cart getMyCart(){
        User user = userService.getLoggedInUser();
        return cartRepository.findByUser(user);
    }

    public Cart addToCart(CartItemDto cartItemDto){
        User user = userService.getLoggedInUser();
        Product product = productRepository.findById(cartItemDto.getProductId()).orElseThrow(()-> new TypePool.Resolution.NoSuchTypeException("Product not found"));
        Cart cart = cartRepository.findByUser(user);
        if(cart==null) {
            cart = cartRepository.save(new Cart(Arrays.asList(), user, 0));
        }
        CartItem cartItem = cartItemRepository.save(new CartItem(product,cart, cartItemDto.getQuantity()));

        cart.setTotalPrice(cart.getTotalPrice()+ product.getPrice()*cartItem.getQuantity());
        cart = cartRepository.save(cart);
        return cart;
    }

    public Cart deleteFromCart(Long cartItemId){
        User user = userService.getLoggedInUser();
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("Cart Item not found"));
        Cart cart = cartRepository.findByUser(user);
        cart.setTotalPrice(cart.getTotalPrice()-cartItem.getProduct().getPrice()+cartItem.getQuantity());
        cartItemRepository.deleteById(cartItemId);
        return cartRepository.save(cart);
    }

}
