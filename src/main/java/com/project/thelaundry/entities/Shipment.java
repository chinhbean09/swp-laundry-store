package com.project.thelaundry.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "shipments")
public class Shipment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "pick_up")
    private Date pickUptime;

    @ManyToOne
    @JoinColumn (name = "staff_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "order_id", nullable = false)
    private Order order;
}
