<%@page import="com.vijacdblz.note.utility.GAHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Google OAuth 2.0</title>
<style type="text/css">
@import url(http://fonts.googleapis.com/css?family=Quattrocento+Sans:400,700);
@import url("http://weloveiconfonts.com/api/?family=entypo");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

:before, :after {
  content: '';
  display: block;
  position: absolute;
  box-sizing: border-box;
}

html, body {
  
  background: #ddd;
}

.section {
  padding: 20px;
  border-bottom: 1px solid #aaa;
}
.btn {
  position: relative;
  display: block;
  width: 210px;
  height: 40px;
  
  margin: 200px auto;
  padding: 5px 0 0 50px;
  font: 700 14px/32px 'Quattrocento Sans', sans-serif;
  text-decoration: none;
  text-transform: uppercase;
  color: #555;
  border-radius: 2px;
  background: linear-gradient(to bottom, #ffffff 0%, #e6e6e6 100%);
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.5);
  cursor: pointer;
}

.btn span {
  font-size: 14px;
  text-transform: none;
  color: #aaa;
}
.btn:before {
  top: 0;
  left: 0;
  width: 40px;
  height: 40px;
  font: 20px/40px entypo;
  text-align: center;
  color: #fff;
  border-radius: 2px 0 0 2px;
}
.btn.gp:before {
  content: '\f30f';
  background: linear-gradient(to bottom, #dd4433 0%, #bd2f20 100%);
}
.btn:hover {
  text-shadow: 0 0 10px #fff;
  
}
/* naked */
.btn.naked:before {
  border-right: 1px solid #aaa;
  background: none;
}


.btn.gp.naked:before {
  color: #d33523;
}
</style>
</head>
<body>
	<%! final GAHelper helper = new GAHelper();%>
	<a class='btn gp naked' href='<%=helper.buildLoginUrl() %>'>
    sign in
    <span>
      with google
    </span>
  	</a>
</body>
</html>