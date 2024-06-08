package com.mio.afterschoolservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mio.afterschoolservice.mapper.PostsMapper;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Posts;
import com.mio.afterschoolservice.service.PostsService;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostsServiceImpl implements PostsService {
    @Autowired
    private PostsMapper postsMapper;

    @Override
    public void addPosts(Posts posts) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        String username= (String) map.get("username");
        posts.setUserId(userId);
        posts.setUsername(username);
        postsMapper.addPosts(posts);
    }

    @Override
    public void deletePosts(Integer id) {
        postsMapper.deletePosts(id);
    }

    @Override
    public void updatePosts(Posts posts) {
        postsMapper.updatePosts(posts);
    }

    @Override
    public void thumbsUp(Integer id) {
        postsMapper.thumbsUp(id);
    }

    @Override
    public void cancel(Integer id) {
        postsMapper.cancel(id);
    }

    @Override
    public Posts getPosts(Integer id) {
        return postsMapper.getPosts(id);
    }

    @Override
    public PageBean<Posts> getList(Integer pageNum, Integer pageSize, Integer userId) {
        PageBean<Posts> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Posts> list=postsMapper.getList(userId);
        PageInfo pageInfo=new PageInfo<>(list);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setItems(pageInfo.getList());
        return pageBean;
    }

    @Override
    public List<Posts> getHotList() {
        return postsMapper.getHotList();
    }
}
