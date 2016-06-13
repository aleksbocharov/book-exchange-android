<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$id = $_POST['book_id'];
	$query = "DELETE FROM books WHERE book_id='$id'";
	$query_params = array($id);
    //Update query

  
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
        $response["message"] = "Database Error. Couldn't delete book!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Book Successfully Deleted!";
    echo json_encode($response);
   
} else {
?>
		<h1>Delete Book</h1> 
		<form action="deletebook.php" method="post"> 
		    Book ID:<br /> 
			<input type="text" name="book_id" placeholder="book_id" /> 
		    <br /><br /> 
		    <input type="submit" value="Delete Book" /> 
		</form> 
	<?php
}

?> 
