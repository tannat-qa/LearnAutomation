package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "android:id/button1",
        CLOSE_ARTICLE_BUTTON = "//*[@content-desc='Navigate up']",
        MY_LIST_FULL_LIST = "//*[@resource-id='org.wikipedia:id/list_of_lists']/*[@class='android.widget.FrameLayout']",
        MY_LIST_SELECT_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{SUBSTRING}']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultMyListSelectElement(String substring) {
        return MY_LIST_SELECT_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATE METHODS */

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.id(TITLE), "Cannot find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        // Ожидаем прогрузку всего меню
        this.waitForElementFullyLoaded(
                By.xpath("//android.widget.LinearLayout"),
                6,
                "The popup menu was not loaded fully",
                15);

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of articles folder",
                5
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                By.id(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                5
        );
    }

    public void addArticleToExistsList(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        // Ожидаем прогрузку всего меню
        this.waitForElementFullyLoaded(
                By.xpath("//android.widget.LinearLayout"),
                5,
                "The popup menu was not loaded fully",
                15);

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5
        );

        // Ждем загрузку списка папок
        this.waitForElementFullyLoaded(
                By.xpath(MY_LIST_FULL_LIST),
                1,
                "The folder list was not loaded fully",
                15
        );

        String my_list_element = getResultMyListSelectElement(name_of_folder);

        // Выбираем созданный список
        this.waitForElementAndClick(
                By.xpath(my_list_element),
                "Cannot find folder '" + name_of_folder + "'",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link",
                5
        );
    }
}
