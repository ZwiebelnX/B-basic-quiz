package com.thoughtworks.gtb.csc.basicquiz.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty(message = "名称不可为空")
    @Length(min = 1, max = 64, message = "用户名长度需要在1 - 64之间")
    @Column(length = 64, nullable = false)
    private String name;

    @NotNull(message = "年龄不可为空")
    @Range(min = 17, max = 99, message = "年龄需要在17 - 99岁之间")
    @Column(nullable = false)
    private Integer age;

    @NotEmpty(message = "头像url不可为空")
    @Length(min = 1, max = 256, message = "头像url字符串长度需要在1 - 256之间")
    @Column(length = 256, nullable = false)
    private String avatar;

    @Length(max = 512, message = "介绍最大长度为512")
    @Column(length = 512)
    private String description;

    @JsonProperty("education")
    @OneToMany(mappedBy = "user")
    private List<Education> educationList;
}
