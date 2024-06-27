package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accSetUp() {
		accPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		Assert.assertEquals(accPage.getAccountPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE,AppError.TITLE_NOT_FOUND);

	}
	
	@Test
	public void accpageUrlTest() {
		Assert.assertTrue(accPage.getAccountPageUrl().contains( AppConstants.ACCOUNT_FRATIONAL_URL),AppError.URL_NOT_FOUND);
	}
	
	@Test
	public void accPageHeaderTest() {
	List<String> accPageHeaderList=accPage.getAccPageHeader();
	Assert.assertEquals(accPageHeaderList, AppConstants.ACC_PAGE_HEADER_LIST,AppError.LIST_IS_NOT_MATCHED);
		
		
	}
	
	
	
	
	
	
	
	
	

}
