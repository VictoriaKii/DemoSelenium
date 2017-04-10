package demoProject;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
	private String homePageUrl = "https://www.musicfolder.com/";
	private String expectedHomePageUrl = "https://www.musicfolder.com/ca/";
	private String expectedHomePageTitle = "Music Folder";
	
	private By closePopupXpath = By.xpath("//button[@class = 'close' and @data-dismiss = 'alert']");
	private By musicFoldersLinkXpath = By.xpath("(//li[@class = 'menu level-top']/a[contains(@href, 'music-folders')])");
	
	
	private WebDriver driver;	
	private WebDriverWait wait;
	
	public HomePage(BrowserDriver browserDriver)	{	
														
		this.driver = browserDriver.get();				
//		this.driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 10);
	}
	
	public HomePage open() {	
		driver.get(homePageUrl);
		
		if (isOpen() == false)
			throw new RuntimeException("home page is not opened");
		return this;
//	method to close holidays alert
//		closePopupIfDisplayed(); 
	}
	
	private boolean isOpen(){
		boolean isUrlCorrect = wait.until(urlToBe(expectedHomePageUrl));
		boolean isTitleCorrect = wait.until(titleIs(expectedHomePageTitle));
		return isUrlCorrect && isTitleCorrect;
	}

	private void closePopupIfDisplayed() {	
		try {
			WebElement closePopup = wait.until(elementToBeClickable(closePopupXpath));
			closePopup.click();
		}
		catch (Exception exception) {
			
		}
	}
	
	public ResultsPage selectMusicFolder() {
		WebElement musicFolderesLink = wait.until(elementToBeClickable(musicFoldersLinkXpath));
		musicFolderesLink.click();
		ResultsPage resultsPage = new ResultsPage(driver);
		return resultsPage;
	}
}
