package pers.prover07.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @author by Prover07
 * @classname FileController
 * @description TODO
 * @date 2022/1/31 13:30
 */
@Controller
@Slf4j
public class FileController {

    /**
     * 文件上传，如果存在不同的文件属性，可以通过 @RequestPart 指定参数名用来接收文件
     * @param email
     * @param username
     * @param photo 单文件
     * @param imgs 多文件
     * @param session
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String uplaodFile(@RequestParam("email") String email,
                             @RequestParam("username") String username,
                             @RequestPart("photo") MultipartFile photo,
                             @RequestPart("imgs") MultipartFile[] imgs,
                             HttpSession session) throws IOException {
        log.info("request info: email={}, username={}, photo.size={}, imgs.length={}",
                email, username, photo.getSize(), imgs.length);
        // 获取保存图片文件夹路径
        String photoPath = session.getServletContext().getRealPath("photo");
        // 判断文件夹是否存在，如果不存在就创建对应的目录
        System.out.println(photoPath);
        File file = new File(photoPath);
        if (!file.exists()) {
            file.mkdir();
        }
        // 保存图片到本地服务器
        photo.transferTo(new File(photoPath + File.separator + photo.getOriginalFilename()));
        return "/home";
    }

}
