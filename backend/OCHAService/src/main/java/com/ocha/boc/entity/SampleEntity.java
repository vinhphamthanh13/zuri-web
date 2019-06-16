package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;

@Getter
@Setter
public class SampleEntity {
    @Email
    private String email;
}
