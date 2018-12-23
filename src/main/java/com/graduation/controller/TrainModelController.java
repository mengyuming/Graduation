package com.graduation.controller;

import com.graduation.bean.Index;
import com.graduation.service.IndexService;
import com.graduation.tools.HelpTest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RestController
@RequestMapping("/train")
public class TrainModelController {
    @Autowired
    private HelpTest helpTest;
    
    @Autowired
    private IndexService indexService;

    @ApiOperation("训练权重占比")
    @GetMapping("/trainModel")
    public Object trainModel(String type, HttpServletRequest request){
        ServletContext servletContext = request.getServletContext();
        ConcurrentHashMap<String, List<Index>> allIndex = indexService.getAllIndex();
        List<Index> index = allIndex.get(type);
        System.out.println("输出从数据库获得的指标评价得分");
        index.stream().forEach(System.out::println);
        System.out.println("输出完毕，开始训练模型");
        FileWriter fileWriter=null;
        BufferedWriter bufferedWriter=null;
        try{
            File newFile = new File("E:/bp/ttt1.txt");
            if(!newFile.exists()){
                newFile.createNewFile();
            }
            fileWriter = new FileWriter(newFile);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.flush();
            if(index.size()>0&&type.equals("s")){
                System.out.println("进入文件输入系统");
                for(Index i:index){
                    System.out.println("开始写入内容");
                    bufferedWriter.write(i.getQ1()+"-");
                    bufferedWriter.write(i.getQ2()+"-");
                    bufferedWriter.write(i.getQ3()+"-");
                    bufferedWriter.write(i.getQ4()+"-");
                    bufferedWriter.write(i.getQ5()+"-");
                    bufferedWriter.write(i.getQ6()+"-");
                    bufferedWriter.write(i.getQ7()+"-");
                    bufferedWriter.write(i.getQ8()+"-");
                    bufferedWriter.write(i.getQ9()+"-");
                    bufferedWriter.write(i.getQ10()+"-");
                    bufferedWriter.write(i.getQ11()+"-");
                    bufferedWriter.write(i.getQ12()+"-");
                    bufferedWriter.write(i.getQ13()+"-");
                    bufferedWriter.write(i.getQ14()+"-");
                    bufferedWriter.write(i.getQ15()+"-");
                    bufferedWriter.write(i.getQ16()+"-");
                    bufferedWriter.write(i.getQ17()+"-");
                    bufferedWriter.write(i.getQ18()+"-");
                    bufferedWriter.write(i.getQ19()+"-");
                    bufferedWriter.write(i.getQ20()+"-");
                    bufferedWriter.write(i.getTotal()+"");
                    bufferedWriter.newLine();
                }
                System.out.println("写入完毕，开始训练模型");
                bufferedWriter.close();
                fileWriter.close();
                File newFile1 = new File("E:/bp/ttt1.txt");
                ConcurrentHashMap<String, List> map = helpTest.predict(newFile1.getPath(), 20);
                List<List<Double>> weight = map.get("weight");
                servletContext.setAttribute("s",map);
                for(List list:weight){
                    list.stream().forEach(System.out::println);
                }
                List biase = map.get("biase");
                biase.stream().forEach(System.out::println);
                return map;
            }else if(index.size()>0&&type.equals("t")){
                for(Index i:index){
                    bufferedWriter.write(i.getQ1()+"-");
                    bufferedWriter.write(i.getQ2()+"-");
                    bufferedWriter.write(i.getQ3()+"-");
                    bufferedWriter.write(i.getQ4()+"-");
                    bufferedWriter.write(i.getQ5()+"-");
                    bufferedWriter.write(i.getQ6()+"-");
                    bufferedWriter.write(i.getQ7()+"-");
                    bufferedWriter.write(i.getQ8()+"-");
                    bufferedWriter.write(i.getQ9()+"-");
                    bufferedWriter.write(i.getQ10()+"-");
                    bufferedWriter.write(i.getQ11()+"-");
                    bufferedWriter.write(i.getQ12()+"-");
                    bufferedWriter.write(i.getQ13()+"-");
                    bufferedWriter.write(i.getQ14()+"-");
                    bufferedWriter.write(i.getTotal()+"");
                    bufferedWriter.newLine();
                }

                System.out.println("写入完毕，开始训练模型");
                bufferedWriter.close();
                fileWriter.close();
                File newFile1 = new File("E:/bp/ttt1.txt");
                ConcurrentHashMap<String, List> map = helpTest.predict(newFile1.getPath(), 20);
                List<List<Double>> weight = map.get("weight");
                servletContext.setAttribute("t",map);
                for(List list:weight){
                    list.stream().forEach(System.out::println);
                }
                List biase = map.get("biase");
                biase.stream().forEach(System.out::println);
                return map;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
