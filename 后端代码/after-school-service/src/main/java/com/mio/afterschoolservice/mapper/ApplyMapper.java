package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Apply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyMapper {
    void addApply(Apply apply);

    Apply getDetail(Integer id);

    void agree(Integer id);

    List<Apply> getList(Integer toUser);

    Apply find(@Param(value = "fromUser") Integer fromUser,@Param(value = "toUser") Integer toUser);
}
