package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject
{
    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        CLOSE_SYNC_ARTICLES_TO_CLOUD_BUTTON = "xpath://XCUIElementTypeButton[@name='places auth close']";
        CLEAR_SEARCH_INPUT = "id:clear mini";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://XCUIElementTypeButton[contains(@name,'Saved')]";
    }

    public IOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
