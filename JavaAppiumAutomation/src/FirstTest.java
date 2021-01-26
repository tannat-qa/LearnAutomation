import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.*;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testForTextElementInSearchField() {
        MainPageObject.assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//android.widget.TextView"),
                "Search Wikipedia",
                "",
                5);
    }

    @Test
    public void testCancelSearchResults() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Android",
                "Cannot find search input",
                5
        );

        // Дожидаемся результата поиска - проверяем что пропал элемент на экране "Пустой результат"
        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_empty_image"),
                "The Search result is empty",
                15
        );

        // Вычисляем количество результатов поиска
        int elementsCountOnPage = MainPageObject.getAmountOfElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']"));
        // Количество элементов должно быть больше 0
        assertTrue("The search result is empty", elementsCountOnPage > 0);

        // Отменяем результат поиска - Х
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@class='android.widget.ImageButton']"),
                "Cannot find X to cancel search",
                5
        );

        // Проверяем, что отображение строк поиска пропало
        elementsCountOnPage = MainPageObject.getAmountOfElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']"));
        // Количество должно быть 0
        assertTrue("The search result is not cleared", elementsCountOnPage == 0);
    }

    @Test
    public void testCheckSearchResultStrings() {
        String search_str = "Java";

        // Ожидаем элемент поиска и выбираем его
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_str,
                "Cannot find search input",
                5
        );

        // Дожидаемся результата поиска
        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_empty_image"),
                "The Search result is empty",
                15
        );

        // Вычисляем количество строк результата поиска
        int elementsCountOnPage = MainPageObject.getAmountOfElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
        // Количество элементов должно быть больше 0
        assertTrue("The search result is empty", elementsCountOnPage > 0);

        // Проходим по каждому элементу результата поиска и проверяем наличие заданного слова
        List<WebElement> element_list = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
        String str;
        search_str = search_str.toLowerCase();

        for (int i = 0; i < elementsCountOnPage; i++) {
            str = element_list.get(i).getAttribute("text").toLowerCase();
            assertTrue("The search result string does not contain search word " + search_str, str.indexOf(search_str) > -1);
        }
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        // Ожидаем элемент поиска и выбираем его
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input (1)",
                5
        );

        String searh_line = "Java";

        // кликаем по поиску (xpath) и вводим текст
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input (1)",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by '" + searh_line + "'",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title (1)",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options (1)",
                5
        );

        // Ожидаем прогрузку всего меню
        MainPageObject.waitForElementFullyLoaded(
                By.xpath("//android.widget.LinearLayout"),
                6,
                "The popup menu was not loaded fully (1)",
                15);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning automation";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                5
        );

        // Поиск второй статьи
        // Возвращаемся назад
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        // Ожидаем элемент поиска и выбираем его
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input (2)",
                5
        );

        searh_line = "Appium";

        // кликаем по поиску (xpath) и вводим текст
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input (2)",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find topic searching by '" + searh_line + "'",
                5
        );

        String title_article = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title (2)",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options (2)",
                5
        );

        // Ожидаем прогрузку всего меню
        MainPageObject.waitForElementFullyLoaded(
                By.xpath("//android.widget.LinearLayout"),
                5,
                "The popup menu was not loaded fully (2)",
                15);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list (2)",
                5
        );

        // Ждем загрузку списка папок
        MainPageObject.waitForElementFullyLoaded(
                By.xpath("//*[@resource-id='org.wikipedia:id/list_of_lists']/*[@class='android.widget.FrameLayout']"),
                1,
                "The folder list was not loaded fully",
                15
        );

        // Выбираем созданный список
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + name_of_folder + "']"),
                "Cannot find folder '" + name_of_folder + "'",
                5
        );

        // Возвращаемся назад
        MainPageObject.waitForElementAndClick(
               By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                5
        );

        // Ждем загрузку списка папок
        MainPageObject.waitForElementFullyLoaded(
                By.xpath("//*[@resource-id='org.wikipedia:id/reading_list_list']/*[@class='android.widget.FrameLayout']"),
                1,
                "The folder list was not loaded fully (2)",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        // Удаляем первую статью свайпом
        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article (1)"
        );

        // Проверяем что статья удалилась
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article 'Java (programming language)'",
                5
        );

        // Проверяем что осталась вторая статья и выбираем ее
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Appium']"),
                "Cannot find the second article 'Appium' in list",
                5
        );

        // Проверяем название статьи
        String title_after_opening = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article (3)",
                15
        );

        assertEquals(
                "Article title have been changed after opening it from saved list",
                title_article,
                title_after_opening
        );
    }

    @Test
    public void testAssertElementPresent() {
        // Ожидаем элемент поиска и выбираем его
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searh_line = "Olympic games";

        // кликаем по поиску (xpath) и вводим текст
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Major international sport event']"),
                "Cannot find 'Major international sport event' topic searching by '" + searh_line + "'",
                5
        );

        assertTrue(
                "Cannot find article title",
                driver.findElement(By.id("org.wikipedia:id/view_page_title_text")).isDisplayed() == true
        );
    }
}
