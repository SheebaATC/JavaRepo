package TestNG;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;


public class start {

	public static void main(String[] args) throws InterruptedException  {
	
		System.setProperty("webdriver.chrome.driver", "E:\\Ecl\\drivers\\chromedriver_win322\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://learn-staging.auzmor.com/login");
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.findElement(By.xpath("//*[@placeholder='Enter your email address / username']")).sendKeys("balakrishnan@american-technology.net");
		driver.findElement(By.xpath("//*[@type='password']")).sendKeys("ATCTech123!");
		driver.findElement(By.xpath("//*[@id='root']//div[4]/button")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[@id='root']/div[1]//a)[4]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='root']/div[1]//button")).click();
		driver.findElement(By.xpath("//*[@placeholder='Give title to the course']")).sendKeys("S1 check");
		try
			{
				if(driver.findElement(By.xpath("//*[@placeholder='Enter your email address / username']")).isDisplayed())
					{
						String payload="{\"status_id\":\"1\"}";
						String requestUrl="https://auzmorhr.testrail.io/index.php?/api/v2/add_result/14934";
						sendPostRequest(requestUrl, payload);
					}
			}
		catch(WebDriverException e)
			{
				String payload="{\"status_id\":\"5\"}";
				String requestUrl="https://auzmorhr.testrail.io/index.php?/api/v2/add_result/14934";
				sendPostRequest(requestUrl, payload);
			}
	}
	
	public static String sendPostRequest(String requestUrl, String payload) {
	    try
	    	{
	    		URL url = new URL(requestUrl);
	    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    		connection.setDoInput(true);
	    		connection.setDoOutput(true);
	    		connection.setRequestMethod("POST");
	    		connection.setRequestProperty("Accept", "application/json");
	    		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	    		connection.setRequestProperty("Authorization", "Basic bGlraGl0aGFAYW1lcmljYW4tdGVjaG5vbG9neS5uZXQ6MTIzNDU2");
	    		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	    		writer.write(payload);
	    		writer.close();
	    		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    		StringBuffer jsonString = new StringBuffer();
	    		String line;
	    		while ((line = br.readLine()) != null)
	    			{
	    				jsonString.append(line);
	    			}
	    		br.close();
	    		connection.disconnect();
	    		return jsonString.toString();
	    	} 
	    catch (Exception e)
	    {
	            throw new RuntimeException(e.getMessage());
	    }

	}

}
