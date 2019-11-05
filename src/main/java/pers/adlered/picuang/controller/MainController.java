package pers.adlered.picuang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.text.DecimalFormat;

@Controller
public class MainController {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        //图片总数计算
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploadImages/";
        try {
            modelAndView.addObject("files", new File(path).listFiles().length);
        } catch (NullPointerException NPE) {
            System.out.println("Welcome to use Picuang, generating new directory...");
            File file = new File(path);
            file.mkdirs();
        }
        //剩余空间计算
        File diskPartition = new File("/");
        String freePartitionSpace = new DecimalFormat("#.00").format(diskPartition.getFreeSpace() / 1073741824);
        modelAndView.addObject("free", freePartitionSpace);
        //限制文件大小
        modelAndView.addObject("limit", maxFileSize);
        return modelAndView;
    }
}
