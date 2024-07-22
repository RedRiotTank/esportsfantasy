package htt.esportsfantasybe.model;

import htt.esportsfantasybe.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * User model class. Links to the user table in the database.
 * @author Alberto Plaza Montes
 */
@Setter
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    private UUID uuid;

    private String mail;
    private String pass;
    private String username;
    private boolean admin;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "userxleague",
            joinColumns = @JoinColumn(name = "useruuid"),
            inverseJoinColumns = @JoinColumn(name = "leagueuuid"))
    private Set<League> leagues;

    /**
     * Constructor for the User class.
     */
    public User() {

    }
    /**
     * Constructor for the User class with a UserDTO object.
     * @param newUserDTO UserDTO object with the information of the new user.
     */
    public User(UserDTO newUserDTO){
        this.uuid = newUserDTO.getUuid();
        this.mail = newUserDTO.getMail();
        this.pass = newUserDTO.getPass();
        this.username = newUserDTO.getUsername();
        this.admin = newUserDTO.isAdmin();

    }


    // TODO: check Userdetails overrides.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}
