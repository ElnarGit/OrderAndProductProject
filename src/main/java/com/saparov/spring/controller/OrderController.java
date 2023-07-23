package com.saparov.spring.controller;

import com.saparov.spring.entity.Order;
import com.saparov.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ){
        Pageable pageable = PageRequest.of(page, size);


        return new ResponseEntity<>(
                orderService.getAllOrders(pageable),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order createOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status){
        Order updateOrder = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(updateOrder,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
         orderService.deleteOrder(id);

         return new ResponseEntity<>("Order deleted", HttpStatus.OK);
    }
}
