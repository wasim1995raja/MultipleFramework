package com.example.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excelutility {
	
	private Workbook workbook;
	
	public void excelFileInitialization(String filePath)
	{
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			workbook = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSingleDataFromExcelFile(String sheetName, int rownum, int cellnum)
	{
		
		
		String data =workbook.getSheet(sheetName).getRow(rownum).getCell(cellnum).getStringCellValue();
		return data;
	}
	
	public Map<String, String> getListofDataFromExcelFile(String sheetName, String expectedTest)
	{
		Sheet sheet = workbook.getSheet(sheetName);
		Map<String,String> map = new HashMap<>();
		
		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String actualTestName= sheet.getRow(i).getCell(1).getStringCellValue();
			
			if(actualTestName.equals(expectedTest))
			{
			for(int j=i; j<=sheet.getLastRowNum(); j++)
			{
				String key= sheet.getRow(j).getCell(2).getStringCellValue();
				String value = sheet.getRow(j).getCell(3).getStringCellValue();
				map.put(key, value);
				
				if(key.equals("####"))
					break;
				
				
			}
			
			}
			
			break;
		}
		return map;
		
	}
public void setDataToExcel(String data, int cellNum, int rownum, String path, String sheetName) {
		
		Sheet sheet = workbook.getSheet(sheetName);
		Cell cell = sheet.getRow(rownum).createCell(cellNum);
		cell.setCellValue(data);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
public void setDataToExcel(String expectedTestName, String status, String path, String sheetName) {
	DataFormatter df = new DataFormatter();
	Sheet sheet = workbook.getSheet(sheetName);
	for(int i=0; i< sheet.getLastRowNum();i++) {
		if(df.formatCellValue(sheet.getRow(i).getCell(1)).contains(expectedTestName)) {
			sheet.getRow(i).createCell(4).setCellValue(status);
		}
	}
	FileOutputStream fos = null;
	try {
		fos = new FileOutputStream(path);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	try {
		workbook.write(fos);
	} catch (IOException e) {
		e.printStackTrace();
	}
	try {
		fos.flush();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public void closeWorkbook() {
	try {
		workbook.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

}
