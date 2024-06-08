package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Notice;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    //添加公告
    @PostMapping()
    public Result addNotice(@RequestBody Notice notice){
        if(noticeService.findByTitle(notice.getTitle())!=null){
            return Result.error("已发布该公告");
        }
        noticeService.addNotice(notice);
        return Result.success();
    }

    //删除公告
    @DeleteMapping()
    public Result deleteNotice(@RequestParam Integer id){
        noticeService.deleteNotice(id);
        return Result.success();
    }

    //修改公告
    @PutMapping()
    public Result updateNotice(@RequestBody Notice notice){
        Notice n=noticeService.findByOther(notice.getTitle(),notice.getId());
        if(n!=null){
            return Result.error("该新闻已发布");
        }
        noticeService.updateNotice(notice);
        return Result.success();
    }

    //获取公告详情
    @GetMapping()
    public Result<Notice> getNotice(@RequestParam Integer id){
        Notice notice=noticeService.getNotice(id);
        return Result.success(notice);
    }

    //获取公告列表
    @GetMapping("/list")
    public Result<PageBean<Notice>> getList(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageBean<Notice> pageBean=noticeService.getList(pageNum,pageSize);
        return Result.success(pageBean);
    }

    //不分页
    @GetMapping("/all")
    public Result<List<Notice>> getAll(){
        List<Notice> list=noticeService.getAll();
        return Result.success(list);
    }
}
