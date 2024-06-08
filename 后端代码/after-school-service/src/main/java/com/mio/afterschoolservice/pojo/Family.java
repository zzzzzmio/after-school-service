package com.mio.afterschoolservice.pojo;

import lombok.Data;

@Data
public class Family {
    private Integer id;
    private Integer parentId;
    private String parentAvatar;
    private String parentName;
    private Integer stuId;
    private String stuAvatar;
    private String stuName;
    private Integer state;
}
