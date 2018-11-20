package com.lcf.controller;

import com.lcf.service.UserSevice;
import com.lcf.pojo.Users;
import com.lcf.utils.MD5Utils;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.lcf.utils.IMoocJSONResult;
@RestController
@Api(value = "用户注册登录接口" , tags = {"注册登录"})
public class RegistLoginController {

	@Autowired
	private UserSevice userSevice;

	@ApiOperation(value = "用户注册" , notes = "用户注册接口")
	@PostMapping("/regist")
	public IMoocJSONResult regist (@RequestBody  Users user) throws  Exception{
		//判断用户名和密码必须不为空
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
			return IMoocJSONResult.errorMsg("用户名或密码不能为空！");
		}
		//判断用户名是否存在
		if(userSevice.queryUsernameIsExist(user.getUsername())){
			return IMoocJSONResult.errorMsg("用户名已存在！");
		}else {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFansCounts(0);
			user.setFollowCounts(0);
			user.setReceiveLikeCounts(0);
			userSevice.saveUser(user);
		}
		//保存用户名
		return  IMoocJSONResult.ok();
	}

	@ApiOperation(value = "用户登录" , notes = "用户登录接口")
	@PostMapping("/login")
	public IMoocJSONResult login  (@RequestBody  Users user) throws  Exception{
		String userName = user.getUsername();
		String password = user.getPassword();
		//判断用户名和密码必须不为空
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
			return IMoocJSONResult.errorMsg("用户名或密码不能为空！");
		}
		//判断用户名是否存在
		Users userResult = userSevice.queryUserForLogin(userName, MD5Utils.getMD5Str(password));

		if(userResult !=null){
			userResult.setPassword("");
			return IMoocJSONResult.ok(userResult);
		}else {
			return IMoocJSONResult.errorMsg("用户名或密码不正确，请重试！");
		}

	}
}
