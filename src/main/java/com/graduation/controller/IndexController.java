package com.graduation.controller;

import com.graduation.bean.Index;
import com.graduation.bean.User;
import com.graduation.service.IndexService;
import com.graduation.tools.BPNetwork;
import com.graduation.tools.ControMessage;
import com.graduation.tools.HelpTest;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexsystemService;

    @Autowired
    private HelpTest helpTest;

    private ControMessage controMessage=new ControMessage();


    @GetMapping("/testBp")
    public void helpTest(HttpServletRequest request, ServletContext application){
            ConcurrentHashMap<String, List> map = helpTest.predict("E:/bp/test1.txt", 3);
            List<List<Double>> weight = map.get("weight");
            for(List list:weight){
                list.stream().forEach(System.out::println);
            }
            List biase = map.get("biase");
            biase.stream().forEach(System.out::println);
    }


    @ApiOperation("获取所有的指标得分")
    @PostMapping("/addAllIndex")
    public ControMessage addAllIndex(Index indexsystem, HttpServletRequest request,ServletContext app){
        User user =(User) request.getSession().getAttribute("user");

        String s = indexsystemService.addAllIndex(indexsystem, request, app);
        if(s.equals("success")){
            controMessage.contrlSuccess().setMessage("评价成功");
            return controMessage;
        }else{
            controMessage.contrlError().setMessage("操作失败！请先点击训练，完成之后在评价！");
            return controMessage;
        }
    }
    @ApiOperation("获取我所参与过的所有的评价信息【我是评价者，不是被评者】")
    @GetMapping("/getMyIndex")
    public ControMessage getMyIndex(HttpServletRequest request){
        CopyOnWriteArrayList<Index> myIndex = indexsystemService.getMyIndex(request);

        if(myIndex!=null&&myIndex.size()>0){
            controMessage.contrlSuccess().setMessage("获取信息成功").setAll().add(myIndex);
            return  controMessage;
        }
        controMessage.contrlError().setMessage("您未参与任何评价！");
        return  controMessage;
    }

    @ApiOperation("获取我被评价的所有信息【我是被评者】")
    @GetMapping("/getOtherIndex")
    public ControMessage getOtherIndex(HttpServletRequest request){
        CopyOnWriteArrayList<Index> myIndex = indexsystemService.getOtherIndex(request);

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
        ConcurrentHashMap<String,List<Index>> allIndex = indexsystemService.getAllIndex();
        if(allIndex!=null&&allIndex.size()>0){
            controMessage.contrlSuccess().setMessage("获取信息成功").setAll().add(allIndex);
            return  controMessage;
        }
        controMessage.contrlError().setMessage("还没有人参与过评教！");
        return  controMessage;
    }


    @ApiOperation("到处导出所有结果为excel")
    @GetMapping("/downloadData")
    public void downloadAllClassmate(String type,HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        ConcurrentHashMap<String, List<Index>> allIndex = indexsystemService.getAllIndex();
        List<Index> indices = allIndex.get(type);
        int rowNum = 1;
        String fileName = "data"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        String[] headers = { "评价者编号", "被评者编号", "课程编号", "评价得分", "评价日期", "备注"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        if(allIndex!=null&&type.equals("s")){
            //在表中存放查询到的数据放入对应的列
            for (Index index : indices) {
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(index.getPnumber());
                row1.createCell(1).setCellValue(index.getBnumber());
                row1.createCell(2).setCellValue(index.getCno());
                row1.createCell(3).setCellValue(index.getOther());
                row1.createCell(4).setCellValue(index.getTimes());
                row1.createCell(5).setCellValue("学生评老师");
                rowNum++;
            }


        }else if(allIndex!=null&&type.equals("t")){

            //在表中存放查询到的数据放入对应的列
            for (Index index : indices) {
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(index.getPnumber());
                row1.createCell(1).setCellValue(index.getBnumber());
                row1.createCell(2).setCellValue(index.getCno());
                row1.createCell(3).setCellValue(index.getOther());
                row1.createCell(4).setCellValue(index.getTimes());
                row1.createCell(4).setCellValue("老师评老师");
                rowNum++;
            }
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }
}
