package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Study;

import java.util.List;

public interface StudyMapper {
    Study find(Study study);

    void addStudy(Study study);

    List<Study> getList(Integer stuId);
}
