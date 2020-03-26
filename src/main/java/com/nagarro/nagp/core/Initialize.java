package com.nagarro.nagp.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.nagarro.nagp.exceptions.BrowserInitizationException;
import com.nagarro.nagp.object_repository.InitializeObject;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Initialize {
	private static File file = null;
	private static FileInputStream fileInputStream = null;
	private static Properties properties = null;
	private static WebDriver driver = null;
	private static String propertyFilePath = "\\src\\main\\resources\\config.properties";
	private static String browser = null;
	private static String workingDirectory = System.getProperty("user.dir");
	private static InitializeObject initializeObject = new InitializeObject();

	public static void init() throws IOException, BrowserInitizationException {

		file = new File(workingDirectory + propertyFilePath);
		fileInputStream = new FileInputStream(file);
		properties = new Properties();
		properties.load(fileInputStream);
		startBrowser();
		initializeObject.init();

		driver.manage().window().maximize();
		getDriver().get(properties.getProperty("application_under_test"));
	}

	private static void startBrowser() throws BrowserInitizationException {
		browser = properties.getProperty("browser").toLowerCase();

		if (browser.compareTo("chrome") == 0) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.compareTo("firefox") == 0) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.compareTo("ie") == 0 || browser.compareTo("internet explorer") == 0) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else if (browser.compareTo("edge") == 0) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			throw new BrowserInitizationException("Something went wrong while opening the browser");
		}
	}

	public static WebDriver getDriver() {
		return driver;
	}
}
