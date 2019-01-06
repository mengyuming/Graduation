package com.graduation.bean;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("professional")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Professional implements Serializable{
    private Integer id;
    private String name;
    private String depart;
    private String description;
}
