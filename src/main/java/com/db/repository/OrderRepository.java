package com.db.repository;

import com.db.model.Order;
import com.db.model.ProductOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    @Aggregation(pipeline = {
            "{ $addFields: {" +
                    "'shoeId': { $toObjectId: '$productId' }" +
                    "} }",
            "{ $lookup: {" +
                    "from: 'shoes'," +
                    "localField: 'shoeId'," +
                    "foreignField: '_id'," +
                    "as: 'shoes'," +
                    "} }",
            "{ $match: { $and: [ { 'product' : 'shoe' }, { 'userId' : ?0 }, { 'shoes' : { $size : 1 } } ] } }",
            "{ $project: {" +
                    "userId: 1," +
                    "product: 1," +
                    "productId: 1," +
                    "productAmount: 1," +
                    "productName: { $arrayElemAt: [ '$shoes.name', 0 ] }," +
                    "productPrice: { $arrayElemAt: [ '$shoes.price', 0 ] }," +
                    "productImage: { $arrayElemAt: [ '$shoes.image', 0 ] }," +
                    "} }"
    })
    List<ProductOrder> findAllByUserId(String userId);

    @Update("{ '$inc' : { 'productAmount' : 1 } }")
    void findAndIncrementProductAmountByUserIdAndProductAndProductId(String userId, String product, String productId);

    @Update("{ '$inc' : { 'productAmount' : -1 } }")
    void findAndDecrementProductAmountByUserIdAndProductAndProductId(String userId, String product, String productId);

    void deleteAllByUserIdAndProductAndProductId(String userId, String product, String productId);
}
