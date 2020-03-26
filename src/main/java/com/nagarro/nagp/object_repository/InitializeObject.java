package com.nagarro.nagp.object_repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.nagarro.nagp.core.Initialize;
import com.nagarro.nagp.customtype.ElementType;
import com.nagarro.nagp.customtype.TypeCategoryPage;
import com.nagarro.nagp.customtype.TypeHeader;
import com.nagarro.nagp.customtype.TypeHomePage;
import com.nagarro.nagp.customtype.TypeSearchResultPage;
import com.nagarro.nagp.elements.PageElements;
import com.nagarro.nagp.elements.Pages;

public class InitializeObject {

	private static File file = null;
	private static FileInputStream fileInputStream = null;
	private static String objectInfoFile = "\\src\\main\\resources\\object_info.xlsx";
	private static String workingDirectory = System.getProperty("user.dir");
	private static Workbook workBook = null;
	private static Sheet sheet = null;
	private static Row row = null;
	static int rowCount = 0;
	static String sheetName = "";

	public static ElementType elementType;
	public TypeHeader typeHeader;
	public TypeCategoryPage typeCategoryPage;
	public TypeSearchResultPage typeSearchResultPage;

	public static HashMap<String, TypeHeader> typeHeaderMap = new HashMap<String, TypeHeader>();
	public static HashMap<String, TypeCategoryPage> typeCategoryPageMap = new HashMap<String, TypeCategoryPage>();
	public static HashMap<String, TypeSearchResultPage> typeSearchResultPageMap = new HashMap<String, TypeSearchResultPage>();

	public void init() throws IOException {
		file = new File(workingDirectory + objectInfoFile);
		fileInputStream = new FileInputStream(file);
		workBook = new XSSFWorkbook(fileInputStream);
		int sheetCount = workBook.getNumberOfSheets();

		for (int sheetIndexer = 0; sheetIndexer < sheetCount; ++sheetIndexer) {
			sheetName = workBook.getSheetName(sheetIndexer);

			initTypes();

		}
	}

	private void initTypes() {
		sheet = workBook.getSheet(sheetName);
		switch (sheetName) {
		case "Header": {
			initTypeObjects(new TypeHeader());
			break;
		}
		case "CategoryPage": {
			initTypeObjects(new TypeCategoryPage());
			break;
		}
		case "SearchResultPage": {
			initTypeObjects(new TypeSearchResultPage());
			break;
		}
		default:

		}

	}

	private void initTypeObjects(ElementType elementType) {
		
		if (elementType instanceof TypeHeader) {
			rowCount = sheet.getLastRowNum();
			for (int rowIndexer = 0; rowIndexer < rowCount; ++rowIndexer) {
				typeHeader = new TypeHeader();
				row = sheet.getRow(rowIndexer + 1);
				typeHeader.setElement(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(),
						row.getCell(2).getStringCellValue());
				typeHeaderMap.put(row.getCell(0).getStringCellValue(), typeHeader);
			}
		} else if (elementType instanceof TypeSearchResultPage) {
			rowCount = sheet.getLastRowNum();
		for (int rowIndexer = 0; rowIndexer < rowCount; ++rowIndexer) {
				typeSearchResultPage = new TypeSearchResultPage();
				row = sheet.getRow(rowIndexer + 1);
				typeSearchResultPage.setElement(row.getCell(0).getStringCellValue(),
						row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue());
				typeSearchResultPageMap.put(row.getCell(0).getStringCellValue(), typeSearchResultPage);
			}
		} else if (elementType instanceof TypeCategoryPage) {
			rowCount = sheet.getLastRowNum();
			for (int rowIndexer = 0; rowIndexer < rowCount; ++rowIndexer) {
				typeCategoryPage = new TypeCategoryPage();
				row = sheet.getRow(rowIndexer + 1);
				typeCategoryPage.setElement(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(),
						row.getCell(2).getStringCellValue());
				typeCategoryPageMap.put(row.getCell(0).getStringCellValue(), typeCategoryPage);
			}
		} else {

		}
	}

	public static WebElement getElement(Pages page, PageElements pageElement) {
		switch (page.toString()) {
		case "Header": {
			elementType = typeHeaderMap.get(pageElement.toString());
			break;
		}
		case "CategoryPage": {
			elementType = typeCategoryPageMap.get(pageElement.toString());
			break;
		}
		case "SearchResultPage": {
			elementType = typeSearchResultPageMap.get(pageElement.toString());
			break;
		}
		}

		switch (elementType.identificationType) {
		case "Xpath": {
			return Initialize.getDriver().findElement(By.xpath(elementType.identificationValue));
		}
		case "ID": {
			return Initialize.getDriver().findElement(By.id(elementType.identificationValue));
		}
		case "Class Name": {
			return Initialize.getDriver().findElement(By.className(elementType.identificationValue));
		}
		case "CSS Selector": {
			return Initialize.getDriver().findElement(By.cssSelector(elementType.identificationValue));
		}
		case "Link Text": {
			return Initialize.getDriver().findElement(By.linkText(elementType.identificationValue));
		}
		case "Partial Link Text": {
			return Initialize.getDriver().findElement(By.partialLinkText(elementType.identificationValue));
		}
		case "Name": {
			return Initialize.getDriver().findElement(By.name(elementType.identificationValue));
		}
		case "Tag Name": {
			return Initialize.getDriver().findElement(By.tagName(elementType.identificationValue));
		}
		default:
			return null;
		}

	}
}
