import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;


public class ViberTest extends BaseTest {

    @Test(enabled = true)
    public void myFirstTest() throws InterruptedException {
        //driver.resetApp();
        if (isNewUser()) {
            verifyWelcomeScreen();
            driver.findElementById(XPaths.launchScreeenOk).click();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            verifyRegisterPage().click();
            registerUser(Constants.countryCode, Constants.phoneNo);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            validateConfirmRegistrationDialogBox(Constants.countryCode + " " + Constants.phoneNo);
            verifyConfirmRegistrationEditButton(Constants.countryCode, Constants.country, getPhoneNo(Constants.phoneNo));
            verifyDialogBox(Constants.accessContactMessage);
            verifyDialogBox(Constants.accessPhoneLogs);
            verifyNewUserProfilePage();
            verifyDialogBox(Constants.accessPhotos);
            verifyDialogBox(Constants.takePicture);
            verifyDialogBox(Constants.recordAudio);
            verifyDialogBox(Constants.makeManagePhone);
            verifyDialogBox(Constants.accessDeviceLocation);
            validateHomePage();
            sendMessageToContact(Constants.friendName);
        } else {
            //Implement logic to send message to already registered user
        }
    }


}