package interview.practice;
/*Backend API Test Automation
Create Account on https://newsapi.org/
Get API_KEY for your account
1. Use https://newsapi.org/v2/top-headlines for Given Country (Eg. India)
2. Fetch top headlines as GET Request to above URL
3. Use parameters as "KEYWORD TO BE SEARCHED" AND "#TOP RANK"(numeric) as
parameters to the test case from a List of Values(keyword n rank combinations).
4. Walk through each headline and check if "KEYWORD TO BE SEARCHED" is present in
Title or Description of Headlines, if present upto "TOP RANK" list of headlines - pass,
otherwise fail.
5. Generate Presentable Test Results Report
Eg.
keyword: Inding Cricket Team
Rank: 30
Test: check if "Indian Cricket Team" is found in top 30 Titles or Description of Headlines
returned by this API. If found - mark test pass otherwise fail. And move to next value pairs from
Test data provider.
*/

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class BackendAPI {
	
	WebDriver driver;
	String apiKey="3e2439032545436caad84693bcbeeb0f";
	
	@BeforeTest
	public void setUp()
	{  
	     
	     System.setProperty("webdriver.gecko.driver","./Drivers/geckodriver.exe");
			driver= new FirefoxDriver();
	}
	
	@BeforeClass
	public void loginPage()
	{	
		driver.get("https://newsapi.org/");
		System.out.println("testing url");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@Test
	public void APITestCase()
	{
		String getValue = driver.findElement(By.xpath("//div[@class='pa3 lh-copy bl b--black-10']")).getText();
		String[] fetchApi=getValue.split("API_KEY");
		
		for(String element: fetchApi)
		{
			driver.get(element.concat(apiKey));
		}
	}
	
	@Parameters({"keywordToBeSearched", "TopRank"})
	@Test
	public void testAPI(String keywordToBeSearched, int topRank)
	{ 
		List<WebElement> list=driver.findElements(By.xpath("//*[starts-with(@class,'objectBox objectBox-string')]"));
		
		for(WebElement newValue: list)
		{	
			int count=0;
			String compareKeyword = newValue.getText();
			String keywordValue = driver.findElement(By.xpath("//span[contains(text(),'title')]")).getText();
			
			  while(keywordValue.contains("title"))
		        {
		        	count++;
			
			if(keywordToBeSearched==compareKeyword && count==topRank)
			{
				Assert.assertSame(compareKeyword, keywordToBeSearched);
			}
			
	        }	
	  	}
	}
	
	@AfterClass
	public void tearDwon()
	{
		driver.quit();
	}

}
