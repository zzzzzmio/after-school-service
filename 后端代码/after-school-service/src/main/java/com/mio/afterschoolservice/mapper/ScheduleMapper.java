package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Schedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleMapper {
    //添加课程至课程表
    void addSchedule(Schedule schedule);

    //根据课程id查找课程表
    Schedule find(@Param(value = "cid") Integer cid,@Param(value = "id") Integer id);

    //从课程表中删除课程
    void deleteSchedule(Integer id);

    //获取课程表
    List<Schedule> getSchedule(Integer userId);

    List<Schedule> getRecommend();
}
