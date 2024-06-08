package com.mio.afterschoolservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mio.afterschoolservice.mapper.CourseMapper;
import com.mio.afterschoolservice.pojo.Course;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.service.CourseSerice;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseSerice {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public void addCourse(Course course) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer tId= (Integer) map.get("id");
        course.setTId(tId);
        courseMapper.addCourse(course);
    }

    @Override
    public Course getCourse(Integer id) {
        return courseMapper.getCourse(id);
    }

    @Override
    public void updateCourse(Course course) {
        courseMapper.updateCourse(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        courseMapper.deleteCourse(id);
    }

    @Override
    public PageBean<Course> getList(Integer pageNum, Integer pageSize) {
        PageBean<Course> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Course> list=courseMapper.getList();
        PageInfo<Course> pageInfo=new PageInfo<>(list);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setItems(pageInfo.getList());
        return pageBean;
    }

    @Override
    public Course findByName(String cName) {
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer tId= (Integer) map.get("id");
        return courseMapper.find(cName,tId);
    }

    @Override
    public Course findOther(Integer id, String cName) {
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer tId= (Integer) map.get("id");
        return courseMapper.findOther(id,cName,tId);
    }

    @Override
    public PageBean<Course> search(String name, Integer pageNum, Integer pageSize) {
        PageBean<Course> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Course> list=courseMapper.search(name);
        PageInfo pageInfo=new PageInfo<>(list);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setItems(pageInfo.getList());
        return pageBean;
    }

    @Override
    public List<Course> getTeaList(Integer id) {
        return courseMapper.getTeaList(id);
    }

    @Override
    public Course findByid(Integer cid) {
        return courseMapper.findById(cid);
    }
}
