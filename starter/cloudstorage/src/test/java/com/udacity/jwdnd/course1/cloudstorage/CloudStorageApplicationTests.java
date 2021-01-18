package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
		driver.get("http://localhost:" + this.port + "/");
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
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
	public void mainAuthFlow() {
		String username = "superDuperUser";
		String password = "admin";

		// should restrict home page and load login page for unauthorized user
		driver.get("http://localhost:" + this.port + "/home");
		assertTrue(loginPage.getTitle().isDisplayed());
		loginPage.goToSignup();

		// should go to sign up page
		assertTrue(signupPage.getTitle().isDisplayed());

		// should not show success message
		assertThrows(NoSuchElementException.class, signupPage::getSuccessMessage);
		// should not show error message
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

		loginPage.login(username + "error", password);
		assertTrue(loginPage.getErrorMessage().isDisplayed());

		loginPage.login(username, password);

		// home page
		assertTrue(homePage.getTitle().isDisplayed());

		// logout
		homePage.logout();
		assertTrue(loginPage.getLogoutMessage().isDisplayed());
		driver.get("http://localhost:" + this.port + "/home");
		assertTrue(loginPage.getTitle().isDisplayed());

		// should show an error message if user already exist
		loginPage.goToSignup();
		signupPage.signup("Super", "Duper", username, password);
		assertTrue(signupPage.getErrorMessage().isDisplayed());
	}

	@Test
	public void notesFlow() {
		login("superDuperUser", "admin");
		// add note
		this.homePage.goToNotesTab();
		this.homePage.addNote("test note", "this the test note");
		this.homePage.goToNotesTab();
		assertEquals( 1, this.homePage.getNoteRowList().size());
		assertEquals("test note", this.homePage.getGetNoteTitleList().get(0).getText());
		assertEquals("this the test note", this.homePage.getGetNoteDescriptionList().get(0).getText());

		// edit note
		this.homePage.getNoteEditBtnList().get(0).click();
		WebElement noteTitleInput = this.homePage.getNoteTitleInput();
		WebElement noteDescriptionInput = this.homePage.getNoteDescriptionInput();
		WebElement saveNotesChangeBtn = this.homePage.getSaveNoteChangesBtn();
		assertEquals("test note",noteTitleInput.getAttribute("value"));
		assertEquals("this the test note",noteDescriptionInput.getAttribute("value"));
		noteTitleInput.clear();
		noteTitleInput.sendKeys("edited note");
		saveNotesChangeBtn.click();
		this.homePage.goToNotesTab();
		assertEquals( 1, this.homePage.getNoteRowList().size());
		assertEquals("edited note", this.homePage.getGetNoteTitleList().get(0).getText());
		assertEquals("this the test note", this.homePage.getGetNoteDescriptionList().get(0).getText());
		// delete note
		this.homePage.getGetNoteDeleteBtnList().get(0).click();
		assertEquals( 0, this.homePage.getNoteRowList().size());
	}

	@Test
	public void credentialFlow() throws InterruptedException {
		String testUrl = "superduper.com";
		String testUsername = "admin";
		String testPassword = "admin";
		login("superDuperUser", "admin");
		// add credentials

		homePage.goToCredentialsTab();
		homePage.addCredential(testUrl, "admin", "admin");
		homePage.goToCredentialsTab();
		assertEquals(1, homePage.getCredentialRowList().size());
		assertEquals(testUrl, homePage.getCredentialUrlList().get(0).getText());
		assertEquals(testUsername, homePage.getCredentialUsernameList().get(0).getText());
		// password should encrypted
		assertNotEquals(testPassword, homePage.getCredentialPasswordList().get(0).getText());
		assertTrue(homePage.getCredentialPasswordList().get(0).getText().length() > testPassword.length());
		//edit credentials
		homePage.getCredentialEditBtnList().get(0).click();
		assertTrue(homePage.testCredentialForm(testUrl, testUsername, testPassword));
		String updatedUrl = "foo.com";
		String updatedUsername = "username";
		String updatedPassword = "password";
		homePage.getCredentialUrlInput().clear();
		homePage.getCredentialUrlInput().sendKeys(updatedUrl);
		homePage.getCredentialUsernameInput().clear();
		homePage.getCredentialUsernameInput().sendKeys(updatedUsername);
		homePage.getCredentialPasswordInput().clear();
		homePage.getCredentialPasswordInput().sendKeys(updatedPassword);
		homePage.getCredentialFormSubmit().click();
		homePage.goToCredentialsTab();
		homePage.getCredentialEditBtnList().get(0).click();
		assertTrue(homePage.testCredentialForm(updatedUrl, updatedUsername, updatedPassword));
		homePage.getCredentialUrlInput().sendKeys("should note be updated");
		homePage.closeCredentialForm();
		assertEquals(updatedUrl, homePage.getCredentialUrlList().get(0).getText());

		// delete credentials
		homePage.deleteFirstCredential();
		homePage.goToCredentialsTab();
		assertEquals(0, homePage.getCredentialRowList().size());
	}




	private void signUp(String username, String password) {
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup("Super", "Duper", username, password);
		signupPage.goToLogin();
		loginPage.login(username, password);
	}
	private void login(String username, String password) {
		loginPage.login(username, password);
	}

}
