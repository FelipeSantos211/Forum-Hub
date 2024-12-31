package com.felipesantos.forumhub.dto;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class RegisterUserDTO {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // Getters e Setters omitidos por brevidade

    public RegisterUserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
