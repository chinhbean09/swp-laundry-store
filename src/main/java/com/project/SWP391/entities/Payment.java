package com.project.SWP391.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "create_date")

    private String createDate;

    @Column(name = "payment_method")
    private String method;


    @ManyToOne
    @JoinColumn (name = "order_id", nullable = false)
    private Order order;
}
