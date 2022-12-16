package mindata.superhero.api.models;
import javax.persistence.*;

@Entity
public class SuperHero {

    @Id
    @Column(name="id")
    String id;

    @Column(name="name")
    String name;

}
