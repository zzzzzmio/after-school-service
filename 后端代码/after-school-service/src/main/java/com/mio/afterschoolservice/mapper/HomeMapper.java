package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Home;
import com.mio.afterschoolservice.pojo.News;

import java.util.List;

public interface HomeMapper {
    //获取顶部新闻数量
    int getHeader();

    //添加主页元素
    void addHome(Home home);

    //获取底部公告数量
    int getFooter();

    //删除
    void deleteHome(Integer id);

    //获取顶部主页元素列表
    List<Home> getHeaderList();

    //获取底部主页元素列表
    List<Home> getFooterList();

    //获取未加入主页元素的新闻公告
    List<News> getNews();

    //获取主页元素列表
    List<Home> getList();
}
