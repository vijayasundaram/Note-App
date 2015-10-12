<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html data-ng-app="notesApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Notes App</title>
<link href="/resources/css/bootstrap.css" rel="stylesheet" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="/resources/css/summernote.css" rel="stylesheet"/>
<link href="/resources/css/main.css" rel="stylesheet"/>

</head>
<body>
<!-- Template copied from BootSnipp keenthemes -->
<div class="container-fluid">
    <div class="row profile">
		<div class="col-md-2 prof-bar">
			<div class="profile-sidebar">
				<!-- SIDEBAR USERPIC -->
				<div class="profile-userpic">
					<img src= "${picture}"  class="img-responsive" alt="">
				</div>
				<!-- END SIDEBAR USERPIC -->
				<!-- SIDEBAR USER TITLE -->
				<div class="profile-usertitle">
					<div class="profile-usertitle-name">
						${username}
					</div>
					<div class="profile-usertitle-job">
						${email}
					</div>
				</div>
				<!-- END SIDEBAR USER TITLE -->
				<!-- SIDEBAR BUTTONS -->
				<div class="profile-userbuttons">
					<a href="#/addNote" class="btn btn-success btn-sm">Add Note</a>
					<a href="/logout/" class="btn btn-danger btn-sm">Log out</a>
				</div>
				<!-- END SIDEBAR BUTTONS -->
				<!-- SIDEBAR MENU -->
				<div class="profile-usermenu">
					<ul class="nav" data-ng-controller="navController">
						<li data-ng-class="{ active: isActive('/')}">
							<a href="#/">
							<i class="glyphicon glyphicon-home"></i>
							All Notes </a>
						</li>
						<li data-ng-class="{ active: isActive('/accountSettings')}">
							<a href="#/accountSettings">
							<i class="glyphicon glyphicon-user"></i>
							Account Settings </a>
						</li>
						<li data-ng-class="{ active: isActive('/help')}">
							<a href="#/help">
							<i class="glyphicon glyphicon-flag"></i>
							Help </a>
						</li>
					</ul>
				</div>
				<!-- END MENU -->
			</div>
		</div>
		<div class="col-md-10">
            <div class="notes-content">
            	<!-- angular templating -->
           		<!-- this is where content will be injected -->
           		<div data-ng-view></div>
            </div>
		</div>	   
	</div>
</div>
<script type="text/javascript" src="/resources/js/lib/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="/resources/js/lib/bootstrap.min.js"></script>
<script src="/resources/js/lib/angular.js"></script>
<script src="/resources/js/lib/angular-route.js"></script>
<script src="/resources/js/lib/moment.min.js"></script>
<script src="/resources/js/lib/angular-moment.min.js"></script>
<script src="/resources/js/lib/summernote.min.js"></script>
<script src="/resources/js/lib/angular-summernote.js"></script>
<script src="/resources/js/lib/angular-sanitize.js"></script>
<script src="/resources/js/notesapp.js" type ="text/javascript"></script>
</body>
</html>