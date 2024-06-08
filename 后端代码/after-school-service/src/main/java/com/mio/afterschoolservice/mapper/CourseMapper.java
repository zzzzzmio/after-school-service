package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    //添加课程
    void addCourse(Course course);

    //获取课程详情
    Course getCourse(Integer id);

    //更新课程
    void updateCourse(Course course);

    //删除课程
    void deleteCourse(Integer id);

    //分页获取课程列表
    List<Course> getList();

    //根据课程名称和授课老师id寻找课程
    Course find(@Param(value = "cName") String cName,@Param(value = "tId") Integer tId);

    //根据课程id和课程名称查找除自己以为的课程
    Course findOther(@Param(value = "id") Integer id,@Param(value = "cName") String cName,@Param(value = "tId") Integer tId);

    List<Course> search(String name);

    List<Course> getTeaList(Integer id);

    Course findById(Integer cid);
}
