package com.project.SWP391.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_times")
public class Time implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "status")
    private int status;
    @Column(name = "date_range")
    @Nationalized
    private String dateRange;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn (name = "store_id")
    private Store store;
    @ManyToOne
    @JoinColumn (name = "category_id")
    private TimeCategory timeCategory;
    @OneToMany(mappedBy = "time")
    private Set<Order> orders;



}

