package car.rental;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


 
public class CarMongoRepository implements CarRepository {

    private static final String DB_NAME = "car-rental";
    private static final String COLLECTION_NAME = "cars";

    private final MongoCollection<Document> collection;

    public CarMongoRepository(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        this.collection = database.getCollection(COLLECTION_NAME);
    }

   
    public void save(Car car) {
        
        Document doc = toDocument(car);
        if (collection.find(eq("id", car.getId())).first() == null) {
            collection.insertOne(doc);
        } else {
            collection.replaceOne(eq("id", car.getId()), doc);
        }
    }

    
    public Car findById(int id) {
        Document doc = collection.find(eq("id", id)).first();
        if (doc == null) {
            return null;
        }
        return fromDocument(doc);
    }

    
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        for (Document doc : collection.find()) {
            cars.add(fromDocument(doc));
        }
        return cars;
    }

   

    private Document toDocument(Car car) {
        return new Document("id", car.getId())
                .append("make", car.getMake())
                .append("model", car.getModel())
                .append("available", car.isAvailable());
    }

    private Car fromDocument(Document doc) {
        Car car = new Car(doc.getString("make"), doc.getString("model"));
       
        if (!doc.getBoolean("available")) {
            car.setAvailable(false);
        }
        return car;
    }
}