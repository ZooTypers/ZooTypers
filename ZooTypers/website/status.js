document.observe("dom:loaded", function() {
	Parse.initialize("Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM", "ZyzXZfYI0pf8usgCpVWsGq0uSaiKC2cMco9sEJiE");
	checkRegistration();
	checkMatches();
	checkWordsList();
	checkLeaderboard();
});

function checkRegistration() {	
	var Users = Parse.Object.extend("User");
	var query = new Parse.Query(Users);
	query.get("qjJ2fOmv6l", {
		success: function(gameScore) {
			$("registration").innerHTML = "<td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold;\"><p>Registration</p></td><td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold; color: green\"><p>Live</p></td><td style=\"width: 166px\"><img src=\"images/checkmark.png\" alt=\"homepage-logo\" style=\"width: 70px; height: 66px; padding-left: 50px;\"></td>";
		},
		error: function(object, error) {
			$("registration").innerHTML = "<td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold;\"><p>Registration</p></td><td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold; color: red\"><p>Down</p></td><td style=\"width: 166px\"><img src=\"images/cross.png\" alt=\"homepage-logo\" style=\"width: 70px; height: 66px; padding-left: 50px;\"></td>";
		}
	});	
}

function checkMatches() {	
	var Matches = Parse.Object.extend("Matches");
	var query = new Parse.Query(Matches);
	query.get("Dk80JlRnkQ", {
		success: function(gameScore) {
			$("matches").innerHTML = "<td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold;\"><p>Matches</p></td><td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold; color: green\"><p>Live</p></td><td style=\"width: 166px\"><img src=\"images/checkmark.png\" alt=\"homepage-logo\" style=\"width: 70px; height: 66px; padding-left: 50px;\"></td>";
		},
		error: function(object, error) {
			$("matches").innerHTML = "<td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold;\"><p>Matches</p></td><td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold; color: red\"><p>Down</p></td><td style=\"width: 166px\"><img src=\"images/cross.png\" alt=\"homepage-logo\" style=\"width: 70px; height: 66px; padding-left: 50px;\"></td>";
		}
	});	
}

function checkLeaderboard() {	
	var MultiLeaderBoard = Parse.Object.extend("MultiLeaderBoard");
	var query = new Parse.Query(MultiLeaderBoard);
	query.get("isb7FnQaRp", {
		success: function(gameScore) {
			$("leaderboard").innerHTML = "<td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold;\"><p>Leaderboard</p></td><td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold; color: green\"><p>Live</p></td><td style=\"width: 166px\"><img src=\"images/checkmark.png\" alt=\"homepage-logo\" style=\"width: 70px; height: 66px; padding-left: 50px;\"></td>";
		},
		error: function(object, error) {
			$("leaderboard").innerHTML = "<td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold;\"><p>Leaderboard</p></td><td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold; color: red\"><p>Down</p></td><td style=\"width: 166px\"><img src=\"images/cross.png\" alt=\"homepage-logo\" style=\"width: 70px; height: 66px; padding-left: 50px;\"></td>";
		}
	});	
}

function checkWordsList() {	
	var WordList = Parse.Object.extend("WordList");
	var query = new Parse.Query(WordList);
	query.get("qn2PxnttVX", {
		success: function(gameScore) {
			$("wordslist").innerHTML = "<td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold;\"><p>Words List</p></td><td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold; color: green\"><p>Live</p></td><td style=\"width: 166px\"><img src=\"images/checkmark.png\" alt=\"homepage-logo\" style=\"width: 70px; height: 66px; padding-left: 50px;\"></td>";
		},
		error: function(object, error) {
			$("wordslist").innerHTML = "<td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold;\"><p>Words List</p></td><td style=\"width: 166px; text-align: center; font-size: 18pt; font-weight: bold; color: red\"><p>Down</p></td><td style=\"width: 166px\"><img src=\"images/cross.png\" alt=\"homepage-logo\" style=\"width: 70px; height: 66px; padding-left: 50px;\"></td>";

		}
	});
}