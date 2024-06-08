package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsMapper {
    //根据标题查找新闻公告
    News findByTitle(String title);

    //添加新闻公告
    void addNews(News news);

    //删除新闻公告
    void deleteNews(Integer id);

    //根据题目查找除自己以外的新闻
    News findOther(@Param(value = "id") Integer id,@Param(value = "title") String title);

    //修改新闻公告
    void updateNews(News news);

    //获取新闻公告列表
    List<News> getList();

    //获取新闻公告详情
    News getNews(Integer id);
}
