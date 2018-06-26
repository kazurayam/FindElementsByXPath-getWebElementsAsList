package com.kazurayam.ksbackyard
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory

class FindElementsByXPath {
	@Keyword
	List<WebElement> getWebElementsAsList(String xpath4elements) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		List<WebElement> elements = webDriver.findElements(By.xpath(xpath4elements))
		return elements
	}
}