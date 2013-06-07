package com.example.zootypers.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

import com.example.zootypers.R;

public class InterfaceUtils {
	
	/**
	 * builds an AlertDialog popup with the given title and message
	 * @param activity the activity this popup should be made in
	 * @param title String representing title of the AlertDialog popup
	 * @param message String representing the message of the AlertDialog
	 * popup
	 */
	public static void buildAlertDialog(Activity activity, final int title, final int message) {
		Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		buildAlertDialog(alertDialogBuilder, title, message);
	}
	
	/**
	 * builds an AlertDialog popup with the given title and message
	 * @param Builder the builder that is building the popup message
	 * @param title String representing title of the AlertDialog popup
	 * @param message String representing the message of the AlertDialog
	 * popup
	 */
	public static void buildAlertDialog(Builder alertDialogBuilder, final int title, final int message) {
		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(R.string.close_alert, new DialogInterface.OnClickListener() {
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
