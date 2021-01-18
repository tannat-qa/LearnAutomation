import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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
        capabilities.setCapability("platformVersion", "8.0.0");
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
        // Пропускаем skip
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Skip button",
                5
        );

        // выбираем поиск
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );

        // Проверяем результат поиска
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }

    @Test
    public void testCancelSearch() {
        // Пропускаем skip
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Skip button",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
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
                By.xpath("//*[@class='android.widget.ImageButton']"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@class='android.widget.ImageButton']"),
                "X is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        // Пропускаем skip
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Skip button",
                5
        );

        // выбираем поиск
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("pcs-edit-section-title-description"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Object-oriented programming language",
                article_title
        );
    }

    @Test
    public void testForTextElementInSearchField() {
        // Пропускаем skip
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Skip button",
                5
        );

        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']"),
                "Search Wikipedia",
                "",
                5);
    }

    @Test
    public void testCancelSearchResults() {
        // Пропускаем skip
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Skip button",
                5
        );

        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
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
        int elementsCountOnPage = getElementsCount(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']"));
        // Количество элементов должно быть больше 0
        Assert.assertTrue("The search result is empty", elementsCountOnPage > 0);

        // Отменяем результат поиска - Х
        waitForElementAndClick(
                By.xpath("//*[@class='android.widget.ImageButton']"),
                "Cannot find X to cancel search",
                5
        );

        // Проверяем, что отображение строк поиска пропало
        elementsCountOnPage = getElementsCount(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']"));
        // Количество должно быть 0
        Assert.assertTrue("The search result is not cleared", elementsCountOnPage == 0);
    }

    @Test
    public void testCheckSearchResultStrings() {
        String search_str = "Java";

        // Пропускаем skip
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Skip button",
                5
        );

        // Ожидаем элемент поиска и выбираем его
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5
        );

        // кликаем по поиску (xpath) и вводим текст
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                search_str,
                "Cannot find search input",
                5
        );

        // Дожидаемся результата поиска
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find search results on page",
                15
        );

        // Вычисляем количество строк результата поиска
        int elementsCountOnPage = getElementsCount(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
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

    private int getElementsCount(By by) {
        int elementsCount = driver.findElements(by).size();
        return elementsCount;
    }
}
