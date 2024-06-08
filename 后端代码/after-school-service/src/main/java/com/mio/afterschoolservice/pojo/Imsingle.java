package com.mio.afterschoolservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Imsingle {
    private Integer id;
    private String content;
    private String fromuser;
    private String fromavatar;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String time;
    private String type;
    private String touser;
    private String toavatar;
    private Integer readed;
    private Integer count;
}
