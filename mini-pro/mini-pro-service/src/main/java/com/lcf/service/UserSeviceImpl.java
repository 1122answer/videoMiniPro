package com.lcf.service;

import com.lcf.mapper.UsersMapper;
import com.lcf.pojo.Users;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSeviceImpl implements UserSevice {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);
        Users result= usersMapper.selectOne(user);
        return result == null?false :true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        String userId=sid.nextShort();
        //String userId="dffdsf";
        user.setId(userId);
        usersMapper.insert(user);
    }
}
