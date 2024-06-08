package com.mio.afterschoolservice.service.impl;

import com.mio.afterschoolservice.mapper.HomeMapper;
import com.mio.afterschoolservice.pojo.Home;
import com.mio.afterschoolservice.pojo.News;
import com.mio.afterschoolservice.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;
    @Override
    public int getHeader() {
        return homeMapper.getHeader();
    }

    @Override
    public void addHome(Home home) {
        homeMapper.addHome(home);
    }

    @Override
    public int getFooter() {
        return homeMapper.getFooter();
    }

    @Override
    public void deleteHome(Integer id) {
        homeMapper.deleteHome(id);
    }

    @Override
    public List<Home> getHeaderList() {
        return homeMapper.getHeaderList();
    }

    @Override
    public List<Home> getFooterList() {
        return homeMapper.getFooterList();
    }

    @Override
    public List<News> getNews() {
        return homeMapper.getNews();
    }

    @Override
    public List<Home> getList() {
        return homeMapper.getList();
    }
}
