package com.mjc.school.service.dto;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthorDtoRequest {

    private Long id;
    private String name;

    public AuthorDtoRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorDtoRequest(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDtoRequest authorDtoRequest = (AuthorDtoRequest) o;
        return Objects.equals(id, authorDtoRequest.id) && Objects.equals(name, authorDtoRequest.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AuthorDtoRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
