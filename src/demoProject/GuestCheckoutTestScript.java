package demoProject;

import static org.junit.Assert.*;

import org.junit.Test;

public class GuestCheckoutTestScript extends BaseTest {

	int RESULT_INDEX = 6;
	int index = 2;
	double taxRate = 0.13;
	
	String firstName = "Just";
	String lastName = "Test";
	String email = "just.test@gmail.com";
	String phone = "613-555-0167";
	String address = "65 Dundas Street West, 303";
	String city = "Toronto";
	String state = "Ontario";
	String zipCode = "M5C 2C3";
	
	@Test
	public void guestCheckout() {
		
		HomePage homePage = new HomePage(driver);	
		
		ResultsPage resultsPage = homePage.open().selectMusicFolder();	
		assertEquals(resultsPage.numberOfResults(), 10); 
		
		String titleResult = resultsPage.resultTitle(RESULT_INDEX);
		
		DetailsPage detailsPage = resultsPage.selectResult(RESULT_INDEX);
		assertTrue(detailsPage.isDisplayed(titleResult) == true);
		detailsPage.orderNow();
	
		double productPrice = detailsPage.basePrice();
	 
		detailsPage.goToFolderNumberingTab()
				   .goTextImprintingTab()
			       .goLogoImprintingTab();
	
		CheckoutPage checkoutPage = detailsPage.addToCart().secureCheckout();
	
		checkoutPage.selectCheckoutMethod("guest")
					.addBillingInfo(firstName, lastName, email, phone, address, city, state, zipCode)
					.selectUpsStandard();
		
		double shippingFee = checkoutPage.shippingFee(index);
		
		checkoutPage.selectPaymentOption();
		
		assertTrue(checkoutPage.subtotal() == productPrice);
		assertTrue(checkoutPage.actualShipping() == shippingFee);
		assertTrue(checkoutPage.actualTax() == expectedTax(productPrice, shippingFee));
		assertTrue(checkoutPage.actualTotal() == expectedTotal(productPrice, shippingFee));
	}
	
	private double expectedTax(double productPrice, double shippingFee) {
		return Math.round(((productPrice + shippingFee) * taxRate) * 100.0) / 100.0;
	}
	
	private double expectedTotal(double productPrice, double shippingFee) {
		return Math.round((productPrice + shippingFee + expectedTax(productPrice, shippingFee)) * 100.0) / 100.0;
	}
}