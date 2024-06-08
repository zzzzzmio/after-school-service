package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Family;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.pojo.User;
import com.mio.afterschoolservice.service.FamilyService;
import com.mio.afterschoolservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/family")
@CrossOrigin
public class FamilyController {
    @Autowired
    private FamilyService familyService;
    @Autowired
    private UserService userService;

    //申请绑定
    @PostMapping()
    public Result apply(@RequestBody Family family){
        System.out.println(family);
        User parent=userService.findParent(family.getParentId());
        User child=userService.findChild(family.getStuId());
        if(parent==null || child==null){
            return Result.error("不能与该用户类型绑定家庭关系");
        }
        Family f=familyService.find(family);
        if(f!=null){
            return Result.error("已绑定家庭关系");
        }
        familyService.apply(family);
        return Result.success();
    }

    //解除绑定
    @DeleteMapping()
    public Result deleteFamily(@RequestParam Integer id){
        familyService.deleteFamily(id);
        return Result.success();
    }

    //同意绑定
    @PutMapping()
    public Result agree(@RequestParam Integer id){
        familyService.agree(id);
        return Result.success();
    }

    //孩子获取申请列表
    @GetMapping("/apply")
    public Result<List<Family>> getApplyList(@RequestParam Integer id){
        List<Family> list=familyService.getApplyList(id);
        return Result.success(list);
    }

    //家长获取申请列表
    @GetMapping("/parent")
    public Result<List<Family>> getParentList(@RequestParam Integer id){
        List<Family> list=familyService.getParentList(id);
        return Result.success(list);
    }

    //获取家庭列表
    @GetMapping()
    public Result<List<Family>> getFamilyList(@RequestParam Integer id){
        List<Family> list=familyService.getFamilyList(id);
        return Result.success(list);
    }
}
