<?PHP
	$hostname_localhost ="hl177.dinaserver.com";  	// Nombre servidor
	$database_localhost ="canalsprojects";			// Nombre base de datos
	$username_localhost ="canalsprojects";			// Nombre de usuario
	$password_localhost ="1q2w3e";					// Contraseña
	$localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost)//Conexión a nuestro servidor mysql
	or
	trigger_error(mysql_error(),E_USER_ERROR); //mensaaje de error si no se puede conectar

	mysql_select_db($database_localhost, $localhost);//seleccion de la base de datos con la qu se desea trabajar

	$dni=$_POST['dni'];
	$query_search = "delete from test where dni='".$dni."'";
	$query_exec = mysql_query($query_search) or die(mysql_error());
	mysql_close($localhost);
?>