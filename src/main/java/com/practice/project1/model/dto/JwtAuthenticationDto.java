package com.practice.project1.model.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDto {

    private String token;

    private String refreshToken;

}
