package com.saparov.spring.controller;

import com.saparov.spring.entity.Order;
import com.saparov.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order createOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status){
        Order updateOrder = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(updateOrder,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
         orderService.deleteOrder(id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
