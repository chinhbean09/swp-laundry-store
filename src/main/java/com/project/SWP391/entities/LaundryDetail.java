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
@Table(name = "service_details")
public class LaundryDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "from_weight")
    private Float from;
    @Column(name = "to_weight")
    private Float to;
    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private Float price;

    @ManyToOne
    @JoinColumn (name = "service_id")
    private Laundry laundryService ;






}
