package com.example.zootypers.ui;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zootypers.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@SuppressLint("NewApi")
public class MultiplayerLobby extends Activity {

	ParseUser currentUser;
	PopupWindow addFriendPopup;
	PopupWindow removeFriendPopup;
	ListPopupWindow listpw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiplayer_lobby);
		Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM",
				"SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C");
		currentUser = ParseUser.getCurrentUser();
		
		TextView usernameText = (TextView) findViewById(R.id.current_user_text);
		String username = currentUser.get("username").toString();
		usernameText.setText(username);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_multiplayer_lobby, menu);
		return true;
	}

	@SuppressLint("NewApi")
	public void viewFriendButton(View view) {
		System.out.println("IT GOT HERE");
		LayoutInflater layoutInflater =
				(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		ViewGroup anchorLayout = (ViewGroup) findViewById(R.id.multiplayer_lobby_layout);

		List<String> currentFL = currentUser.getList("friendsList");
		if (currentFL == null || currentFL.size() == 0) {
			final String title = "No friends :(";
			final String message = "Sorry you have no friends";
			buildAlertDialog(title, message);
		} else {
			listpw = new ListPopupWindow(this);
			listpw.setAnchorView(anchorLayout);
			listpw.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, currentFL.toArray()));
			listpw.setHeight(500);
			listpw.show();
		}
	}
	
	public void addFriendButton(View view) {
		buildPopup(true);
	}

	public void addFriend(View view) {
		final View contentView = addFriendPopup.getContentView();
		EditText friendInput = (EditText) contentView.findViewById(R.id.friend_input);
		String friendUsername = friendInput.getText().toString();

		if (friendUsername.equals(currentUser.get("username"))) {
			// user tried to add him/herself
			final String title = "Invalid friend";
			final String message = "You cannot add yourself as a friend, idiot";
			buildAlertDialog(title, message);
			return;
		}
		
		List<String> friendsList = currentUser.getList("friendsList");
		if (friendsList == null) {
			friendsList = new ArrayList<String>();
		} else {
			if (searchFriendsList(friendsList, friendUsername) != -1) {
				// user already has this friend
				final String title = "Already added this friend";
				final String message = friendUsername + " is already your friend";
				buildAlertDialog(title, message);
				return;
			}
		}
		// search for the person the user wants to add as a friend
		List<ParseObject> userResults = new ArrayList<ParseObject>();
		ParseQuery otherUserQuery = ParseUser.getQuery();
		otherUserQuery.whereEqualTo("username", friendUsername);
		try {
			userResults = otherUserQuery.find();
		} catch (ParseException e1) {
			// fail
			e1.printStackTrace();
		}
				
		if (userResults.size() != 0) {
			// means that the user was found, so add the name to the list
			friendsList.add(friendUsername);
			currentUser.put("friendsList", friendsList);
		} else {
			// user was not found, inform the user
			final String title = "User not found";
			final String message = "Sorry, we could not find the user that you requested";
			buildAlertDialog(title, message);
		}
		// save the update
		try {	
			currentUser.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String title = "Added a friend successfully!";
		final String message = "You have successfully added " + friendUsername +
				" as a friend!";
		buildAlertDialog(title, message);
		addFriendPopup.dismiss();
	}
	
	public void removeFriendButton(View view) {
		buildPopup(false);
	}
	
	public void removeFriend(View view) {
		final View contentView = removeFriendPopup.getContentView();
		EditText friendInput = (EditText) contentView.findViewById(R.id.friend_input);
		String friendUsername = friendInput.getText().toString();
		
		if (friendUsername.equals(currentUser.get("username"))) {
			// user tried to remove him/herself
			final String title = "Invalid friend";
			final String message = "You cannot remove yourself as a friend, idiot";
			buildAlertDialog(title, message);
			return;
		}
		
		List<String> friendsList = currentUser.getList("friendsList");
		if (friendsList == null) {
			friendsList = new ArrayList<String>();
		} else {
			int index = searchFriendsList(friendsList, friendUsername);
			if (index != -1) {
				// found the friend so remove the current friend
				friendsList.remove(index);
				// user already has this friend
				final String title = "Friend removed";
				final String message = friendUsername + " was successfully removed from your" +
						"friends list";
				buildAlertDialog(title, message);
				return;
			} else {
				// the friend isnt in the friend list
				final String title = "Invalid Friend";
				final String message = "You cannot remove a friend that you do not have";
				buildAlertDialog(title, message);
			}
		}
	}
	public void exitPopup(View view) {
		addFriendPopup.dismiss();
	}
	/**
	 * Builds and displays the add friend popup.
	 * @param layoutInflater The LayoutInflater to use.
	 * @param parentLayout The parent layout to display the popup in.
	 * @param dispsw If the password popup is currently being displayed
	 * (and therefore should be dismissed).
	 */
	@SuppressLint("InlinedApi")
	public final void buildPopup(boolean isAddFriend) {
		LayoutInflater layoutInflater =
				(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.multiplayer_lobby_layout);
		// Build the login poup
		View popupView;
		if (isAddFriend) {
			popupView = layoutInflater.inflate(R.layout.add_friend_layout, null);
			addFriendPopup = new PopupWindow(popupView,
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
			addFriendPopup.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
		} else {
			popupView = layoutInflater.inflate(R.layout.remove_friend_layout, null);
			removeFriendPopup = new PopupWindow(popupView,
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
			removeFriendPopup.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
		}
		
	}
	
	/**
	 * Builds an AlertDialog popup with the given title and message.
	 * @param alertDialogBuilder The AlertDialog.Builder
	 * @param title The title of the popup.
	 * @param message The message in the popup.
	 */
	private void buildAlertDialog(final String title, final String message) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				// if this button is clicked, close the dialog box
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show the message
		alertDialog.show();
	}

	private int searchFriendsList(List<String> friendsList, String otherFriend) {
		// search if the user is already friends with the given user
		for (int i = 0; i < friendsList.size(); i++) {
			if (friendsList.get(i).equals(otherFriend)) {
				return i;
			}
		}
		return -1;
	}
}
