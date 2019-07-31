
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;

public class CalculatorTest {

    private static WindowsDriver CalculatorSession = null;
    private static WebElement CalculatorResult = null;

    @BeforeClass
    public static void setup() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            //Microsoft.WindowsCalculator_8wekyb3d8bbwe!App
            //Microsoft.WindowsCalculator_10.1905.30.0_x64__8wekyb3d8bbwe!App
            //Microsoft.WindowsCalculator_10.1905.30.0_x64__8wekyb3d8bbwe
            capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            CalculatorSession = new WindowsDriver<RemoteWebElement>(new URL("http://127.0.0.1:4723"), capabilities);
            CalculatorSession.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            CalculatorResult = CalculatorSession.findElementByAccessibilityId("CalculatorResults");
            Assert.assertNotNull(CalculatorResult);
            }catch(Exception e){
            e.printStackTrace();
        } finally {
            System.out.println("");
        }
    }
    @Test
    public void verifyTitle()
    {

        String title=CalculatorSession.getTitle();
        Assert.assertEquals("Calculator", title);
        System.out.println("Test01: title of the page is :"+title);
    }

    @Test
    public void additionTrue()
    {
        CalculatorSession.findElementByName("Five").click();
        CalculatorSession.findElementByName("Plus").click();
        CalculatorSession.findElementByName("Five").click();
        CalculatorSession.findElementByName("Equals").click();
        Assert.assertEquals("10", _GetCalculatorResultText());
        System.out.println("Test01:(5+5)= : "+ _GetCalculatorResultText());
        CalculatorSession.findElementByName("Minus").click();
        CalculatorSession.findElementByName("Three").click();
        CalculatorSession.findElementByName("Equals").click();
        Assert.assertEquals("7", _GetCalculatorResultText());
        System.out.println("Test01:(5+5)-3= : "+ _GetCalculatorResultText());
    }
    @Test
    public void additionFalse()
    {
        CalculatorSession.findElementByName("Five").click();
        CalculatorSession.findElementByName("Plus").click();
        CalculatorSession.findElementByName("Five").click();
        CalculatorSession.findElementByName("Equals").click();
        Assert.assertEquals("11", _GetCalculatorResultText());
        System.out.println("Test02:Addition (5+5=11): "+ _GetCalculatorResultText());
    }

    @AfterClass
    public static void TearDown()
    {
        CalculatorResult = null;
        if (CalculatorSession != null) {
            CalculatorSession.quit();
        }
        CalculatorSession = null;
    }

    @Before
    public void Clear()
    {
        CalculatorSession.findElementByName("Clear").click();
        Assert.assertEquals("0", _GetCalculatorResultText());
        System.out.println("Test : initialise to 0"+_GetCalculatorResultText());
    }

    protected String _GetCalculatorResultText()
    {
        // trim extra text and whitespace off of the display value
        return CalculatorResult.getText().replace("Display is", "").trim();
    }

}
