<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>Logout Page</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
		<style type="text/css">
		      body {
		        padding-top: 40px;
		        padding-bottom: 40px;
		        background-color: #f5f5f5;
		      }
		
		      .form-signin {
		        max-width: 300px;
		        padding: 19px 29px 29px;
		        margin: 0 auto 20px;
		        background-color: #fff;
		        border: 1px solid #e5e5e5;
		        -webkit-border-radius: 5px;
		           -moz-border-radius: 5px;
		                border-radius: 5px;
		        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		                box-shadow: 0 1px 2px rgba(0,0,0,.05);
		      }
		      .form-signin .form-signin-heading,
		      .form-signin .checkbox {
		        margin-bottom: 10px;
		      }
		      .form-signin input[type="text"],
		      .form-signin input[type="password"] {
		        font-size: 16px;
		        height: auto;
		        margin-bottom: 15px;
		        padding: 7px 9px;
		      }
		</style>	
		<c:if test="${empty username}">
			<meta http-equiv="refresh" content="5;url=/">
		</c:if>
	</head>
	<body>
		<c:if test="${not empty username}">
			<div class="container">
				<form name='f' action="<c:url value='j_spring_security_check' />"
					method='POST' class="form-signin">
					
					<h2 class="form-signin-heading">enough for Today?</h2>
						<a href="<c:url value="/j_spring_security_logout" />" > Yes! (Logout ${username})</a><br/>
						<a href="<c:url value="/" />" > Not really...</a>
				</form>
			</div>
			<script src="lib/jquery.js"></script>
			<script src="lib/bootstrap.js"></script>
		</c:if>
	</body>
</html>