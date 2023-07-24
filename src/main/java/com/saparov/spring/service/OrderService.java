package com.saparov.spring.service;

import com.saparov.spring.entity.Order;
import com.saparov.spring.entity.Product;
import com.saparov.spring.exception.NotFoundException;
import com.saparov.spring.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public Map<String,Object> getAllOrders(Pageable pageable){

        Map<String, Object> response = new HashMap<>();

        Page<Order> orderPage =  orderRepository.findAll(pageable);
        response.put("products",   orderPage.getContent());
        response.put("currentPage", orderPage.getNumber());
        response.put("totalItems", orderPage.getTotalElements());
        response.put("totalPages", orderPage.getTotalPages());

        return response;

    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status){
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id){
        orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));

        orderRepository.deleteById(id);
    }
}
