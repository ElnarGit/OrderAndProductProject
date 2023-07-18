package com.saparov.spring.service;

import com.saparov.spring.entity.Order;
import com.saparov.spring.repository.OrderRepository;
import com.saparov.spring.util.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, String status){
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }
}
