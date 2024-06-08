package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Posts;

import java.util.List;

public interface PostsMapper {
    //发帖
    void addPosts(Posts posts);

    //删贴
    void deletePosts(Integer id);

    //修改帖子
    void updatePosts(Posts posts);

    //点赞
    void thumbsUp(Integer id);

    //取消点赞
    void cancel(Integer id);

    //获取帖子详情
    Posts getPosts(Integer id);

    //获取帖子列表
    List<Posts> getList(Integer userId);

    //获取热门帖子
    List<Posts> getHotList();
}
