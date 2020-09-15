package com.thoughtworks.gtb.csc.basicquiz.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private int id;

    @NotEmpty(message = "名称不可为空")
    @Length(min = 1, max = 64, message = "用户名长度需要在1 - 64之间")
    private String name;

    @NotNull(message = "年龄不可为空")
    @Range(min = 17, max = 99, message = "年龄需要在17 - 99岁之间")
    private Integer age;

    @NotEmpty(message = "头像url不可为空")
    @Length(min = 1, max = 256, message = "头像url字符串长度需要在1 - 256之间")
    private String avatar;

    @Length(max = 512, message = "介绍最大长度为512")
    private String description;

    @JsonProperty("education")
    private List<Education> educationList;
}
