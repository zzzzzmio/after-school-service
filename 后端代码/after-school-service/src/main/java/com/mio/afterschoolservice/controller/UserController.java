package com.mio.afterschoolservice.controller;

import com.mio.afterschoolservice.pojo.PageBean;
import com.mio.afterschoolservice.pojo.Result;
import com.mio.afterschoolservice.pojo.User;
import com.mio.afterschoolservice.service.UserService;
import com.mio.afterschoolservice.utils.JwtUtil;
import com.mio.afterschoolservice.utils.Md5Util;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //注册
    @PostMapping("/register")
    public Result register(Integer usertype, @Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user=userService.findByName(username);
        if(user!=null){ //有相同用户名的用户
            return Result.error("用户名已存在");
        }
        //否则进行注册
        String pd= Md5Util.getMD5String(password);
        userService.register(usertype,username,pd);
        return Result.success();
    }

    //登录
    @PostMapping("/login")
    public Result login(Integer usertype, @Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user=userService.findByName(username);
        if(user==null){
            return Result.error("用户名不正确");
        }
        if(Md5Util.getMD5String(password).equals(user.getPassword())&&usertype==user.getUsertype()){
            Map<String,Object> claim=new HashMap<>();
            claim.put("id",user.getId());
            claim.put("username",user.getUsername());
            String token = JwtUtil.genToken(claim);
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            opsForValue.set(token,token);
            return Result.success(token);
        }
        return Result.error("密码或用户类型不正确");
    }

    //获取用户信息
    @GetMapping("/userinfo")
    public Result<User> getUserInfo(){
        Map<String,Object> map= ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user=userService.findByName(username);
        return Result.success(user);
    }

    //更新用户昵称与电子邮箱
    @PutMapping("/update")
    public Result updateInfo(@RequestBody User user){
        userService.updateInfo(user.getNickname(), user.getEmail());
        return Result.success();
    }

    //更新用户头像
    @PutMapping("/avatar")
    public Result updateAvatar(@RequestParam String avatar){
        userService.updateAvatar(avatar);
        return Result.success();
    }

    //重置密码
    @PatchMapping("/password")
    public Result resetPassword(@RequestParam String old_psw,@RequestParam String new_psw,@RequestParam String re_psw,@RequestHeader("Authorization") String token){
        Map<String,Object> map=ThreadLocalUtil.get();
        String username= (String) map.get("username");
        User user = userService.findByName(username);
        if(!Md5Util.getMD5String(old_psw).equals(user.getPassword())){
            return Result.error("密码错误");
        }
        if(old_psw.equals(new_psw)){
            return Result.error("新密码不能与旧密码一致");
        }
        if(!new_psw.equals(re_psw)){
            return Result.error("两次输入的密码不一致");
        }
        String pd = Md5Util.getMD5String(new_psw);
        userService.resetPassword(pd,user.getId());
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

    //充值
    @PostMapping("/money")
    public Result money(@RequestParam Integer money){
        userService.money(money);
        return Result.success();
    }

    //获取用户列表
    @GetMapping("/list")
    public Result<PageBean<User>> getList(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageBean<User> pageBean=userService.getList(pageNum,pageSize);
        return Result.success(pageBean);
    }

    //根据id获取用户详情
    @GetMapping("/detail")
    public Result<User> getDetail(@RequestParam Integer id){
        User user=userService.getDetail(id);
        return Result.success(user);
    }

    //根据id更新用户信息
    @PutMapping("/change")
    public Result changeInfo(@RequestBody User user){
        User u=userService.findOther(user.getUsername(),user.getId());
        if(u!=null){
            return Result.error("用户名重复");
        }
        userService.changeInfo(user);
        return Result.success();
    }

    //搜索好友
    @GetMapping("/friend")
    public  Result<List<User>> find(@RequestParam String name){
        List<User> list=userService.find(name);
        return Result.success(list);
    }
}
