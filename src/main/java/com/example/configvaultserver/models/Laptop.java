package com.example.configvaultserver.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "laptop")
@Data
public class Laptop {

    @Id
    private int laptopId;
    private String modelNumber;
    private String brand;

    @OneToOne()
    @JoinColumn(name = "student_id")
    private Student student;

}
