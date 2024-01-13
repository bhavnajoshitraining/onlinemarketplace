package tda.darkarmy.onlinemarketplace.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tda.darkarmy.onlinemarketplace.dto.ProductDto;
import tda.darkarmy.onlinemarketplace.exception.ResourceNotFoundException;
import tda.darkarmy.onlinemarketplace.model.CartItem;
import tda.darkarmy.onlinemarketplace.model.OrderItem;
import tda.darkarmy.onlinemarketplace.model.Product;
import tda.darkarmy.onlinemarketplace.model.User;
import tda.darkarmy.onlinemarketplace.repository.CartItemRepository;
import tda.darkarmy.onlinemarketplace.repository.OrderItemRepository;
import tda.darkarmy.onlinemarketplace.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final String BASE_URL = "http://localhost:8000";
    private Path fileStoragePath;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public ProductService() {
        try {
            fileStoragePath = Paths.get("src\\main\\resources\\static\\fileStorage").toAbsolutePath().normalize();
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }


    public List<Product> findMyProducts(){
        User user = userService.getLoggedInUser();
        return productRepository.findByUser(user);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

    public Product addProduct(ProductDto productDto){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(productDto.getImage().getOriginalFilename()));
        fileName = fileName.replace(" ", "");
        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        try {
            Files.copy(productDto.getImage().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product product = modelMapper.map(productDto, Product.class);
        product.setUser(userService.getLoggedInUser());
        product.setImageUrl(BASE_URL + "/fileStorage/" + fileName);
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, ProductDto productDto){
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        Product product1 = modelMapper.map(productDto, Product.class);
        product1.setUser(userService.getLoggedInUser());
        product1.setId(productId);
        product1.setImageUrl(product.getImageUrl());
        return productRepository.save(product1);
    }

    public String deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        List<OrderItem> orderItem = orderItemRepository.findByProduct(product);
        List<CartItem> cartItem = cartItemRepository.findByProduct(product);
        orderItem.stream().forEach(orderItem1 -> orderItemRepository.deleteById(orderItem1.getId()));
        cartItem.stream().forEach(cartItem1 -> cartItemRepository.deleteById(cartItem1.getId()));
        productRepository.deleteById(productId);
        return "Product deleted successfully";
    }
}
