package com.mio.afterschoolservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mio.afterschoolservice.mapper.AnswerMapper;
import com.mio.afterschoolservice.pojo.Answer;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.service.AnswerService;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public void addAnswer(Answer answer) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        String username= (String) map.get("username");
        answer.setUserId(userId);
        answer.setUsername(username);
        answerMapper.addAnswer(answer);
    }

    @Override
    public void deleteAnswer(Integer id) {
        answerMapper.deleteAnswer(id);
    }

    @Override
    public void thumbsUp(Integer id) {
        answerMapper.thumbsUp(id);
    }

    @Override
    public void cancel(Integer id) {
        answerMapper.cancel(id);
    }

    @Override
    public PageBean<Answer> getList(Integer pid, Integer pageNum, Integer pageSize) {
        PageBean<Answer> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Answer> list=answerMapper.getList(pid);
        PageInfo pageInfo=new PageInfo<>(list);
        pageBean.setItems(pageInfo.getList());
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }
}
