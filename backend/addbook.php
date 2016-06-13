<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$query = "INSERT INTO books (username, book_title, author, isbn ) VALUES ( :user, :book_title, :author, :isbn) ";

    //Update query
    $query_params = array(
        ':user' => $_POST['username'],
        ':book_title' => $_POST['book_title'],
		':author' => $_POST['author'],
		':isbn' => $_POST['isbn']
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
        $response["message"] = "Database Error. Couldn't add book!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Book Successfully Added!";
    echo json_encode($response);
   
} else {
?>
		<h1>Add Book</h1> 
		<form action="addbook.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    Title:<br /> 
		    <input type="text" name="book_title" placeholder="post title" /> 
		    <br /><br />
			Author:<br /> 
		    <input type="text" name="author" placeholder="post author" /> 
		    <br /><br />
			Isbn:<br /> 
		    <input type="text" name="isbn" placeholder="post isbn" /> 
		    <br /><br />
		    <input type="submit" value="Add Book" /> 
		</form> 
	<?php
}

?> 
