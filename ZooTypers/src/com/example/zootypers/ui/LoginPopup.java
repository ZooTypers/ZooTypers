package com.example.zootypers.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.util.InterfaceUtils;
import com.example.zootypers.util.InternetConnectionException;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Utility for login / register / resert password.
 * @author cdallas, littlpunk
 */
public class LoginPopup {

	@SuppressWarnings("unused")
	private ParseUser currentUser;

	// The popups themselves
	private PopupWindow login_ppw;
	private PopupWindow password_ppw;

	@SuppressLint("InlinedApi")
	public LoginPopup(final ParseUser u) {
		currentUser = u;
	}

	/**
	 * Builds and displays the login popup.
	 * @param layoutInflater The LayoutInflater to use.
	 * @param parentLayout The parent layout to display the popup in.
	 * @param dispsw If the password popup is currently being displayed
	 * (and therefore should be dismissed).
	 */
	@SuppressLint("InlinedApi")
	public final void buildLoginPopup(LayoutInflater layoutInflater, ViewGroup parentLayout,
	final boolean dispsw) {
		// If need be, dismiss the password popup
		if (dispsw) {
			password_ppw.dismiss();
		}

		// Build the login poup
		View popupView = layoutInflater.inflate(R.layout.login_popup, null);
		login_ppw = new PopupWindow(popupView,
		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		login_ppw.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
	}

	/**
	 * Builds and displays the reset password popup.
	 * @param layoutInflater The LayoutInflater to use.
	 * @param parentLayout The parent layout to display the popup in.
	 */
	@SuppressLint("InlinedApi")
	public void buildResetPopup(LayoutInflater layoutInflater, ViewGroup parentLayout) {
		// Build the reset password popup
		View popupView = layoutInflater.inflate(R.layout.reset_pw_layout, null);
		password_ppw = new PopupWindow(popupView,
		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		password_ppw.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
		// dismiss the login popup
		login_ppw.dismiss();
	}

	/**
	 * Handles what happens when user clicks the login button.
	 * @return the username to pass to the multiplayer activity.
	 * If this is "", the activity should NOT continue to the multiplayer screen.
	 * @throws InternetConnectionException 
	 */
	public final String loginButton() throws InternetConnectionException {
		// Get the username and password inputs
		final View contentView = login_ppw.getContentView();
		EditText usernameInput = (EditText) contentView.findViewById(R.id.username_login_input);
		EditText passwordInput = (EditText) contentView.findViewById(R.id.password_login_input);
		String usernameString = usernameInput.getText().toString().trim();
		final String passwordString = passwordInput.getText().toString();

		// Where to display an error message
		final TextView errorMessage = (TextView) contentView.findViewById(R.id.login_error_message);

		// Try to login with the given inputs
		ParseUser user;
		try {
			user = ParseUser.logIn(usernameString, passwordString);
		} catch (ParseException e) {
			e.fillInStackTrace();
			boolean errorOccured = false;
			List<ParseObject> usernameResults = new ArrayList<ParseObject>();
			List<ParseObject> passwordResults = new ArrayList<ParseObject>();
			ParseQuery query = ParseUser.getQuery();
			// try to find the username that the user typed in
			query.whereEqualTo("username", usernameString);
			try {
				query.count();
				usernameResults = query.find();
			} catch (ParseException e1) {
				// error occured trying to find the username
				errorOccured = true;
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				errorOccured = true;
				e1.printStackTrace();
			}

			// try to find the password that the user typed in
			// associated with that username
			query.whereEqualTo("username", usernameString);
			query.whereEqualTo("password", passwordString);
			try {
				query.count();
				passwordResults = query.find();
			} catch (ParseException e1) {
				// error occured trying to find the password
				errorOccured = true;
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				errorOccured = true;
				e1.printStackTrace();
			}

			// figure out the error
			if (errorOccured) {
				errorMessage.setText(R.string.error_login_unexp);
				return "";
			}
			if ((usernameResults.size() == 0) && (passwordResults.size() == 0)) {
				errorMessage.setText(R.string.error_login_combo);
			} else if ((usernameResults.size() == 0) && (passwordResults.size() != 0)) {
				errorMessage.setText(R.string.error_login_uname);
			} else if ((usernameResults.size() != 0) && (passwordResults.size() == 0)) {
				errorMessage.setText(R.string.error_login_pswd);
			} else {
				// unexpected error occured

			}
			// signals an error occured
			return "";
		}

		// Check for verified email
		boolean emailVerified = user.getBoolean("emailVerified");
		if (!emailVerified) {
			errorMessage.setText(R.string.error_login_verif);
			ParseUser.logOut();
			currentUser = ParseUser.getCurrentUser();
			usernameString = "";
		} else {
			currentUser = user;
		}

		return usernameString;
	}

	/**
	 * Exits the login popup window.
	 */
	public final void exitLoginPopup() {
		login_ppw.dismiss();
	}

	/**
	 * Logs out the current user.
	 * @param alertDialogBuilder The AlertDialog.Builder
	 */
	public final void logoutUser(final AlertDialog.Builder alertDialogBuilder) {
		ParseUser.logOut();
		currentUser = ParseUser.getCurrentUser();
		InterfaceUtils.buildAlertDialog(alertDialogBuilder, 
		R.string.logged_out_title, R.string.logged_out_msg);
		// make the views disappear
	}

	/**
	 * Handles what happens when user wants to their reset password.
	 * @param alertDialogBuilder The AlertDialog.Builder
	 */
	public final boolean resetPassword(final AlertDialog.Builder alertDialogBuilder) {
		// get the contents of the popup window and get the email the user typed in
		final View contentView = password_ppw.getContentView();
		EditText emailReset = (EditText) contentView.findViewById(R.id.email_forgot_password_input);
		final String emailString = emailReset.getText().toString();

		final TextView errorMessage = (TextView) contentView.findViewById(R.id.reset_error_message);
		// try to reset the password by sending an email
		try {
			ParseUser.requestPasswordReset(emailString);
			// success
			InterfaceUtils.buildAlertDialog(alertDialogBuilder, 
			R.string.pswd_reset_title, R.string.pswd_reset_msg);
			return true;
		} catch (ParseException e) {
			// failure
			int errorCode = e.getCode();
			if (errorCode == ParseException.INVALID_EMAIL_ADDRESS) {
				errorMessage.setText(R.string.error_reset_email);
			} else {
				errorMessage.setText(R.string.error_reset_fail);
			}
			return false;
		}
	}

}
