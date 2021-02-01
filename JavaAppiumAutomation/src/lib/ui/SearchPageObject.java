package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text,'Search…')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
        SEARCH_EMPTY_RESULT_IMAGE = "org.wikipedia:id/search_empty_image",
        SEARCH_INIT_ELEMENT_FIELD = "//*[@resource-id='org.wikipedia:id/search_container']//android.widget.TextView",
        SEARCH_RESULT_ELEMENT_BY_TITLE_AND_DESC = "//*[@resource-id='org.wikipedia:id/page_list_item_container' and .//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}'] and ..//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String article_title, String article_description) {
        return SEARCH_RESULT_ELEMENT_BY_TITLE_AND_DESC.replace("{TITLE}", article_title).replace("{DESCRIPTION}", article_description);
    }
    /* TEMPLATE METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find and click search init element",5);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public int getAmountOfArticles() {
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT), "Cannot find empty result element", 15);

    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),"We supposed not to find any results");
    }

    public void waitForEmptyResultsImageNotPresent() {
        this.waitForElementNotPresent(By.id(SEARCH_EMPTY_RESULT_IMAGE), "The Search result is empty", 15);
    }

    public WebElement assertSearchElementHasText(String expected_text) {
        WebElement element = this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT_FIELD), "", 5);

        String element_text = element.getAttribute("text");

        Assert.assertEquals(
                "The text field of the element is not equal expected text",
                expected_text,
                element_text
        );

        return element;
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find article with title '" + title + "' and description '" + description + "'", 5);
    }
}
