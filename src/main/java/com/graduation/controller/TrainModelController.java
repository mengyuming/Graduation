package com.graduation.controller;

import com.graduation.bean.Index;
import com.graduation.service.IndexService;
import com.graduation.tools.HelpTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Controller
@RequestMapping("/train")
public class TrainModelController {
    @Autowired
    private HelpTest helpTest;
    
    @Autowired
    private IndexService indexService;

    @GetMapping("/trainModel")
    public void trainModel(String type, HttpServletRequest request, ServletContext applocation){
        ConcurrentHashMap<String, List<Index>> allIndex = indexService.getAllIndex();
        List<Index> index = allIndex.get(type);
        FileWriter fileWriter=null;
        BufferedWriter bufferedWriter=null;
        try{
            File oldFile = new File(request.getSession().getServletContext().getRealPath("/upload/") + type+"data");
            oldFile.delete();
            File newFile = new File(request.getSession().getServletContext().getRealPath("/upload/") + type+"data");
            fileWriter = new FileWriter(newFile);
            bufferedWriter=new BufferedWriter(fileWriter);
            if(index.size()>0&&type.equals("s")){
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
                    bufferedWriter.write(i.getQ15()+"-");
                    bufferedWriter.write(i.getQ16()+"-");
                    bufferedWriter.write(i.getQ17()+"-");
                    bufferedWriter.write(i.getQ18()+"-");
                    bufferedWriter.write(i.getQ19()+"-");
                    bufferedWriter.write(i.getQ20()+"-");
                    bufferedWriter.write(i.getOther()+"\n");
                }
                ConcurrentHashMap<String, List> predict1=helpTest.predict(newFile.getPath(),20);
                applocation.setAttribute("squestion",predict1);
            }else if(index.size()>0&&type.equals("t")){
                for(Index i:index){
                    bufferedWriter.write(i.getQ1()+",");
                    bufferedWriter.write(i.getQ2()+",");
                    bufferedWriter.write(i.getQ3()+",");
                    bufferedWriter.write(i.getQ4()+",");
                    bufferedWriter.write(i.getQ5()+",");
                    bufferedWriter.write(i.getQ6()+",");
                    bufferedWriter.write(i.getQ7()+",");
                    bufferedWriter.write(i.getQ8()+",");
                    bufferedWriter.write(i.getQ9()+",");
                    bufferedWriter.write(i.getQ10()+",");
                    bufferedWriter.write(i.getQ11()+",");
                    bufferedWriter.write(i.getQ12()+",");
                    bufferedWriter.write(i.getQ13()+",");
                    bufferedWriter.write(i.getQ14()+",");
                    bufferedWriter.write(i.getOther()+"\n");
                }
                ConcurrentHashMap<String, List> predict2 = helpTest.predict(newFile.getPath(), 14);
                applocation.setAttribute("tquestion",predict2);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
