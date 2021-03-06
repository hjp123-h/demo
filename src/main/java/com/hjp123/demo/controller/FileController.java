package com.hjp123.demo.controller;

import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.FileDTO;
import com.hjp123.demo.mapper.UserMapper;
import com.hjp123.demo.provider.AliOssProvider;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.sql.SQLException;


@Controller
public class FileController {

    @Autowired
    private AliOssProvider aliOssProvider;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");

        //图片上传接口
        try {
            URL  url = aliOssProvider.upload(file.getInputStream(), "question/" + file.getOriginalFilename());

            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setMessage("上传成功，点击确定按钮即可");
            fileDTO.setUrl(url.toString());
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败，请刷新重试");
            return fileDTO;

        }

    }

    @PostMapping("/file/uploadel")
    @ResponseBody
    public FileDTO uploadel(HttpServletRequest request,
                            MultipartFile file) {

        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            return null;
        }
        //图片上传接口
        try {
            URL  url = aliOssProvider.upload(file.getInputStream(), "avatar/" + file.getOriginalFilename());
            userMapper.updateAvatat(user.getId(),url.toString());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setMessage("上传成功，点击确定按钮即可");
            fileDTO.setUrl(url.toString());
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败，请刷新重试");
            return fileDTO;

        }

    }

}
