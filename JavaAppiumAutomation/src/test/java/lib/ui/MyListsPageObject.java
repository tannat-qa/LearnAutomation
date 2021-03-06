package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        MY_LISTS_ELEMENT,
        FOLDER_LISTS_ELEMENTS,
        FIRST_SAVED_ARTICLE_PATH,
        REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveButtonByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    public MyListsPageObject (RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Swipe by article with title '{article_title}' to delete it")
    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(article_xpath, "Cannot find saved article");
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saved",
                    10
            );
        }

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }

        if (Platform.getInstance().isMw()) {
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }

    @Step("Check that saved article with title '{article_title}' is present")
    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementPresent(article_xpath, "Cannot find saved article by title " + article_title, 15);
    }

    @Step("Check that article with title '{article_title}' is not present")
    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + article_title, 15);
    }

    @Step("Open the selected folder '{name_of_folder}'")
    public void selectFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);

        this.waitForElementAndClick(folder_name_xpath, "Cannot find folder by name " + name_of_folder, 5);
    }

    @Step("Open 'My lists' menu")
    public void openMyLists (int count_of_folders) {
        this.waitForElementAndClick(
                MY_LISTS_ELEMENT,
                "Cannot find navigation button 'My lists'",
                5
        );

        // Ждем загрузку списка папок
        this.waitForElementFullyLoaded(
                FOLDER_LISTS_ELEMENTS,
                count_of_folders,
                "The folder list was not loaded fully",
                15
        );
    }

    @Step("Click and open the first saved article in my lists")
    public void openFirstSavedArticleFromList() {
        this.waitForElementAndClick(
                FIRST_SAVED_ARTICLE_PATH,
                "Cannot find article to open in saved list",
                5
        );
    }
}
