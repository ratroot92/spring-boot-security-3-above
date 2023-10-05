package com.example.configvaultserver.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "jpa_student")
@Data
public class Student {

    @Id
    private int studentId;
    private String studentName;
    private String about;

    @OneToOne(mappedBy = "student")
    private Laptop laptop;

}
