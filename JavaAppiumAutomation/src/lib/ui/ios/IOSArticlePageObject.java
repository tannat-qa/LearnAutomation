package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject
{
    static {
        //TITLE = "id:Java (programming language)";
        TITLE = "id:ZooParc de Beauval";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        MY_LIST_FULL_LIST = "xpath://*[@resource-id='org.wikipedia:id/list_of_lists']/*[@class='android.widget.FrameLayout']";
        MY_LIST_SELECT_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{SUBSTRING}']";
    }

    public IOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}