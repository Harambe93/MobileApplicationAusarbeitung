package ma.ws2022.barcodeapp;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Dog extends RealmObject {

    @PrimaryKey
    @RealmField("_id")
    private ObjectId id;
    private String name;
    private int age;

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
