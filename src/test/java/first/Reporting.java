package first;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class Reporting {


	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println("BeforTest_reportSetup");
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("E:\\report\\extent1.html");
		htmlReporter.loadXMLConfig("src/test/java/first/extent.config.xml");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		ExtentTest test = extent.createTest("Active links");
		System.out.println("reporting started");
		htmlReporter.config().setDocumentTitle("TESTBOT");
		htmlReporter.config().setTheme(Theme.STANDARD);
		test.assignAuthor("SHEEBA");
		System.setProperty("webdriver.chrome.driver", "E:\\Ecl\\drivers\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.gmail.com");
		driver.findElement(By.xpath("(//input[@class = \"whsOnd zHQkBf\"])[1]"))
				.sendKeys("sheeba@american-technology.net");
		driver.findElement(By.xpath("//div[@id=\"identifierNext\"]")).click();
		JPasswordField pf = new JPasswordField();
		JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		String password = new String(pf.getPassword());
		driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//span[text() = \"Next\"]")).click();
		Thread.sleep(3000);
		WebElement search = driver.findElement(By.xpath("//input[@placeholder = \"Search mail\"]"));
		search.clear();
		search.sendKeys("New Testing Request from : label:unread ");
		search.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//span[starts-with(text(), \"New Testing Request from\")])[last()]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//p[@class = \"MsoNormal\"]/a[1]")).click();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		String url = driver.getCurrentUrl();
		Thread.sleep(3000);
		System.out.println(url);
		driver.get(url);
		List<WebElement> links = driver.findElements(By.tagName("a"));
		int n = links.size();
		System.out.println("Total links are " + n);
		// test.log(Status.INFO, "Total links are: " + n);
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < n; i++) {
			WebElement ele = links.get(i);
			String url1 = ele.getAttribute("href");
			list.add(url1);
		}

		writeExcel(list, n, url,extent,test);
		extent.flush();
		System.out.println("Exported to excel");

		

		driver.quit();

	}

	public static void writeExcel(List<String> list, int num, String url2, ExtentReports extent1, ExtentTest test2) throws IOException {

		int counter = 0;
		int counter2 = 0;
		ExtentTest logger=extent1.createTest("Inactive links");
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet("WriteExcel");
		Row row1 = sheet.createRow(0);
		Cell cell1 = row1.createCell(0);
		Cell cell2 = row1.createCell(1);
		cell1.setCellValue("Total number of links");
		cell2.setCellValue(num);

		Row row2 = sheet.createRow(1);
		Cell cell3 = row2.createCell(0);
		Cell cell4 = row2.createCell(1);
		cell3.setCellValue("Application URL");
		cell4.setCellValue(url2);
		test2.log(Status.INFO, "Application URL is: " + url2);

		Row row5 = sheet.createRow(4);
		Cell cell9 = row5.createCell(0);
		Cell cell10 = row5.createCell(1);
		cell9.setCellValue("LINKS");
		cell10.setCellValue("RESULT");

		int size = list.size();
		for (int i = 5; i < size; i++) {
			String value = list.get(i);
			Row row = sheet.createRow(i);
			for (int j = 0; j < 1; j++) {
				Cell cell = row.createCell(j);
				System.out.println(value);
				cell.setCellValue(value);
				

				

				for (int k = 1; k < 2; k++) {

					try {
						URL url = new URL(value);
						HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

						httpURLConnect.setConnectTimeout(3000);

						httpURLConnect.connect();


						if (httpURLConnect.getResponseCode() == 200) {

							String res = httpURLConnect.getResponseMessage();
							Cell cell11 = row.createCell(k);
							cell11.setCellValue(res);
							System.out.println(res);
							counter++;

						}else {
							//logger.log(Status.PASS, "Inactive link: " + url);
						
						if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
							logger.log(Status.PASS, "Inactive link: " + httpURLConnect);
							String res = httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND;
							
							Cell cell12 = row.createCell(k);
							cell12.setCellValue(res);
							System.out.println(res);

						}
						}
					} catch (MalformedURLException e) {
						
						
						Cell celll3 = row.createCell(k);
						celll3.setCellValue("NOT OK");

						System.out.println("The URL is not valid.");
						System.out.println(e.getMessage());
						counter2++;
						
					} catch (ClassCastException e) {
						Cell celll3 = row.createCell(k);
						celll3.setCellValue("NOT OK");

						System.out.println("The URL is not valid.");
						System.out.println(e.getMessage());
						counter2++;
						

					} catch (SocketTimeoutException e) {
						Cell celll3 = row.createCell(k);
						celll3.setCellValue("NOT OK");
						System.out.println("Timeout");
						counter2++;
						

					} catch (Exception e) {
						Cell celll3 = row.createCell(k);
						celll3.setCellValue("NOT OK");
						e.printStackTrace();
						counter2++;
						
					}

				}
			}
		}
		logger.log(Status.PASS, "Inactive links: " + counter2);		
		test2.log(Status.PASS, "Active links: " + counter);
		FileOutputStream out = new FileOutputStream(new File("E:\\report\\demo.xlsx"));
		workBook.write(out);
		out.close();
		workBook.close();

	}

}
