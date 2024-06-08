package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Home;
import com.mio.afterschoolservice.pojo.News;

import java.util.List;

public interface HomeService {
    int getHeader();

    void addHome(Home home);

    int getFooter();

    void deleteHome(Integer id);

    List<Home> getHeaderList();

    List<Home> getFooterList();

    List<News> getNews();

    List<Home> getList();
}
