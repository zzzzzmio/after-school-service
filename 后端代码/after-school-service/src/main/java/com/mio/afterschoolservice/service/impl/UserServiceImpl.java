package com.mio.afterschoolservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mio.afterschoolservice.mapper.UserMapper;
import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.User;
import com.mio.afterschoolservice.service.UserService;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public void register(Integer usertype, String username, String pd) {
        userMapper.register(usertype,username,pd);
    }

    @Override
    public void updateInfo(String nickname, String email) {
        Map<String,Object> map= ThreadLocalUtil.get();
        String username= (String) map.get("username");
        userMapper.updateInfo(nickname,email,username);
    }

    @Override
    public void updateAvatar(String avatar) {
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        String name= (String) map.get("username");
        userMapper.updateAvatar(avatar,id);
        userMapper.updateInsingleAvatar(avatar,name);
    }

    @Override
    public void resetPassword(String pd, Integer id) {
        userMapper.resetPassword(pd,id);
    }

    @Override
    public void money(Integer money) {
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        userMapper.money(money,id);
    }

    @Override
    public PageBean<User> getList(Integer pageNum, Integer pageSize) {
        PageBean<User> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<User> list=userMapper.getList();
        PageInfo pageInfo=new PageInfo<>(list);
        pageBean.setItems(pageInfo.getList());
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }

    @Override
    public User getDetail(Integer id) {
        return userMapper.getDetail(id);
    }

    @Override
    public User findOther(String username, Integer id) {
        return userMapper.findOther(username,id);
    }

    @Override
    public void changeInfo(User user) {
        userMapper.changeInfo(user);
    }

    @Override
    public User findParent(Integer parentId) {
        return userMapper.findParent(parentId);
    }

    @Override
    public User findChild(Integer stuId) {
        return userMapper.findChild(stuId);
    }

    @Override
    public List<User> find(String name) {
        return userMapper.find(name);
    }
}
