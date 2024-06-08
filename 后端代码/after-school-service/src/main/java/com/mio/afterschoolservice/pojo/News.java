package com.mio.afterschoolservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class News {
    private Integer id;
    private String title;
    private String context;
    private String coverImg;
    private Integer userId;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String createTime;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String updateTime;
}
