package demoProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserDriver {
	
	WebDriver driver;
	
	String chromeDriverLocation = "C:/Users/victoria/Desktop/PROJECTS_SELENIUM/JARS AND CHROMEDRIVER/chromedriver.exe";
	String ieDriverLocation = "C:/Users/victoria/Desktop/PROJECTS_SELENIUM/JARS AND CHROMEDRIVER/IEDriverServer.exe";
	
	  public BrowserDriver(String browserName) throws Exception {
	     createDriver(browserName);
	  }

	  private void createDriver(String browserName) throws Exception {
	    switch (browserName) {
	       case "firefox":
	          this.driver = new FirefoxDriver();
	          break;
			
	       case "chrome":
	          System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
	          this.driver = new ChromeDriver();
	          break;

	       case "ie":
	          System.setProperty("webdriver.ie.driver", ieDriverLocation); 
	          this.driver = new InternetExplorerDriver();
	          break;
				
	      default:
	          throw new Exception("invalid browser name");
	    }				
	 }
				
	 public WebDriver get() {
	    return this.driver;
	 }
		
	 public void quit() {		
	    this.get().quit();		
	 }
}
