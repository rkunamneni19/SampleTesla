import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.core.annotation.Order;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestTeslaSearch {
    WebDriver driver;
    private String browser;
    Properties prop=new Properties();

    public String br;
    public String cpath;
    public String fpath;
    public String url;
    public String spath;
    public String intext;
    public String falseTitle;
    public String title;
    private String ftest;

    @Before
    public void initTest() throws IOException, FileNotFoundException {
        FileInputStream ip= new FileInputStream("C:\\Users\\Ramesh\\IdeaProjects\\SampleTesla\\src\\test\\java\\config.properties");
        prop.load(ip);
        FileInputStream ip1= new FileInputStream("C:\\Users\\Ramesh\\IdeaProjects\\SampleTesla\\src\\test\\java\\object.properties");
        prop.load(ip1);

        browser=prop.getProperty("inbrowser");
        //please modify the property file data before you run it.
        cpath=prop.getProperty("chromepath");
        fpath=prop.getProperty("firefoxpath");

        //Check if parameter passed as 'chrome'
        if(browser.equalsIgnoreCase("chrome"))
        {
            //set path to chromedriver.exe
            System.setProperty("webdriver.chrome.driver",cpath);
            //create chrome instance
            driver = new ChromeDriver();
        }
        //Check if parameter passed as 'firefox'
        else if(browser.equalsIgnoreCase("firefox"))
        {
            //create firefox instance
            System.setProperty("webdriver.firefox.marionette", fpath);
            driver = new FirefoxDriver();
        }
        else {
            System.out.println("Please choose the chrome browser for your test");
            driver.quit();
        }
        url=prop.getProperty("url");
        driver.get(url);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test() throws InterruptedException, IOException
    {
        spath=prop.getProperty("searchpath");
        WebElement in1 = driver.findElement(By.xpath(spath));
        //Fill search text
        intext=prop.getProperty("input");
        in1.sendKeys(intext);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        in1.sendKeys(Keys.ENTER);

        List<WebElement> links=driver.findElements(By.partialLinkText(intext));

       ArrayList<String> targets = new ArrayList<String>();
        //collect targets locations
        for (WebElement link : links) {
            targets.add(link.getAttribute("href"));
        }
        int i=0;
        for (String target : targets)
        {
            driver.navigate().to(target);
            i++;
            Thread.sleep(6000);
            String actualtitle=driver.getTitle();


            System.out.println("title is : "+actualtitle);
            if (i<3)
            {
                if (target.isEmpty())
                {
                    continue;
                }
                title =actualtitle;
                TestCase.assertTrue(title.contains(intext));
                System.out.println("Test :  "+title);
                driver.navigate().back();
            }
            else
            {
                break;
            }
        }
    }

    @Test
    public void Test() {
        spath=prop.getProperty("searchpath");
        WebElement in1 = driver.findElement(By.xpath(spath));
        intext=prop.getProperty("input");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        in1.sendKeys(intext);
        in1.sendKeys(Keys.ENTER);
        ftest=prop.getProperty("falsetext");
       // System.out.println("ftest: "+ftest);
        TestCase.assertTrue(driver.getPageSource().contains(ftest));
        System.out.println("Test02:"+ftest);
    }
    @After
    public void last()
    {
        driver.quit();
    }

}
