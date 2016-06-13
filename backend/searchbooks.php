<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$keyword = $_POST['bookname'];
	$query = "SELECT * FROM books WHERE book_title LIKE '%$keyword%' ";
	$query_params = array($keyword);
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
        $response["message"] = "Database Error. Couldn't search book!";
        die(json_encode($response));
    }
	$rows = $stmt->fetchAll();

    if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Books Found!";
    $response["posts"]   = array();
    
 
	foreach ($rows as $row) {
        $post  = array();
	
        //this line is new:
        $post["book_title"] = $row["book_title"];
        $post["author"]    = $row["author"];
        $post["isbn"]  = $row["isbn"];
		$post["username"]  = $row["username"];
        
        
        //update our repsonse JSON data
        array_push($response["posts"], $post);
    }

    
    // echoing JSON response
    echo json_encode($response);
    
    
} 	else {
    $response["success"] = 0;
    $response["message"] = "No Books Found!";
    die(json_encode($response));
}
   
} else {
?>

<h1>Search </h1> 
		<form action="searchbooks.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="bookname" placeholder="bookname" /> 
		    <br /><br /> 
		    <input type="submit" value="Seach Book" /> 
		</form> 
		<?php
}

?> 