package com.project.thelaundry.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="stores")
public class Store {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name")
    @Nationalized
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    @Nationalized
    private String address;

    @Column(name = "district")
    private String district;

    @Column(name="status")
    private int status;

    @OneToMany(mappedBy = "store")
    private Set<Time> times;

    @OneToMany(mappedBy = "store")
    private Set<Order> orders;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "store")
    private List<com.project.thelaundry.entities.Laundry> laundryServices;



}
