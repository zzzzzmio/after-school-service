package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Apply;
import com.mio.afterschoolservice.pojo.Friend;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.ApplyService;
import com.mio.afterschoolservice.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
@CrossOrigin
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private FriendService friendService;

    //添加申请
    @PostMapping()
    public Result addApply(@RequestBody Apply apply){
        Friend friend=friendService.find(apply.getFromUser(),apply.getToUser());
        if(friend!=null){
            return Result.error("已添加为好友");
        }
        Apply a=applyService.find(apply.getFromUser(),apply.getToUser());
        if(a!=null){
            return Result.error("已发出申请");
        }
        applyService.addApply(apply);
        return Result.success();
    }

    //同意申请
    @DeleteMapping()
    public Result agree(@RequestParam Integer id){
        applyService.agree(id);
        return Result.success();
    }

    //拒绝
    @DeleteMapping("/refuse")
    public Result refuse(@RequestParam Integer id){
        applyService.refuse(id);
        return Result.success();
    }

    //获取申请列表
    @GetMapping()
    public Result<List<Apply>> getList(@RequestParam Integer toUser){
        List<Apply> list=applyService.getList(toUser);
        return Result.success(list);
    }

}
