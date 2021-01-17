<!DOCTYPE html>
<html>
<head>
	<title>Login Page</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
	<!-- 	Meta tag required to make web page responsive -->
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
	
	<div class="container-fluid pageHeader">
		<div class = "row pt-3 pb-3">
			<div class = "col-7">
				<h2 class="textColor">USC CS 310 Stock Portfolio Management</h2>
			</div>
			<div class = "col-5 text-right">
				<h2 class="textColor"><a class="formatLink" href="register.jsp">Register</a></h2>
			</div>
		</div>
	</div>

	<div class="container text-center mt-5 titleColor">
		<h1 id="loginTitle">Login</h1>
	</div>

	<!-- For action, just add endpoint as defined by servlet you want to communicate with -->
	<!-- change this action to redirect to homepage once homepage defined!! -->
	<form class="mt-5 mb-5 col-5 mx-auto" id="form-container" action="home.jsp" method="POST" onsubmit="return validateLogin();">

		<div class="text-danger pt-3 errorMessage" id="loginError"></div>

		<div class="row justify-content-center pt-3">
			<div class ="col-12">
				<div class="form-group">
					<label class="label" for="username-login">
					<span class="text-danger">* </span> Username:
					</label>
					<input type="text" name="username-login" class="form-control" id="username-login">
					<div class="text-danger" id="username-error"></div>
				</div>
			</div>
		</div>

		<div class="row justify-content-center pt-3">
			<div class="col-12">
				<div class="form-group">
					<label class="label" for="password-login">
					<span class="text-danger">* </span> Password:
					</label>
					<input type="password" name="password-login" class="form-control" id="password-login">
					<div class="text-danger" id="password-error"></div>
				</div>
			</div>
		</div>

		<div class="text-center pt-3 pb-3">
			<button type="submit" class="btn btn-primary" id="loginButton">Login</button>
		</div>

	</form>

	<script type="text/javascript">

		// need to make sure define servlet with the endpoint /login for this ajax request to work
		
		// function to send username/password for login to backend
		function validateLogin() {
			var xhttp = new XMLHttpRequest(); //only need this for ajax
			var username = document.getElementById("username-login").value;
			var password = document.getElementById("password-login").value;
			password = md5(password);
			xhttp.open("POST", "/login?username=" + username + "&password="+ password, false); //false means synchronous
			xhttp.send();
			
			// handle any error messages
			if(xhttp.responseText.trim() != "Success") {
				
				document.getElementById("loginError").innerHTML = xhttp.responseText;
				return false;
			}
			return true; // redirect to file pointed at by action field above
		}

	</script>


	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
	<script src="https://rawgit.com/emn178/js-md5/master/build/md5.min.js"></script>
</body>
</html>