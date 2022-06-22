package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Role(){};
}
