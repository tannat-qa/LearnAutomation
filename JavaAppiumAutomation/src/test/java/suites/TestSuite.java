package suites;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import tests.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        MyListsTest.class,
        ArticleTests.class,
        ChangeAppConditionTests.class,
        SearchTests.class
})

public class TestSuite {
}
