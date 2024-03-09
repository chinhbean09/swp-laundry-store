package com.project.SWP391.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "order_date")
    private Long orderDate;

    @Column(name = "total_price")
    private float total;
    @Column(name = "status")
    private int status;
    @Column(name = "isReport")
    private int isReport;

    @Column(name = "isPaid")
    private int isPaid;
    @Column(name = "report_content")
    @Nationalized
    private String reportContent;

    @ManyToOne
    @JoinColumn (name = "customer_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn (name = "time_id", nullable = true)
    private Time time;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Item> items;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Payment> payments;



}
