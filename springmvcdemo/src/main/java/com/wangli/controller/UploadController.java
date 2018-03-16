package com.wangli.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class UploadController {
    @RequestMapping("/upload")
    public String upload(HttpServletRequest req, HttpServletResponse res) {
        try {
            if (ServletFileUpload.isMultipartContent(req)) {
                //1.创建DiskFileItemFactory对象
                DiskFileItemFactory factory = new DiskFileItemFactory();
                //2.创建ServletFileUpload对象，并设置上传文件的大小限制。
                ServletFileUpload sfu = new ServletFileUpload(factory);
                sfu.setFileSizeMax(10*1024*1024);
                sfu.setHeaderEncoding("utf-8");
                //3.调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象
                List<FileItem> fileItemList = sfu.parseRequest(req);
                Iterator<FileItem> iterator = fileItemList.iterator();
                //4.遍历list，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
                while (iterator.hasNext()) {
                    FileItem item = iterator.next();
                    //如果是普通表单
                    if (item.isFormField()) {
                        String name = item.getFieldName();//name属性值
                        String value = item.getString("utf-8"); //name对应的value值
                        System.out.println(name + "=" + value);
                        //非普通表单 <input type="file">
                    } else {
                        String fileName = item.getName();//原文件名
                        System.out.println("原文件名：" + fileName);
                        String suffix = fileName.substring(fileName.lastIndexOf("."));//扩展名

                        //新文件名
                        String newFileName = new Date().getTime() + suffix;
                        System.out.println("新文件名：" + newFileName);

                        //5.调用FileItem的write()方法，写入文件
                        File file = new File("C:/Users/super/Desktop/" + newFileName);
                        System.out.println(file.getAbsolutePath());
                        item.write(file);
                    }
                }
            }
            return "success";
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "fail";
    }

}
