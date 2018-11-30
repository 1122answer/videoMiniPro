package com.lcf.controller;

import com.lcf.pojo.Users;
import com.lcf.utils.IMoocJSONResult;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@RequestMapping("/video")
@Api(value = "视频相关业务接口" , tags = {"视频相关接口"})
public class VideoController {


    @ApiOperation(value = "用户上传视频" , notes = "用户上传视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id" ,required = true ,dataType = "String" ,paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐id" ,required = false ,dataType = "String" ,paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "视频播放长度" ,required = true ,dataType = "String" ,paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度" ,required = true ,dataType = "String" ,paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度" ,required = true ,dataType = "String" ,paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述" ,required = false ,dataType = "String" ,paramType = "form")
    })

    @PostMapping(value="/upload",headers = "content-type=multipart/form-data")
    public IMoocJSONResult upload  (String userId,
                                    String bgmId,
                                    double videoSeconds,
                                    int videoWidth,
                                    int videoHeight,
                                    String desc,
                                        @ApiParam(value = "短视频",required=true) MultipartFile videoFile) throws  Exception{
        if(StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户ID不能为空");
        }
        //文件保存的命名空间
        String fileSpace ="C:/lcf-videos";
        //保存到数据库中的相对路径
        String uploadPathDB = "/"+userId+"/video";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try{
            if(videoFile !=null ){
                String fileName= videoFile.getOriginalFilename();
                if(StringUtils.isNotBlank(fileName)){
                    //文件上传最终保存路径
                    String finalVideoPath = fileSpace + uploadPathDB +"/"+ fileName;

                    //设置数据库保存路径
                    uploadPathDB+=("/"+fileName);
                    File outFile = new File(finalVideoPath);
                    if(outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()){
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = videoFile.getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);
                }

            }else {
                return IMoocJSONResult.errorMsg("上传出错...");
            }

        }catch (Exception e){
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("上传出错...");
        }finally {
            if (fileOutputStream !=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        return IMoocJSONResult.ok();
    }
}
