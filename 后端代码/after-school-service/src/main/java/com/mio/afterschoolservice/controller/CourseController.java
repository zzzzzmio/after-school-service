package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.Course;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.service.CourseSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseSerice courseSerice;

    //添加课程
    @PostMapping("/add")
    public Result addCourse(@RequestBody Course course){
        Course c=courseSerice.findByName(course.getCName());
        if(c!=null){
            return Result.error("您已发布该课程");
        }
        courseSerice.addCourse(course);
        return Result.success();
    }

    //获取课程详情
    @GetMapping("/get")
    public Result<Course> getCourse(@RequestParam Integer id){
        Course course=courseSerice.getCourse(id);
        return Result.success(course);
    }

    //更新课程
    @PutMapping("/update")
    public Result updateCourse(@RequestBody Course course){
        Course c=courseSerice.findOther(course.getId(),course.getCName());
        if(c!=null){
            return Result.error("您已发布该课程");
        }
        courseSerice.updateCourse(course);
        return Result.success();
    }

    //删除课程
    @DeleteMapping("/delete")
    public Result deleteCourse(@RequestParam Integer id){
        courseSerice.deleteCourse(id);
        return Result.success();
    }

    //分页获取课程列表
    @GetMapping("/getlist")
    public Result<PageBean<Course>> getList(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageBean<Course> pageBean=courseSerice.getList(pageNum,pageSize);
        return Result.success(pageBean);
    }

    //搜索
    @GetMapping("/search")
    public Result<PageBean<Course>> search(@RequestParam String cname,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageBean<Course> pageBean=courseSerice.search(cname,pageNum,pageSize);
        return Result.success(pageBean);
    }

    //老师获取课程列表
    @GetMapping("/teacher")
    public Result<List<Course>> getTeaList(@RequestParam Integer id){
        List<Course> list=courseSerice.getTeaList(id);
        return Result.success(list);
    }
}
