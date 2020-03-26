package com.nagarro.nagp.tests;

import java.util.logging.Logger;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.nagarro.nagp.core.Initialize;
import com.nagarro.nagp.elements.PageElements;
import com.nagarro.nagp.elements.Pages;
import com.nagarro.nagp.object_repository.InitializeObject;

public class TestHomePage {
	Actions action;
	Logger logger;

	@BeforeSuite
	public void setupSuite() {
		try {
			
			Initialize.init();
			action = new Actions(Initialize.getDriver());
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void killBrowser() {
		Initialize.getDriver().quit();
	}

	@Test
	public void categorySearchItemCompare() {
		try {
			action.moveToElement(InitializeObject.getElement(Pages.Header, PageElements.categoryWomen)).perform();
			Thread.sleep(2000);
			InitializeObject.getElement(Pages.Header, PageElements.subCategoryWomenTShirt).click();
			Thread.sleep(5000);

			String categoryItemName = InitializeObject
					.getElement(Pages.CategoryPage, PageElements.categoryPageFirstItem).getText();
			InitializeObject.getElement(Pages.Header, PageElements.searchTextBox).sendKeys(categoryItemName);
			InitializeObject.getElement(Pages.Header, PageElements.searchButton).click();
			Thread.sleep(5000);

			String searchResultItemName = InitializeObject
					.getElement(Pages.SearchResultPage, PageElements.searchResultFirstItem).getText();

			Assert.assertEquals(categoryItemName, searchResultItemName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
