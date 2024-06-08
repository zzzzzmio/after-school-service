package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Posts;

import java.util.List;

public interface PostsService {
    void addPosts(Posts posts);

    void deletePosts(Integer id);

    void updatePosts(Posts posts);

    void thumbsUp(Integer id);

    void cancel(Integer id);

    Posts getPosts(Integer id);

    PageBean<Posts> getList(Integer pageNum, Integer pageSize, Integer userId);

    List<Posts> getHotList();
}
