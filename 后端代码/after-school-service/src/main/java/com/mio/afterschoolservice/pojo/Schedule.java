package com.mio.afterschoolservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Schedule {
    private Integer id;
    private Integer stuId;
    private Integer cId;
    private String cName;
    private String cTime;
    private String tName;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String createTime;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String updateTime;
}
