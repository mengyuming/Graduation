package com.graduation.bean;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("site")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Site {
    private Integer id;
    private String cno;
    private String name;
    private String city;
    private String longitude;
    private String latitude;
    private String date;
    private String hour;
    private String value;
    private String type;
}
