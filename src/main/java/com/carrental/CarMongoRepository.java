package com.carrental;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CarMongoRepository implements CarRepository {

    private MongoCollection<Document> collection;

    public CarMongoRepository(String host, int port) {
        MongoClient client = new MongoClient(
            new ServerAddress(host, port)
        );
        MongoDatabase database = client.getDatabase("carrental");
        collection = database.getCollection("cars");
    }

    @Override
    public void save(Car car) {
        Document doc = new Document()
            .append("plate", car.getPlate())
            .append("make", car.getMake())
            .append("model", car.getModel())
            .append("available", car.isAvailable());
        collection.insertOne(doc);
    }

    @Override
    public List<Car> findAll() {
        return StreamSupport
            .stream(collection.find().spliterator(), false)
            .map(doc -> {
                Car car = new Car(
                    doc.getString("plate"),
                    doc.getString("make"),
                    doc.getString("model")
                );
                Boolean available = doc.getBoolean("available");
                car.setAvailable(available != null && available);
                return car;
            })
            .collect(Collectors.toList());
    }

    @Override
    public void delete(String plate) {
        collection.deleteOne(
            new Document("plate", plate)
        );
    }

    public void dropCollection() {
        collection.drop();
    }

}