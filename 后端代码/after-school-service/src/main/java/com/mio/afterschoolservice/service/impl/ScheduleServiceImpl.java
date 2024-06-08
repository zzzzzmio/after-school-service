package com.mio.afterschoolservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mio.afterschoolservice.mapper.CourseMapper;
import com.mio.afterschoolservice.mapper.ScheduleMapper;
import com.mio.afterschoolservice.pojo.Course;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Schedule;
import com.mio.afterschoolservice.service.ScheduleService;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public void addSchedule(Integer cid) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer stuId= (Integer) map.get("id");
        Schedule schedule=new Schedule();
        schedule.setStuId(stuId);
        schedule.setCId(cid);
        Course course=courseMapper.getCourse(cid);
        schedule.setCName(course.getCName());
        schedule.setCTime(course.getCTime());
        schedule.setTName(course.getTName());
        scheduleMapper.addSchedule(schedule);
    }

    @Override
    public Schedule find(Integer cid) {
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        return scheduleMapper.find(cid,id);
    }

    @Override
    public void deleteSchedule(Integer id) {
        scheduleMapper.deleteSchedule(id);
    }

    @Override
    public PageBean<Schedule> getSchedule(Integer pageNum, Integer pageSize) {
        PageBean<Schedule> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        List<Schedule> list=scheduleMapper.getSchedule(userId);
        PageInfo pageInfo=new PageInfo<>(list);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setItems(pageInfo.getList());
        return pageBean;
    }

    @Override
    public List<Schedule> getRecommend() {
        return scheduleMapper.getRecommend();
    }
}
