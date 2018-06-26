getting a List<org.openqa.selenium.WebElement> from a Web page
=====

## What is this?

This is a simple [Katalon Studio](https://www.katalon.com/) project for demonstration purpose.
You can clone this out on you PC and execute it with your Katalon Studio.

This project was developped using Katalon Studio ver5.4.2

This project is developed to propose a solution for the following discussion:

- https://forum.katalon.com/discussion/7520/the-xpath-for-the-url-is-correct-but-not-visible-if-added-via-addproperty

The question raised in the disscussion was:

>Emails sent from the .csv in this order:
> - email 1
> - email 2
> - email n
>
>Emails shown in opposite order in the Inbox:
> - email n
> - email 2
> - email 1
>
>I used the same .csv and same loop. Just have 2 variables. One starts from the 1st row of the file and increments all the way to the last record in the file. The other variable works in opposite direction.

## Rewording Problem to resolve

Let me define a problem with a simple HTML as target.

1. I have [a Web page](http://demoaut-mimic.kazurayam.com/6967_testbed.html) to test. The target page has a list of web elements. Let me name it as **the found elements**. Please find a list of &lt;option&gt; elements in the following HTML snippet:
```
<select id="combo_facility" name="facility" class="form-control">
    <option value="Tokyo">Tokyo CURA Healthcare Center</option>
    <option value="Hongkong">Hongkong CURA Healthcare Center</option>
    <option value="Seoul">Seoul CURA Healthcare Center</option>
</select>
```
The way how the web elements are marked up is not significant. You may have &lt;li&gt;, &lt;tr&gt;, &lt;a&gt; or whatever. We are able to code appropriate XPath expression to select the elements of our interest out of the target HTML.

2. My test code has a list of expected texts to be displayed in the target pages. Let me name it as **the expected data**. Let me suppose I have the following literal in my test case, for example:
```
List<Map<String,String>> data =  [
    ["text":"Hongkong CURA Healthcare Center"],
    ["text":"Seoul CURA Healthcare Center"],
    ["text":"Tokyo CURA Healthcare Center"],
	["text":"New York CURA Healthcare Center"]
]
```
3. I want to determine 'yes' or 'no' for each items of **the expected data**. My test case should emit message for each text if it is "displayed:yes" or "displayed:no" in the target Web page.
4. The two lists roughly corresponds; but these are not 100% correspondent. I mean:
  - The size of **the found elements** is not necessarily equal to the size of **the expected data**. Making one-to-one correspondence between entries of each lists may results remainders.  
  - These lists may be sorted differently. Either of these may be unsorted at all.
5. I want to iterate over **the expected data** to find out if each text is displayed in the target page. Therefore ***I want to perform nested iteration over the found elements*** in order to perform text matching.

I think that this problem is frequently asked in the in the Katalon forum.
- https://forum.katalon.com/discussion/6967/get-text-of-multiple-div-elements
- https://forum.katalon.com/discussion/7520/the-xpath-for-the-url-is-correct-but-not-visible-if-added-via-addproperty

## Blocking shortage of Katalon Studio

Now I want to perform, in my test case, an iteration over **the found elements**. However, Katalon Studio does not provide a built-in keyword which returns a list of web elements out of the target Web page. This is the very problem I found.

## Solution proposed

### My custom keyword

Here I have developed a Groovy class as custom keyword for Katalon Studio: `com.kazurayam.ksbackyard.FindElementsByXPath`. This class implements a method: `List<org.openqa.selenium.WebElement> getWebElementsAsList(String xpath)`. The source code is [here](https://github.com/kazurayam/KatalonDiscussion6967/blob/master/Keywords/com/kazurayam/ksbackyard/FindElementsByXPath.groovy). Here I copy&paste it:
```
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
```

Please note that this keyword returns `List<org.openqa.selenium.WebElement>`. Hence, those who use this custom keyword have to study [WebDriver's API document/ WebElement](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/WebElement.html).

## My test case

Here is my test case [TC_getWebElementsAsList](https://github.com/kazurayam/FindElementsByXPath-getWebElementsAsList/blob/master/Scripts/TC_getWebElementsAsList/Script1529984673167.groovy)
```
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.navigateToUrl('http://demoaut-mimic.kazurayam.com/6967_testbed.html')

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
	WebUI.comment("${m.text} is displayed:${m.found}")
}

WebUI.closeBrowser()
```

### Result

When I execuite this test case, I got the following output in the log:
```
>>> Tokyo CURA Healthcare Center is displayed: yes
...
>>> Hongkong CURA Healthcare Center is displayed: yes
...
>>> Seoul CURA Healthcare Center is displayed: yes
...
>>> New York CURA Healthcare Center is displayed: no
```

This is what I wanted to see. My success.
