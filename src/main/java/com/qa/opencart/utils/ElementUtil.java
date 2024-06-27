package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;

public class ElementUtil {
	WebDriver driver;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	private void nullChack(String value) {
		if (value == null) {
			throw new ElementException("value is null" + value);
		}
	}

	public WebElement getElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			return element;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present in the page" + locator);
			e.printStackTrace();
			return null;
		}
	}

	public void doSendKeys(By locator, String value) {
		nullChack(value);
		getElement(locator).sendKeys(value);
	}

	/**
	 * click on the element without wait
	 * 
	 * @param locator
	 */
	public void doClick(By locator) {
		getElement(locator).click();
	}

	/**
	 * click on the element with wait
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void doClick(By locator, int timeout) {
		waitForElementVisible(locator, timeout).click();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public List<WebElement> doGetElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementCount(By locator) {
		return doGetElements(locator).size();
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> elelist = doGetElements(locator);
		List<String> eleTextList = new ArrayList();
		for (WebElement e : elelist) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public void doSelectBtIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	// It return list of dropDown text
	public List<String> getDropDownOptionsText(By locator) {
		Select select = new Select(driver.findElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionTextList = new ArrayList();
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionTextList.add(text);
		}
		return optionTextList;
	}

	// It returns size of dropdown list
	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(driver.findElement(locator));
		int size = select.getOptions().size();
		return size;
	}

	// select text from dropdown without using and
	// selectbyindex,selectbyvalue,selectbyvisibletext
	public void selectValueFromDropDown(By locator, String value) {
		Select select = new Select(driver.findElement(locator));
		List<WebElement> optionsList = select.getOptions();
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

// select text from dropdown without using Select class 
	public void selectValurFromDropdownWithOutSelect(By locator, String value) {
		List<WebElement> optionList = doGetElements(locator);
		int size = optionList.size();
		for (WebElement e : optionList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

//on search field click on expected input
	public void doSearch(By searchField, String inputText, By suggestions, String expvalue)
			throws InterruptedException {
		doSendKeys(searchField, inputText);
		Thread.sleep(3000);
		List<WebElement> suggestionList = doGetElements(suggestions);
		System.out.println(suggestionList.size());
		for (WebElement e : suggestionList) {
			String text = e.getText();
			if (text.contains(expvalue)) {
				e.click();
				break;
			}
		}
	}

	/**
	 * An expression for checking of an element is present on DOM of the page
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPrecence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expression for checking of an element is present on DOM of the page and
	 * visibility Visibility means that the element not only displayed but also has
	 * height and weight that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * to check partial title with wait
	 * 
	 * @param titlefraction
	 * @param timout
	 * @return
	 */
	public String waitForTitleContains(String titlefraction, int timout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timout));
		try {
			if (wait.until(ExpectedConditions.titleContains(titlefraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {

			System.out.println("its not a correct title");
		}
		return driver.getTitle();
	}

	/**
	 * to check the title with wait
	 * 
	 * @param titlevalue
	 * @param timout
	 * @return
	 */
	public String waitForTitleToBe(String titlevalue, int timout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timout));
		try {
			if (wait.until(ExpectedConditions.titleIs(titlevalue))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {

			System.out.println("its not a correct title");
		}
		return driver.getTitle();
	}

	/**
	 * to check the URL with wait
	 * 
	 * @param urlvalue
	 * @param timout
	 * @return
	 */
	public String waitForUrlToBe(String urlvalue, int timout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timout));
		try {
			if (wait.until(ExpectedConditions.urlToBe(urlvalue))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {

			System.out.println("its not a correct url");
		}
		return driver.getCurrentUrl();
	}

	/**
	 * to check partial URL with wait
	 * 
	 * @param Urlfraction
	 * @param timout
	 * @return
	 */
	public String waitForUrlContains(String Urlfraction, int timout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timout));
		try {
			if (wait.until(ExpectedConditions.urlContains(Urlfraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("its not a correct url");
		}
		return driver.getCurrentUrl();

	}

	// ******************************* wait for Alert ***************

	public Alert waitJsAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String getAlertText(int timeout) {
		Alert alert = waitJsAlert(timeout);
		String text = alert.getText();
		alert.accept();
		return text;
	}

	public void acceptAlert(int timeout) {
		waitJsAlert(timeout).accept();
	}

	public void alertSendkeys(int timeout, String value) {
		Alert alert = waitJsAlert(timeout);
		alert.sendKeys(value);
		alert.accept();

	}

}
