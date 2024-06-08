package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.News;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.NewsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@CrossOrigin
public class NewsController {
    @Autowired
    private NewsService newsService;

    //添加新闻公告
    @PostMapping()
    public Result addNews(@RequestBody News news){
        News n=newsService.findByTitle(news.getTitle());
        if(n!=null){
            return Result.error("已发布该新闻");
        }
        newsService.addNews(news);
        return Result.success();
    }

    //删除新闻公告
    @DeleteMapping()
    public Result deleteNews(@RequestParam Integer id){
        newsService.deleteNews(id);
        return Result.success();
    }

    //修改新闻公告
    @PutMapping()
    public Result updateNews(@RequestBody News news){
        News n=newsService.findOther(news.getId(),news.getTitle());
        if(n!=null){
            return Result.error("已发布该新闻");
        }
        newsService.updateNews(news);
        return Result.success();
    }

    //分页获取新闻公告列表
    @GetMapping("/list")
    public Result<PageBean<News>> getList(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        PageBean<News> pageBean=newsService.getList(pageNum,pageSize);
        return Result.success(pageBean);
    }

    //获取新闻公告详情
    @GetMapping()
    public Result<News> getNews(@RequestParam Integer id){
        News news=newsService.getNews(id);
        return Result.success(news);
    }
}
