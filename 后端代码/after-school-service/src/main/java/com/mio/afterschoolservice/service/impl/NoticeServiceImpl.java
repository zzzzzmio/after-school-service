package com.mio.afterschoolservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mio.afterschoolservice.mapper.NoticeMapper;
import com.mio.afterschoolservice.pojo.Notice;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public Notice findByTitle(String title) {
        return noticeMapper.findByTitle(title);
    }

    @Override
    public void addNotice(Notice notice) {
        noticeMapper.addNotice(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        noticeMapper.deleteNotice(id);
    }

    @Override
    public Notice findByOther(String title, Integer id) {
        return noticeMapper.findByOther(title,id);
    }

    @Override
    public void updateNotice(Notice notice) {
        noticeMapper.updateNotice(notice);
    }

    @Override
    public Notice getNotice(Integer id) {
        return noticeMapper.getNotice(id);
    }

    @Override
    public PageBean<Notice> getList(Integer pageNum, Integer pageSize) {
        PageBean<Notice> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Notice> list=noticeMapper.getList();
        PageInfo pageInfo=new PageInfo<>(list);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setItems(pageInfo.getList());
        return pageBean;
    }

    @Override
    public List<Notice> getAll() {
        return noticeMapper.getList();
    }
}
