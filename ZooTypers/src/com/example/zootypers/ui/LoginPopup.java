package com.example.zootypers.ui;

import com.example.zootypers.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class LoginPopup {
  
  @SuppressWarnings("unused")
  private ParseUser currentUser;

  // The popups themselves
  private PopupWindow login_ppw;
  private PopupWindow password_ppw;

  @SuppressLint("InlinedApi")
  public LoginPopup(ParseUser u) {
    currentUser = u;
  }

  @SuppressLint("InlinedApi")
  /**
   * Builds and displays the login popup.
   * @param layoutInflater The LayoutInflater to use.
   * @param parentLayout The parent layout to display the popup in.
   * @param dispsw If the password popup is currently being displayed
   * (and therefore should be dismissed).
   */
  public void buildLoginPopup(LayoutInflater layoutInflater, ViewGroup parentLayout, boolean dispsw) {
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

  @SuppressLint("InlinedApi")
  /**
   * Builds and displays the reset password popup.
   * @param layoutInflater The LayoutInflater to use.
   * @param parentLayout The parent layout to display the popup in.
   */
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
   * Handles what happens when user clicks the login button
   * @return the username to pass to the multiplayer activity.
   * If this is "", the activity should NOT continue to the multiplayer screen.
   */
  public String loginButton() {
    // Get the username and password inputs
    final View contentView = login_ppw.getContentView();
    EditText usernameInput = (EditText) contentView.findViewById(R.id.username_login_input);
    EditText passwordInput = (EditText) contentView.findViewById(R.id.password_login_input);
    String usernameString = usernameInput.getText().toString();
    final String passwordString = passwordInput.getText().toString();
    
    // Where to display an error message
    final TextView errorMessage = (TextView) contentView.findViewById(R.id.login_error_message);
        
    // Try to login with the given inputs
    ParseUser user;
    try {
      user = ParseUser.logIn(usernameString, passwordString);
    } catch (ParseException e) {
      // TODO figure out if username and/or password is wrong
      errorMessage.setText("Could not login");
      return "";
    }

    // Check for verified email
    boolean emailVerified = user.getBoolean("emailVerified");
    if (!emailVerified) {
      errorMessage.setText("Email is not verified");
      ParseUser.logOut();
      currentUser = ParseUser.getCurrentUser();
      usernameString = "";
    } else {
      currentUser = user;
    }
    
    return usernameString;
  }

  /**
   * Exits the login popup window
   */
  public void exitLoginPopup() {
    login_ppw.dismiss();
  }

  /**
   * Logs out the current user
   * @param alertDialogBuilder The AlertDialog.Builder
   */
  public void logoutUser(AlertDialog.Builder alertDialogBuilder) {
    ParseUser.logOut();
    currentUser = ParseUser.getCurrentUser();
    final String title = "Logged Out";
    final String message = "You have successfully logged out";
    buildAlertDialog(alertDialogBuilder, title, message);
    // make the views disappear
  }

  /**
   * Handles what happens when user wants to their reset password.
   * @param alertDialogBuilder The AlertDialog.Builder
   */
  public void resetPassword(final AlertDialog.Builder alertDialogBuilder) {
    // get the contents of the popup window and get the email the user typed in
    final View contentView = password_ppw.getContentView();
    EditText emailReset = (EditText) contentView.findViewById(R.id.email_forgot_password_input);
    final String emailString = emailReset.getText().toString();

    final TextView errorMessage = (TextView) contentView.findViewById(R.id.login_error_message);
    // try to reset the password by sending an email
    ParseUser.requestPasswordResetInBackground(emailString, new RequestPasswordResetCallback() {
      public void done(ParseException e) {
        if (e == null) {
          // success
          final String title = "Password Reset";
          final String message = "An email has been sent to " + emailString;
          buildAlertDialog(alertDialogBuilder, title, message);
        } else {
          // failure
          int errorCode = e.getCode();
          if (errorCode == ParseException.INVALID_EMAIL_ADDRESS) {
            errorMessage.setText("Invalid Email Address");
          } else {
            errorMessage.setText("Password Reset Failed");
          }
        }
      }
    });
  }

  /**
   * Builds an AlertDialog popup with the given title and message
   * @param alertDialogBuilder The AlertDialog.Builder
   * @param title The title of the popup.
   * @param message The message in the popup.
   */
  private void buildAlertDialog(AlertDialog.Builder alertDialogBuilder, String title, String message) {
   // set title
    alertDialogBuilder.setTitle(title);

    // set dialog message
    alertDialogBuilder
    .setMessage(message)
    .setCancelable(false)
    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        // if this button is clicked, close the dialog box
        dialog.cancel();
      }
    });

    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();

    // show the message
    alertDialog.show();
  }

}
