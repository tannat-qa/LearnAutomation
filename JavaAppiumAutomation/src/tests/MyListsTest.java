package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class MyListsTest extends CoreTestCase {
    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";

        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openMyLists(1);

        MyListsPageObject.selectFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title_1 = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning automation";

        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        String article_title_2 = ArticlePageObject.getArticleTitle();

        ArticlePageObject.addArticleToExistsList(name_of_folder);

        ArticlePageObject.closeArticle();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openMyLists(1);

        MyListsPageObject.selectFolderByName(name_of_folder);

        MyListsPageObject.swipeByArticleToDelete(article_title_1);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title_1);


        MyListsPageObject.selectFolderByName(article_title_2);
        String title_after_opening = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after opening it from saved list",
                article_title_2,
                title_after_opening
        );
    }
}
