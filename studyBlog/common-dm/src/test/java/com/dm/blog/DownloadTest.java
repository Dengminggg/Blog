package com.dm.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@SpringBootTest
public class DownloadTest {

    @Autowired
    ServletContext servletContext;

    public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指下载的文件的路径。
            File file = new File(path);
            // 取得文件名
            String filename = file.getName();
            // 取得文件的后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            //以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            //读入buffer
            fis.read(buffer);
            fis.close();

            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");

            toClient.write(buffer);
            toClient.flush();
            toClient.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    /**
     * 下载本地文件
     *
     * @param response
     * @throws FileNotFoundException
     */
    public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {

        //文件的默认保存名
        String fileName = "Operator.doc".toString();
        //从文件的存放路径读到流中
        InputStream inStream = new FileInputStream("c:/Operator.doc");

        //设置输出的格式
        response.reset();
        String mimeType = servletContext.getMimeType(fileName);
        response.setContentType(mimeType);
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        //循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 下载网络文件
     *
     * @param response
     * @throws MalformedURLException
     */
    public void downloadNet(HttpServletResponse response) throws MalformedURLException {

        int bytesum = 0;
        int byteread = 0;
        //填入URL
        URL url = new URL("windine.blogdriver.com/logo.gif");

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();

            FileOutputStream fs = new FileOutputStream("c:/abc.gif");

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {

        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }

        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;
        // 非常重要
        response.reset();

        // 在线打开方式
        if (isOnLine) {
            URL u = new URL("file:///" + filePath);
            response.setContentType(u.openConnection().getContentType());
            // 文件名应该编码成UTF-8
            response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
        }
        // 纯下载方式
        else {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        br.close();
        out.close();
    }
}
