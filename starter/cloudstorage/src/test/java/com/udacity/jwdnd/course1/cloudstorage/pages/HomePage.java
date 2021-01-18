package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    private final String titleSelector = "[data-testid='title']";
    private WebDriver driver;
    private NoteSection noteSection;
    private CredentionSection credentionSection;

    @FindBy(css = "[data-testid='logout']")
    private WebElement logoutBtn;
    @FindBy(css = "[data-testid='notes-link']")
    public WebElement notesLink;
    /* note section */
    private static class NoteSection {
        @FindBy(css = "[data-testid='note-title-input']")
        public WebElement noteTitleInput;
        @FindBy(css = "[data-testid='note-description-input']")
        public WebElement noteDescriptionInput;
        @FindBy(css = "[data-testid='save-note-changes-btn']")
        public WebElement saveNoteChangesBtn;
        @FindAll({@FindBy(css = "[data-testid='notes-list']")})
        public List<WebElement> noteRowList;
        @FindAll({@FindBy(css = "[data-testid='edit-note-btn']")})
        public List<WebElement> noteEditBtnList;
        @FindAll({@FindBy(css ="[data-testid='note-title']")})
        public List<WebElement> getNoteTitleList;
        @FindAll({@FindBy(css ="[data-testid='delete-note-btn']")})
        public List<WebElement> getNoteDeleteBtnList;

        @FindBy(css = "[data-testid='add-note-btn']")
        public WebElement addNoteBtn;
        @FindAll({@FindBy(css ="[data-testid='note-description']")})
        private List<WebElement> getNoteDescriptionList;
        public NoteSection(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }
    }

    public WebElement getNoteTitleInput() {
        return noteSection.noteTitleInput;
    }

    public WebElement getNoteDescriptionInput() {
        return noteSection.noteDescriptionInput;
    }

    public WebElement getSaveNoteChangesBtn() {
        return noteSection.saveNoteChangesBtn;
    }

    public List<WebElement> getGetNoteTitleList() {
        return noteSection.getNoteTitleList;
    }

    public List<WebElement> getGetNoteDescriptionList() {
        return noteSection.getNoteDescriptionList;
    }

    public List<WebElement> getGetNoteDeleteBtnList() {
        return noteSection.getNoteDeleteBtnList;
    }

    public void goToNotesTab() {notesLink.click();}

    public void addNote(String title, String description) {
        this.noteSection.addNoteBtn.click();
        this.noteSection.noteTitleInput.sendKeys(title);
        this.noteSection.noteDescriptionInput.sendKeys(description);
        this.noteSection.saveNoteChangesBtn.click();
    }

    public List<WebElement> getNoteRowList() {
        return this.noteSection.noteRowList;
    }

    public List<WebElement> getNoteEditBtnList() {
        return this.noteSection.noteEditBtnList;
    }

    /* credential section */
    private class CredentionSection {
        @FindBy(css = "[data-testid='credentials-link']")
        public WebElement tabLink;

        @FindBy(css = "[data-testid='add-credential-btn']")
        public WebElement addButton;

        @FindBy(css = "[data-testid='credential-url-input']")
        public WebElement urlInput;
        @FindBy(css = "[data-testid='credential-username-input']")
        public WebElement userNameInput;
        @FindBy(css = "[data-testid='credential-password-input']")
        public WebElement passwordInput;

        @FindBy(css = "[data-testid='credentials-submit']")
        public WebElement submitBtn;
        @FindBy(css = "[data-testid='credential-form']")
        public WebElement form;

        @FindAll({@FindBy(css = "[data-testid='credentials-row']")})
        public List<WebElement> rowList;

        @FindAll({@FindBy(css = "[data-testid='credential-url']")})
        public List<WebElement> urlList;

        @FindAll({@FindBy(css = "[data-testid='credential-username']")})
        public List<WebElement> usernameList;

        @FindAll({@FindBy(css = "[data-testid='credential-password']")})
        public List<WebElement> passwordList;

        @FindAll({@FindBy(css = "[data-testid='credential-edit']")})
        public List<WebElement> editBtnList;

        @FindAll({@FindBy(css = "[data-testid='credential-delete']")})
        public List<WebElement> deleteBtnList;

        public WebElement getCloseFormBtn() {
            return driver.findElement(By.cssSelector("[data-testid='credentials-close-form']"));
        };

        public CredentionSection(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }
    }

    public void goToCredentialsTab() {
        credentionSection.tabLink.click();
    }

    public void openNewCredentialForm() {
        credentionSection.addButton.click();
    }


    public void addCredential(String url, String username, String password) {
        openNewCredentialForm();
       credentionSection.urlInput.sendKeys(url);
       credentionSection.userNameInput.sendKeys(username);
       credentionSection.passwordInput.sendKeys(password);
       credentionSection.submitBtn.click();
    }


    public boolean testCredentialForm(String url, String username, String password) {
        return credentionSection.urlInput.getAttribute("value").equals(url)
                && credentionSection.userNameInput.getAttribute("value").equals(username)
                && credentionSection.passwordInput.getAttribute("value").equals(password);
    }

    public WebElement getCredentialUsernameInput() {
        return credentionSection.userNameInput;
    };
    public WebElement getCredentialPasswordInput() {
        return credentionSection.passwordInput;
    };
    public WebElement getCredentialUrlInput() {
        return credentionSection.urlInput;
    };
    public WebElement getCredentialFormSubmit() {
        return credentionSection.submitBtn;
    };
    public void closeCredentialForm() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement closeBtn = credentionSection.getCloseFormBtn();
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn));
        credentionSection.getCloseFormBtn().click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("[data-testid='credential-modal']"))));
    }
    public List<WebElement> getCredentialRowList() {
        return credentionSection.rowList;
    }
    public List<WebElement> getCredentialUsernameList() {
        return credentionSection.usernameList;
    }
    public List<WebElement> getCredentialPasswordList() {
        return credentionSection.passwordList;
    }
    public List<WebElement> getCredentialEditBtnList() {return credentionSection.editBtnList;};

    public List<WebElement> getCredentialDeleteBtnList() {return credentionSection.deleteBtnList;};

    public List<WebElement> getCredentialUrlList() {
        return credentionSection.urlList;
    }
    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.driver = webDriver;
        this.noteSection = new NoteSection(this.driver);
        this.credentionSection = new CredentionSection(this.driver);
    }
    public WebElement getTitle() {
        return this.driver.findElement(By.cssSelector(titleSelector));
    }

    public void logout() {
        logoutBtn.click();
    }

}