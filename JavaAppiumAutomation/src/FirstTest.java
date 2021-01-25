import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/Users/tanya/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");
        //capabilities.setCapability("app", "/Users/tannat/Documents/GitHub/LearnAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15);

    }

    @Test
    public void testCancelSearchWithXButton() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testForTextElementInSearchField() {
        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//android.widget.TextView"),
                "Search Wikipedia",
                "",
                5);
    }

    @Test
    public void testCancelSearchResults() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Android",
                "Cannot find search input",
                5
        );

        // Дожидаемся результата поиска - проверяем что пропал элемент на экране "Пустой результат"
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_empty_image"),
                "The Search result is empty",
                15
        );

        // Вычисляем количество результатов поиска
        int elementsCountOnPage = getAmountOfElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']"));
        // Количество элементов должно быть больше 0
        Assert.assertTrue("The search result is empty", elementsCountOnPage > 0);

        // Отменяем результат поиска - Х
        waitForElementAndClick(
                By.xpath("//*[@class='android.widget.ImageButton']"),
                "Cannot find X to cancel search",
                5
        );

        // Проверяем, что отображение строк поиска пропало
        elementsCountOnPage = getAmountOfElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']"));
        // Количество должно быть 0
        Assert.assertTrue("The search result is not cleared", elementsCountOnPage == 0);
    }

    @Test
    public void testCheckSearchResultStrings() {
        String search_str = "Java";

        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_str,
                "Cannot find search input",
                5
        );

        // Дожидаемся результата поиска
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_empty_image"),
                "The Search result is empty",
                15
        );

        // Вычисляем количество строк результата поиска
        int elementsCountOnPage = getAmountOfElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
        // Количество элементов должно быть больше 0
        Assert.assertTrue("The search result is empty", elementsCountOnPage > 0);

        // Проходим по каждому элементу результата поиска и проверяем наличие заданного слова
        List<WebElement> element_list = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
        String str;
        search_str = search_str.toLowerCase();

        for (int i = 0; i < elementsCountOnPage; i++) {
            str = element_list.get(i).getAttribute("text").toLowerCase();
            Assert.assertTrue("The search result string does not contain search word " + search_str, str.indexOf(search_str) > -1);
        }
    }

    @Test
    public void testSwipeArticle() {
        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find topic searching by 'Appium'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );
    }

    @Test
    public void saveFirstArticleToMyList() {
        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        // Ожидаем прогрузку всего меню
        waitForElementFullyLoaded(
                By.xpath("//android.widget.LinearLayout"),
                6,
                "The popup menu was not loaded fully",
                15);

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                5
        );

        // Возвращаемся назад
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                5
        );

        // Ждем загрузку списка папок
        waitForElementFullyLoaded(
                By.xpath("//*[@resource-id='org.wikipedia:id/reading_list_list']/*[@class='android.widget.FrameLayout']"),
                1,
                "The folder list was not loaded fully",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searh_line = "Linkin Park Diskography";

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_result_locator,
                15
        );

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searh_line = "zxcvasdfqwer";

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty results label by the request " + searh_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We found some results by request " + searh_line
        );
   }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searh_line = "Java";

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by '" + searh_line + "'",
                15
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article (1)",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article (2)",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article (3)",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen second rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searh_line = "Java";

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by '" + searh_line + "'",
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background",
                5
        );
    }

    @Test
    public void saveTwoArticlesToMyList() {
        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input (1)",
                5
        );

        String searh_line = "Java";

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input (1)",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by '" + searh_line + "'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title (1)",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options (1)",
                5
        );

        // Ожидаем прогрузку всего меню
        waitForElementFullyLoaded(
                By.xpath("//android.widget.LinearLayout"),
                6,
                "The popup menu was not loaded fully (1)",
                15);

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning automation";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                5
        );

        // Поиск второй статьи
        // Возвращаемся назад
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input (2)",
                5
        );

        searh_line = "Appium";

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searh_line,
                "Cannot find search input (2)",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find topic searching by '" + searh_line + "'",
                5
        );

        String title_article = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title (2)",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options (2)",
                5
        );

        // Ожидаем прогрузку всего меню
        waitForElementFullyLoaded(
                By.xpath("//android.widget.LinearLayout"),
                5,
                "The popup menu was not loaded fully (2)",
                15);

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list (2)",
                5
        );

        // Ждем загрузку списка папок
        waitForElementFullyLoaded(
                By.xpath("//*[@resource-id='org.wikipedia:id/list_of_lists']/*[@class='android.widget.FrameLayout']"),
                1,
                "The folder list was not loaded fully",
                15
        );

        // Выбираем созданный список
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + name_of_folder + "']"),
                "Cannot find folder '" + name_of_folder + "'",
                5
        );

        // Возвращаемся назад
        waitForElementAndClick(
               By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                5
        );

        // Ждем загрузку списка папок
        waitForElementFullyLoaded(
                By.xpath("//*[@resource-id='org.wikipedia:id/reading_list_list']/*[@class='android.widget.FrameLayout']"),
                1,
                "The folder list was not loaded fully (2)",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        // Удаляем первую статью свайпом
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article (1)"
        );

        // Проверяем что статья удалилась
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article 'Java (programming language)'",
                5
        );

        // Проверяем что осталась вторая статья и выбираем ее
        waitForElementAndClick(
                By.xpath("//*[@text='Appium']"),
                "Cannot find the second article 'Appium' in list",
                5
        );

        // Проверяем название статьи
        String title_after_opening = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article (3)",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after opening it from saved list",
                title_article,
                title_after_opening
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private WebElement assertElementHasText(By by, String expected_text, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);

        String element_text = element.getAttribute("text");

        Assert.assertEquals(
                "The text field of the element is not equal expected text",
                expected_text,
                element_text
        );

        return element;
    }

    private int getAmountOfElements(By by) {
        List elementsCount = driver.findElements(by);
        return elementsCount.size();
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;

        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up.\n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private void waitForElementFullyLoaded(By by, int count_of_elements, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        wait.until(ExpectedConditions.numberOfElementsToBe(by, count_of_elements));
    }

    private void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
