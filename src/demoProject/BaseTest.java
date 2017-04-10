package demoProject;

import org.junit.After;
import org.junit.Before;

public class BaseTest {
	
	BrowserDriver driver;
	
	@Before 
	public void setUp() throws Exception {
	  driver = new BrowserDriver("chrome");
	}
		                               
	@After
	public void tearDown() {        
	  driver.quit();        
	}      
}
