package com.practice.project1.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
public class Email {

    private String[] to;

    private String subject;

    private String body;

}
