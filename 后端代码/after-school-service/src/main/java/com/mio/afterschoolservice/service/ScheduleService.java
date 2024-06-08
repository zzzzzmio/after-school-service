package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Schedule;

import java.util.List;

public interface ScheduleService {
    void addSchedule(Integer cid);

    Schedule find(Integer cid);

    void deleteSchedule(Integer id);

    PageBean<Schedule> getSchedule(Integer pageNum, Integer pageSize);

    List<Schedule> getRecommend();
}
