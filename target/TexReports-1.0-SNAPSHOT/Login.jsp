<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 17.02.2022
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Tax Report</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!--===============================================================================================-->
  <link rel="icon" type="image/png" href="Login_v16/images/icons/favicon.ico"/>
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/bootstrap/css/bootstrap.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/animate/animate.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/css-hamburgers/hamburgers.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/animsition/css/animsition.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/select2/select2.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/daterangepicker/daterangepicker.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/css/util.css">
  <link rel="stylesheet" type="text/css" href="Login_v16/css/main.css">
  <!--===============================================================================================-->
  <script>
    function validate()
    {
      var username = document.form.username.value;
      var password = document.form.password.value;

      if (username==null || username=="")
      {
        alert("Username cannot be blank");
        return false;
      }
      else if(password==null || password=="")
      {
        alert("Password cannot be blank");
        return false;
      }
    }
  </script>
</head>
<body>

<div class="limiter">
  <div class="container-login100" style="background-image: url('Login_v16/images/bg-01.jpg');">
    <div class="wrap-login100 p-t-30 p-b-50">
				<span class="login100-form-title p-b-41">
					Account Login
				</span>
      <form class="login100-form validate-form p-b-33 p-t-5" action="/login" method="post" onsubmit="return validate()">

        <div class="wrap-input100 validate-input" data-validate = "Enter username">
          <input class="input100" type="text" name="username" placeholder="User name">
          <span class="focus-input100" data-placeholder="&#xe82a;"></span>
        </div>

        <div class="wrap-input100 validate-input" data-validate="Enter password">
          <input class="input100" type="password" name="password" placeholder="Password">
          <span class="focus-input100" data-placeholder="&#xe80f;"></span>
        </div>
        <div><span style="color:red"><%=(request.getAttribute("errMessage") == null) ? ""
                : request.getAttribute("errMessage")%></span>
         </div>
        <div class="container-login100-form-btn m-t-32">
          <button class="login100-form-btn">
            Login
          </button>

          <button class="login100-form-btn">
            <li><a href="<%=request.getContextPath()%>/Register.jsp"
                   class="nav-link" style="color: white">Registration</a></li>
          </button>
        </div>


      </form>
    </div>
  </div>
</div>


<div id="dropDownSelect1"></div>

<!--===============================================================================================-->
<script src="Login_v16/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/bootstrap/js/popper.js"></script>
<script src="Login_v16/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/daterangepicker/moment.min.js"></script>
<script src="Login_v16/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/js/main.js"></script>

</body>
</html>