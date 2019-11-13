package first;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Final{

public static void main(String[] args) throws InterruptedException, IOException
{
System.setProperty("webdriver.chrome.driver", "E:\\\\Ecl\\\\drivers\\\\chromedriver_win32\\\\chromedriver.exe");
    WebDriver driver=new ChromeDriver();
    driver.manage().window().maximize();
    driver.get("https://www.gmail.com");
	driver.findElement(By.xpath("(//input[@class = \"whsOnd zHQkBf\"])[1]")).sendKeys("sheeba@american-technology.net");
	driver.findElement(By.xpath("//div[@id=\"identifierNext\"]")).click();
	JPasswordField pf = new JPasswordField();
	JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	  String password = new String(pf.getPassword());
	  driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys(password);
	  driver.findElement(By.xpath("//span[text() = \"Next\"]")).click();
	  Thread.sleep(3000);
	   WebElement search = driver.findElement(By.xpath("//input[@placeholder = \"Search mail\"]"));
	   search.clear();
	   search.sendKeys("New Testing Request from : label:unread ");
	   search.sendKeys(Keys.ENTER);
	   Thread.sleep(2000);
	driver.findElement(By.xpath("(//span[text() = \"New Testing Request from John\"])[4]")).click();
		 String username = driver.findElement(By.xpath("//font[text() = 'Application Username']/following::font[1]")).getText();
	 	System.out.println("Application username is:" +username);
		String password1 = driver.findElement(By.xpath("//font[text() = 'Application Password']/following::font[1]")).getText();
		System.out.println("Application password is:" +password1);
		  driver.findElement(By.xpath("//td[text() = 'Application Url']/following::a[1]")).click();
		for(String winHandle : driver.getWindowHandles()){
			 driver.switchTo().window(winHandle);
		}
		 
		 String url = driver.getCurrentUrl();
		 Thread.sleep(3000);
		 System.out.println(url);	
		 driver.get(url);
         List<WebElement> links=driver.findElements(By.tagName("a"));
        System.out.println("Total links are "+links.size());
        List<String> list = new ArrayList<String>();

         for(int i=0;i<links.size();i++)
         {
             WebElement ele= links.get(i);
             String url1=ele.getAttribute("href");
        	 list.add(url1);
        	 //verifyLinkActive(url1); 
            
         }
         
         writeExcel(list);
         System.out.println("Exported to excel");
         driver.quit();
         

     }

         
     public static void writeExcel(List<String> list) throws IOException
     {
    	 XSSFWorkbook workBook = new XSSFWorkbook();
    	 XSSFSheet sheet = workBook.createSheet("WriteExcel");
    	 for(int i = 0 ; i < list.size() ; i++) {
    		 String value = list.get(i);
    		 Row row = sheet.createRow(i);
    		 for(int j = 0 ; j < 1 ; j++) {
    			 Cell cell = row.createCell(j);
    			 System.out.println(value);
    			 cell.setCellValue(value);
    			 for (int k = 1 ; k < 2 ; k ++) {
    				 URL url = new URL(value);

    		            HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();

    		            httpURLConnect.setConnectTimeout(3000);

    		            httpURLConnect.connect();

    		            if(httpURLConnect.getResponseCode()==200)
    		            {
    		         
    		              String res = httpURLConnect.getResponseMessage();
    		              Cell cell1 = row.createCell(k);
    		              cell1.setCellValue(res);
    		              System.out.println(res);
    		             
    		             }
    		           if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)
    		            {
    		                String res = httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND;
    		                Cell cell1 = row.createCell(k);
    		                cell1.setCellValue(res);
    		                System.out.println(res);
    		             }
    			 }
    		 }
    	 }
    	 FileOutputStream out = new FileOutputStream(new File("E:\\report\\final5.xlsx"));
    	 workBook.write(out);
    	 out.close();
    	 workBook.close();
     }
     

     
	  

}

