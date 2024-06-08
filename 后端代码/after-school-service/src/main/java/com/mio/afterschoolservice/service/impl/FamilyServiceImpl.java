package com.mio.afterschoolservice.service.impl;

import com.mio.afterschoolservice.mapper.FamilyMapper;
import com.mio.afterschoolservice.pojo.Family;
import com.mio.afterschoolservice.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyServiceImpl implements FamilyService {
    @Autowired
    private FamilyMapper familyMapper;

    @Override
    public Family find(Family family) {
        return familyMapper.find(family);
    }

    @Override
    public void apply(Family family) {
        familyMapper.apply(family);
    }

    @Override
    public void deleteFamily(Integer id) {
        familyMapper.deleteFamily(id);
    }

    @Override
    public void agree(Integer id) {
        familyMapper.agree(id);
    }

    @Override
    public List<Family> getApplyList(Integer id) {
        return familyMapper.getApplyList(id);
    }

    @Override
    public List<Family> getFamilyList(Integer id) {
        return familyMapper.getFamilyList(id);
    }

    @Override
    public List<Family> getParentList(Integer id) {
        return familyMapper.getParentList(id);
    }
}
