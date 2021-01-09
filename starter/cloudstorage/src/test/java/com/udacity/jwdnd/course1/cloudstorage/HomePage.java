package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final String titleSelector = "[data-testid='title']";
    private WebDriver driver;

   @FindBy(css = "[data-testid='logout']")
   private WebElement logoutBtn;
    
    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.driver = webDriver;
    }
    public WebElement getTitle() {
        return this.driver.findElement(By.cssSelector(titleSelector));
    }

    public void logout() {
        this.logoutBtn.click();
    }
    
}