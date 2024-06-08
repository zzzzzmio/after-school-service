package com.mio.afterschoolservice.service.impl;

import com.mio.afterschoolservice.mapper.ApplyMapper;
import com.mio.afterschoolservice.mapper.FriendMapper;
import com.mio.afterschoolservice.pojo.Apply;
import com.mio.afterschoolservice.pojo.Friend;
import com.mio.afterschoolservice.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private FriendMapper friendMapper;

    @Override
    public void addApply(Apply apply) {
        applyMapper.addApply(apply);
    }

    @Override
    public void agree(Integer id) {
        Apply apply=applyMapper.getDetail(id);
        friendMapper.addFriend(apply);
        applyMapper.agree(id);

    }

    @Override
    public void refuse(Integer id) {
        applyMapper.agree(id);
    }

    @Override
    public List<Apply> getList(Integer toUser) {
        return applyMapper.getList(toUser);
    }

    @Override
    public Apply find(Integer fromUser, Integer toUser) {
        return applyMapper.find(fromUser,toUser);
    }
}
