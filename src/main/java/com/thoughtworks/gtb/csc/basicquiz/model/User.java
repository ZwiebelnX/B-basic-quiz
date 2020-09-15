package com.thoughtworks.gtb.csc.basicquiz.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

    private int id;

    private String name;

    private int age;

    private String avatar;

    private String description;

    @JsonProperty("education")
    private List<Education> educationList;
}
