package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Imsingle;

import java.util.List;

public interface ImsingleService {
    void add(Imsingle imsingle);

    List<Imsingle> getList(String fromuser, String touser);

    void insert(Imsingle imsingle);

    List<Imsingle> getUnreaded(String touser);

    void read(String fromuser, String touser);
}
