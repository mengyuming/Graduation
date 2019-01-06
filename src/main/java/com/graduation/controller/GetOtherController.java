package com.graduation.controller;

import com.graduation.bean.Depart;
import com.graduation.bean.Professional;
import com.graduation.service.GetOtherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/other")
public class GetOtherController {

    @Autowired
    private GetOtherService getOtherService;

    @ApiOperation("根据学校获取所有学院列表")
    @RequestMapping(value = "/getAllDepart",method = RequestMethod.GET)
    public Object getAllDepart(String name){
        List<Depart> list=getOtherService.getAllDepart(name);
        return list;
    }

    @ApiOperation("根据学院获取所有专业列表")
    @RequestMapping(value = "/getAllDepart",method = RequestMethod.GET)
    public Object getAllProfession(String name){
        List<Professional> list=getOtherService.getAllProfession(name);
        return list;
    }
}
