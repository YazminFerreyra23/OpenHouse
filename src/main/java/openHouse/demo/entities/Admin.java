package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "admin")
public class Admin extends User {

    public Admin() {
        super();
    }
}
