package demoProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
	
	private String expectedUrl = "https://www.musicfolder.com/ca/checkout/";
	private String expectedCheckoutPageTitle = "Checkout";
	private By continueLocator = By.id("onepage-guest-register-button");
	private By continueXpath = By.xpath("//button[@onclick='billing.save()']");
	private By continueXpathShipping = By.xpath("//button[@onclick='shippingMethod.save()']");
	private By continueXpathPayment = By.xpath("//button[@onclick='payment.save()']");
	
	private By nameFieldLocator =  By.id("billing:firstname");
	private By lastNameFieldLocator =  By.id("billing:lastname");
	private By emailFieldLocator =  By.id("billing:email");
	private By phoneFieldLocator =  By.id("billing:telephone");
	private By addressFieldLocator =  By.id("billing:street1");
	private By cityFieldLocator =  By.id("billing:city");
	private By provinceFieldLocator =  By.id("billing:region_id");
	private By postalCodeFieldLocator =  By.id("billing:postcode");
	
	private String shippingOptions = "(//dl[@id='sp_methods']//li)";
	private By chequeOptionLocator = By.id("p_method_checkmo");
	private By priceLocator = By.xpath("//a[@class = 'cart-summary']//span[@class = 'price']");
	
	private By subtotalLocator = By.xpath("//td[contains(text(), 'Subtotal')]/following-sibling::td");
	private By shippingLocator = By.xpath("//td[contains(text(), 'Shipping')]/following-sibling::td");
	private By taxLocator = By.xpath("//td[contains(text(), 'Tax')]/following-sibling::td");
	private By totalLocator = By.xpath("//strong[contains(text(), 'Total')]//following::td");
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public CheckoutPage(WebDriver driver){ 
		this.driver = driver;	
		wait = new WebDriverWait(driver, 10);
		
		if(isOpen() == false)
			throw new RuntimeException("SendToFriend page not open");
	}
	
	private boolean isOpen() {		
		boolean isUrlCorrect = wait.until(urlContains(expectedUrl));
		boolean isTitleCorrect = wait.until(titleIs(expectedCheckoutPageTitle));
		return isUrlCorrect && isTitleCorrect;
	}
	
	public CheckoutPage selectCheckoutMethod(String customerType)  {
		
		String customerTypeLocator = "//input[@id = 'login:" + customerType + "']";
		WebElement selectedCustomerType = wait.until(elementToBeClickable(By.xpath(customerTypeLocator)));
		selectedCustomerType.click();
		selectContinue();
		return this;
	}
	
	private void selectContinue() {
		WebElement continueBtn = wait.until(elementToBeClickable(continueLocator));
		continueBtn.click();
	}
	
	public CheckoutPage addBillingInfo(String firstName, String lastName, String email, String phone, String address, 
			String city, String state, String zipCode) {
		
		WebElement nameField = wait.until(visibilityOfElementLocated(nameFieldLocator));
		nameField.sendKeys(firstName);
		
		WebElement lastNameField = wait.until(visibilityOfElementLocated(lastNameFieldLocator));
		lastNameField.sendKeys(lastName);
		
		WebElement emailField = wait.until(visibilityOfElementLocated(emailFieldLocator));
		emailField.sendKeys(email);
		
		WebElement phoneField = wait.until(visibilityOfElementLocated(phoneFieldLocator));
		phoneField.sendKeys(phone);
		
		WebElement addressField = wait.until(visibilityOfElementLocated(addressFieldLocator));
		addressField.sendKeys(address);
		
		WebElement cityField = wait.until(visibilityOfElementLocated(cityFieldLocator));
		cityField.sendKeys(city);
		
		Select dropdown = new Select(driver.findElement(provinceFieldLocator));
		dropdown.selectByVisibleText(state);
	
		WebElement postalCodeField = wait.until(visibilityOfElementLocated(postalCodeFieldLocator));
		postalCodeField.sendKeys(zipCode);
		
		WebElement continueBtn = wait.until(elementToBeClickable(continueXpath));
		continueBtn.click();
		return this;
	}
	
	public CheckoutPage selectUpsStandard() {
		return selectShipping(2);
	}
	
	public CheckoutPage selectShipping(int optionIndex) {
		String shippingOptionXpath = shippingOptions + "[" + optionIndex + "]/input";
		WebElement shippingOption = wait.until(elementToBeClickable(By.xpath(shippingOptionXpath)));
		shippingOption.click();
		WebElement continueBtn = wait.until(elementToBeClickable(continueXpathShipping));
		continueBtn.click();
		return this;
	}
	
	public double shippingFee(int index ) {
		String shippingPriceXpath = shippingOptions + "[" + index + "]//span";
		WebElement shippingPriceElement = wait.until(visibilityOfElementLocated(By.xpath(shippingPriceXpath)));
		String shippingPrice = shippingPriceElement.getText().replace("CA$", "");
		return Double.parseDouble(shippingPrice);
	}
	
	public CheckoutPage selectPaymentOption() {
		WebElement chequeOption = wait.until(elementToBeClickable(chequeOptionLocator));
		chequeOption.click();
		WebElement continueBtn = wait.until(elementToBeClickable(continueXpathPayment));
		continueBtn.click();
		return this;
	}
	
	public double subtotal() {
		WebElement subtotalElement = wait.until(visibilityOfElementLocated(subtotalLocator));
		String subtotalText = subtotalElement.getText().replace("CA$", "");
		double subtotal = Double.parseDouble(subtotalText);
		return subtotal;
	}
	
	public double actualShipping() {
		WebElement shippingElement = wait.until(visibilityOfElementLocated(shippingLocator));
		String shipping = shippingElement.getText().replace("CA$", "");
		return Double.parseDouble(shipping);
	}
	
	public double productPrice() {
		WebElement priceElement = wait.until(visibilityOfElementLocated(priceLocator));
		String price = priceElement.getText().replace("CA$", "");
		return Double.parseDouble(price);
	}
	
	public double actualTax() {
		WebElement taxElement = wait.until(visibilityOfElementLocated(taxLocator));
		String tax = taxElement.getText().replace("CA$", "");
		return Double.parseDouble(tax);
	}
	
	public double actualTotal() {
		WebElement totalElement = wait.until(visibilityOfElementLocated(totalLocator));
		String total = totalElement.getText().replace("CA$", "");
		return Double.parseDouble(total);
	}
}