package com.db.repository;

import com.db.model.Favorite;
import com.db.model.ProductFavorite;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {
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
                    "productName: { $arrayElemAt: [ '$shoes.name', 0 ] }," +
                    "productPrice: { $arrayElemAt: [ '$shoes.price', 0 ] }," +
                    "productImage: { $arrayElemAt: [ '$shoes.image', 0 ] }," +
                    "} }"
    })
    List<ProductFavorite> findAllByUserId(String userId);
    void deleteAllByUserIdAndProductAndProductId(String userId, String product, String productId);
}
