package com.mio.afterschoolservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Apply {
    private Integer id;
    private Integer fromUser;
    private String fromName;
    private String fromAvatar;
    private Integer toUser;
    private String toName;
    private String toAvatar;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String createTime;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String updateTime;
}
