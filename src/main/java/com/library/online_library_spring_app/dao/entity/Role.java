package com.library.online_library_spring_app.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.online_library_spring_app.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles", schema = "online_library_schema")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    RoleName roleName;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Users> users;
    @Column(name = "is_active")
    Boolean isActive=true;
    @CreationTimestamp
    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at")
    Timestamp updatedAt;

    @Override
    public String toString() {
        return "Role{" +
                "roleName=" + roleName +
                ", isActive=" + isActive +
                '}';
    }
}
