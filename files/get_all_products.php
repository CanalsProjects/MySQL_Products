
<?php

/* get_all_products.php */
 
header('Content-Type: text/html; charset=utf-8');
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

$query = "SELECT pid, name, img, price, description FROM products";

if (isset($_GET["q"])) {
 
    $q = $_GET['q'];
	
	// get all products from products table
	$query = $query . " where `name` LIKE \"%" . $q . "%\"";
}

if (isset($_GET["sort"])) {
	
	$sort = $_GET['sort'];
	
	if ($sort ==0){
		$query = $query . " order by price";
	} else {
		$query = $query . " order by name";
	}

}

if (isset($_GET["tsort"])) {
	
	$tsort = $_GET['tsort'];
	
	if ($tsort == 0){
		$query = $query . " asc";
	} else {
		$query = $query . " desc";
	}

}
 
if (isset($_GET["start"]) && isset($_GET["end"])) {
 
    $start = $_GET['start'];
	$end = $_GET['end'];
 
	// get all products from products table
	$query = $query . " LIMIT ". $start . " , ". $end;
}

mysql_query("SET NAMES 'utf8'");

$result = mysql_query($query) or die(mysql_error());

	 
// check for empty result
if (mysql_num_rows($result) > 0) {
	// looping through all results
	// products node
	$response["products"] = array();
 
	while ($row = mysql_fetch_array($result)) {
		// temp user array
		$product = array();
		$product["pid"] = $row["pid"];
		$product["name"] = $row["name"];
		$product["img"] = $row["img"];
		$product["price"] = $row["price"];
		$product["description"] = $row["description"];
		//$product["created_at"] = $row["created_at"];
		//$product["updated_at"] = $row["updated_at"];
 
		// push single product into final response array
		array_push($response["products"], $product);
	}
	// success
	$response["success"] = 1;
	// echoing JSON response
	echo json_encode($response);
} else {
	// no products found
	$response["success"] = 0;
	$response["message"] = "No products found";
	
	// echoing JSON response
	echo json_encode($response);
}

?>