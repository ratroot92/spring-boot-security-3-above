package com.example.configvaultserver.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Difference Between @Size, @Length, and @Column(length=value)
 * https://www.baeldung.com/jpa-size-length-column-differences
 *
 * Difference Between @NotNull, @NotEmpty, and @NotBlank Constraints in Bean
 * Validation
 * https://www.baeldung.com/java-bean-validation-not-null-empty-blank
 */

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID id;

    @Column(name = "name", updatable = true, nullable = false, length = 255)
    @Size(max = 100, message = "Name must be at most 100 characters")

    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "email", updatable = true, nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "password", updatable = true, nullable = false, length = 255)
    private String password;

    @Column(name = "phone", updatable = true, nullable = false)
    private String phone;

    @Column(name = "status", updatable = true, nullable = false)
    private Integer status = 1;

    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;

    }

}
