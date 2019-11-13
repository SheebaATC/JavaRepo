package TestNG;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class TestNGSample {

	  @Test
	  public void verifyHomepage() {
	       
	      
	      System.setProperty("webdriver.chrome.driver", "E:\\Ecl\\drivers\\chromedriver_win322\\chromedriver.exe");
	      WebDriver driver = new ChromeDriver();
		  driver.manage().window().maximize();
	      driver.get("http://192.168.5.105:1234/safe-training-enrollment/course-content/?id=SA20190824");
	      String actualTitle = driver.getTitle();
	      System.out.println(actualTitle);
	      driver.close();
	  }
}
