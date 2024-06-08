package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Answer;

import java.util.List;

public interface AnswerMapper {
    //添加回复
    void addAnswer(Answer answer);

    //删除回复
    void deleteAnswer(Integer id);

    //点赞
    void thumbsUp(Integer id);

    //取消点赞
    void cancel(Integer id);

    //获取回复列表
    List<Answer> getList(Integer pid);
}
