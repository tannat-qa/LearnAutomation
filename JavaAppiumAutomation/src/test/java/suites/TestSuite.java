package suites;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import tests.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ArticleTests.class,
        ChangeAppConditionTests.class,
        MyListsTest.class,
        SearchTests.class
})

public class TestSuite {
}
