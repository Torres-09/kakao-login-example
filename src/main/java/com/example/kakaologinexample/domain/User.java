package com.example.kakaologinexample.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;

    @Builder
    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
