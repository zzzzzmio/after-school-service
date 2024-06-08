package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Imsingle;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ImsingleMapper {
    void add(Imsingle imsingle);

    List<Imsingle> getList(String fromuser, String touser);

    void insert(Imsingle imsingle);

    List<Imsingle> getUnreaded(String touser);

    void read(@Param(value = "fromuser") String fromuser,@Param(value = "touser") String touser);
}
