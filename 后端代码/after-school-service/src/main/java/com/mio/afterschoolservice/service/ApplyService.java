package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Apply;

import java.util.List;

public interface ApplyService {
    void addApply(Apply apply);

    void agree(Integer id);

    void refuse(Integer id);

    List<Apply> getList(Integer toUser);

    Apply find(Integer fromUser, Integer toUser);
}
