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
		//��ũ�� ����
		XSSFWorkbook workbook = new XSSFWorkbook();
		//��ũ��Ʈ ����
		XSSFSheet sheet = workbook.createSheet();
		//�� ����
		XSSFRow row = sheet.createRow(0);
		//�� ����
		XSSFCell cell;
		//��� ���� ����
		cell = row.createCell(0);
		cell.setCellValue("��¥");
		cell = row.createCell(1);
		cell.setCellValue("�湮 �ο�");
		cell = row.createCell(2);
		cell.setCellValue("����");
		
		//����Ʈ�� size��ŭ row�� ����
		SalesVO svo;
		for(int rowldx = 0; rowldx<list.size(); rowldx++) {
			svo = list.get(rowldx);
			
			//�� ����
			row = sheet.createRow(rowldx+1);
			
			cell = row.createCell(0);
			cell.setCellValue(svo.getPay_date()+"");
			cell = row.createCell(1);
			cell.setCellValue(svo.getTotal_count());
			cell = row.createCell(2);
			cell.setCellValue(svo.getDay_sales());
		}
		
		//�Էµ� ���� ���Ϸ� ����
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
