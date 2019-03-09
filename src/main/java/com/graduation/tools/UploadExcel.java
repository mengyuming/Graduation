package com.graduation.tools;

import com.sun.istack.internal.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@ResponseBody
@RequestMapping("/excel")
public class UploadExcel {

    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public String  uploadLibExcel(HttpServletRequest request, @RequestParam("file")MultipartFile file, Model model) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Logger logger = Logger.getLogger(this.getClass());
        System.out.println(originalFilename);
        logger.info(originalFilename);
        String contentType = file.getContentType();
        logger.info(contentType);
        System.out.println(contentType);
        HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));

        //获取所有的工作表的的数量
        int numberOfSheets = workbook.getNumberOfSheets();
        System.out.println(numberOfSheets);
        for (int i = 0; i < 1; i++) {
            //获取一个sheet也就是一个工作簿
            HSSFSheet sheet = workbook.getSheetAt(i);
            if(i==0){
                HSSFRow titleRow = sheet.getRow(0);
                //表头那个单元格
                HSSFCell titleCell = titleRow.getCell(0);

                String title = titleCell.getStringCellValue();

                System.out.println("标题是："+title);
            }
            //获取sheet中一共有多少行，遍历行（注意第一行是标题）
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            System.out.println(physicalNumberOfRows);
            for (int j = 2; j < physicalNumberOfRows; j++) {
                HSSFRow titleRow = sheet.getRow(j);
                    for (Cell cell : titleRow) {
                        //读取数据前设置单元格类型
                        cell.setCellType(CellType.STRING);
                        String value = cell.getStringCellValue();
                        System.out.print("value:" + value + " ");
                    }
                    System.out.println("----------------");
            }

        }
        return null;
    }

}
