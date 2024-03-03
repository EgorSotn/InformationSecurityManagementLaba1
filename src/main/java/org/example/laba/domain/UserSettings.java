package org.example.laba.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_settings")
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "block")
    boolean block;
    @Column(name = "match_password")
    String matchPassword;
    @Column(name = "restriction_password")
    boolean restrictionPassword;
}
