package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.pojo.Study;
import com.mio.afterschoolservice.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study")
@CrossOrigin
public class StudyController {
    @Autowired
    private StudyService studyService;

    //添加打卡记录
    @PostMapping()
    public Result addStudy(@RequestBody Study study){
        Study s=studyService.find(study);
        if(s!=null){
            return Result.error("该天已打卡");
        }
        studyService.addStudy(study);
        return Result.success();
    }

    //查询是否打卡
    @PostMapping("/check")
    public Result<Integer> check(@RequestBody Study study){
        Study s=studyService.find(study);
        if(s!=null){
            return Result.success(1);
        }
        return Result.success(0);
    }

    //获取最近几天打卡记录
    @GetMapping()
    public Result<List<Study>> getList(@RequestParam Integer stuId){
        List<Study> list=studyService.getList(stuId);
        return Result.success(list);
    }
}
