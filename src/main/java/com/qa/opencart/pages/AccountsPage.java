package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AccountsPage {

	private WebDriver driver;

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
	}

	private By logout = By.linkText("Logout");
	private By headers = By.xpath("//div[@id='content']/h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	public String getAccountPageTitle() {
		String title = driver.getTitle();
		System.out.println("Account page title " + title);
		return title;
	}

	public String getAccountPageUrl() {
		String url = driver.getCurrentUrl();
		System.out.println("Account page URl " + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return driver.findElement(logout).isDisplayed();
	}

	public List<String> getAccPageHeader() {
		List<WebElement> headerList = driver.findElements(headers);
		List<String> headerValList = new ArrayList<>();
		for (WebElement e : headerList) {
			String text = e.getText();
			headerValList.add(text);
		}
		return headerValList;
	}

	public boolean isSearchExist() {
		return driver.findElement(search).isDisplayed();
	}

	public SearchResultPage doSearch(String searchKey) {
		System.out.println("Searching: " + searchKey);
		if (isSearchExist()) {
			driver.findElement(search).sendKeys(searchKey);
			driver.findElement(searchIcon).click();
			return new SearchResultPage(driver);
		} else {
			System.out.println("search field in not present on the page");
			return null;
		}
	}

}
