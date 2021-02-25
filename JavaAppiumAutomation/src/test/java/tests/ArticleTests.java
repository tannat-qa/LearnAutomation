package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We open 'Java Object-oriented programming language' article and make sure the title is expected")
    @Step("Starting test testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        //ArticlePageObject.takeScreenshot("article_page");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article")})
    @DisplayName("Swipe article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article")})
    @DisplayName("Check article title is present on page")
    @Description("Check that article title is present on page without wait after opening article")
    @Step("Starting test testAssertElementPresent")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAssertElementPresent() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();

        String search_line = "Olympic games";
        SearchPageObject.typeSearchLine(search_line);

        SearchPageObject.clickByArticleWithSubstring("Major international sport event");

        if (Platform.getInstance().isAndroid()) {
            Assert.assertTrue(
                "Cannot find article title",
                driver.findElement(By.id("org.wikipedia:id/view_page_title_text")).isDisplayed() == true);
        } else if (Platform.getInstance().isIOS()) {
            Assert.assertTrue(
                    "Cannot find article title",
                    driver.findElement(By.id("Olympic Games")).isDisplayed() == true);
        } else {
            Assert.assertTrue(
                    "Cannot find article title",
                    driver.findElement(By.cssSelector("#content h1")).isDisplayed() == true
            );
        }
    }

    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article")})
    @DisplayName("Check search articles results by title and description")
    @Description("Check search results by title and description at the same time")
    @Step("Starting test testSearchArticlesByTitleAndDescription")
    @Severity(value = SeverityLevel.MINOR)
    public void testSearchArticlesByTitleAndDescription() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Olympic");

        if (Platform.getInstance().isMw()) {
            SearchPageObject.waitForElementByTitleAndDescription("Olympic", "Disambiguation page providing links to topics that could be referred to by the same search term");
        } else {
            SearchPageObject.waitForElementByTitleAndDescription("Olympic", "Wikimedia disambiguation page");
        }
        SearchPageObject.waitForElementByTitleAndDescription("Olympic Games", "Major international sport event");
        SearchPageObject.waitForElementByTitleAndDescription("Olympic symbols", "Symbols of the International Olympic Games");
    }
}
