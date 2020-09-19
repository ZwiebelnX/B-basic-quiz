package com.thoughtworks.gtb.csc.basicquiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Education {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JsonIgnore
    private User user;
    
    @NotNull(message = "描述不能为空")
    @Length(min = 1, max = 2048, message = "描述需在1 - 2048个字符之间")
    @Column(length = 2048, nullable = false)
    private String description;

    @NotNull(message = "年份不能为空")
    @Range(min = 1900, max = 2100, message = "年份需要在1900 - 2100年之间")
    @Column(nullable = false)
    private long year;

    @NotNull(message = "标题不能为空")
    @Length(min = 1, max = 128, message = "标题需在1 - 128个字符之间")
    @Column(length = 128, nullable = false)
    private String title;
}
