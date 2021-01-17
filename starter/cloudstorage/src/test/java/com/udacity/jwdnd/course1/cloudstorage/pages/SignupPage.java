package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private final WebDriver driver;
    private final By title = By.cssSelector("[data-testid='title']");
    
    @FindBy(css = "#inputFirstName")
    private WebElement firstNameField;

    @FindBy(css = "#inputLastName")
    private WebElement lastNameField;

    @FindBy(css = "#inputUsername")
    private WebElement usernameField;

    @FindBy(css = "#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "[data-testid='submit-button']")
    private WebElement submitButton;

    @FindBy(css = "[data-testid='success-message']")
    private WebElement successMessage;

    @FindBy(css = "[data-testid='login-link']")
    private WebElement loginLink;

    @FindBy(css = "[data-testid='error-message']")
    private  WebElement errorMessage;



    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.driver = webDriver;
    }

    public void signup(String firstName, String lastName, String username, String password) {
        this.firstNameField.sendKeys(firstName);
        this.lastNameField.sendKeys(lastName);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }

    public WebElement getSuccessMessage() {
        return this.driver.findElement(By.cssSelector("[data-testid='success-message']"));
    }

    public WebElement getErrorMessage() {
        return this.driver.findElement(By.cssSelector("[data-testid='error-message']"));
    }
    public boolean isErrorMsgInDoc() {
        try{
            return errorMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public void goToLogin() {
        this.loginLink.click();
    }

    public WebElement getTitle() {
        return this.driver.findElement(title);
    }
}
