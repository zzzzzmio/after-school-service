package com.mio.afterschoolservice.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.mio.afterschoolservice.pojo.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class UploadController {
    //文件上传存储路径
    private static final String filepath=System.getProperty("user.dir")+"/file/";

    //文件上传
    @PostMapping()
    public Result<String> upload(MultipartFile file){
        //获取当前时间戳
        String flag=System.currentTimeMillis()+"";
        //获取原始文件名
        String filename=file.getOriginalFilename();
        try{
            //如果没有file文件夹，会在项目根目录下创建一个file文件夹
            if(!FileUtil.isDirectory(filepath)){
                FileUtil.mkdir(filepath);
            }
            //文件存储形式：时间戳-文件名
            FileUtil.writeBytes(file.getBytes(),filepath+flag+"-"+filename);
//            System.out.println(filename+"上传成功");
        } catch (Exception e){
            System.out.println(e);
        }
        return Result.success(flag);
    }

    //文件获取/下载
    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response){
        if (!FileUtil.isDirectory(filepath)){
            FileUtil.mkdir(filepath);
        }
        OutputStream os;
        List<String> fileNames=FileUtil.listFileNames(filepath);
        String avatar=fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");
        try{
            if(StrUtil.isNotEmpty(avatar)){
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(avatar,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes=FileUtil.readBytes(filepath+avatar);
                os=response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {

        }
    }

}
