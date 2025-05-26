package com.lluc.backend.shopapp.shopapp.services.Implementations;

import com.lluc.backend.shopapp.shopapp.models.entities.Order;
import com.lluc.backend.shopapp.shopapp.models.entities.OrderProduct;
import com.lluc.backend.shopapp.shopapp.models.entities.PricingValue;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.models.entities.UserAddress;
import com.lluc.backend.shopapp.shopapp.models.request.OrderRequest;
import com.lluc.backend.shopapp.shopapp.repositories.OrderRepository;
import com.lluc.backend.shopapp.shopapp.repositories.ProductRepository;
import com.lluc.backend.shopapp.shopapp.repositories.UserAddressRepository;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.OrderService;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProductService productService;

    @Override
        @Transactional
        public Order createOrder(String username, OrderRequest orderRequest) {
        // Buscar al usuario por su username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Buscar la dirección por su ID
        UserAddress address = userAddressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + orderRequest.getAddressId()));

        // Crear el pedido
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUser(user);
        order.setStreet(address.getStreet());
        order.setCity(address.getCity());
        order.setState(address.getState());
        order.setPostalCode(address.getPostalCode());
        order.setCountry(address.getCountry());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        // Asociar los productos al pedido
        List<OrderProduct> orderProducts = orderRequest.getProducts().stream().map(productRequest -> {
                OrderProduct orderProduct = new OrderProduct();
                Product product = productRepository.findById(productRequest.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productRequest.getProductId()));

                // Actualizar el stock utilizando el nuevo método
                productService.updateStock(product, productRequest.getCategory(), productRequest.getQuantity());

                // Configurar los datos del producto en el pedido
                orderProduct.setProduct(product);
                orderProduct.setOrder(order);
                orderProduct.setQuantity(productRequest.getQuantity());
                orderProduct.setStatus("PENDING");
                orderProduct.setCategory(productRequest.getCategory()); // Asignar la categoría
                orderProduct.setCompany(product.getCompany()); // Asignar la empresa
                return orderProduct;
        }).collect(Collectors.toList());

        order.setProducts(orderProducts);

        emailService.sendOrderConfirmationEmail(user.getEmail(), order);

        // Guardar el pedido en la base de datos
        return orderRepository.save(order);
        }

    
}