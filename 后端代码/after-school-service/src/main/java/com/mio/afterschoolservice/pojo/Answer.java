package com.mio.afterschoolservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Answer {
    private Integer id;
    private Integer userId;
    private String username;
    private Integer pId;
    private String context;
    private Integer good;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String createTime;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String updateTime;
}
