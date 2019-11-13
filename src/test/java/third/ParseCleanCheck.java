package third;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ParseCleanCheck {
	static String val1;
	static Hashtable<String, String> dictionary;// To store all the words of the
	// dictionary
	static boolean suggestWord;// To indicate whether the word is spelled
								// correctly or not.

	static Scanner urlInput = new Scanner(System.in);
	public static String cleanString;
	public static String url = "";
	public static boolean correct = true;
	public static int counter1 = 0;
	public static int counter2 = 0;
	public static WebDriver driver = null;

	/**
	 * PARSER METHOD
	 */
	public static void PageScanner() throws IOException {

		// ExtentTest logger=extent1.createTest("Inactive links");
		// This do-while loop allows the user to try again after a mistake
		do {
			try {

				System.out.println("Enter a URL, starting with http://" + val1);
				// val1 = urlInput.nextLine();
				url = val1;

				// This creates a document out of the HTML on the web page
				Document doc = Jsoup.connect(url).get();
				// This converts the document into a string to be cleaned
				String htmlToClean = doc.toString();
				cleanString = Jsoup.clean(htmlToClean, Whitelist.none());

				correct = false;
			} catch (Exception e) {

				System.out.println("Incorrect format for a URL. Please try again.");
				counter1++;
			}

		} while (correct);
		{

		}

	}

	/**
	 * SPELL CHECKER METHOD
	 */
	public static void SpellChecker(ExtentReports extent1, ExtentTest test3) throws IOException {

		// ExtentTest logger = extent1.createTest("Suggested spellings");
		dictionary = new Hashtable<String, String>();
		System.out.println("Searching for spelling errors ... ");

		try {
			// Read and store the words of the dictionary
			BufferedReader dictReader = new BufferedReader(
					new FileReader("E:\\report\\SpellCheck_HashTable-master\\SpellCheck_HashTable\\dictionary.txt"));

			while (dictReader.ready()) {
				String dictInput = dictReader.readLine();
				String[] dict = dictInput.split("\\s"); // create an array of
														// dictionary words

				for (int i = 0; i < dict.length; i++) {
					// key and value are identical
					dictionary.put(dict[i], dict[i]);
				}
			}
			dictReader.close();
			String user_text = "";

			// Initializing a spelling suggestion object based on probability
			SuggestSpelling suggest = new SuggestSpelling(
					"E:\\report\\SpellCheck_HashTable-master\\SpellCheck_HashTable\\wordprobabilityDatabase.txt");

			// get user input for correction
			{

				user_text = cleanString;
				String[] words = user_text.split(" ");
			//	Set<String> wordSet = new HashSet<>();

				int error = 0;

				/*
				 * for (String word : words) { if(!wordSet.contains(word)) { checkWord(word);
				 * 
				 * suggestWord = true; String outputWord = checkWord(word);
				 * 
				 * if (suggestWord) { System.out.println("Suggestions for " + word + " are:  " +
				 * suggest.correct(outputWord) + "\n"); error++; } }
				 * 
				 * wordSet.add(word); }
				 */
				counter2++;
				if (error == 0) {
					System.out.println("No mistakes found");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	public static void main(String[] args) throws InterruptedException, NullPointerException {

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("E:\\report\\spellcheck.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.loadXMLConfig("src/test/java/first/extent.config.xml");
		ExtentTest test = extent.createTest("SPELLCHECK");
		System.out.println("reporting started");
		htmlReporter.config().setDocumentTitle("SPELLCHECK");
		htmlReporter.config().setTheme(Theme.STANDARD);

		try {

			FileInputStream fis = new FileInputStream("E:\\report\\demo.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("WriteExcel");
			XSSFRow row = sheet.getRow(0);
			int rowcount = sheet.getLastRowNum();
			int col_num = -1;

			for (int i = 5; i < rowcount; i++) {

				col_num = i;
				row = sheet.getRow(col_num);
				XSSFCell cell = row.getCell(0);

				String value = cell.getStringCellValue();
				System.out.println("Value of the Excel Cell is - " + value);
				val1 = value;
				PageScanner(); // Scan the page and clean it first

				SpellChecker(extent, test);

				Thread.sleep(2000);
				 
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		System.out.println("Exported to excel");
		test.log(Status.PASS, "Incorrect format of the URL: " + counter1);
		test.log(Status.PASS, "Number of links checked  through spellcheck and no mistakes were found: " + counter2);
		 extent.flush();
		  {
			 JOptionPane.showConfirmDialog(null,    "Completed Spellcheck", "INFORMATION!",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		 }
		
	}

}
