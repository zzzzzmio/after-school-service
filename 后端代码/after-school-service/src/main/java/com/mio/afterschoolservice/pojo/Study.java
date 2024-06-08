package com.mio.afterschoolservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Study {
    private Integer id;
    private Integer stuId;
    private Integer year;
    private Integer month;
    private Integer date;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String time;
    private String img;
}
