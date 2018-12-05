package com.lcf.service;

import com.lcf.pojo.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean queryUsernameIsExist(String username);
    public void saveUser(Users user);
    public Users queryUserForLogin(String userName, String password);
/*
* @Descripion:修改用户信息
*
* */
    public void updateUserInfo(Users user);
    public Users queryUserInfo(String userId);
    /**
     * @Description: 查询用户是否喜欢点赞视频
     */
    public boolean isUserLikeVideo(String userId, String videoId);

}
