package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Home;
import com.mio.afterschoolservice.pojo.News;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@CrossOrigin
public class HomeController {
    @Autowired
    private HomeService homeService;

    //添加
    @PostMapping()
    public Result addHome(@RequestBody Home home){
        //顶部新闻
        if(home.getLocation()==1){
            if(homeService.getHeader()<6){
                homeService.addHome(home);
                return Result.success();
            }
            else {
                return Result.error("主页顶部新闻已达最大数量");
            }
        }
        else { //底部公告
            if(homeService.getFooter()<6){
                homeService.addHome(home);
                return Result.success();
            }
            else {
                return Result.error("主页底部公告已达最大数量");
            }
        }
    }

    //删除
    @DeleteMapping()
    public Result deleteHome(@RequestParam Integer id){
        homeService.deleteHome(id);
        return Result.success();
    }

    //获取顶部主页元素列表
    @GetMapping("/header")
    public Result<List<Home>> getHeaderList(){
        List<Home> list=homeService.getHeaderList();
        return Result.success(list);
    }

    //获取底部主页元素列表
    @GetMapping("/footer")
    public Result<List<Home>> getFooterList(){
        List<Home> list=homeService.getFooterList();
        return Result.success(list);
    }

    //获取未加入主页元素的新闻公告列表
    @GetMapping("/news")
    public Result<List<News>> getNews(){
        List<News> list=homeService.getNews();
        return Result.success(list);
    }

    //获取主页元素列表
    @GetMapping()
    public Result<List<Home>> getList(){
        List<Home> list=homeService.getList();
        return Result.success(list);
    }
}
