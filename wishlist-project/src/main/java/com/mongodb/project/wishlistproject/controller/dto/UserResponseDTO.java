package com.mongodb.project.wishlistproject.controller.dto;

public class UserResponseDTO {

    private String nome;

    public UserResponseDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
