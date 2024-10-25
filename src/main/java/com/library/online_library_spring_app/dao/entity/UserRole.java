package com.library.online_library_spring_app.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Getter
@Setter

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_role", schema = "online_library_schema")
public class UserRole {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id"  ,referencedColumnName = "id")
        Users user;
        @JoinColumn(name = "role_id"  ,referencedColumnName = "id")
        @ManyToOne(fetch = FetchType.EAGER)
        Role role;
        @Column(name ="is_active")
        Boolean isActive;
        @CreationTimestamp
        @Column(name = "created_at")
        Timestamp createdAt;
        @Column(name = "updated_at")
        Timestamp updatedAt;

        @Override
        public String toString() {
                return "UserRole{" +
                        "id=" + id +
                        ", user=" + user +
                        ", role=" + role +
                        ", isActive=" + isActive +
                        ", createdAt=" + createdAt +
                        ", updatedAt=" + updatedAt +
                        '}';
        }
}
