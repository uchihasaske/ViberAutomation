import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import sun.tools.jconsole.JConsole;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    public static URL url;
    public static DesiredCapabilities cap;
    public static AndroidDriver<MobileElement> driver;
    public static MobileElement mElement = null;

    @BeforeSuite
    public void setupAppium() throws MalformedURLException {
        final String URL_STRING = "http://0.0.0.0:4723/wd/hub";
        url = new URL(URL_STRING);
        cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "Pixel2");
        cap.setCapability("udid", "emulator-5554");
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "10");
        cap.setCapability("appPackage", "com.viber.voip");
        cap.setCapability("appActivity", "com.viber.voip.WelcomeActivity");
        driver = new AndroidDriver<MobileElement>(url, cap);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        //driver.resetApp();
    }

    @AfterSuite
    public void uninstallApp() throws InterruptedException {
        //driver.removeApp("");
    }

    public static boolean isNewUser() {
        mElement = driver.findElementById(Elements.welcomeMessageTitle);
        if (mElement.isDisplayed()) {
            Assert.assertEquals(mElement.getText(), Constants.welcomeToViber);
            return true;
        }
        return false;
    }

    public static void verifyWelcomeScreen() {
        mElement = driver.findElementById(Elements.welcomeMessageTitle);
        Assert.assertEquals(mElement.getText(), Constants.welcomeToViber);
        mElement = driver.findElementById(Elements.welcomeMessage);
        Assert.assertEquals(mElement.getText(), Constants.welcomeMessage);
        mElement = driver.findElementById(Elements.termsAndPolicy);
        Assert.assertEquals(mElement.getText(), Constants.termsAndPolicyText);
        mElement = driver.findElementById(Elements.transferHistoryFromAnotherMobile);
        Assert.assertEquals(mElement.getText(), Constants.transferHistory);
        mElement = driver.findElementById(Elements.welcomePageContinue);
        Assert.assertEquals(mElement.getText(), Constants.continueButton);
    }

    public static void registerUser(String isdNo, String phoneNo) {
        mElement = driver.findElementById(Elements.countryIsdCode);
        enterUserInput(mElement, isdNo);
        mElement = driver.findElementById(Elements.countryName);
        Assert.assertEquals(mElement.getText(), "India");
        mElement = driver.findElementById(Elements.phoneNo);
        enterUserInput(mElement, phoneNo);
        Assert.assertEquals(mElement.getText(), phoneNo.substring(0, 5) + " " + phoneNo.substring(phoneNo.length() - 5));
        mElement = driver.findElementById(Elements.registerPageContinue);
        Assert.assertEquals(mElement.getText(), Constants.continueButton);
        mElement.click();
    }

    public static void validateConfirmRegistrationDialogBox(String phoneNo) {
        mElement = driver.findElementById(Elements.confirmRegistrationTitle);
        Assert.assertEquals(mElement.getText(), Constants.confirmPhoneMessage);
        mElement = driver.findElementById(Elements.confirmRegistrationPhoneNo);
//      Assert.assertEquals(mElement.getText(), phoneNo);
        mElement = driver.findElementById(Elements.confirmRegistrationMessage);
        Assert.assertEquals(mElement.getText(), Constants.codeActivationMessage);
        mElement = driver.findElementById(Elements.editButton);
        Assert.assertEquals(mElement.getText(), Constants.edit);
        mElement = driver.findElementById(Elements.yesButton);
        Assert.assertEquals(mElement.getText(), Constants.yes);
    }

    public MobileElement verifyRegisterPage() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        mElement = driver.findElementByXPath(Elements.registerPageTitle);
        Assert.assertEquals(mElement.getText(), "Enter your phone number");
        mElement = driver.findElementById(Elements.registerPageSubTitle);
        Assert.assertEquals(mElement.getText(), "Make sure you can receive SMS to this number so that we can send you a code.");
        mElement = driver.findElementById(Elements.countryName);
        Assert.assertEquals(mElement.getText(), "United States of America");
        mElement = driver.findElementById(Elements.countryIsdCode);
        Assert.assertEquals(mElement.getText(), "1");
        mElement = driver.findElementById(Elements.phoneNo);
        Assert.assertEquals(mElement.getText(), "Phone number");
        mElement = driver.findElementById(Elements.registerPageContinue);
        Assert.assertEquals(mElement.getText(), Constants.continueButton);
        return mElement;
    }

    public static void verifyConfirmRegistrationEditButton(String isdCode, String country, String phoneNo) {
        mElement = driver.findElementById(Elements.editButton);
        Assert.assertEquals(mElement.getText(), Constants.edit);
        mElement.click();
        mElement = driver.findElementByXPath(Elements.registerPageTitle);
        Assert.assertEquals(mElement.getText(), "Enter your phone number");
        mElement = driver.findElementById(Elements.registerPageSubTitle);
        Assert.assertEquals(mElement.getText(), "Make sure you can receive SMS to this number so that we can send you a code.");
        mElement = driver.findElementById(Elements.countryName);
        Assert.assertEquals(mElement.getText(), country);
        mElement = driver.findElementById(Elements.countryIsdCode);
        Assert.assertEquals(mElement.getText(), isdCode);
        mElement = driver.findElementById(Elements.phoneNo);
        Assert.assertEquals(mElement.getText(), phoneNo);
        mElement = driver.findElementById(Elements.registerPageContinue);
        Assert.assertEquals(mElement.getText(), Constants.continueButton);
        mElement.click();
        mElement = driver.findElementById(Elements.yesButton);
        Assert.assertEquals(mElement.getText(), Constants.yes);
        mElement.click();
    }

    static boolean isUserContactsUsesViber() {
        mElement = driver.findElementByClassName(Elements.inviteFriendsOnViber);
        return !mElement.getText().equals("Invite friends");
    }

    public static void validateHomePage() {
        if (!isUserContactsUsesViber()) {
            mElement = driver.findElementByClassName(Elements.homePageTitle);
            Assert.assertEquals(mElement.getText(), "Viber");
            mElement = driver.findElementById(Elements.homePageSearch);
            Assert.assertTrue(mElement.isEnabled());
            mElement = driver.findElementById(Elements.noMessage);
            Assert.assertEquals(mElement.getText(), "No messages yet");
            mElement = driver.findElementById(Elements.composeButton);
            Assert.assertEquals(mElement.getText(), "Send your first message");
            mElement = driver.findElementById(Elements.inviteButton);
            Assert.assertEquals(mElement.getText(), "Invite friends to Viber");
            mElement = driver.findElementById(Elements.fabContainer);
            Assert.assertTrue(mElement.isDisplayed());
        } else {
            //
        }
    }

    public static void sendMessageToContact(String contact) {
        mElement = driver.findElementById(Elements.composeButton);
        mElement.click();
        searchContact(contact);
    }

    public static void searchContact(String contact) {
        enterUserInput(driver.findElementById(Elements.searchBox), contact);
        mElement = driver.findElementById(Elements.contactName);
        Assert.assertEquals(mElement.getText(), contact);
        Assert.assertTrue(mElement.getText().equals(contact), "Unable to find contact " + contact);
        mElement.click();
    }

    public static void inviteContact(String contact){
        mElement = driver.findElementById(Elements.inviteSearchBox);
        Assert.assertEquals(mElement.getText(), Constants.search);
        enterUserInput(mElement, contact);
        mElement = driver.findElementById(Elements.contactName);
        Assert.assertEquals(mElement.getText(), Constants.friendName);
        mElement = driver.findElementByClassName("android.widget.TextView");
        Assert.assertEquals(mElement.getText(), "Invite to Viber");
        mElement.click();
        mElement = driver.findElementById("com.google.android.apps.messaging:id/conversation_title");
        Assert.assertEquals(mElement.getText(), Constants.friendName);
        mElement = driver.findElementById("Navigate up");
        Assert.assertEquals(mElement.isDisplayed(), "Free and secure calls and messages to any Viber user in the world.");
        mElement.click();
        mElement = driver.findElementByXPath("//android.widget.ImageButton[@content-desc=\"Navigate up\"](“Navigate up\")");
        Assert.assertEquals(mElement.isDisplayed(), "Free and secure calls and messages to any Viber user in the world.");
        mElement.click();
    }

    public static void verifyNewUserProfilePage() {
        mElement = driver.findElementById(Elements.newUserImage);
        Assert.assertTrue(mElement.isDisplayed());
        mElement = driver.findElementById(Elements.newUserName);
        Assert.assertEquals(mElement.getText(), "Full name");
        mElement = driver.findElementById(Elements.birthDate);
        Assert.assertEquals(mElement.getText(), "Date of Birth");
        mElement = driver.findElementById(Elements.sendUpdateCheckBox);
        //TODO: check checkbox
        mElement = driver.findElementById(Elements.handleData);
        Assert.assertEquals(mElement.getText(), "Learn how we handle this data");
        mElement = driver.findElementById(Elements.newUserProfileContinue);
        Assert.assertTrue(mElement.isDisplayed());
        mElement.click();
        selectAge();
    }

    public static void selectAge() {
        mElement = driver.findElementById(Elements.below16);
        Assert.assertEquals(mElement.getText(), "I am below 16");
        mElement = driver.findElementById(Elements.above16);
        Assert.assertEquals(mElement.getText(), "I am above 16");
        mElement.click();
    }

    public static void verifyDialogBox(String message) {
        mElement = driver.findElementById(Elements.dialogBoxTitle);
        Assert.assertEquals(mElement.getText(), message);
        mElement = driver.findElementById(Elements.denyButton);
        Assert.assertEquals(mElement.getText(), Constants.deny);
        mElement = driver.findElementById(Elements.allowButton);
        Assert.assertEquals(mElement.getText(), Constants.allow);
        mElement.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public static void enterUserInput(MobileElement mElement, String input) {
        mElement.click();
        mElement.clear();
        mElement.sendKeys(input);
    }

    public static String getPhoneNo(String number) {
        return Constants.phoneNo.substring(0, 5) + " " + Constants.phoneNo.substring(Constants.phoneNo.length() - 5);
    }
}
