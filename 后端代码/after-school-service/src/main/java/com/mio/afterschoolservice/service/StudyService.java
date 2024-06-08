package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Study;

import java.util.List;

public interface StudyService {
    Study find(Study study);

    void addStudy(Study study);

    List<Study> getList(Integer stuId);
}
