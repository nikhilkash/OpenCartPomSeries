package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1) Page Objects with By locators

	private By emailId = By.xpath("//input[@id='input-email']");
	private By password = By.xpath("//input[@id='input-password']");
	private By loginBtn = By.xpath("//input[@class='btn btn-primary']");
	private By ForgotPwdLink = By.linkText("Forgotten Password");

//2)create a public constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil= new ElementUtil(driver);
	}

	// 3)public page action methods

	public String getLoginPageTitle() {
		
		String title = eleUtil.waitForTitleToBe(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		System.out.println("Login page title " + title);
		return title;
	}

	public String getLoginPageUrl() {
		String url=eleUtil.waitForUrlContains(AppConstants.LOGIN_FRATIONAL_URL, TimeUtil.DEFAULT_TIME);
		System.out.println("Login page URl " + url);
		return url;
	}

	public boolean checkForgotPwdLink() {
		return driver.findElement(ForgotPwdLink).isDisplayed();
	}

	public AccountsPage doLogin(String username, String pwd) {
	
		driver.findElement(emailId).sendKeys(username);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(loginBtn).click();
		
		return new AccountsPage(driver);
//		String title = driver.getTitle();
//		System.out.println("Account page Title " + title);
//		return title;

	}

}
