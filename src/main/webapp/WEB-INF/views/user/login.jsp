<%@page import="com.vijacdblz.note.utility.GAHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Google OAuth 2.0</title>
</head>
<body>
	<div class="oauthDemo">
		<%! final GAHelper helper = new GAHelper();%>
	</div>
	<a href='<%=helper.buildLoginUrl() %>'>log in with google</a>
			
</body>
</html>