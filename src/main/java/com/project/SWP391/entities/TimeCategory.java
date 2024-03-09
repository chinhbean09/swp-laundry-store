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
@Table(name = "time_categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class TimeCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Nationalized
    private String name;


    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "timeCategory")
    private Set<Time> times ;
}
