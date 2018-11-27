package com.lcf.controller;

import com.lcf.pojo.Users;
import com.lcf.pojo.vo.UsersVO;
import com.lcf.service.UserSevice;
import com.lcf.utils.IMoocJSONResult;
import com.lcf.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

@RestController
@Api(value = "用户相关业务接口" , tags = {"业务详情"})
public class UserController extends BasicController{

	@Autowired
	private UserSevice userSevice;

	@ApiOperation(value = "用户上传头像" , notes = "用户上传头像接口")
	@ApiImplicitParam(name = "userId", value = "用户id" ,required = true ,dataType = "String" ,paramType = "query")
	@PostMapping("/uploadFace")
	public IMoocJSONResult uploadFace  (String userId,
		@RequestParam("file") MultipartFile[] files) throws  Exception{
		//文件保存的命名空间
		String fileSpace ="C:/lcf-videos";
		//保存到数据库中的相对路径
		String uploadPathDB = "/"+userId+"/face";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try{
			if(files !=null && files.length>0){
				String fileName= files[0].getOriginalFilename();
				if(StringUtils.isNotBlank(fileName)){
					//文件上传最终保存路径
					String finalFacePath = fileSpace + uploadPathDB +"/"+ fileName;
					//设置数据库保存路径
					File outFile = new File(finalFacePath);
					if(outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()){
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream,fileOutputStream);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (fileOutputStream !=null){
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}

			return IMoocJSONResult.ok();
	}
}
