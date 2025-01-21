package com.practice.project1.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGmail {

    @Id
    private String id;

    private String given_name;

    private String family_name;

    private String name;

    private String email;

    private String picture;
}
