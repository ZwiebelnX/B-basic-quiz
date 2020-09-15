package com.thoughtworks.gtb.csc.basicquiz.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    private int id;

    private int userId;

    @NotNull(message = "描述不能为空")
    @Length(min = 1, max = 2048, message = "描述需在1 - 2048个字符之间")
    private String description;

    @NotEmpty(message = "年份不能为空")
    @Pattern(regexp = "^(19\\d\\d|20\\d\\d|2100)$", message = "年份不符合规范或未处于1900 - 2100年之间")
    private String year;

    @NotNull(message = "标题不能为空")
    @Length(min = 1, max = 128, message = "标题需在1 - 128个字符之间")
    private String title;
}
