package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.Friend;

import java.util.List;

public interface FriendService {
    void deleteFriend(Integer ownerId, Integer fid);

    List<Friend> getList(Integer id);

    Friend find(Integer fromUser, Integer toUser);
}
