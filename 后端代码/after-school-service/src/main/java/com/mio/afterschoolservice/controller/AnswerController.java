package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Answer;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
@CrossOrigin
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    //添加回复
    @PostMapping()
    public Result addAnswer(@RequestBody Answer answer){
        answerService.addAnswer(answer);
        return Result.success();
    }

    //删除回复
    @DeleteMapping()
    public Result deleteAnswer(@RequestParam Integer id){
        answerService.deleteAnswer(id);
        return Result.success();
    }

    //点赞
    @PutMapping()
    public Result thumbsUp(@RequestParam Integer id){
        answerService.thumbsUp(id);
        return Result.success();
    }

    //取消点赞
    @PutMapping("/cancel")
    public Result cancel(@RequestParam Integer id){
        answerService.cancel(id);
        return Result.success();
    }

    //获取回复列表
    @GetMapping()
    public Result<PageBean<Answer>> getList(@RequestParam Integer pid,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageBean<Answer> pageBean=answerService.getList(pid,pageNum,pageSize);
        return Result.success(pageBean);
    }
}
