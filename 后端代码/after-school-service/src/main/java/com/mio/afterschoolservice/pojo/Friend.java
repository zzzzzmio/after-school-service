package com.mio.afterschoolservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Friend {
    private Integer id;
    private Integer ownerId;
    private String ownerName;
    private String ownerAvatar;
    private Integer fid;
    private String fName;
    private String favatar;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String createTime;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private String updateTime;
}
