package demoProject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import org.openqa.selenium.support.ui.WebDriverWait;

public class DetailsPage {
	
	private By orderNowId = By.id("order");
	private By customizeOptionsXpath = By.xpath("//div[@class = 'quote']/a[@id = 'order']");
	private By addToCartLocator = By.xpath("//div[@class='product-options-bottom 1']//button[@title='Add to Cart']");
	
	private By phase1IconLocator = By.xpath("//span[@data-item-id = '1']");
	private By phase2IconLocator = By.xpath("//span[@data-item-id = '2']");
	private By phase3IconLocator = By.xpath("//span[@data-item-id = '3']");
	private By phase4IconLocator = By.xpath("//span[@data-item-id = '4']");
	
	String secureCheckoutJS = "document.getElementById('eg-ajaxcart-checkout').click()";
	By basePriceLocator = By.xpath("//div[@class = 'product-options-bottom 1']//span[@class = 'regular-price']");
	
	private WebDriver driver;	
	private WebDriverWait wait;
	private JavascriptExecutor jsExecutor;
	
	public DetailsPage (WebDriver drv)	{	
		driver = drv;					
		wait = new WebDriverWait(driver, 10);
		jsExecutor = (JavascriptExecutor) driver;
	}
	
	//this method works for all the folders except for The Standard Black Folder and Deluxe. 
	//The titles for 1st and 2nd folders aren't consistent with the rest of products
	
	public Boolean isDisplayed(String resultTitle) { 
		Boolean isUrlCorrect = false;		
		Boolean isTitleCorrect = false;		
		
		isUrlCorrect = pageURL().contains(resultTitle.toLowerCase().replace("/ ", "").replace(" ", "-"));
		isTitleCorrect = pageTitle().contains(resultTitle.toLowerCase().replace("/ ", ""));
		return isUrlCorrect && isTitleCorrect; 
	}
	
	private String pageTitle() {
		return driver.getTitle().toLowerCase().replace("/ ", "");
	}

	private String pageURL() {
		return driver.getCurrentUrl();
	}
	
	public DetailsPage orderNow() {
		WebElement orderNowButton = wait.until(elementToBeClickable(orderNowId));
		orderNowButton.click();
		assertTrue(isModelOptionsTabDisplayed());
		return this;
	}
	
	public DetailsPage customizeYourOptions() {
		WebElement customizeYourOptionsButton = wait.until(elementToBeClickable(customizeOptionsXpath));
		customizeYourOptionsButton.click();
		return this;
	}
	
	public boolean is4StepProcessVisible() {
		boolean result;
		try {
			wait.until(visibilityOfElementLocated(phase1IconLocator)); 
			wait.until(visibilityOfElementLocated(phase2IconLocator));
			wait.until(visibilityOfElementLocated(phase3IconLocator));
			wait.until(visibilityOfElementLocated(phase4IconLocator));
			result = true;
		}
		catch (Exception exception){
			result = false;
		}
		return result;
	}
	
	public boolean isModelOptionsTabDisplayed() {
		return isPhaseSelected(1);
	}
	
	public DetailsPage goToFolderNumberingTab() {
		DetailsPage page = goToPhase(2);
		assertTrue(isPhaseSelected(2));
		return page;
	}
	
	public DetailsPage goTextImprintingTab() {
		DetailsPage page = goToPhase(3);
		assertTrue(isPhaseSelected(3));
		return page;
	}
	
	public DetailsPage goLogoImprintingTab() {
		DetailsPage page = goToPhase(4);
		assertTrue(isPhaseSelected(4));
		return page;
	}
	
	public boolean isPhaseSelected(int phaseNumber) {
		String phaseLocator = "//span[@data-item-id = '" + phaseNumber + "']";
		WebElement phaseElement = wait.until(elementToBeClickable(By.xpath(phaseLocator)));
		String classValue = phaseElement.getAttribute("class");
		return classValue.contains("current");
	}
	
	public DetailsPage goToPhase(int phaseNumber) {
		String phaseLocator = "//span[@data-item-id = '" + phaseNumber + "']";
		WebElement phaseElement = wait.until(elementToBeClickable(By.xpath(phaseLocator)));
		phaseElement.click();
		return this;
	}
	
	public double basePrice() {
		WebElement basePriceElement = wait.until(visibilityOfElementLocated(basePriceLocator));
		String basePriceText = basePriceElement.getText().replace("CA$", "").replace(" Each", "");
		double basePrice = Double.parseDouble(basePriceText);
		return basePrice;
	}
	
	public DetailsPage addToCart() {
		WebElement addToCartButton = wait.until(visibilityOfElementLocated(addToCartLocator));
		addToCartButton.click();
		return this;
	}
	
	public CheckoutPage secureCheckout() {
		jsExecutor.executeScript(secureCheckoutJS);
		return new CheckoutPage(driver);
		
	}	
}