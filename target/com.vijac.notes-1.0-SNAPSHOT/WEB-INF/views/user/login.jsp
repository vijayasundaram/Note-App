<%@page import="com.vijac.notes.utility.GAHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login | Notes</title>
<link href="/resources/css/bootstrap.css" rel="stylesheet" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/resources/css/login.css">
</head>
<body>

            <%! final GAHelper helper = new GAHelper();%>

            <div class="container">

            <% if(request.getAttribute("email")!=null){%>
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <center> We have sent a password to your email address <strong>${email}.</strong></center>
                </div>
            <%}%>
              <div class="card card-container">
                <a class='btn btn-lg btn-signin-google btn-block' href='<%=helper.buildLoginUrl() %>'><i class="fa fa-google fa-lg"></i>Login with Google</a>
                <form class="form-signin">
                  <span id="reauth-email" class="reauth-email"></span>
                  <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                  <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                  <div id="remember" class="checkbox">

                    <label>
                      <input type="checkbox" value="remember-me"> Remember me
                    </label>
                  </div>
                  <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
                </form><!-- /form -->
                    <a href="#" class="forgot-password">
                    Forgot the password?
                    </a>
                  <p></p>
                    <a href="/account/register/" class="forgot-password">
                    Create new Account
                    </a>
              </div><!-- /card-container -->
            </div><!-- /container -->

</body>
</html>