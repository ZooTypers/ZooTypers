package com.example.zootypers.test;

import org.junit.*;
import com.example.zootypers.*;
import com.example.zootypers.R;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

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
	
	public LoginTest() {
		super(TitlePage.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Test(timeout = TIMEOUT)
	public void testingRegisteringForAccountInUse() {
		solo.clickOnButton("Multiplayer");
		solo.clickOnButton("Register");
		EditText username = (EditText) solo.getView(R.id.username_register_input);
		solo.enterText(username, "David");
		EditText password = (EditText) solo.getView(R.id.password_input_register);
		solo.enterText(password, "12345");
		EditText password2 = (EditText) solo.getView(R.id.confirm_password_input_register);
		solo.enterText(password2, "12345");
		EditText email = (EditText) solo.getView(R.id.email_input_register);
		solo.enterText(email, "davidqwe123@hotmail.com");
		solo.clickOnButton("Submit");
		solo.searchText("Username is already in use.");
	}
	
	@Test(timeout = TIMEOUT)
	public void testingLoginToExistingUserAndLoggingOut() {
		solo.clickOnButton("Multiplayer");
		EditText username = (EditText) solo.getView(R.id.username_login_input);
		solo.enterText(username, "David");
		EditText password = (EditText) solo.getView(R.id.password_login_input);
		solo.enterText(password, "1234567");
		solo.clickOnButton("Login");
		solo.goBack();
		solo.clickOnButton("Logout");
		solo.searchText("Logged Out");
	}
	
	@Test(timeout = TIMEOUT)
	public void testingForgotPasswordInputFailure() {
		solo.clickOnButton("Multiplayer");
		EditText email = (EditText) solo.getView(R.id.email_forgot_password_input);
		solo.enterText(email, "davidqwe123@hotmail.com");
		solo.clickOnButton("Reset");
		solo.searchText("Password Reset Failed");
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
