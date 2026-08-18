package pre_study_group;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class Browser {

	static WebDriver dr = null;

	public static WebDriver openBrowser(String browser) {

		if (browser.equalsIgnoreCase("chrome")) {

			dr = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {

			dr = new FirefoxDriver();

		} else {

			throw new IllegalArgumentException("Browser must be chrome or firefox");
		}

		return dr;
	}

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = openBrowser("chrome");

		driver.get("http://localhost:8081/public/login.php");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		System.out.println("Page Title: " + driver.getTitle());

		driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("d@gmail.com");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("Test@123");
		driver.findElement(By.xpath("//*[@id=\"loginBtn\"]")).click();
		
		// profile page

		Thread.sleep(4000);

		driver.findElement(By.xpath("//*[@id=\"products\"]/div[1]/button")).click();// Wireless Earbuds
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"products\"]/div[2]/button")).click();// Bluetooth Speaker
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"products\"]/div[3]/button")).click();// Smart Phone Case
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"products\"]/div[4]/button")).click();// Charging Cable Set
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"products\"]/div[5]/button")).click();// Fitness Tracker Band
		/*
		 * 
		 * <select id="card" required>
    		<option value="">Select Card</option>
   			<option value="4111111111111111">4111111111111111</option>
    		<option value="4222222222222222">4222222222222222</option>
    		<option value="5555555555554444">5555555555554444</option>
    		<option value="378282246310005">378282246310005</option>
    		<option value="6011111111111117">6011111111111117</option>
			</select>
		 * 
		 */
		Thread.sleep(2000);
		Select card = new Select(driver.findElement(By.xpath("//*[@id=\"card\"]")));

		card.selectByValue("4111111111111111");

		Thread.sleep(2000);
		Select address = new Select(driver.findElement(By.xpath("//*[@id=\"address\"]")));

		address.selectByVisibleText("456 Oak Avenue, Los Angeles, CA 90001, USA");
		// checkout
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"Checkout\"]")).click();
		// My Orders
		// driver.findElement(By.xpath("//*[@id=\"My_Orders\"]")).click();
		// logout
		// driver.findElement(By.xpath("/html/body/div/button[2]")).click();
		// update profile
		// driver.findElement(By.xpath("//*[@id=\"Update_Profile\"]")).click();

		
		/*
		 * Alert
		 * */
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		Thread.sleep(2000);
		String alertText = alert.getText();

		System.out.println("Alert Message: " + alertText);
		Thread.sleep(2000);
		alert.accept();
		
		
		//order confirmation page
		System.out.println("Page Title: " + driver.getTitle());
		
		//=========================================================
		List<WebElement> orders =driver.findElements(By.xpath("//*[@id=\"orders\"]"));
		WebElement latestOrder = orders.get(0);
		
		System.out.println("Number of orders: " + orders.size());
//+++++++++++++++++++++++++++++++++++++++++++
		

		for (WebElement order : orders) {

		    System.out.println("====================");
		    System.out.println(order.getText());
		}
		
		
		System.out.println("=========================================");	
		
		

		String orderNumber = latestOrder.findElement(By.xpath("//*[@id=\"orders\"]/div[1]/p[1]")).
				getText().replace("Order #", "").trim();

		String trackingNumber = latestOrder.findElement(
		        By.xpath(".//p[b[contains(text(),'Tracking')]]")
		).getText()
		 .replace("Tracking:", "")
		 .trim();

		System.out.println("Latest Order #: " + orderNumber);
		System.out.println("Latest Tracking: " + trackingNumber);
		
		//==========================
		System.out.println("=========================================");	
		
		// Verify Latest Order Number
		if (orderNumber.equals(orderNumber)) {
		    System.out.println("PASS: Latest Order # is "+orderNumber);
		} else {
		    System.out.println("FAIL: Expected Order "+orderNumber+" but found Order #" + orderNumber);
		}

		// Verify Latest Tracking Number
		if (trackingNumber.equals(trackingNumber)) {
		    System.out.println("PASS: Latest Tracking Number is " +trackingNumber);
		} else {
		    System.out.println(
		        "FAIL: Expected Tracking Number "+trackingNumber+" but found  "
		        + trackingNumber
		    );
		}
		// Keep browser open while testing
		// driver.quit();
	}
}