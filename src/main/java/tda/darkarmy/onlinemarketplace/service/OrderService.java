package tda.darkarmy.onlinemarketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tda.darkarmy.onlinemarketplace.dto.ChangeOrderStatusRequestDto;
import tda.darkarmy.onlinemarketplace.dto.OrderItemDto;
import tda.darkarmy.onlinemarketplace.exception.ResourceNotFoundException;
import tda.darkarmy.onlinemarketplace.model.*;
import tda.darkarmy.onlinemarketplace.repository.*;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MailSenderService mailSenderService;

    public List<Orders> getMyOrders(){
        return orderRepository.findByUser(userService.getLoggedInUser());
    }

    public List<Orders> getAllOrders(){
        return orderRepository.findAll();
    }

    public Orders placeOrder(OrderItemDto[] orderDto) throws MessagingException, InterruptedException {
        User user = userService.getLoggedInUser();
        Orders orders = orderRepository.save(new Orders(user));

        Cart cart = cartRepository.findByUser(user);
        double total = 0d;
        String mailBody = "";
        for(int i=0;i<orderDto.length;i++){
            OrderItemDto orderItem = orderDto[i];
            Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
            int quantity = orderItem.getQuantity()<= product.getStock()? orderItem.getQuantity() : product.getStock();
            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
            orderItemRepository.save(new OrderItem(product, quantity, orders));
            String mailBodyText="<h3>Name: "+ product.getName()+"</h3><br/><h3>Quantity: "+orderItem.getQuantity()+"</h3><br/><h3>Price: "+orderItem.getQuantity()* product.getPrice()+"</h3></br>";
            mailBody+=mailBodyText;

            total = total + orderItem.getQuantity()* product.getPrice();
        }

        mailBody+="<br/><h2>Total: "+total+"</h2>";
        Orders orders1 = orderRepository.findById(orders.getId()).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
       mailSenderService.send(user, mailBody);

        System.out.println("Orders:  "+orders);
        return orders1;
    }

    public OrderItem changeStatus(Long orderId, ChangeOrderStatusRequestDto orderStatusRequestDto){
        OrderItem orderItem = orderItemRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        orderItem.setStatus(orderStatusRequestDto.getStatus());
        Product product = productRepository.findById(orderItem.getProduct().getId()).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        if(orderStatusRequestDto.getStatus().equalsIgnoreCase("cancelled")){
            product.setStock(product.getStock()+orderItem.getQuantity());
            productRepository.save(product);
        }
        return orderItemRepository.save(orderItem);
    }
}
