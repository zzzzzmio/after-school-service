package com.mio.afterschoolservice.service;

import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.User;

import java.util.List;

public interface UserService {
    User findByName(String username);

    void register(Integer usertype, String username, String pd);

    void updateInfo(String nickname, String email);

    void updateAvatar(String avatar);

    void resetPassword(String pd, Integer id);

    void money(Integer money);

    PageBean<User> getList(Integer pageNum, Integer pageSize);

    User getDetail(Integer id);

    User findOther(String username, Integer id);

    void changeInfo(User user);

    User findParent(Integer parentId);

    User findChild(Integer stuId);

    List<User> find(String name);
}
