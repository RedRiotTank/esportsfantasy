package htt.esportsfantasybe.model;

import htt.esportsfantasybe.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;


@Setter
@Getter
@Entity
/**
 * Breve descripción de la clase.
 *
 * Descripción más detallada de la clase, su propósito y funcionalidad.
 * Puedes incluir detalles como los campos de la clase, los métodos, etc.
 */
public class User implements UserDetails {

    /**
     * Descripción del campo.
     */
    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    private UUID uuid;

    private String mail;
    private String pass;
    private String username;
    private boolean admin;

    public User() {

    }
    public User(UserDTO newUserDTO){
        this.mail = newUserDTO.getMail();
        this.pass = newUserDTO.getPass();
        this.username = newUserDTO.getUsername();
        this.admin = newUserDTO.isAdmin();

    }

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
