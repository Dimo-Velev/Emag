package com.example.emag.service;

import com.example.emag.model.DTOs.address.OrderAddressDTO;
import com.example.emag.model.DTOs.order.CreatedOrderDTO;
import com.example.emag.model.DTOs.order.OrderWithFewInfoDTO;
import com.example.emag.model.DTOs.order.CreateOrderDTO;
import com.example.emag.model.DTOs.orderContent.OrderContentDTO;
import com.example.emag.model.entities.*;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.OrderRepository;
import com.example.emag.model.repositories.OrderStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public List<OrderWithFewInfoDTO> getAllOrders(int id) {
        List<Order> orderList = orderRepository.getAllByUserId(id);
        return orderList.stream()
                .map(order -> mapper.map(order, OrderWithFewInfoDTO.class))
                .collect(Collectors.toList());
    }

    public OrderWithFewInfoDTO getOrderById(int id, int userId) {
        Order order = findOrderById(id);
        checkIfItsUserOrder(order, userId);
        return mapper.map(order, OrderWithFewInfoDTO.class);
    }

    @Transactional
    public OrderWithFewInfoDTO cancelOrderById(int id, int userId) {
        Order order = findOrderById(id);
        checkIfItsUserOrder(order, userId);
        if (order.getStatus().getId() == 7){
            throw new BadRequestException("Order already canceled.");
        }
        if (order.getStatus().getId() > 3){
            throw new BadRequestException("You can't cancel the order anymore, because it's on its way to the address or delivered.");
        }
        order.setStatus(orderStatusRepository.findById(7).orElseThrow(() -> new NotFoundException("This should never happen, we have them in the DB.")));
        orderRepository.save(order);
        Set<Product> productList = order.getProductsInOrder().stream()
                .peek(orderContent -> orderContent.getProduct().setQuantity(orderContent.getProduct().getQuantity()+orderContent.getQuantity()))
                .map(orderContent -> orderContent.getProduct())
                .collect(Collectors.toSet());
        productRepository.saveAll(productList);
        return mapper.map(order, OrderWithFewInfoDTO.class);
    }

    private Order findOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found."));
    }

    private void checkIfItsUserOrder(Order order, int userId) {
        if (order.getUser().getId() != userId) {
            throw new UnauthorizedException("You have no access to this resource.");
        }
    }

    @Transactional
    public CreatedOrderDTO createOrder(CreateOrderDTO dto, int userId) {
        User user = getUserById(userId);
        PaymentType paymentType = paymentTypeRepository.findById(dto.getPaymentId()).
                orElseThrow(() -> new BadRequestException("That payment type doesn't exist."));
        Address address = user.getAddresses().stream().filter(address1 -> address1.getId() == dto.getAddressId()).findAny().orElseThrow(() ->
                new BadRequestException("That address doesn't exist in your addresses."));
        Set<CartContent> products = getProductsFromCart(user);
        Order order = createNewOrder(products, user, paymentType, address);
        CreatedOrderDTO dtoOrder = new CreatedOrderDTO();
        dtoOrder.setName("Bill" + order.getId());
        dtoOrder.setCreatedAt(order.getCreatedAt());
        dtoOrder.setAddress(mapper.map(order.getAddress(), OrderAddressDTO.class));
        dtoOrder.setProducts(order.getProductsInOrder().stream()
                .map(orderContent -> {
                    OrderContentDTO orderDto = new OrderContentDTO();
                    orderDto.setName(orderContent.getProduct().getName());
                    orderDto.setQuantity(orderContent.getQuantity());
                    return orderDto;
                })
                .collect(Collectors.toSet()));
        dtoOrder.setTotalPrice(order.getPrice());
        return dtoOrder;
    }

    private Set<CartContent> getProductsFromCart(User user) {
        Set<CartContent> cartContents = cartContentRepository.findByUserId(user.getId());
        if (cartContents.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }
        Set<CartContent> productsInCart = user.getProductsInCart();
        checkEachProductQuantityInDB(productsInCart);
        cartContentRepository.deleteAll(productsInCart);
        return productsInCart;
    }

    private void checkEachProductQuantityInDB(Set<CartContent> productsInCart) {
        productsInCart.forEach(cartContent -> {
            if (cartContent.getProduct().getQuantity() < cartContent.getQuantity()) {
                throw new BadRequestException("Not enough quantity of " + cartContent.getProduct().getName());
            }
        });
    }

    private Order createNewOrder(Set<CartContent> products, User user, PaymentType paymentType, Address address) {
        Order order = new Order();
        order.setPrice(calculatePrice(products));
        order.setUser(user);
        order.setPaymentType(paymentType);
        order.setAddress(address);
        Set<OrderContent> orderContents = new HashSet<>();
        Set<Product> editedQuantityProducts = new HashSet<>();
        order.setStatus(orderStatusRepository.findById(1).orElseThrow(() -> new BadRequestException("This should never happen.")));
        orderRepository.save(order);
        order.setId(order.getId());
        products.forEach(cartContent -> {
            OrderContentKey key = new OrderContentKey();
            OrderContent orderContent = new OrderContent();
            orderContent.setOrder(order);
            orderContent.setProduct(cartContent.getProduct());
            orderContent.setQuantity(cartContent.getQuantity());
            key.setProductId(cartContent.getProduct().getId());
            key.setOrderId(order.getId());
            orderContent.setId(key);
            Product product = productRepository.findById(orderContent.getProduct().getId()).orElseThrow(
                    () -> new BadRequestException("Product not found." + orderContent.getProduct().getName()));
            product.setQuantity(product.getQuantity() - orderContent.getQuantity());
            editedQuantityProducts.add(product);
            orderContents.add(orderContent);
        });
        order.setProductsInOrder(orderContents);
        productRepository.saveAll(editedQuantityProducts);
        orderContentRepository.saveAll(orderContents);
        return order;
    }
}
