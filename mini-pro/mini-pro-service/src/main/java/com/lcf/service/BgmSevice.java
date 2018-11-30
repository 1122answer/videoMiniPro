package com.lcf.service;

import com.lcf.pojo.Bgm;
import com.lcf.pojo.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BgmSevice {
    public List<Bgm> queryBgmList();

}
