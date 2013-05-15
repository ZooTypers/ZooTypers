package com.example.zootypers.test;

import com.example.zootypers.*;
import com.example.zootypers.R;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Testing to see if the login feature for our game works.
 * 
 * Black box test. 
 * 
 * @author dyxliang
 *
 */

public class LoginTest extends ActivityInstrumentationTestCase2<TitlePage> {
	
	private Solo solo;
	
	public LoginTest() {
		super(TitlePage.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testingRegisteringForAccount() {
		solo.clickOnButton("Multiplayer");
		solo.clickOnButton("Register");
		EditText username = (EditText) solo.getView(R.id.username_register_input);
		solo.enterText(username, "David");
		EditText password = (EditText) solo.getView(R.id.password_input_register);
		solo.enterText(password, "12345");
		EditText password2 = (EditText) solo.getView(R.id.confirm_password_input_register);
		solo.enterText(password2, "12345");
		EditText email = (EditText) solo.getView(R.id.email_input_register);
		solo.enterText(email, "davidrandomemail123@hotmail.com");
		solo.clickOnButton("Submit");
		solo.sleep(3000);
	}
	
	public void testingLoginToExistingUser() {
		solo.clickOnButton("Multiplayer");
		EditText username = (EditText) solo.getView(R.id.username_login_input);
		solo.enterText(username, "David");
		solo.sleep(1000);
		EditText password = (EditText) solo.getView(R.id.password_login_input);
		solo.enterText(password, "1234567");
		solo.sleep(1000);
		solo.clickOnButton("Login");
		solo.goBack();
	}
	
	public void testingForgotPasswordInput() {
		solo.clickOnButton("Multiplayer");
		EditText email = (EditText) solo.getView(R.id.email_forgot_password_input);
		solo.enterText(email, "davidrandomemail123@hotmail.com");
		solo.clickOnButton("Reset Password");
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
