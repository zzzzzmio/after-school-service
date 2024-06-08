package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Imsingle;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.ImsingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imsingle")
@CrossOrigin
public class ImsingleController {
    @Autowired
    private ImsingleService imsingleService;

    //获取消息列表
    @GetMapping("/list")
    public Result<List<Imsingle>> getList(@RequestParam String fromuser,@RequestParam String touser){
        List<Imsingle> list=imsingleService.getList(fromuser,touser);
        return Result.success(list);
    }

    //插入消息（测试用）
    @PostMapping()
    public Result insert(@RequestBody Imsingle imsingle){
        imsingleService.insert(imsingle);
        return Result.success();
    }

    //获取未读消息
    @GetMapping("/unread")
    public Result<List<Imsingle>> getUnreaded(@RequestParam String touser){
        List<Imsingle> list=imsingleService.getUnreaded(touser);
        return Result.success(list);
    }

    //更新未读消息
    @PutMapping("/read")
    public Result read(@RequestParam String fromuser,@RequestParam String touser){
        imsingleService.read(fromuser,touser);
        return Result.success();
    }
}
