package com.project.thelaundry.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class User implements UserDetails {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "fullname", nullable = false)
    @Nationalized
    private String fullName;

    @Column(name = "address")
    @Nationalized
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "image", columnDefinition = "TEXT")
    private String image;
    @Column(name = "status")
    private int status;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return role.getAuthorities();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }


//    @OneToMany(mappedBy = "user")
//    private Set<Order> orders;
//
//    @Getter
//    @OneToMany(mappedBy = "user")
//    private Set<Feedback> feedbacks;
//
//    @OneToMany(mappedBy = "user")
//    private Set<Shipment> shipments;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return this.status == 1;
    }
}







