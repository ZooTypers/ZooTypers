package com.example.zootypers.test;

import org.junit.Test;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.zootypers.R;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

/**
 * Testing to see if the login feature works correctly by logging into
 * an existing user, registering a new user, and also entering a password
 * for recovering information.
 * 
 * (Black box testing to make sure that the login UI works.)
 * 
 * @author dyxliang
 *
 */

public class LoginTest extends ActivityInstrumentationTestCase2<TitlePage> {
	
	private Solo solo;
	private static final int TIMEOUT = 10000;
	
	@SuppressLint("NewApi")
    public LoginTest() {
		super(TitlePage.class);
	}

    /**
     * create a new solo class to use robotium
     * @throws Exception if activity isn't instantiated
     */
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

    /**
     * Goes to Multiplayer activity, click Register and putting in record that is already used
     */
	@Test(timeout = TIMEOUT)
	public void testingRegisteringForAccountInUse() {
		solo.clickOnButton("Multiplayer");
		solo.clickOnText("Join now!");
		EditText username = (EditText) solo.getView(R.id.username_register_input);
		solo.enterText(username, "David");
		EditText password = (EditText) solo.getView(R.id.password_input_register);
		solo.enterText(password, "12345");
		EditText password2 = (EditText) solo.getView(R.id.confirm_password_input_register);
		solo.enterText(password2, "12345");
		EditText email = (EditText) solo.getView(R.id.email_input_register);
		solo.enterText(email, "davidqwe123@hotmail.com");
		solo.clickOnButton("Submit");
		solo.sleep(5000);
		solo.searchText("Username is already in use.");
	    solo.finishOpenedActivities();
	}

    /**
     * Goes to Multiplayer activity and login with an active account then logout.
     */
	@Test(timeout = TIMEOUT)
	public void testingLoginToExistingUserAndLoggingOut() {
		solo.clickOnButton("Multiplayer");
		EditText username = (EditText) solo.getView(R.id.username_login_input);
		solo.enterText(username, "David");
		EditText password = (EditText) solo.getView(R.id.password_login_input);
		solo.enterText(password, "1234567");
		solo.clickOnButton("Login");
		solo.sleep(5000);
		solo.clickOnButton("Main Menu");
		solo.clickOnButton("Logout");
		solo.searchText("Logged Out");
	    solo.finishOpenedActivities();
	}

//    /**
//     * Goes to Multiplayer activity and request an invalid email for a password reset.
//     */
//	@Test(timeout = TIMEOUT)
//	public void testingForgotPasswordInputFailure() {
//	    solo.clickOnButton("Multiplayer");
//		solo.clickOnText("Forgot your password?");
//		EditText email = (EditText) solo.getView(R.id.email_forgot_password_input);
//		solo.enterText(email, "davidqwe123@hotmail.com");
//		solo.clickOnButton("Reset");
//	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
