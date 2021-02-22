package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{TITLE}')]";
        FIRST_SAVED_ARTICLE_PATH = "xpath://XCUIElementTypeCell[@index=0]";
        IS_ARTICLE_SAVED_ELEMENT = "xpath://XCUIElementTypeButton[contains(@name,'Saved')]";
    }

    public IOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
