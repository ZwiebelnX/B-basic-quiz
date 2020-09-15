package com.thoughtworks.gtb.csc.basicquiz.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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
public class Education {

    private long id;

    private long userId;

    @NotNull(message = "描述不能为空")
    @Length(min = 1, max = 2048, message = "描述需在1 - 2048个字符之间")
    private String description;

    @NotNull(message = "年份不能为空")
    @Range(min = 1900, max = 2100, message = "年份需要在1900 - 2100年之间")
    private long year;

    @NotNull(message = "标题不能为空")
    @Length(min = 1, max = 128, message = "标题需在1 - 128个字符之间")
    private String title;
}
