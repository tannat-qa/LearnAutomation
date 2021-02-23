package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:div>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_INIT_ELEMENT_FIELD = "css:form.search-box>input.search";
        SEARCH_RESULT_ELEMENT_BY_TITLE_AND_DESC = "xpath://li[contains(@class,'page-summary')][@title='{TITLE}']//div[@class='wikidata-description'][contains(text(),'{DESCRIPTION}')]";
        SEARCH_RESULT_BY_ARTICLE_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'results-list-container')]//a[@data-title='{TITLE}']";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
