package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Posts;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostsController {
    @Autowired
    private PostsService postsService;

    //发帖
    @PostMapping()
    public Result addPosts(@RequestBody Posts posts){
        postsService.addPosts(posts);
        return Result.success();
    }

    //删贴
    @DeleteMapping()
    public Result deletePosts(@RequestParam Integer id){
        postsService.deletePosts(id);
        return Result.success();
    }

    //修改帖子
    @PutMapping()
    public Result updatePosts(@RequestBody Posts posts){
        postsService.updatePosts(posts);
        return Result.success();
    }

    //点赞
    @PutMapping("/good")
    public Result thumbsUp(@RequestParam Integer id){
        postsService.thumbsUp(id);
        return Result.success();
    }

    //取消点赞
    @PutMapping("/cancel")
    public Result cancel(@RequestParam Integer id){
        postsService.cancel(id);
        return Result.success();
    }

    //获取帖子详情
    @GetMapping()
    public Result<Posts> getPosts(@RequestParam Integer id){
        Posts posts=postsService.getPosts(id);
        return Result.success(posts);
    }

    //分页获取帖子列表
    @GetMapping("/list")
    public Result<PageBean<Posts>> getList(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam(required = false) Integer userId){
        PageBean<Posts> pageBean=postsService.getList(pageNum,pageSize,userId);
        return Result.success(pageBean);
    }

    //获取热门帖子
    @GetMapping("/hot")
    public Result<List<Posts>> getHotList(){
        List<Posts> list=postsService.getHotList();
        return Result.success(list);
    }

}
