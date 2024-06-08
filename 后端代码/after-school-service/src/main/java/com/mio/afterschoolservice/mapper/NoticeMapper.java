package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    Notice findByTitle(String title);

    void addNotice(Notice notice);

    void deleteNotice(Integer id);

    Notice findByOther(@Param(value = "title") String title,@Param(value = "id") Integer id);

    void updateNotice(Notice notice);

    Notice getNotice(Integer id);

    List<Notice> getList();
}
