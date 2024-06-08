package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Answer;
import com.mio.afterschoolservice.pojo.PageBean;

public interface AnswerService {
    void addAnswer(Answer answer);

    void deleteAnswer(Integer id);

    void thumbsUp(Integer id);

    void cancel(Integer id);

    PageBean<Answer> getList(Integer pid, Integer pageNum, Integer pageSize);
}
