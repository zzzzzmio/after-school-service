package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Notice;
import com.mio.afterschoolservice.pojo.PageBean;

import java.util.List;

public interface NoticeService {
    Notice findByTitle(String title);

    void addNotice(Notice notice);

    void deleteNotice(Integer id);

    Notice findByOther(String title, Integer id);

    void updateNotice(Notice notice);

    Notice getNotice(Integer id);

    PageBean<Notice> getList(Integer pageNum, Integer pageSize);

    List<Notice> getAll();
}
