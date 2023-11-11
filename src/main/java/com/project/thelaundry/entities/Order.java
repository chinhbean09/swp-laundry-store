package com.project.thelaundry.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.Date;
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
    @CreationTimestamp
    @Column(name = "order_date")
    private String orderDate;
    @Column(name = "note_text")
    @Nationalized
    private String noteText;
    @Column(name = "total_price")
    private float total;
    @Column(name = "status")
    private int status;
    @Column(name = "isReport")
    private int isReport;
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
    private Set<Shipment> shipments;


}
