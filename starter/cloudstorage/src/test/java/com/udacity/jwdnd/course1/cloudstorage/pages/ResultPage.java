package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ResultPage {
    public WebDriver driver;

    @FindBy(css = "[data-testid='error-message']")
    private WebDriver errorMessage;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isSuccess() {
        return this.driver.findElement(By.cssSelector("[data-testid='success']")).isDisplayed();
    }

    public boolean isError() {
        return this.driver.findElement(By.cssSelector("[data-testid='error']")).isDisplayed();
    }

    public void gotoHomePage() {
        if(isSuccess()) {
            this.driver.findElement(By.cssSelector("[data-testid='success-home-link']"));
        } else {
            this.driver.findElement(By.cssSelector("[data-testid='error-home-link']"));
        }
    }

}
