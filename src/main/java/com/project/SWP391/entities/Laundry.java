package com.project.SWP391.entities;

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
@Table(name = "services")
public class Laundry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Nationalized
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String imageBanner;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "isDeleted")
    private Integer isDeleted;

    @Column(name = "isStandard")
    private Boolean isStandard;

    @OneToMany(mappedBy = "laundryService")
    private Set<LaundryDetail> details;

    @ManyToMany
    List<Material> materials;

    @OneToMany (mappedBy = "laundryService")
    private Set<Item> items ;

    @OneToMany (mappedBy = "laundryService")
    private Set<Item> feedbacks ;

    @ManyToOne
    @JoinColumn (name = "cloth_id")
    private Cloth cloth;
}
