package com.db.controller;

import com.db.model.Shoe;
import com.db.model.ProductShoe;
import com.db.repository.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/shoes")
public class ShoeController {
    @Autowired
    private ShoeRepository shoeRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("")
    public ResponseEntity<List<ProductShoe>> getAllShoes() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(shoeRepository.findAllAsProduct());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductShoe>> getAllShoesByUser(@PathVariable String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(shoeRepository.findAllByUser(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Shoe> addShoe(@RequestBody Shoe shoe) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(shoeRepository.insert(shoe));
    }

    @PutMapping("/update")
    public ResponseEntity<Shoe> updateShoe(@RequestBody Shoe shoe) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(shoeRepository.save(shoe));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShoe(@PathVariable String id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        shoeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
