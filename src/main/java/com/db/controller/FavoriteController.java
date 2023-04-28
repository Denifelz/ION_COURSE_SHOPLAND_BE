package com.db.controller;

import com.db.model.Favorite;
import com.db.model.ProductFavorite;
import com.db.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductFavorite>> getAllFavoritesByUser(@PathVariable String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(favoriteRepository.findAllByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Favorite> addFavorite(@RequestBody Favorite favorite) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(favoriteRepository.insert(favorite));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFavorite(@RequestBody Favorite favorite) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        favoriteRepository.deleteAllByUserIdAndProductAndProductId(favorite.getUserId(), favorite.getProduct(), favorite.getProductId());
        return ResponseEntity.ok().build();
    }
}
