package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTest extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";
    private static final String
        login = "***",
        password = "***";


    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMw()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle());

            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openMyLists(1);
            MyListsPageObject.selectFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title_1 = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMw()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    article_title_1,
                    ArticlePageObject.getArticleTitle());

            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();

        if (Platform.getInstance().isIOS()) {
            ArticlePageObject.clearPreviousSearchInput();
        }
        SearchPageObject.typeSearchLine("Appium");

        if (Platform.getInstance().isMw()) {
            SearchPageObject.clickByArticleWithoutSubstring("Appium");
        } else {
            SearchPageObject.clickByArticleWithSubstring("Appium");
        }

        String article_title_2 = ""; // название второй статьи (используется в тесте для Android)

        if (Platform.getInstance().isAndroid()) {
            article_title_2 = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addArticleToExistsList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openMyLists(1);
            MyListsPageObject.selectFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(article_title_1);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title_1);

        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.selectFolderByName(article_title_2);
            String title_after_opening = ArticlePageObject.getArticleTitle();

            Assert.assertEquals(
                    "Article title have been changed after opening it from saved list",
                    article_title_2,
                    title_after_opening
            );
        } else {
            // Открываем первую статью из списка
            MyListsPageObject.openFirstSavedArticleFromList();
            // Проверяем, что статья сохранена - "флажок" Saved внизу экрана (iOS) (звезда в MW) установлен
            ArticlePageObject.checkOpenedArticleIsSaved();
        }
    }
}
