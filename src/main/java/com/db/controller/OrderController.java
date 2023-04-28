package com.db.controller;

import com.db.model.Order;
import com.db.model.ProductOrder;
import com.db.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductOrder>> getAllOrdersByUser(@PathVariable String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(orderRepository.findAllByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(orderRepository.insert(order));
    }

    @PutMapping("/incrementAmount")
    public ResponseEntity<?> incrementOrderAmount(@RequestBody Order order) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        orderRepository.findAndIncrementProductAmountByUserIdAndProductAndProductId(order.getUserId(), order.getProduct(), order.getProductId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/decrementAmount")
    public ResponseEntity<?> decrementOrderAmount(@RequestBody Order order) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        orderRepository.findAndDecrementProductAmountByUserIdAndProductAndProductId(order.getUserId(), order.getProduct(), order.getProductId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestBody Order order) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        orderRepository.deleteAllByUserIdAndProductAndProductId(order.getUserId(), order.getProduct(), order.getProductId());
        return ResponseEntity.ok().build();
    }
}
