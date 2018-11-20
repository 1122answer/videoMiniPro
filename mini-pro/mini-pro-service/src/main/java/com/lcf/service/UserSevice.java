package com.lcf.service;

import com.lcf.pojo.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserSevice {
    public boolean queryUsernameIsExist(String username);
    public void saveUser(Users user);
}
