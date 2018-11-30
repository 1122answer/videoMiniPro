package com.lcf.service.impl;

import com.lcf.mapper.BgmMapper;
import com.lcf.mapper.UsersMapper;
import com.lcf.pojo.Bgm;
import com.lcf.pojo.Users;
import com.lcf.service.BgmSevice;
import com.lcf.service.UserSevice;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BgmSeviceImpl implements BgmSevice {
    @Autowired
    private BgmMapper bgmMapper;
    @Autowired
    private Sid sid;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bgm> queryBgmList(){

        return bgmMapper.selectAll();
    }
}
