package com.wangli.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import com.wangli.util.PDFUtils;
import com.wangli.util.ServletUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class ExportPdf {
    @RequestMapping("toPdf")
    public void exportPdf(HttpServletRequest request, HttpServletResponse response) {
        String html = ServletUtils.forward(request,response,"/WEB-INF/views/success.jsp"); //转发请求到jsp，返回解析之后的内容而不是输出到浏览器
        try {
            byte[] pdf = PDFUtils.html2pdf(html);
            response.setContentType("application/pdf");
            response.setHeader("Content-Length",String.valueOf(pdf.length));
            response.setHeader("Connection","keep-alive");
            response.setHeader("Accept-Ranges","none");
            response.setHeader("X-Frame-Options","DENY");
            OutputStream out = response.getOutputStream();
            out.write(pdf);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (CssResolverException e) {
            e.printStackTrace();
        }
    }


}
