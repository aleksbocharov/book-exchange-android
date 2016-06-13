<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$query = "INSERT INTO magazines (username, mag_title, mag_edition) VALUES ( :user, :mag_title, :mag_edition) ";

    //Update query
    $query_params = array(
        ':user' => $_POST['username'],
        ':mag_title' => $_POST['mag_title'],
		':mag_edition' => $_POST['mag_edition']
    );
  
	//execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error. Couldn't add magazine!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Magazine Successfully Added!";
    echo json_encode($response);
   
} else {
?>
		<h1>Add Magazine</h1> 
		<form action="addmagazine.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    Title:<br /> 
		    <input type="text" name="mag_title" placeholder="post title" /> 
		    <br /><br />
			Edition:<br /> 
		    <input type="text" name="mag_edition" placeholder="post edition" /> 
		    <br /><br />
		    <input type="submit" value="Add Book" /> 
		</form> 
	<?php
}

?> 
