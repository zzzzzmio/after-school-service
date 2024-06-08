package com.mio.afterschoolservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mio.afterschoolservice.mapper.NewsMapper;
import com.mio.afterschoolservice.pojo.News;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.service.NewsService;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public News findByTitle(String title) {
        return newsMapper.findByTitle(title);
    }

    @Override
    public void addNews(News news) {
        Map<String, Object> map= ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        news.setUserId(userId);
        newsMapper.addNews(news);
    }

    @Override
    public void deleteNews(Integer id) {
        newsMapper.deleteNews(id);
    }

    @Override
    public void updateNews(News news) {
        newsMapper.updateNews(news);
    }

    @Override
    public News findOther(Integer id, String title) {
        return newsMapper.findOther(id,title);
    }

    @Override
    public PageBean<News> getList(Integer pageNum, Integer pageSize) {
        PageBean<News> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<News> list=newsMapper.getList();
        PageInfo pageInfo=new PageInfo<>(list);
        pageBean.setItems(pageInfo.getList());
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }

    @Override
    public News getNews(Integer id) {
        return newsMapper.getNews(id);
    }
}
