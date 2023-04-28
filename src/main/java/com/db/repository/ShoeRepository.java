package com.db.repository;

import com.db.model.Shoe;
import com.db.model.ProductShoe;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShoeRepository extends MongoRepository<Shoe, String> {
    @Aggregation(pipeline = {
            "{ $project: {" +
                    "name: 1," +
                    "price: 1," +
                    "color: 1," +
                    "season: 1," +
                    "type: 1," +
                    "drawing: 1," +
                    "composition: 1," +
                    "image: 1," +
                    "isFavorite: { $literal: false }," +
                    "orderAmount: null" +
            "} }"
    })
    List<ProductShoe> findAllAsProduct();

    @Aggregation(pipeline = {
            "{ $addFields: {" +
                    "'productId': { $toString: '$_id' }" +
            "} }",
            "{ $lookup: {" +
                    "from: 'favorites'," +
                    "localField: 'productId'," +
                    "foreignField: 'productId'," +
                    "as: 'favorites'," +
                    "pipeline: [" +
                    "{ $match: { $and: [ { 'product' : 'shoe' }, { 'userId' : ?0 } ] } }" +
                    "]" +
            "} }",
            "{ $lookup: {" +
                    "from: 'orders'," +
                    "localField: 'productId'," +
                    "foreignField: 'productId'," +
                    "as: 'orders'," +
                    "pipeline: [" +
                    "{ $match: { $and: [ { 'product' : 'shoe' }, { 'userId' : ?0 } ] } }" +
                    "]" +
            "} }",
            "{ $project: {" +
                    "name: 1," +
                    "price: 1," +
                    "color: 1," +
                    "season: 1," +
                    "type: 1," +
                    "drawing: 1," +
                    "composition: 1," +
                    "image: 1," +
                    "isFavorite: { $cond: [ { $eq: [ { $arrayElemAt: [ '$favorites.product', 0 ] }, 'shoe' ] }, true, false ] }," +
                    "orderAmount: { $cond: [ { $eq: [ { $size: '$orders' } , 1 ] }, { $arrayElemAt: [ '$orders.productAmount', 0 ] }, null ] }" +
            "} }"
    })
    List<ProductShoe> findAllByUser(String userId);
}
