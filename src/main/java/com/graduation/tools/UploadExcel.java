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

        //获取一共有多少sheet，然后遍历
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            HSSFSheet sheet = workbook.getSheetAt(i);
            //获取sheet中一共有多少行，遍历行（注意第一行是标题）
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

            for (int j = 0; j < physicalNumberOfRows; j++) {
                if (j == 0) {
                    continue;//标题行
                }
                Sheet sheet1 = workbook.getSheetAt(0);
                for (Row row : sheet1) {
                    int index = 0;
                    for (Cell cell : row) {
                        //读取数据前设置单元格类型
                        cell.setCellType(CellType.STRING);
                        String value = cell.getStringCellValue();
                        System.out.print("value:" + value + " ");
                        index++;
                    }
                    System.out.println();
                }

            }

        }
        return null;
    }

}
