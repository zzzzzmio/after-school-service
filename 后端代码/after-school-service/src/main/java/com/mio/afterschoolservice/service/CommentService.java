package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Comment;
import com.mio.afterschoolservice.pojo.PageBean;

public interface CommentService {
    void addComment(Comment comment);

    void deleteComment(Integer id);

    void thumbsUp(Integer id);

    void cancel(Integer id);

    PageBean<Comment> getComment(Integer cid, Integer pageNum, Integer pageSize);
}
