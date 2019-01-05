package com.graduation.controller;

import com.graduation.service.TrainModelServcice;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


@Controller
@ResponseBody
@RequestMapping("/train")
public class TrainModelController {


    @Autowired
    private TrainModelServcice trainModelServcice;

    @ApiOperation("更新权重占比")
    @RequestMapping(value = "/updateModel",method = RequestMethod.GET)
    public Object updateModel(String type, HttpServletRequest request){
        trainModelServcice.updateModel(type, request);

        return "正在更新，请稍等...";
    }




    @ApiOperation("训练权重占比")
    @RequestMapping(value = "/trainModel",method = RequestMethod.GET)
    public Object trainModel(String type, HttpServletRequest request){
        trainModelServcice.trainModel(type,request);

        return "正在训练，请稍等";
    }
}
