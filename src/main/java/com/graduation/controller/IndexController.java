package com.graduation.controller;

import com.graduation.bean.Course;
import com.graduation.bean.Index;
import com.graduation.bean.User;
import com.graduation.service.CourseService;
import com.graduation.service.IndexService;
import com.graduation.service.UserService;
import com.graduation.tools.ControMessage;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@ResponseBody
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexsystemService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    private ControMessage controMessage=new ControMessage();




    @ApiOperation("测试获取的权重值以及偏置值")
    @RequestMapping(value = "/testBp",method = RequestMethod.GET)
    public void helpTest(HttpServletRequest request,String type){
        System.out.println("查询权重，偏置");
        ServletContext app = request.getServletContext();
        if(type!=null){
            Map map = (Map)app.getAttribute(type);
            if(map!=null) {
                //获取权重集合
                List weight = (List) map.get("weight");
                System.out.println(weight.size());

                //获取输入层到隐含层的权重
//            List<Double> list1 = (List<Double>) weight.get(0);
//            System.out.println(list1.size());
//            System.out.println("输入层到隐含层的权重："+list1.size());
                //获取隐含层到输出层的权重
//            List<Double> list2 = (List<Double>) weight.get(1);
//            System.out.println("隐含层到输出层的权重:"+list2.size());
//            System.out.println(list2.size());
                List biase =(List) map.get("biase");
                System.out.println(biase.size());
                System.out.println("偏置量"+biase.size());
            }
        }

    }


    @ApiOperation("新增评论得分")
    @RequestMapping(value = "/addAllIndex",method = RequestMethod.POST)

    public ControMessage addAllIndex(Index indexsystem, HttpServletRequest request){
        System.out.println(indexsystem);
        List<Course> courseByCno = courseService.getCourseByCno(indexsystem.getCno());
        if(courseByCno==null||courseByCno.size()<=0){
            controMessage.contrlError().setMessage("操作失败！没有此课程编号！");
            return controMessage;
        }
        //这个type表明此课程是否为实验课  0表示为实验课
        Integer type = courseByCno.get(0).getType();
        if(type.equals(0)){
            indexsystem.setOther("实验课");
        }
        indexsystem.setOther("普通课");
        String tid = courseByCno.get(0).getTid().toString();
        User user1=userService.getUserById(Integer.valueOf(tid),"老师");
        indexsystem.setBnumber(user1.getStunum());

        String s = indexsystemService.addAllIndex(indexsystem, request);
        if(s.equals("success")){
            controMessage.contrlSuccess().setMessage("评价成功");
            return controMessage;
        }else{
            controMessage.contrlError().setMessage("操作失败！请先点击训练，完成之后在评价！");
            return controMessage;
        }
    }
    @ApiOperation("获取我所参与过的所有的评价信息【我是评价者，不是被评者】")
    @RequestMapping(value = "/getMyIndex",method = RequestMethod.GET)
    public ControMessage getMyIndex(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        CopyOnWriteArrayList<Index> myIndex = indexsystemService.getMyIndex(user,request);

        if(myIndex!=null&&myIndex.size()>0){
            controMessage.contrlSuccess().setMessage("获取信息成功").setAll().add(myIndex);
            return  controMessage;
        }
        controMessage.contrlError().setMessage("您未参与任何评价！");
        return  controMessage;
    }

    @ApiOperation("获取我被评价的所有信息【我是被评者】")
    @RequestMapping(value = "/getOtherIndex",method = RequestMethod.GET)
    public ControMessage getOtherIndex(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        CopyOnWriteArrayList<Index> myIndex = indexsystemService.getOtherIndex(user,request);

        if(myIndex!=null&&myIndex.size()>0){
            controMessage.contrlSuccess().setMessage("获取信息成功").setAll().add(myIndex);
            return  controMessage;
        }
        controMessage.contrlError().setMessage("您未参与任何评价！");
        return  controMessage;
    }

    @ApiOperation("获得所有的评教的结果")
    @RequestMapping(value = "/getAllIndex",method = RequestMethod.GET)
    public ControMessage getAllIndex(){
        ConcurrentHashMap<String,List<Index>> allIndex = indexsystemService.getAllIndex();
        if(allIndex!=null&&allIndex.size()>0){
            controMessage.contrlSuccess().setMessage("获取信息成功").setAll().add(allIndex);
            return  controMessage;
        }
        controMessage.contrlError().setMessage("还没有人参与过评教！");
        return  controMessage;
    }


    @ApiOperation("导出所有评价结果为excel")
    @RequestMapping(value = "/downloadData",method = RequestMethod.GET)
    public String downloadAllClassmate(String type,HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        ConcurrentHashMap<String, List<Index>> allIndex = indexsystemService.getAllIndex();
        if(allIndex==null||allIndex.size()<1){
            return "获取评教失败！！！";
        }
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
                Double total = index.getTotal();
                String s=null;
                if(total>0.8){
                    s="非常好";
                }else if(total>0.75){
                    s="良好";
                }
                else if(total>0.6){
                    s="一般";
                }
                else if(total>0.25){
                    s="差";
                }
                else if(total>0.75){
                    s="非常差";
                }
                row1.createCell(0).setCellValue(index.getPnumber());
                row1.createCell(1).setCellValue(index.getBnumber());
                row1.createCell(2).setCellValue(index.getCno());
                row1.createCell(3).setCellValue(s);
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
                row1.createCell(3).setCellValue(index.getTotal());
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
        return "成功！！！";
    }
}
