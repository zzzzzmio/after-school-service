package com.mio.afterschoolservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mio.afterschoolservice.mapper.CommentMapper;
import com.mio.afterschoolservice.mapper.UserMapper;
import com.mio.afterschoolservice.pojo.Comment;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.service.CommentService;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void addComment(Comment comment) {
        Map<String, Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        String username= (String) map.get("username");
        String avatar=userMapper.findAvatar(userId);
        comment.setAvatar(avatar);
        comment.setUserId(userId);
        comment.setUsername(username);
        commentMapper.addComment(comment);
    }

    @Override
    public void deleteComment(Integer id) {
        commentMapper.deleteComment(id);
    }

    @Override
    public void thumbsUp(Integer id) {
        commentMapper.thumbsUp(id);
    }

    @Override
    public void cancel(Integer id) {
        commentMapper.cancel(id);
    }

    @Override
    public PageBean<Comment> getComment(Integer cid, Integer pageNum, Integer pageSize) {
        PageBean<Comment> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> list=commentMapper.getComment(cid);
        PageInfo pageInfo=new PageInfo<>(list);
        pageBean.setItems(pageInfo.getList());
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }
}
