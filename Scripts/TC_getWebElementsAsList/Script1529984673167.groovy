import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('http://demoaut-mimic.kazurayam.com/6967_testbed.html')


// The following code was developed to answer to the question raised in
// https://forum.katalon.com/discussion/7520/the-xpath-for-the-url-is-correct-but-not-visible-if-added-via-addproperty
//
// The following code checks if expected texts displayed in the target web page.
// We will use FindWebElementsByXPath#webElementsAsList(String xpath)
//   to get a List<org.openqa.selenium.WebElement) out of the page.

List<Map<String,String>> data =  [
	["text":"Tokyo CURA Healthcare Center"],
	["text":"Hongkong CURA Healthcare Center"],
	["text":"Seoul CURA Healthcare Center"],
	["text":"New York CURA Healthcare Center"]
]

List<WebElement> webElementsInPage =
	CustomKeywords.'com.kazurayam.ksbackyard.FindElementsByXPath.getWebElementsAsList'('//select[@name="facility"]/option')
WebUI.comment("webElementsInPage.size()=${webElementsInPage.size()}")
for (int i = 0; i < data.size(); i++) {
	data[i].found = 'no'
	for (WebElement el: webElementsInPage) {
		WebElement node = el.findElement(By.xpath('.'))  // you can further search for any descendant nodes of the WebElement
		String t = node.getText().trim()
		if (t == data[i].text) {
			data[i].found = 'yes'
		}
	}
}

for (Map m : data) {
	WebUI.comment("${m.text}:${m.found}")
}

WebUI.closeBrowser()