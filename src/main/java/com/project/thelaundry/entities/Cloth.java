package com.project.thelaundry.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import java.util.List;
import java.io.Serializable;


@Getter
@Setter

public class Cloth implements Serializable{
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