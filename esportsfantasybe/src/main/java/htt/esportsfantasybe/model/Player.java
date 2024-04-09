package htt.esportsfantasybe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    private UUID uuid;

    private String username;
    private String fullname;
    private String role;
    private float value;

}
