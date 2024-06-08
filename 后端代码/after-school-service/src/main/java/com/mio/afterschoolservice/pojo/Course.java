package com.mio.afterschoolservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Course {
    private Integer id;
    private String cName;
    private String tName;
    private Integer tId;
    private String content;
    private String cTime;
    private Integer price;
    private String resources;
    private String coverImg;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
}
