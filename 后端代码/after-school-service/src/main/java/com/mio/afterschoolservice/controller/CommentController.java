package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Comment;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    //添加评论
    @PostMapping()
    public Result addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return Result.success();
    }

    //删除评论
    @DeleteMapping()
    public Result deleteComment(@RequestParam Integer id){
        commentService.deleteComment(id);
        return Result.success();
    }

    //点赞
    @PutMapping()
    public Result thumbsUp(@RequestParam Integer id){
        commentService.thumbsUp(id);
        return Result.success();
    }

    //取消点赞
    @PutMapping("/cancel")
    public Result cancel(@RequestParam Integer id){
        commentService.cancel(id);
        return Result.success();
    }

    //获取课程评论列表
    @GetMapping()
    public Result<PageBean<Comment>> getComment(@RequestParam Integer cid,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageBean<Comment> pageBean=commentService.getComment(cid,pageNum,pageSize);
        return Result.success(pageBean);
    }
}
