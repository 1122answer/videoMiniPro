package com.lcf.service;

import com.lcf.pojo.Bgm;
import com.lcf.pojo.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BgmService {
    public List<Bgm> queryBgmList();
    /**
     * @Description: 根据id查询bgm信息
     */
    public Bgm queryBgmById(String bgmId);
}
