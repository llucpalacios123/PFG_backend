package com.lluc.backend.shopapp.shopapp.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_user_email", columnNames = "email"),
        @UniqueConstraint(name = "UK_user_username", columnNames = "username")
    }
)
public class User {
    
    @Version
    @Getter @Setter private Integer version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;
   
    @Column(unique = true)
    @NotBlank
    @Email
    @Getter @Setter private String email;

    @NotBlank
    @Getter @Setter private String password;

    @NotBlank
    @Getter @Setter private String firstName;

    @NotBlank
    @Getter @Setter private String lastName;

    @Column(unique = true)
    @NotBlank
    @Size(min = 4, max = 20)
    @Getter @Setter private String username;

    @ManyToOne
    @Getter @Setter private Company empresa;

    @OneToOne(mappedBy = "administrator")
    @Getter @Setter private Company companyAdmin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"),
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    @Getter @Setter private List<Role> roles;

    @Getter @Setter private Boolean verified = false;

    @CreationTimestamp
    @Column(updatable = false)
    @Getter @Setter private LocalDateTime fechaAlta; // Fecha de alta
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<UserAddress> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<Order> orders; // Lista de pedidos asociados al usuario
}