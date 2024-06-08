package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Comment;

import java.util.List;

public interface CommentMapper {
    //添加评论
    void addComment(Comment comment);

    //删除评论
    void deleteComment(Integer id);

    //点赞
    void thumbsUp(Integer id);

    //取消点赞
    void cancel(Integer id);

    //获取课程评论列表
    List<Comment> getComment(Integer cid);
}
