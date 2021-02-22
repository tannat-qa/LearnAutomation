package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://ul[@id='page-actions']/li[@id='page-actions-watch']/a[text()='Watch']";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions li#page-actions-watch a.watched";
        //CLOSE_ARTICLE_BUTTON = "id:Back";
        //CLOSE_SYNC_ARTICLES_TO_CLOUD_BUTTON = "xpath://XCUIElementTypeButton[@name='places auth close']";
        //CLEAR_SEARCH_INPUT = "id:clear mini";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
