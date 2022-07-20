package com.example.nosql.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Size;

@Document
@Data
public class Role implements GrantedAuthority {

    @Id
    private String id;

    @Size(max = 16)
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
