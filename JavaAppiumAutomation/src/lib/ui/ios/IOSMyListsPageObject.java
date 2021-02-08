package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{TITLE}')]";
        MY_LISTS_ELEMENT = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
        FOLDER_LISTS_ELEMENTS = "xpath://*[@resource-id='org.wikipedia:id/reading_list_list']/*[@class='android.widget.FrameLayout']";
    }

    public IOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
