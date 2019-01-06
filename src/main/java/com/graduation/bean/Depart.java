package com.graduation.bean;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("depart")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Depart implements Serializable{
    private Integer id;
    private String name;
    private String school;
    private String description;
}
