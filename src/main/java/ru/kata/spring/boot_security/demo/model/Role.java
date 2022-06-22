package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class Role implements GrantedAuthority {

    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "roles")
    private Set<User> users;


    public String getAuthority() {
        return getName();
    }
}
