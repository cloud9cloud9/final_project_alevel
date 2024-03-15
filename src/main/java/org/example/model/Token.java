package org.example.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "token")
public class Token extends BaseEntity {

    @Column(name = "token_name", unique = true)
    private String token;

    @Column(name = "is_expired")
    private boolean isExpired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}
