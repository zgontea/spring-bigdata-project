package com.example.demo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.example.demo.entities.Category;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderDetail;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.repositories.CategoryRepository;
import com.repositories.OrderDetailRepository;
import com.repositories.OrderRepository;
import com.repositories.ProductRepository;
import com.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunAtStart {
    private final CategoryRepository categoryRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public RunAtStart(CategoryRepository categoryRepository, OrderDetailRepository orderDetailRepository,
            OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        super();
        this.categoryRepository = categoryRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void runAtStart() {

        User user1 = new User("Eryk", "Dorosz", "erykdorosz@gmail.com", "12345", false);
        User user2 = new User("Marta", "Zalewska", "siema@lol.pl", "12378", true);
        userRepository.save(user1);
        userRepository.save(user2);

        Category category = new Category();
        category.setCategoryName("Wodeczki");
        category.setDescription("Dobre, bo polskie");

        categoryRepository.save(category);

        Date date = new Date();
        Product product = new Product("Zubrowka", "image1", new BigDecimal("7.50"), category);

        productRepository.save(product);

        OrderDetail orderDetail = new OrderDetail();
        Set<OrderDetail> orderDetails = new HashSet<>();
        orderDetails.add(orderDetail);

        Order order = new Order("Jan Gontarski", "lol@wp.pl", "666555444", "Zielona Dziura", "Polako", "95-020", date, user1);
        order.setOrderDetails(orderDetails);

        orderDetail.setPrice(new BigDecimal("20"));
        orderDetail.setProduct(product);
        orderDetail.setQuantity(1);
        orderDetail.setOrder(order);

        orderRepository.save(order);

    }
}
