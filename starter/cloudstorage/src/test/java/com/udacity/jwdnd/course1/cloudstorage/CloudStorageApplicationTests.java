package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {

		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void mainFlow() {
		String username = "superDuperUser";
		String password = "admin";
		driver.get("http://localhost:" + this.port + "/login");
		SignupPage signupPage = new SignupPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		HomePage homePage = new HomePage(driver);

		//should load login page
		assertTrue(loginPage.getTitle().isDisplayed());
		loginPage.goToSignup();

		//should go to sign up page
		assertTrue(signupPage.getTitle().isDisplayed());

		//should not show success message
		assertThrows(NoSuchElementException.class, signupPage::getSuccessMessage);
		//should not show error message
		assertThrows(NoSuchElementException.class, signupPage::getErrorMessage);

		// sign up new user
		signupPage.signup("Super", "Duper", username, password);
		assertTrue(signupPage.getSuccessMessage().isDisplayed());

		// login
		signupPage.goToLogin();
		assertTrue(loginPage.getTitle().isDisplayed());
		
		// should hide logout and error messages
		assertThrows(NoSuchElementException.class, loginPage::getErrorMessage);
		assertThrows(NoSuchElementException.class, loginPage::getLogoutMessage);

		loginPage.login(username+"error", password);
		assertTrue(loginPage.getErrorMessage().isDisplayed());

		loginPage.login(username, password);

		//home page
		assertTrue(homePage.getTitle().isDisplayed());

		//logout
		homePage.logout();
		assertTrue(loginPage.getLogoutMessage().isDisplayed());

	}

}
