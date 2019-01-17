package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.SalesVO;

public class SalesExcel {

	public boolean xlsxWriter(List<SalesVO> list, String saveDir) {
		//况农合 积己
		XSSFWorkbook workbook = new XSSFWorkbook();
		//况农矫飘 积己
		XSSFSheet sheet = workbook.createSheet();
		//青 积己
		XSSFRow row = sheet.createRow(0);
		//伎 积己
		XSSFCell cell;
		//庆歹 沥焊 备己
		cell = row.createCell(0);
		cell.setCellValue("朝楼");
		cell = row.createCell(1);
		cell.setCellValue("规巩 牢盔");
		cell = row.createCell(2);
		cell.setCellValue("概免");
		
		//府胶飘狼 size父怒 row甫 积己
		SalesVO svo;
		for(int rowldx = 0; rowldx<list.size(); rowldx++) {
			svo = list.get(rowldx);
			
			//青 积己
			row = sheet.createRow(rowldx+1);
			
			cell = row.createCell(0);
			cell.setCellValue(svo.getPay_date()+"");
			cell = row.createCell(1);
			cell.setCellValue(svo.getTotal_count());
			cell = row.createCell(2);
			cell.setCellValue(svo.getDay_sales());
		}
		
		//涝仿等 郴侩 颇老肺 静扁
		String strReportExelName = "daySales_"+System.currentTimeMillis()
		+".xlsx";
		File file = new File(saveDir+"\\"+strReportExelName);
		FileOutputStream fos = null;
		
		boolean saveSuccess;
		saveSuccess = false;
		try {
			fos = new FileOutputStream(file);
			if (fos != null) {
				workbook.write(fos);
				saveSuccess = true;
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(workbook != null)
					workbook.close();
				if(fos!=null)
					fos.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return saveSuccess;
	}
	
	
}
