package com.mio.afterschoolservice.mapper;

import com.mio.afterschoolservice.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //根据名字查找用户
    public User findByName(String username);

    //注册
    public void register(@Param(value = "usertype") Integer usertype,@Param(value = "username") String username,@Param(value = "pd") String pd);

    //更新用户昵称与电子邮箱
    void updateInfo(@Param(value = "nickname") String nickname, @Param(value = "email") String email,@Param(value = "username") String username);

    //更新用户头像
    void updateAvatar(@Param(value = "avatar") String avatar,@Param(value = "id") Integer id);

    //更新密码
    void resetPassword(@Param(value = "pd") String pd,@Param(value = "id") Integer id);

    //充值
    void money(@Param(value = "money") Integer money,@Param(value = "id") Integer id);

    //获取用户头像
    String findAvatar(Integer id);

    List<User> getList();

    User getDetail(Integer id);

    User findOther(@Param(value = "username") String username,@Param(value = "id") Integer id);

    void changeInfo(User user);

    void updateInsingleAvatar(@Param(value = "avatar") String avatar,@Param(value = "name") String name);

    User findParent(Integer parentId);

    User findChild(Integer stuId);

    List<User> find(String name);
}
