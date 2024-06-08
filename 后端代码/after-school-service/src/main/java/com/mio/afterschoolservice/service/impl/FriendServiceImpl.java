package com.mio.afterschoolservice.service.impl;

import com.mio.afterschoolservice.mapper.FriendMapper;
import com.mio.afterschoolservice.pojo.Friend;
import com.mio.afterschoolservice.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;
    @Override
    public void deleteFriend(Integer ownerId, Integer fid) {
        friendMapper.deleteFriend(ownerId,fid);
    }

    @Override
    public List<Friend> getList(Integer id) {
        return friendMapper.getList(id);
    }

    @Override
    public Friend find(Integer fromUser, Integer toUser) {
        return friendMapper.find(fromUser,toUser);
    }
}
