package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Friend;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {
    @Autowired
    private FriendService friendService;

    //删除好友
    @DeleteMapping()
    public Result deleteFriend(@RequestParam Integer ownerId,@RequestParam Integer fid){
        friendService.deleteFriend(ownerId,fid);
        return Result.success();
    }

    //获取好友列表
    @GetMapping()
    public Result<List<Friend>> getList(@RequestParam Integer id){
        List<Friend> list=friendService.getList(id);
        return Result.success(list);
    }
}
