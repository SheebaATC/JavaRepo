package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TestFile {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "E:\\Ecl\\drivers\\chromedriver_win322\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://192.168.5.105:1234/safe-training-enrollment/course-content/?id=SA20190824");
		driver.findElement(By.xpath("(//a[text() ='Industries'])[1]")).click();
		//driver.findElement(By.xpath("(//a[text() ='Learn More'])[2]")).click();
		System.out.println("Welcome");
		driver.close();
	}
}
