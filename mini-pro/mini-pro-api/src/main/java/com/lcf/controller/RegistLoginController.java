package com.lcf.controller;

import com.lcf.pojo.vo.UsersVO;
import com.lcf.service.UserService;
import com.lcf.pojo.Users;
import com.lcf.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.lcf.utils.IMoocJSONResult;

import java.util.UUID;

@RestController
@Api(value = "用户注册登录接口" , tags = {"注册登录"})
public class RegistLoginController extends BasicController{

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户注册" , notes = "用户注册接口")
	@PostMapping("/regist")
	public IMoocJSONResult regist (@RequestBody  Users user) throws  Exception{
		//判断用户名和密码必须不为空
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
			return IMoocJSONResult.errorMsg("用户名或密码不能为空！");
		}
		//判断用户名是否存在
		if(userService.queryUsernameIsExist(user.getUsername())){
			return IMoocJSONResult.errorMsg("用户名已存在！");
		}else {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFansCounts(0);
			user.setFollowCounts(0);
			user.setReceiveLikeCounts(0);
			userService.saveUser(user);
		}
//		//保存用户名
//		String uniquerToken = UUID.randomUUID().toString();
//		redis.set(USER_REDIS_SESSION + ":" + user.getId(), uniquerToken,1000*60*30);
//		UsersVO usersVO = new UsersVO();
//		//为什么是空对象，需要拷贝
//		BeanUtils.copyProperties(user,usersVO);
//		usersVO.setUserToken(uniquerToken);
        UsersVO usersVO= setUserRedisSessionToken(user);
		return  IMoocJSONResult.ok(usersVO);
	}

	public UsersVO  setUserRedisSessionToken( Users userModel){
        //保存用户名
        String uniquerToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniquerToken,1000*60*30);
        UsersVO usersVO = new UsersVO();
        //为什么是空对象，需要拷贝
        BeanUtils.copyProperties(userModel,usersVO);
        usersVO.setUserToken(uniquerToken);
        return usersVO;
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
		Users userResult = userService.queryUserForLogin(userName, MD5Utils.getMD5Str(password));

		if(userResult !=null){
			userResult.setPassword("");
            UsersVO usersVO= setUserRedisSessionToken(userResult);
			return IMoocJSONResult.ok(usersVO);
		}else {
			return IMoocJSONResult.errorMsg("用户名或密码不正确，请重试！");
		}

	}

	@ApiOperation(value = "用户注销" , notes = "用户注销接口")
	@ApiImplicitParam(name = "userId", value = "用户id" ,required = true ,dataType = "String" ,paramType = "query")
	@PostMapping("/logOut")
	public IMoocJSONResult logOut  (String userId) throws  Exception{
			redis.del(USER_REDIS_SESSION + ":" + userId);
			return IMoocJSONResult.ok();
	}
}
