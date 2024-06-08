package com.mio.afterschoolservice.service.impl;

import com.mio.afterschoolservice.mapper.StudyMapper;
import com.mio.afterschoolservice.pojo.Study;
import com.mio.afterschoolservice.service.StudyService;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudyServiceImpl implements StudyService {
    @Autowired
    private StudyMapper studyMapper;

    @Override
    public Study find(Study study) {
        Map<String, Object> map= ThreadLocalUtil.get();
        Integer stuId= (Integer) map.get("id");
        study.setStuId(stuId);
        return studyMapper.find(study);
    }

    @Override
    public void addStudy(Study study) {
        Map<String, Object> map= ThreadLocalUtil.get();
        Integer stuId= (Integer) map.get("id");
        study.setStuId(stuId);
        studyMapper.addStudy(study);
    }

    @Override
    public List<Study> getList(Integer stuId) {
        return studyMapper.getList(stuId);
    }
}
