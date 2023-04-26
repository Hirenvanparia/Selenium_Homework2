package org.example;

import com.google.common.annotations.VisibleForTesting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestSuit {
    protected static WebDriver driver;
    // expected Massage for registration
    static String expectedRegistrationCompleteMsg = "Your registration completed";
    // expected Massage for  vote
    static String expectedUserVoteMsg = "Only registered users can vote.";
    // expected Massage for  email a friend
    static String expectedEmailFriendMsg = "Only registered customers can use email a friend feature";
    // expected Massage for  compare a product
    static String expectedCompareItemMsg = "You have no items to compare.";
    // expected Massage for  item in shopping cart
    static String expectedCartMsg = "Leica T Mirrorless Digital Camera";
    // expected Massage for  registered vote
    static String expectedRegisteredVoter = "15 vote(s)...";
    //expected Massage for Refer A Friend
    static String expectedReferAFriend = "Your message has been sent.";

    @BeforeMethod
    public static void openBrowser() {
        driver = new ChromeDriver();
        driver.get("https://demo.nopcommerce.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public static void closeBrowser() {
        driver.close();
    }


    @Test
    public static void verifyUserShouldBeAbleToRegistrationSuccessfully() {
        // click on register button
        clickOnElement(By.className("ico-register"));
        // type Firstname
        typeText(By.id("FirstName"), "Raj");
        // type LastName
        typeText(By.id("LastName"), "Patel");
        // type email Address
        typeText(By.name("Email"), "testemail+" + timestamp() + "@gmail.com");
        // type password
        typeText(By.id("Password"), "Patel1234");
        // type confirm password
        typeText(By.id("ConfirmPassword"), "Patel1234");
        // click on register submit button
        clickOnElement(By.id("register-button"));
        // gettext from Web element
        String actualMassage = getTextFromElement(By.xpath("//div [@ class= \"result\"]"));
        System.out.println("My Massage:" + actualMassage);
        Assert.assertEquals(actualMassage, expectedRegistrationCompleteMsg, "registration is not working");

    }

    @Test
    public static void verifyUserShouldBeAbelToVoteSuccessfully() {
        // click on good
        clickOnElement(By.id("pollanswers-2"));
        // click on vote
        clickOnElement(By.id("vote-poll-1"));
        // web waiting
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"poll-vote-error\"]")));
        // get text from web element
        String voteMassage = getTextFromElement(By.xpath("//div[@class=\"poll-vote-error\"]"));
        System.out.println("My Massage:" + voteMassage);
        Assert.assertEquals(voteMassage, expectedUserVoteMsg, " Your Vote Can Not Registered Successfully");
    }

    @Test

    public static void verifyUserShouldBeAbleToEmailAFriend() {
        //click on Apple Macbook pro
        clickOnElement(By.xpath("// a [ @ href=\"/apple-macbook-pro-13-inch\"]"));
        // click on email a friend
        clickOnElement(By.className("email-a-friend"));
        //type friends email
        typeText(By.id("FriendEmail"), "Potermail+" + timestamp() + "@gmail.com");
        // type your email
        typeText(By.id("YourEmailAddress"), "Harryemail+" + timestamp() + "@gmail.com");
        // type personal massage
        typeText(By.name("PersonalMessage"), "This is a Very Good Macbook");
        // click on send email
        clickOnElement(By.name("send-email"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // get text web element
        String emailAFriendMsg = getTextFromElement(By.xpath("//div [@ class = 'message-error validation-summary-errors']"));
        System.out.println("My Massage:" + emailAFriendMsg);
        Assert.assertEquals(emailAFriendMsg, expectedEmailFriendMsg, " Only Registered User Can Email");
    }

    @Test
    public static void verifyUserShouldBeAbleToCompareTwoProduct() {
        //click on Electronics
        clickOnElement(By.xpath("//div [@class=\"header-menu\"]/ul[1]/li[2]/a[1]"));
        // click on Cell Phone
        clickOnElement(By.xpath("//div [@class=\"category-grid sub-category-grid\"]/div[1]/div[2]"));
        //select the Product
        clickOnElement(By.linkText("HTC One M8 Android L 5.0 Lollipop"));
        //click on add to compare list
        clickOnElement(By.xpath("//div[@class=\"compare-products\"]"));
        //click on Gift Cards
        clickOnElement(By.xpath("//div[@class=\"header-menu\"]/ul[1]/li[7]/a[1]"));
        //select the Product
        clickOnElement(By.linkText("$25 Virtual Gift Card"));
        //click on add to compare list
        clickOnElement(By.xpath("//div[@class=\"compare-products\"]"));
        // click on Product comparison
        clickOnElement(By.xpath("//div[@id=\"bar-notification\"]/div[1]/p[1]/a[1]"));
        // print first product name
        String actualMessage1 = getTextFromElement(By.linkText("HTC One M8 Android L 5.0 Lollipop"));
        System.out.println("First Product:" + actualMessage1);
        // print second product name
        String actualMessage2 = getTextFromElement(By.linkText("$25 Virtual Gift Card"));
        System.out.println("Second Product:" + actualMessage2);
        // click on clear list
        clickOnElement(By.className("clear-list"));
        // print compare massage
        String compareMessage = getTextFromElement(By.xpath("//div[@class=\"page-body\"]"));
        System.out.println("My Message:" + compareMessage);
        Assert.assertEquals(compareMessage, expectedCompareItemMsg, "products comparison");
    }

    @Test
    public static void verifyUserShouldBeAbleToAddItemToShoppingCart() {
        // click on electronics
        clickOnElement(By.linkText("Electronics"));
        // click on camara & photo
        clickOnElement(By.linkText("Camera & photo"));
        // select the product
        clickOnElement(By.linkText("Leica T Mirrorless Digital Camera"));
        // add to cart
        clickOnElement(By.id("add-to-cart-button-16"));
        //click on shopping cart
        clickOnElement(By.linkText("Shopping cart"));
        //get text web element
        String addToCartMsg = getTextFromElement(By.xpath("//*[@id=\"shopping-cart-form\"]/div[1]/table/tbody/tr/td[3]"));
        System.out.println("My Message:" + addToCartMsg);
        Assert.assertEquals(addToCartMsg, expectedCartMsg, "Item Add To Shopping Cart");
    }

    @Test
    public static void verifyRegisteredUserShouldBeAbleTOVote() {
        // click on register button
        clickOnElement(By.className("ico-register"));
        // type Firstname
        typeText(By.id("FirstName"), "Harry");
        // type LastName
        typeText(By.id("LastName"), "Porter");
        // type email Address
        typeText(By.name("Email"), "Harryemail+" + timestamp() + "@gmail.com");
        // type password
        typeText(By.id("Password"), "Porter1234");
        // type confirm password
        typeText(By.id("ConfirmPassword"), "Porter1234");
        // click on register submit button
        clickOnElement(By.id("register-button"));
        //click on login button
        clickOnElement(By.className("ico-login"));
        // type A email address
        typeText(By.id("Email"), "Harry@gmail.com");
        // type A Password
        typeText(By.id("Password"), "Porter1234");
        //click on login button
        clickOnElement(By.xpath("//a[@href=\"/login?returnUrl=%2F\"]"));
        //click on good button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        clickOnElement(By.id("pollanswers-2"));
        //click on vote button
        clickOnElement(By.id("vote-poll-1"));
        //get text web element
        String voteMsg = getTextFromElement(By.xpath("//span[@class=\"poll-total-votes\"]"));
        System.out.println("My Message:" + voteMsg);
        Assert.assertEquals(voteMsg, expectedRegisteredVoter, "Total Vote");
    }

    @Test
    public static void verifyRegisteredUserShouldBeAbleToReferAFriend() {
        //click on register
        clickOnElement(By.className("ico-register"));
        // type Firstname
        typeText(By.id("FirstName"), "Harry");
        // type LastName
        typeText(By.id("LastName"), "Porter");
        // type email Address
        typeText(By.name("Email"), "Harryemail+" + timestamp() + "@gmail.com");
        // type password
        typeText(By.id("Password"), "Porter1234");
        // type confirm password
        typeText(By.id("ConfirmPassword"), "Porter1234");
        // click on register submit button
        clickOnElement(By.id("register-button"));
        //click on login button
        clickOnElement(By.xpath("//a[@href=\"/login?returnUrl=%2F\"]"));
        // type A email address
        typeText(By.id("Email"), "Harry@gmail.com");
        // type A Password
        typeText(By.id("Password"), "Porter1234");
        //click on login button
        clickOnElement(By.xpath("//div[@class=\"buttons\"]//button[@class=\"button-1 login-button\"]"));
        //click on Apple Macbook pro
        clickOnElement(By.xpath("// a [ @ href=\"/apple-macbook-pro-13-inch\"]"));
        // click on email a friend
        clickOnElement(By.className("email-a-friend"));
        //type friends email
        typeText(By.id("FriendEmail"), "Potermail+" + timestamp() + "@gmail.com");
        //type a PersonalMassage
        typeText(By.id("PersonalMessage"), "This is a Very Good Macbook");
        String emailAFriendMsg = getTextFromElement(By.xpath("//div [@class=\"result\"]"));
        System.out.println("My Message" + emailAFriendMsg);
        Assert.assertEquals(emailAFriendMsg, expectedReferAFriend, "Your Message Sent");

    }

    public static void clickOnElement(By by) {
        driver.findElement(by).click();
    }

    public static void typeText(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    public static String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }

    public static long timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }
}


