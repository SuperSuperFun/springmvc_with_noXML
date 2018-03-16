package com.wangli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class DownloadController {
    @RequestMapping("download")
    public void download(HttpServletResponse res) {
        try {
            String imgPath = "E:/memories/tx.JPG";
            File file = new File(imgPath);
            FileImageInputStream fiis = new FileImageInputStream(file);
            int len;
            byte[] buff = new byte[1024];
            /*
            * 禁止浏览器缓存的操作：
            * response.setHeader("Pragma", "No-cache");
            * response.setHeader("Cache-Control", "No-cache");
            * response.setDateHeader("Expires", 0);
            * */
            //通知客户端响应文件属于什么MIME类型，Servlet默认为text/plain，但通常需要显式地指定为text/html
            res.setHeader("Content-Type", "application/octet-stream");
            /*
            * 下载文件时,我们一般设置Content-Disposition告诉浏览器下载文件的名称,是否在浏览器中内嵌显示.
            * Content-disposition: inline; filename=xxx.pdf 表示浏览器内嵌显示一个文件
            * Content-disposition: attachment; filename=xxx.pdf 表示会下载文件
            * */
            //通知客户端用下载的方式接受数据
            res.setHeader("Content-Disposition", "attachment;fileName=111.png");
            OutputStream os = res.getOutputStream();
            while ((len=fiis.read(buff)) != -1) {
                os.write(buff, 0 ,len);
            }
            os.close();
            fiis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
