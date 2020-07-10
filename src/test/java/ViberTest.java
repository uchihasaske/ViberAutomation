import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;


public class ViberTest extends BaseTest {

    @Test(enabled = true)
    public void newUserOnbordTest() throws InterruptedException {
        //driver.resetApp();
        if (isNewUser()) {
            verifyWelcomeScreen();
            driver.findElementById(Elements.launchScreeenOk).click();
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
        }
    }

    @Test(enabled = true)
    public void sendMessageToContact() throws InterruptedException {
        if (isUserContactsUsesViber()) {
            sendMessageToContact(Constants.friendName);
        }
    }

    @Test(enabled = true)
    public void inviteContactsToViber() throws InterruptedException {
        if (!isUserContactsUsesViber()) {
            inviteContact(Constants.friendName);
        }
    }
}
