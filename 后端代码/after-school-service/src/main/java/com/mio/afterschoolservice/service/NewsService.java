package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.News;
import com.mio.afterschoolservice.pojo.PageBean;

public interface NewsService {
    News findByTitle(String title);

    void addNews(News news);

    void deleteNews(Integer id);

    void updateNews(News news);

    News findOther(Integer id, String title);

    PageBean<News> getList(Integer pageNum, Integer pageSize);

    News getNews(Integer id);
}
