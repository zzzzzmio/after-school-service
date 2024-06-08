package com.mio.afterschoolservice.service.impl;

import com.mio.afterschoolservice.mapper.ImsingleMapper;
import com.mio.afterschoolservice.pojo.Imsingle;
import com.mio.afterschoolservice.service.ImsingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImsingleServiceImpl implements ImsingleService {

    @Autowired
    private ImsingleMapper imsingleMapper;

    @Override
    public void add(Imsingle imsingle) {
        imsingleMapper.add(imsingle);
    }

    @Override
    public List<Imsingle> getList(String fromuser, String touser) {
        return imsingleMapper.getList(fromuser,touser);
    }

    @Override
    public void insert(Imsingle imsingle) {
        imsingle.setType("text");
        imsingleMapper.insert(imsingle);
    }

    @Override
    public List<Imsingle> getUnreaded(String touser) {
        return imsingleMapper.getUnreaded(touser);
    }

    @Override
    public void read(String fromuser, String touser) {
        imsingleMapper.read(fromuser,touser);
    }
}
