package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Course;
import com.mio.afterschoolservice.pojo.PageBean;

import java.util.List;

public interface CourseSerice {
    void addCourse(Course course);

    Course getCourse(Integer id);

    void updateCourse(Course course);

    void deleteCourse(Integer id);

    PageBean<Course> getList(Integer pageNum, Integer pageSize);

    Course findByName(String cName);

    Course findOther(Integer id, String cName);

    PageBean<Course> search(String name, Integer pageNum, Integer pageSize);

    List<Course> getTeaList(Integer id);

    Course findByid(Integer cid);
}
