Custom Katalon Keyword: getting HTML elements as list
=====

### What is this?

This is a simple Katalon Studio project for demonstration purpose.
You can clone this out on you PC and execute it with your Katalon Studio.

This project is developed to propose a solution for the following discussion:

- https://forum.katalon.com/discussion/7520/the-xpath-for-the-url-is-correct-but-not-visible-if-added-via-addproperty

Question raised there was:

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

### Rewording Problem to resolve

1. I have a Web page to test. The target page has a list of web elements. Let me name it as **the found elements**. The way how the web elements are marked up is not significant. I am able to specify any appropriate XPath expression to select the elements of interest out of the target HTML. Let me suppose I have the following elements, for example:
```
<select id="combo_facility" name="facility" class="form-control">
    <option value="Tokyo">Tokyo CURA Healthcare Center</option>
    <option value="Hongkong">Hongkong CURA Healthcare Center</option>
    <option value="Seoul">Seoul CURA Healthcare Center</option>
</select>
```
2. My test code has a list of expected texts to be displayed in the target pages. Let me name it as **the expected texts**. Let me suppose I have the following literal in my test case, for example:
```
List<Map<String,String>> data =  [
    ["text":"Hongkong CURA Healthcare Center"],
    ["text":"Seoul CURA Healthcare Center"],
    ["text":"Tokyo CURA Healthcare Center"],
	["text":"New York CURA Healthcare Center"]
]
```
3. The two lists roughly corresponds, but strictly speaking not equivalent. This means:
  - The size of **the found elements** is not necessarily equal to the size of **the expected texts**. Making one-to-one correspondence may results remainders on both.  
  - The sorting order of **the found elements** and the sorting order of **the expected texts** may differ. Even more, both may be unsorted at all.
4. I want to iterate over **the expected texts** to find out if each expected text is displayed in **the target page**. In order to find it I want to perform a nested iteration over **the found elements** for each of the expected texts to try matching.
5. I want to show 'yes' or 'no' for each items of **the expeted texts**. This is the final output from my test case.

I think that this is a Frequently Asked Quetion in the Katalon forum.
- https://forum.katalon.com/discussion/6967/get-text-of-multiple-div-elements
- https://forum.katalon.com/discussion/7520/the-xpath-for-the-url-is-correct-but-not-visible-if-added-via-addproperty

### Technical issues : shortage of Katalon Studio



### Solution proposed

Here is the code of my test case
- '[TC_getWebElementsAsList](https://github.com/kazurayam/FindElementsByXPath-getWebElementsAsList/blob/master/Scripts/TC_getWebElementsAsList/Script1529984673167.groovy)':
