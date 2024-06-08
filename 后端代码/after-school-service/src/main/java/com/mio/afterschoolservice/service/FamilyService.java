package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Family;

import java.util.List;

public interface FamilyService {
    Family find(Family family);

    void apply(Family family);

    void deleteFamily(Integer id);

    void agree(Integer id);

    List<Family> getApplyList(Integer id);

    List<Family> getFamilyList(Integer id);

    List<Family> getParentList(Integer id);
}
