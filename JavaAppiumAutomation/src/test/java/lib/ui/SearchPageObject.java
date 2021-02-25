package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_IMAGE,
        SEARCH_INIT_ELEMENT_FIELD,
        SEARCH_RESULT_ELEMENT_BY_TITLE_AND_DESC,
        SEARCH_RESULT_BY_ARTICLE_BY_SUBSTRING_TPL;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementbyArticleTitle(String substring) {
        return SEARCH_RESULT_BY_ARTICLE_BY_SUBSTRING_TPL.replace("{TITLE}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String article_title, String article_description) {
        return SEARCH_RESULT_ELEMENT_BY_TITLE_AND_DESC.replace("{TITLE}", article_title).replace("{DESCRIPTION}", article_description);
    }
    /* TEMPLATE METHODS */

    @Step("Initializing the search field")
    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,"Cannot find and click search init element",5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT,"Cannot find search input after clicking search init element");
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    @Step("Clicking button to cancel search result")
    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    @Step("Typing '{search_line}' to the search line")
    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    @Step("Waiting for search result with substring '{substring}'")
    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    @Step("Waiting for search result and click article that contains substring '{substring}' in title")
    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    @Step("Clicking by article without description in search results")
    public void clickByArticleWithoutDescription(String substring) {
        String search_result_xpath = getResultSearchElementbyArticleTitle(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    @Step("Getting amount of found articles")
    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Getting amount of articles in search results")
    public int getAmountOfArticles() {
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Waiting for empty results label")
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);

    }

    @Step("Making sure there are no results for the search")
    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT,"We supposed not to find any results");
    }

    @Step("Waiting for image 'empty results' is not present")
    public void waitForEmptyResultsImageNotPresent() {
        this.waitForElementNotPresent(SEARCH_EMPTY_RESULT_IMAGE, "The Search result is empty", 15);
    }

    @Step("Assert that search element has text '{expected_text}'")
    public WebElement assertSearchElementHasText(String expected_text) {
        WebElement element = this.waitForElementPresent(SEARCH_INIT_ELEMENT_FIELD, "Cannot find search init element", 5);
        String element_text;

        if (Platform.getInstance().isAndroid()) {
            element_text = element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            element_text = element.getAttribute("name");
        } else {
            element_text = element.getAttribute("aria-label");
        }

        Assert.assertEquals(
                "The text field of the element is not equal expected text",
                expected_text,
                element_text
        );

        return element;
    }

    @Step("Waiting for element with title '{title}' and description '{description}' at the same time")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(search_result_xpath, "Cannot find article with title '" + title + "' and description '" + description + "'", 5);
    }
}
