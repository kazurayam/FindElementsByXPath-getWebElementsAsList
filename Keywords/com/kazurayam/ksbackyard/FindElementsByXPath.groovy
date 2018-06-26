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
	
	@Keyword
	List<String> getElementContentsAsList(String xpath4elements) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		List<WebElement> elements = webDriver.findElements(By.xpath(xpath4elements))
		def contents = new ArrayList<String>()
		for (WebElement el : elements) {
			def content = el.getText()
			if (content != null) {
				contents.add(content)
			} else {
				contents.add('')
			}
		}
		return contents
	}

	@Keyword
	List<String> getAttributeValuesAsList(String xpath4elements, String attributeName) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		List<WebElement> elements = webDriver.findElements(By.xpath(xpath4elements))
		def values = new ArrayList<String>()
		for (WebElement el : elements) {
			def value = el.getAttribute(attributeName)
			if (value != null) {
				values.add(value)
			} else {
				values.add('')
			}
		}
		return values
	}


}