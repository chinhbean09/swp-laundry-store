package com.project.thelaundry.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.Set;
@Getter
@Setter

@Entity
@Table(name = "time_categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class TimeCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Nationalized
    private String name;

    @OneToMany(mappedBy = "timeCategory")
    private Set<com.project.thelaundry.entities.Time> times ;
}
