package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Epic("Tests for Search")
public class SearchTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Simple search")
    @Description("Search for 'Java' and check search results")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Check cancel search")
    @Description("We init the search option and then cancel the search")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Check amount of not empty search")
    @Description("Search the article and check that we have more than 0 results")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(search_line);

        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Check amount of empty search")
    @Description("Search the article without results and check that wiki has no search results")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        String search_line = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Search by article and cancel search results")
    @Description("Search by the given pattern, get the results of the search and cancel the search results")
    @Step("Starting test testCancelSearchResults")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearchResults() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        String search_line = "Android";
        SearchPageObject.typeSearchLine(search_line);

        int elementsCountOnPage = SearchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue(
                "We found too few results",
                elementsCountOnPage > 0
        );

        // Отменяем результат поиска - Х
        SearchPageObject.clickCancelSearch();

        elementsCountOnPage = SearchPageObject.getAmountOfArticles();
        // Количество должно быть 0
        Assert.assertTrue("The search result is not cleared", elementsCountOnPage == 0);
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Check the title of search results")
    @Description("We search by the word and check that every search result contains this word")
    @Step("Starting test testCheckSearchResultStrings")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckSearchResultStrings() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        String search_line = "Java";
        SearchPageObject.typeSearchLine(search_line);

        // Вычисляем количество строк результата поиска
        int elementsCountOnPage = SearchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue(
                "The search result is empty",
                elementsCountOnPage > 0
        );

        // Проходим по каждому элементу результата поиска и проверяем наличие заданного слова
        List<WebElement> element_list;

        if (Platform.getInstance().isAndroid()) {
            element_list = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
        } else if (Platform.getInstance().isIOS()) {
            element_list = driver.findElements(By.xpath("//XCUIElementTypeLink[@type='XCUIElementTypeLink']"));
        } else {
            element_list = driver.findElements(By.cssSelector("li.page-summary>a"));
        }

        String str;
        search_line = search_line.toLowerCase();

        for (int i = 0; i < elementsCountOnPage; i++) {
            if (Platform.getInstance().isAndroid()) {
                str = element_list.get(i).getAttribute("text").toLowerCase();
            } else if (Platform.getInstance().isIOS()) {
                str = element_list.get(i).getAttribute("name").toLowerCase();
            } else {
                str = element_list.get(i).getAttribute("data-title").toLowerCase();
            }
            Assert.assertTrue("The search result string does not contain search word " + search_line, str.indexOf(search_line) > -1);
        }
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Check the label of search input field")
    @Description("Check that the input field for search contains 'Search Wikipedia'")
    @Step("Starting test testForTextElementInSearchField")
    @Severity(value = SeverityLevel.MINOR)
    public void testForTextElementInSearchField() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        if (Platform.getInstance().isMw()) {
            SearchPageObject.initSearchInput();
        }
        SearchPageObject.assertSearchElementHasText("Search Wikipedia");
    }
}
