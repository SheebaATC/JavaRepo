package SeleniumMaven.Seleniummavendemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class sample {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "E:\\Ecl\\drivers\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com");
		driver.findElement(By.xpath("//input[@title=\"Search\"]")).sendKeys("India");
		driver.findElement(By.xpath("(//input[@name='btnK'])[2]")).click();
		Thread.sleep(3000);
		
		
		
		driver.close();
		

	}

}
