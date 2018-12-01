package com.lcf.controller;

import com.lcf.service.BgmService;
import com.lcf.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bgm")
@Api(value = "背景音乐接口" , tags = {"查询背景音乐"})
public class BgmController {
    @Autowired
    private BgmService bgmService;
    @PostMapping("list")
    @ApiOperation(value = "查询背景音乐" , notes = "查询背景音乐")
    public IMoocJSONResult list() {
        return IMoocJSONResult.ok(bgmService.queryBgmList());
    }

}
