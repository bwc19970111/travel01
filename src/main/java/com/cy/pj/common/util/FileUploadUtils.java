package com.cy.pj.common.util;

import com.cy.pj.sys.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class FileUploadUtils {

    private static String uploadFolder;

    private static String imgSuffixRegular = "^\\.gif|\\.jpeg|\\.png|\\.jpg|\\.bmp$";

    private static String videoSuffixRegular = "^\\.mp4|\\.rmvb|\\.flv|\\.mpeg|\\.avi$";

    private static String musicSuffixRegular = "^\\.mp3|\\.wav|\\.wma|\\.ogg|\\.ape|\\.acc$";


    @Value("${file.uploadFolder}")
    public void setClusterName(String clusterName) {
        uploadFolder = clusterName;
    }
    /**
     * 返回文件上传后路径
     * @param multipartFile
     * @param request 请求
     * @return
     */
    public static String fileUpload(MultipartFile multipartFile) {
        System.out.println(uploadFolder);
        if(multipartFile==null) {
            throw new ServiceException("文件为空");
        }
        if(multipartFile.isEmpty()) {
            throw new ServiceException("文件为空");
        }
        String folder = "";

        //生成新文件名
        String newName = UUID.randomUUID().toString();
        //获取文件后缀 并进行拼接
        String sourceName = multipartFile.getOriginalFilename();
        String sourceSuffix = sourceName.substring(sourceName.lastIndexOf("."),sourceName.length());
        newName+=sourceSuffix;
        //判断类型
        int rightSuffix = isRightSuffix(sourceSuffix);

        switch (rightSuffix) {
            case 0:
                folder = "/img";
                break;
            case 1:
                folder = "/video";
                break;
            case 2:
                folder = "/img";
                break;
            default:
                folder = "/music";
                break;
        }
        String dataFlod = getDataFlod();
        folder+=dataFlod;
        File f = new File(uploadFolder+folder);
        if(!f.exists()) {
            f.mkdirs();
        }
        File file = new File(uploadFolder+folder+newName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean fileCopy = fileCopy(multipartFile,file);
        if(!fileCopy) {
            throw new ServiceException("文件保存失败");
        }
        return "/static"+folder+newName;
    }

    /**
     *
     * @param suffix
     * @return 返回 0 是图片  1是 视频 2是音乐
     */
    private static int isRightSuffix(String suffix) {
        int result = -1;
        if(suffix.matches(imgSuffixRegular)) {
            result = 0;
        }else if(suffix.matches(videoSuffixRegular)) {
            result = 1;
        }else if(suffix.matches(musicSuffixRegular)){
            result = 2;
        }
        return result;
    }

    /**
     *  获取当前时间生成目录
     * @return
     */
    private static String getDataFlod() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
        String format = simpleDateFormat.format(date);
        return format;
    }

    private static boolean fileCopy(MultipartFile sourceFile,File file) {
        try {
            BufferedOutputStream stream = null;
            byte[] bytes = sourceFile.getBytes();
            stream = new BufferedOutputStream(new FileOutputStream(
                    file));
            stream.write(bytes);// 写入
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}