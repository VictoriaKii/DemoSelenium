package demoProject;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {
	
	private String expectedResultsPageTitle = "Music Folders";
	private String expectedResultsPageUrl = "https://www.musicfolder.com/ca/music-folders";
	
	//locator to find 9 result products
	private By resultListXpath = By.xpath("//ol[@id = 'products-list']/li");
	
	private String resultsXpath = "(//ol[@id='products-list']/li)";
		
	private By titleResultXpath = By.xpath(".//h2[@class = 'product-name']/a");
	private By priceResultXpath = By.xpath(".//div[@class = 'price-box']//span[@class='price']");
	
	private By descriptionResultXpath = By.xpath(".//div[@class = 'desc std']");
	private By viewDetailsResultXpath = By.xpath(".//div[@class = 'add-to-bag']/a");
	private By resultCountLocator = By.xpath("(//div[@class = 'total-count'])[2]");
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public ResultsPage (WebDriver drv){
		driver = drv;
		wait = new WebDriverWait(driver, 10);
		
		if(isOpen() == false)
			throw new RuntimeException("results page not open");
	}
	
	private boolean isOpen() {		
		boolean isUrlCorrect = wait.until(urlToBe(expectedResultsPageUrl));
		boolean isTitleCorrect = wait.until(titleIs(expectedResultsPageTitle));
		return isUrlCorrect && isTitleCorrect;
	}

	public int numberOfResults() {
		List<WebElement> resultList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(resultListXpath));
		return resultList.size();
	}
	
	public int resultCount() {
		WebElement resultCountLabel = wait.until(visibilityOfElementLocated(resultCountLocator));
		String resultCountText = resultCountLabel.getText().trim();
		int spaceIndex = resultCountText.indexOf(" ");
		resultCountText = resultCountText.substring(0, spaceIndex);
		return Integer.parseInt(resultCountText);
	}
	
	public boolean doesResultCountTextInclude(String text) {
		return wait.until(ExpectedConditions.textToBePresentInElementLocated(resultCountLocator, text));
	}
	
	public boolean isTitleDisplayed(int resultIndex){
		String resultXpath = resultsXpath + "[" + resultIndex + "]"; 
		WebElement result = wait.until(elementToBeClickable(By.xpath(resultXpath))); 
		WebElement titleElement = result.findElement(titleResultXpath);
		return titleElement.getText().length() > 0; 
	}
	
	public String resultTitle(int resultIndex){ //get resultIndex parameter - and will return a string
		String resultXpath = resultsXpath + "[" + resultIndex + "]"; 
		WebElement result = wait.until(elementToBeClickable(By.xpath(resultXpath))); 
		WebElement titleElement = result.findElement(titleResultXpath); 
		return titleElement.getText();
	}
	
	public double price(int resultIndex){
		String resultXpath = resultsXpath + "[" + resultIndex + "]"; 
		WebElement result = wait.until(elementToBeClickable(By.xpath(resultXpath)));
		WebElement priceElement = result.findElement(priceResultXpath);
		String priceText = priceElement.getText().replace("CA$", "");
		double price = Double.parseDouble(priceText);
		return price;
	}

	public boolean isDescriptionDisplayed(int resultIndex){
		String resultXpath = resultsXpath + "[" + resultIndex + "]"; 
		WebElement result = wait.until(elementToBeClickable(By.xpath(resultXpath)));
		WebElement descriptionElement = result.findElement(descriptionResultXpath);
		return descriptionElement.getText().length() > 0;
		
	}
	
	public boolean isViewDetailsDisplayed(int resultIndex){
		String resultXpath = resultsXpath + "[" + resultIndex + "]"; 
		WebElement result = wait.until(elementToBeClickable(By.xpath(resultXpath)));
		WebElement viewDetailsElement = result.findElement(viewDetailsResultXpath);
		return viewDetailsElement.isDisplayed();
	}
	
	public DetailsPage selectResult(int resultIndex) {
		String resultXpath = resultsXpath + "[" + resultIndex + "]"; 
		WebElement result = wait.until(elementToBeClickable(By.xpath(resultXpath))); 
		WebElement resultTitle = result.findElement(titleResultXpath);
		resultTitle.click();
		DetailsPage detailsPage = new DetailsPage(driver);
		return detailsPage;
	}
	
}
