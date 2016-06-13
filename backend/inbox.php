<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");
if (!empty($_POST)) {
//initial query
$receiver = $_POST['receiver'];
$query = "Select * FROM comments WHERE receiver = '$receiver'";

$query_params = array($username);
//execute query
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute();
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}

// Finally, we can retrieve all of the found rows into an array using fetchAll 
$rows = $stmt->fetchAll();


if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Post Available!";
    $response["posts"]   = array();
    
 
 foreach ($rows as $row) {
        $post             = array();
	
        //this line is new:
        $post["post_id"]  = $row["post_id"];

        $post["username"] = $row["username"];
        $post["title"]    = $row["title"];
        $post["message"]  = $row["message"];
        
        
        //update our repsonse JSON data
        array_push($response["posts"], $post);
    }

    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}
}
else {
?>

<h1>Search </h1> 
		<form action="inbox.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="receiver" placeholder="receiver" /> 
		    <br /><br /> 
		    <input type="submit" value="See Messages" /> 
		</form> 
		<?php
}

?> 