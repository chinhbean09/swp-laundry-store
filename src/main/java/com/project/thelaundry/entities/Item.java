package com.project.thelaundry.entities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;
    @Column(name = "total_price")
    private float total;


    @Column(name = "weight")
    private float weight;

    @ManyToOne
    @JoinColumn (name = "service_id")
    private Laundry laundryService;

    @ManyToOne
    @JoinColumn (name = "order_id", nullable = false)
    private Order order;

}
