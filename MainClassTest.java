import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass {
    @Test
    public void testGetLocalNumber() {
        int a = getLocalNumber();
        Assert.assertTrue("The result of getLocalNumber() is not equal 14!",a == 14);
    }

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("Private field 'class_number' of MainClass is not great than 45!", getClassNumber() > 45);
    }

    @Test
    public void testGetClassString() {
        Assert.assertTrue("Private field String 'class_string' is not contains 'hello' or 'Hello'.",
                (getClassString().indexOf("Hello") > -1 || getClassString().indexOf("hello") > -1));
    }
}
