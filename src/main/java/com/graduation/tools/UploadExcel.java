package com.graduation.tools;

import com.graduation.bean.Site;
import com.graduation.dao.Site1Dao;
import com.sun.istack.internal.logging.Logger;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/excel")
public class UploadExcel {

    @Autowired
    private Site1Dao site1Dao;

 //   @RequestMapping(value="/upload",method = RequestMethod.POST)
//    public String  uploadLibExcel(HttpServletRequest request, @RequestParam("file")MultipartFile file, Model model) throws IOException {
//        String originalFilename = file.getOriginalFilename();
//        Logger logger = Logger.getLogger(this.getClass());
//        System.out.println(originalFilename);
//        logger.info(originalFilename);
//        String contentType = file.getContentType();
//        logger.info(contentType);
//        System.out.println(contentType);
//        HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
//
//        //获取所有的工作表的的数量
//        int numberOfSheets = workbook.getNumberOfSheets();
//        System.out.println(numberOfSheets);
//        for (int i = 0; i < 1; i++) {
//            //获取一个sheet也就是一个工作簿
//            HSSFSheet sheet = workbook.getSheetAt(i);
//            if(i==0){
//                HSSFRow titleRow = sheet.getRow(0);
//                //表头那个单元格
//                HSSFCell titleCell = titleRow.getCell(0);
//
//                String title = titleCell.getStringCellValue();
//
//                System.out.println("标题是："+title);
//            }
//            //获取sheet中一共有多少行，遍历行（注意第一行是标题）
//            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//            System.out.println(physicalNumberOfRows);
//            for (int j = 2; j < physicalNumberOfRows; j++) {
//                HSSFRow titleRow = sheet.getRow(j);
//                    for (Cell cell : titleRow) {
//                        //读取数据前设置单元格类型
//                        cell.setCellType(CellType.STRING);
//                        String value = cell.getStringCellValue();
//                        System.out.print("value:" + value + " ");
//                    }
//                    System.out.println("----------------");
//            }
//
//        }
//        return null;
//    }


    @RequestMapping(value="/upload1",method = RequestMethod.POST)
    public String  uploadLibExcel1(HttpServletRequest request, @RequestParam("file")MultipartFile file, Model model) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Logger logger = Logger.getLogger(this.getClass());
        System.out.println(originalFilename);
        logger.info(originalFilename);
        String contentType = file.getContentType();
        logger.info(contentType);
        System.out.println(contentType);
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        //获取所有的工作表的的数量
        int numberOfSheets = workbook.getNumberOfSheets();
        System.out.println(numberOfSheets);
        for (int i = 0; i < 1; i++) {
            //获取一个sheet也就是一个工作簿
            XSSFSheet sheet = workbook.getSheetAt(i);
            if(i==0){
                XSSFRow titleRow = sheet.getRow(0);
                //表头那个单元格
                XSSFCell titleCell = titleRow.getCell(0);

                String title = titleCell.getStringCellValue();

                System.out.println("标题是："+title);
            }
            //获取sheet中一共有多少行，遍历行（注意第一行是标题）
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

            for (int j = 1; j < physicalNumberOfRows; j++) {
                XSSFRow titleRow = sheet.getRow(j);
                int physicalNumberOfCells = titleRow.getPhysicalNumberOfCells();
                System.out.println(physicalNumberOfCells+"::physicalNumberOfCells");
                Site site=new Site();
                List<String> list=new ArrayList();
                for (Cell cell : titleRow) {
                    //读取数据前设置单元格类型
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    if(value!=null&&!value.equals(" ")){
                        list.add(value);
                    }else{
                        list.add("null");
                    }


                }
                site.setCno(list.get(0)).setName(list.get(1))
                        .setCity(list.get(2)).setLongitude(list.get(3)).setLatitude(list.get(4));
                System.out.println(site);
                site1Dao.addSite(site);
            }

        }
        return null;
    }


    @RequestMapping(value="/upload2",method = RequestMethod.POST)
    public String  uploadLibExcel2(HttpServletRequest request, @RequestParam("file")MultipartFile file, Model model) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Logger logger = Logger.getLogger(this.getClass());
        System.out.println(originalFilename);
        logger.info(originalFilename);
        String contentType = file.getContentType();
        logger.info(contentType);
        System.out.println(contentType);
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        //获取所有的工作表的的数量
        int numberOfSheets = workbook.getNumberOfSheets();
        System.out.println(numberOfSheets);
        for (int i = 0; i < 1; i++) {
            //获取一个sheet也就是一个工作簿
            XSSFSheet sheet = workbook.getSheetAt(i);
            if(i==0){
                XSSFRow titleRow = sheet.getRow(0);
                //表头那个单元格
                XSSFCell titleCell = titleRow.getCell(0);

                String title = titleCell.getStringCellValue();

                System.out.println("标题是："+title);
            }
            //获取sheet中一共有多少行，遍历行（注意第一行是标题）
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

            for (int j = 1; j < physicalNumberOfRows; j++) {
                XSSFRow titleRow = sheet.getRow(j);
                int physicalNumberOfCells = titleRow.getPhysicalNumberOfCells();
                System.out.println(physicalNumberOfCells+"::physicalNumberOfCells");
                String date=null,hour=null,type=null;
                Site site1=new Site();
                for(int h=0 ; h<physicalNumberOfCells ; h++){
                    XSSFCell cell = titleRow.getCell(h);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    if(h==0){
                        date=value;
                    }else if(h==1){
                        hour=value;
                    }else if(h==3){
                        type=value;
                    }else{
                        XSSFCell cell1 = sheet.getRow(0).getCell(h);
                        cell1.setCellType(CellType.STRING);
                        String cno = cell1.getStringCellValue();
                        site1.setDate(date).setHour(hour).setType(type).setValue(value).setCno(cno);
                        site1Dao.updateSite(site1);
                    }
                }
            }

        }
        return null;
    }
}
