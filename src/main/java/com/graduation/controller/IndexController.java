package com.graduation.controller;

import com.graduation.bean.Index;
import com.graduation.service.IndexService;
import com.graduation.tools.ControMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexsystemService;

    private ControMessage controMessage=new ControMessage();

    @ApiOperation("获取所有的指标得分")
    @PostMapping("/addAllIndex")
    public ControMessage addAllIndex(Index indexsystem){
        indexsystemService.addAllIndex(indexsystem);
        controMessage.contrlSuccess().setMessage("评价成功");
        return controMessage;
    }
    @ApiOperation("获取我所参与过的所有的评价信息【我是评价者，不是被评者】")
    @GetMapping("/getMyIndex")
    public ControMessage getMyIndex(){
        List<Index> myIndex = indexsystemService.getMyIndex();

        if(myIndex!=null&&myIndex.size()>0){
            controMessage.contrlSuccess().setMessage("获取信息成功").setAll().add(myIndex);
            return  controMessage;
        }
        controMessage.contrlError().setMessage("您未参与任何评价！");
        return  controMessage;
    }

    @ApiOperation("获取我被评价的所有信息【我是被评者】")
    @GetMapping("/getOtherIndex")
    public ControMessage getOtherIndex(){
        List<Index> myIndex = indexsystemService.getOtherIndex();

        if(myIndex!=null&&myIndex.size()>0){
            controMessage.contrlSuccess().setMessage("获取信息成功").setAll().add(myIndex);
            return  controMessage;
        }
        controMessage.contrlError().setMessage("您未参与任何评价！");
        return  controMessage;
    }

    @ApiOperation("获得所有的评教的结果")
    @GetMapping("getAllIndex")
    public ControMessage getAllIndex(){
        List<Index> allIndex = indexsystemService.getAllIndex();
        if(allIndex!=null&&allIndex.size()>0){
            controMessage.contrlSuccess().setMessage("获取信息成功").setAll().add(allIndex);
            return  controMessage;
        }
        controMessage.contrlError().setMessage("还没有人参与过评教！");
        return  controMessage;
    }
}
