package org.suresec.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author wcc
 * @time 2019-09-18 11:18
 * @description 通过excel批量下载网络文件，顺道改个名字
 */
public class POIReadExcelTool {

    public static void main(String[] args) throws Exception {
        List<Map> list = readXls();
        for(Map stu : list){
        	downloadAmachment(stu.get("filePath")+"",stu.get("fileName")+"");
        	
        }
    }

    public static List<Map> readXls() throws Exception {
        InputStream is = new FileInputStream("C:/Users/19820/Desktop/download.xlsx");

        HSSFWorkbook excel = new HSSFWorkbook(is);
        Map stu = null;
        List<Map> list = new ArrayList<Map>();
        
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < excel.getNumberOfSheets(); numSheet++) {
            HSSFSheet sheet = excel.getSheetAt(numSheet);
            if (sheet == null)
                continue;
            // 循环行Row
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                if (row == null)
                    continue;
                stu = new HashMap<String,String>();
                
                HSSFCell cell0 = row.getCell(0);
                HSSFCell cell1 = row.getCell(1);
                if (cell0 == null)
                    break;
                stu.put("fileName", cell0.getStringCellValue());
                stu.put("filePath", cell1.getStringCellValue());
                list.add(stu);
            }
        }

        return list;
    }

    public static String downloadAmachment(String downLoadPath, String realFileName) throws Exception {
		try {
			File file = new File("E:/"+realFileName);
			if(file.exists()) {
				return null;
			}
			
			BufferedInputStream bis = null;
			FileOutputStream bos = null;
            List<Map> list = POIReadExcelTool.readXls();
            int i = 0;
            URL url = new URL(downLoadPath);
            //获取此路径的连接
            URLConnection conn = url.openConnection();
            Long fileLength = conn.getContentLengthLong();//获取文件大小
			bis = new BufferedInputStream(conn.getInputStream());//构造读取流
			bos = new FileOutputStream(file,false);//构造输出流
			byte[] buff = new byte[1024];
			int bytesRead;
			//每次读取缓存大小的流，写到输出流
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
           
			bis.close();
			bos.close();
			System.out.println(downLoadPath+"___"+realFileName);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
