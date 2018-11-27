package com.lcf.service;

import com.lcf.mapper.UsersMapper;
import com.lcf.pojo.Users;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserSeviceImpl implements UserSevice {
    @Autowired
    private UsersMapper userMapper;
    @Autowired
    private Sid sid;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);
        Users result= userMapper.selectOne(user);
        return result == null?false :true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        String userId=sid.nextShort();
        //String userId="dffdsf";
        user.setId(userId);
        userMapper.insert(user);
    }

//    @Override
//    public Users queryUserForLogin(String userName, String password) {
//
//
//    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {

        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        Users result = userMapper.selectOneByExample(userExample);

        return result;
    }
    public void updateUserInfo(Users user){
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id",user.getId());
        userMapper.updateByExampleSelective(user,userExample);

    }
}
