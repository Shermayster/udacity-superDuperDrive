package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;




public class LoginPage {
    private final String errorMessageSelector = "[data-testid='error-message']";
    private final String logoutMessageSelector = "[data-testid='logout-message']";
    private final By byTitle = By.cssSelector("[data-testid='title']");
    private final By submitBtn = By.cssSelector("[data-testid='login-button']");

    private final WebDriver driver;
    @FindBy(css="#inputUsername")
    private WebElement usernameField;

    @FindBy(css="#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "[data-testid='signup-link']")
    private WebElement signupLink;

   

    public LoginPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.driver.findElement(submitBtn).click();
    }

    public WebElement getErrorMessage() {
        return this.driver.findElement(By.cssSelector(errorMessageSelector));
    }

    public WebElement getLogoutMessage() {
        return this.driver.findElement(By.cssSelector(logoutMessageSelector));
    }

    public WebElement getTitle() {
        return this.driver.findElement(byTitle);
    }

    public void goToSignup() {
        this.signupLink.click();
    }

}
