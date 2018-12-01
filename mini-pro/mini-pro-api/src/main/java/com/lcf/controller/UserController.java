package com.lcf.controller;

import com.lcf.pojo.Users;
import com.lcf.pojo.vo.UsersVO;
import com.lcf.service.UserService;
import com.lcf.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@Api(value = "用户相关业务接口" , tags = {"业务详情"})
@RequestMapping("/user")
public class UserController extends BasicController{

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户上传头像" , notes = "用户上传头像接口")
	@ApiImplicitParam(name = "userId", value = "用户id" ,required = true ,dataType = "String" ,paramType = "query")
	@PostMapping("/uploadFace")
	public IMoocJSONResult uploadFace  (String userId,
		@RequestParam("file") MultipartFile[] files) throws  Exception{
		if(StringUtils.isBlank(userId)){
			return IMoocJSONResult.errorMsg("用户ID不能为空");
		}
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
					uploadPathDB+=("/"+fileName);
					File outFile = new File(finalFacePath);
					if(outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()){
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
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
		Users user=new Users();
		user.setId(userId);
		user.setFaceImage(uploadPathDB);
		userService.updateUserInfo(user);
		return IMoocJSONResult.ok(uploadPathDB);
	}
	@ApiOperation(value = "查询用户信息" , notes = "查询用户信息接口")
	@ApiImplicitParam(name = "userId", value = "用户id" ,required = true ,dataType = "String" ,paramType = "query")
	@PostMapping("/query")
	public IMoocJSONResult query(String userId) throws Exception{
		if(StringUtils.isBlank(userId)){
			return IMoocJSONResult.errorMsg("用户ID不能为空");
		}
		Users userInfo= userService.queryUserInfo(userId);
		UsersVO usersVO=new UsersVO();
		BeanUtils.copyProperties(userInfo,usersVO);
		return IMoocJSONResult.ok(usersVO);
	}
}
