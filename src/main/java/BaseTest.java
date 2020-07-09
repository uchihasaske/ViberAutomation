import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

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
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
        url = new URL(URL_STRING);//3
        cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "Pixel2");
        cap.setCapability("udid", "emulator-5554");
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "9");
        cap.setCapability("appPackage", "com.viber.voip");
        cap.setCapability("appActivity", "com.viber.voip.WelcomeActivity");   //4
        driver = new AndroidDriver<MobileElement>(url, cap);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.resetApp();
    }

    @AfterSuite
    public void uninstallApp() throws InterruptedException {
        //driver.removeApp("");
    }

    public static boolean isNewUser() {
        mElement = driver.findElementById(XPaths.welcomeMessageTitle);
        if (mElement.isDisplayed()) {
            Assert.assertEquals(mElement.getText(), Constants.welcomeToViber);
            return true;
        }
        return false;
    }

    public static void verifyWelcomeScreen() {
        mElement = driver.findElementById(XPaths.welcomeMessageTitle);
        Assert.assertEquals(mElement.getText(), Constants.welcomeToViber);
        mElement = driver.findElementById(XPaths.welcomeMessage);
        Assert.assertEquals(mElement.getText(), Constants.welcomeMessage);
        mElement = driver.findElementById(XPaths.termsAndPolicy);
        Assert.assertEquals(mElement.getText(), Constants.termsAndPolicyText);
        mElement = driver.findElementById(XPaths.transferHistoryFromAnotherMobile);
        Assert.assertEquals(mElement.getText(), Constants.transferHistory);
        mElement = driver.findElementById(XPaths.welcomePageContinue);
        Assert.assertEquals(mElement.getText(), Constants.continueButton);
    }

    public static void registerUser(String isdNo, String phoneNo) {
        mElement = driver.findElementById(XPaths.countryIsdCode);
        enterUserInput(mElement, isdNo);
        mElement = driver.findElementById(XPaths.countryName);
        Assert.assertEquals(mElement.getText(), "India");
        mElement = driver.findElementById(XPaths.phoneNo);
        enterUserInput(mElement, phoneNo);
        Assert.assertEquals(mElement.getText(), phoneNo.substring(0, 5) + " " + phoneNo.substring(phoneNo.length() - 5));
        mElement = driver.findElementById(XPaths.registerPageContinue);
        Assert.assertEquals(mElement.getText(), Constants.continueButton);
        mElement.click();
    }

    public static void validateConfirmRegistrationDialogBox(String phoneNo) {
        mElement = driver.findElementById(XPaths.confirmRegistrationTitle);
        Assert.assertEquals(mElement.getText(), Constants.confirmPhoneMessage);
        mElement = driver.findElementById(XPaths.confirmRegistrationPhoneNo);
//      Assert.assertEquals(mElement.getText(), phoneNo);
        mElement = driver.findElementById(XPaths.confirmRegistrationMessage);
        Assert.assertEquals(mElement.getText(), Constants.codeActivationMessage);
        mElement = driver.findElementById(XPaths.editButton);
        Assert.assertEquals(mElement.getText(), Constants.edit);
        mElement = driver.findElementById(XPaths.yesButton);
        Assert.assertEquals(mElement.getText(), Constants.yes);
    }

    public MobileElement verifyRegisterPage() {
        mElement = driver.findElementByXPath(XPaths.registerPageTitle);
        Assert.assertEquals(mElement.getText(), "Enter your phone number");
        mElement = driver.findElementById(XPaths.registerPageSubTitle);
        Assert.assertEquals(mElement.getText(), "Make sure you can receive SMS to this number so that we can send you a code.");
        mElement = driver.findElementById(XPaths.countryName);
        Assert.assertEquals(mElement.getText(), "United States of America");
        mElement = driver.findElementById(XPaths.countryIsdCode);
        Assert.assertEquals(mElement.getText(), "1");
        mElement = driver.findElementById(XPaths.phoneNo);
        Assert.assertEquals(mElement.getText(), "Phone number");
        mElement = driver.findElementById(XPaths.registerPageContinue);
        Assert.assertEquals(mElement.getText(), Constants.continueButton);
        return mElement;
    }

    public static void verifyConfirmRegistrationEditButton(String isdCode, String country, String phoneNo) {
        mElement = driver.findElementById(XPaths.editButton);
        Assert.assertEquals(mElement.getText(), Constants.edit);
        mElement.click();
        mElement = driver.findElementByXPath(XPaths.registerPageTitle);
        Assert.assertEquals(mElement.getText(), "Enter your phone number");
        mElement = driver.findElementById(XPaths.registerPageSubTitle);
        Assert.assertEquals(mElement.getText(), "Make sure you can receive SMS to this number so that we can send you a code.");
        mElement = driver.findElementById(XPaths.countryName);
        Assert.assertEquals(mElement.getText(), country);
        mElement = driver.findElementById(XPaths.countryIsdCode);
        Assert.assertEquals(mElement.getText(), isdCode);
        mElement = driver.findElementById(XPaths.phoneNo);
        Assert.assertEquals(mElement.getText(), phoneNo);
        mElement = driver.findElementById(XPaths.registerPageContinue);
        Assert.assertEquals(mElement.getText(), Constants.continueButton);
        mElement.click();
        mElement = driver.findElementById(XPaths.yesButton);
        Assert.assertEquals(mElement.getText(), Constants.yes);
        mElement.click();
    }

    private static boolean isUserContactsUsesViber() {
        mElement = driver.findElementByClassName(XPaths.inviteFriendsOnViber);
        return mElement.getText().equals("Invite friends");
    }

    public static void validateHomePage() {
        if (!isUserContactsUsesViber()) {
            mElement = driver.findElementByClassName(XPaths.homePageTitle);
            Assert.assertEquals(mElement.getText(), "Viber");
            mElement = driver.findElementById(XPaths.homePageSearch);
            Assert.assertTrue(mElement.isEnabled());
            mElement = driver.findElementById(XPaths.noMessage);
            Assert.assertEquals(mElement.getText(), "No messages yet");
            mElement = driver.findElementById(XPaths.composeButton);
            Assert.assertEquals(mElement.getText(), "Send your first message");
            mElement = driver.findElementById(XPaths.inviteButton);
            Assert.assertEquals(mElement.getText(), "Invite friends to Viber");
            mElement = driver.findElementById(XPaths.fabContainer);
            Assert.assertTrue(mElement.isDisplayed());
        }
    }

    public static void sendMessageToContact(String contact) {
        mElement = driver.findElementById(XPaths.composeButton);
        mElement.click();
        searchContact(contact);
    }

    public static void searchContact(String contact) {
        enterUserInput(driver.findElementById(XPaths.searchBox), contact);
        mElement = driver.findElementById(XPaths.contactName);
        Assert.assertEquals(mElement.getText(), contact);
        Assert.assertTrue(mElement.getText().equals(contact), "Unable to find contact " + contact);
        mElement.click();
    }

    public static void verifyNewUserProfilePage() {
        mElement = driver.findElementById(XPaths.newUserImage);
        Assert.assertTrue(mElement.isDisplayed());
        mElement = driver.findElementById(XPaths.newUserName);
        Assert.assertEquals(mElement.getText(), "Full name");
        mElement = driver.findElementById(XPaths.birthDate);
        Assert.assertEquals(mElement.getText(), "Date of Birth");
        mElement = driver.findElementById(XPaths.sendUpdateCheckBox);
        //TODO: check checkbox
        mElement = driver.findElementById(XPaths.handleData);
        Assert.assertEquals(mElement.getText(), "Learn how we handle this data");
        mElement = driver.findElementById(XPaths.newUserProfileContinue);
        Assert.assertTrue(mElement.isDisplayed());
        mElement.click();
        selectAge();
    }

    public static void selectAge() {
        mElement = driver.findElementById(XPaths.below16);
        Assert.assertEquals(mElement.getText(), "I am below 16");
        mElement = driver.findElementById(XPaths.above16);
        Assert.assertEquals(mElement.getText(), "I am above 16");
        mElement.click();
    }

    public static void verifyDialogBox(String message) {
        mElement = driver.findElementById(XPaths.dialogBoxTitle);
        Assert.assertEquals(mElement.getText(), message);
        mElement = driver.findElementById(XPaths.denyButton);
        Assert.assertEquals(mElement.getText(), Constants.deny);
        mElement = driver.findElementById(XPaths.allowButton);
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
