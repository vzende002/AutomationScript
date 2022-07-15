package StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePageStepDef {
	
	WebDriver driver;
	
	@Before
	public void set_Browser() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\zende\\Downloads\\Selenium Downloads\\Drivers\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@After
	public void close_Browser() {
		driver.close();
		driver.quit();
	}
	
	
	@Given("I navigate to the PwC Digital Pulse website")
	public void navigate_to_the_website() {
	    // Launching the PwC Digital Pulse Website
		try {
			
			driver.get("https://www.pwc.com.au/digitalpulse.html");
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			System.out.println("--Launched the Digital Pulse Website Successfully--");
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Failed to navigate to the PwC Digital Pulse website");
		}
			
	}

	@When("I am viewing the Home page")
	public void verify_home_page() {
	    // Verify that the right page has been launched
		try {
			
			Assert.assertEquals("https://www.pwc.com.au/digitalpulse.html", driver.getCurrentUrl());
			 System.out.println("-------Verified that I have navigated to correct webpage-------");
		}
		catch(Exception e) 
		{
			System.out.println("Failed to view the Home page");
		}
	}
	
	@Then("I am viewing the Subscribe page")
	public void verify_subscribe_page() 
	{
		try {
			Assert.assertEquals("https://www.pwc.com.au/digitalpulse/subscribe.html", driver.getCurrentUrl());
			 System.out.println("-------Verified that I have navigated to correct webpage-------");
		}
		catch(Exception e)
		{
			System.out.println("I am viewing the Subscribe page");
		}
	}

	@Then("I am presented with {string} columns of articles")
	public void verify_no_of_columns_in_home_page(String expected_no_of_col) {
	    // Verify that there are 3 columns of articles
	    try {
	    	
	    	int actual_no_of_cols=driver.findElements(By.xpath("//div[contains(@class,\"headline_column\")]")).size();
	    	Assert.assertEquals(expected_no_of_col, Integer.toString(actual_no_of_cols));
	    	System.out.println("-------Verified that there are "+expected_no_of_col+ " of articles on the homepage-------");
	    	
	    }
	    catch(Exception e) 
	    {
	    	System.out.println("Failed to presented with "+expected_no_of_col+" columns of articles");
	    }
	}
	
	@And("The {string} column is displaying {string} articles")
	public void verify_no_of_articles_in_each_column(String column_type, String no_of_articles) 
	{
		try 
		{
			
			String row_xpath="";
			if(column_type.equalsIgnoreCase("middle"))
					row_xpath="//div[@class=\"headline_column1\"]/article";
			else if(column_type.equalsIgnoreCase("left"))
					row_xpath="//div[@class=\"headline_column2\"]/article";
			else if(column_type.equalsIgnoreCase("right"))
					row_xpath="//div[@class=\"headline_column3\"]/article";
			
			
			int actual_articles=driver.findElements(By.xpath(row_xpath)).size();
			
			Assert.assertEquals(no_of_articles, Integer.toString(actual_articles));
	    	System.out.println("-------Verified that the "+column_type+ " column has "+ no_of_articles + " articles on the homepage-------");
			
		}
		catch(Exception e) 
		{
			System.out.println("Error At: The "+column_type+" column is displaying "+no_of_articles+" articles");
			Assert.fail();
		}
		
		
	}
	
	@When("I click on {string}")
	public void click(String click_name) 
	{
		try {
			WebElement element=null;
			if(click_name.equalsIgnoreCase("Subscribe")) 
			{
				element= driver.findElement(By.xpath("(//*[@navigation-title='Subscribe'])[1]"));
				element.click();
				WebDriverWait wt = new WebDriverWait(driver,60);
				WebElement FirstName =driver.findElement(By.xpath("//input[@id='FirstName']"));
				
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", FirstName);
				wt.until(ExpectedConditions.visibilityOf(FirstName));
				
			}
			else if(click_name.equalsIgnoreCase("Magnifying glass icon") || click_name.equalsIgnoreCase("Search"))
				element=driver.findElement(By.xpath("//button[@class='search-hide levelOneLink']"));
			
			else if(click_name.equalsIgnoreCase("Submit Search"))
				element=driver.findElement(By.xpath("//input[@class='submit-search']"));
			
			element.click();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			
			System.out.println("---Successfully clicked "+click_name+" ----");
			
		}
		catch(Exception e) 
		{
			System.out.println("Failed to click method");
			Assert.fail();
		}
	}
	
	
	@And("Enter data in all the fields in the form")
	public void fill_the_subscribe_form() {
		try {
		// Locators for the mentioned fields	
			WebElement firstname= driver.findElement(By.xpath("//input[@id='FirstName']"));
			WebElement lastname= driver.findElement(By.xpath("//input[@id='LastName']"));
			WebElement organisation= driver.findElement(By.xpath("//input[@id='Company']"));
			WebElement job_title= driver.findElement(By.xpath("//input[@id='JobTitle']"));
			WebElement email= driver.findElement(By.xpath("//input[@id='EmailAddress']"));
			WebElement state= driver.findElement(By.xpath("//select[@id='State']"));
			WebElement country= driver.findElement(By.xpath("//select[@id='Country']"));
			
			firstname.sendKeys("Jon");
			lastname.sendKeys("Deo");
			organisation.sendKeys("PwC");
			job_title.sendKeys("Sr.Associate");
			email.sendKeys("jon.deo@pwc.com");
			
			Select select=new Select(state);
			select.selectByValue("ACT");
			
			select =new Select(country);
			select.selectByValue("Australia");
			
			System.out.println("--Completed the Subscribe form fields----");
			
		}
		catch(Exception e) 
		{
			System.out.println("Failed to enter data in all the fields in the form");
			e.printStackTrace();
			Assert.fail();
			
		}
	}
	
	@And("I will need to complete Google reCAPTCHA")
	public void complete_recaptcha() 
	{
		try 
		{
			driver.findElement(By.xpath("//div[@class='recaptcha-checkbox-checkmark']")).click();
			System.out.println("------Sucessfully completed google Captcha---------");
		}
		catch(Exception e) 
		{
			System.out.println("Failed to complete Google reCAPTCHA");
			Assert.fail();
		}
	}
	
	@And("I will verify that the submit button is enabled")
	public void verify_submit_button_in_subscribe_form() 
	{
		try 
		{
			if(driver.findElement(By.xpath("//input[@id='btn-submit']")).isEnabled())
				System.out.println("--Submit button is enabled--");
			else
				throw new Exception("Submit button not clickable");
		}
		catch(Exception e)
		{
			System.out.println("Failed to verify that the submit button is enabled");
			Assert.fail();
		}
	}
	
	
	@And("I enter the text {string}")
	public void enter_text_in_search_field(String text) 
	{
		try 
		{
			driver.findElement(By.xpath("//input[@name='searchfield']")).sendKeys(text);
			System.out.println("------Sucessfully entered text - "+text+" in searchbox---------");
		}
		catch(Exception e)
		{
			System.out.println("Failed to enter the text "+text);
			Assert.fail();
		}
	}
	
	
	@And("I submit the search")
	public void submit_the_search()
	{
		try {
			click("Submit Search");
			System.out.println("---Successfully submitted search---");
		}
		catch(Exception e) 
		{
			System.out.println("Failed to submit the search");
		}
	}
	
	@Then("I am taken to the search results page")
	public void verify_search_page()
	{
		try {
			driver.findElement(By.xpath("//title[contains(text(),'Search results')]"));
			System.out.println("--Successfully taken to serach results page---");
		}
		catch(Exception e) 
		{
			System.out.println("Error At: I am taken to the search results page");
			Assert.fail();
		}
	}
	
	@And("I am presented with at least {string} search result")
	public void verify_search_result_minimum_count(String count) {
		try {
			int search_count=driver.findElements(By.xpath("//div[contains(@class,'sr-with-img') or contains(@class,'pull-right')]")).size();
			int min_expected_count=Integer.parseInt(count);
			
			if(min_expected_count<=search_count)
				System.out.println("----Verified min search result count---");
			else
				throw new Exception("Min search result count not reached");	
		}
		catch(Exception e) 
		{
			System.out.println("I am presented with at least "+count+" search result");
			Assert.fail();
		}
		
	}
}


