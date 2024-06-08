package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.*;
import com.mio.afterschoolservice.service.CourseSerice;
import com.mio.afterschoolservice.service.ScheduleService;
import com.mio.afterschoolservice.service.UserService;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
@CrossOrigin
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CourseSerice courseSerice;
    @Autowired
    private UserService userService;

    //添加课程至课程表
    @PostMapping()
    public Result addSchedule(@RequestParam Integer cid){
        Schedule schedule=scheduleService.find(cid);
        if(schedule!=null){
            return Result.error("该课程已在您的课程表当中");
        }
        Course course=courseSerice.findByid(cid);
        Map<String,Object> map= ThreadLocalUtil.get();
        String name= (String) map.get("username");
        User user=userService.findByName(name);
        if(user.getMoney()<course.getPrice()){
            return Result.error("账户余额不足");
        }
        scheduleService.addSchedule(cid);
        return Result.success();
    }

    //从课程表中删除课程
    @DeleteMapping()
    public Result deleteSchedule(@RequestParam Integer id){
        scheduleService.deleteSchedule(id);
        return Result.success();
    }

    //获取课程表
    @GetMapping()
    public Result<PageBean<Schedule>> getSchedule(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageBean<Schedule> pageBean=scheduleService.getSchedule(pageNum,pageSize);
        return Result.success(pageBean);
    }

    //获取精品课程
    @GetMapping("/recommend")
    public Result<List<Schedule>> getRecommend(){
        List<Schedule> list=scheduleService.getRecommend();
        return Result.success(list);
    }
}
