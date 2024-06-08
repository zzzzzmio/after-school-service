package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.Apply;
import com.mio.afterschoolservice.pojo.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper {
    void addFriend(Apply apply);

    void deleteFriend(@Param(value = "ownerId") Integer ownerId,@Param(value = "fid") Integer fid);

    List<Friend> getList(Integer id);

    Friend find(@Param(value = "fromUser") Integer fromUser,@Param(value = "toUser") Integer toUser);
}
