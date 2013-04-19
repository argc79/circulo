<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
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

</head>
<body onload='document.f.j_username.focus();'>
	<c:if test="${not empty error}">
		<div class="errorblock alert alert-block alert-error fade in">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>Error!</strong> Your login attempt was not successful, try again.<br /> Caused :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
	
	<div class="container">
		<form name='f' action="<c:url value='j_spring_security_check' />"
			method='POST' class="form-signin">
			
			<h2 class="form-signin-heading">Please sign in</h2>
			<input type='text' name='j_username' value='' class="input-block-level" placeholder="User">
			<input type='password' name='j_password' class="input-block-level" placeholder="Password"/>
			<input name="submit" type="submit"  class="btn btn-large btn-primary" value="Submit" />
		</form>
		<form action="j_spring_openid_security_check" id=”googleOpenId” method="post" target="_top">
    		<input id="openid_identifier" name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id"/>
    		<img src="/img/social/google.png" onClick="submitForm('googleOpenId')”/>
		</form>
	</div>
	<script src="lib/jquery.js"></script>
	<script src="lib/bootstrap.js"></script>
</body>
</html>