package com.thoughtworks.gtb.csc.basicquiz.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessError {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp timestamp;

    private String status;

    private String error;

    private String message;
}
