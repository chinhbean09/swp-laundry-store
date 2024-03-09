package com.project.SWP391.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clothes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})}
)

public class Cloth implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Nationalized
    private String name;


    @Column(name = "status")
    private Integer status;


    @OneToMany(mappedBy = "cloth")
    private List<Laundry> laundryServices;
}
