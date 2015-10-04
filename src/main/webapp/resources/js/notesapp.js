var notesApp = angular.module('notesApp', ['ngRoute','ngAnimate']);
	
    // configure our routes
    notesApp.config(function($routeProvider) {
        $routeProvider

            
            .when('/', {
                templateUrl : '/resources/partials/listNotes.html',
                controller  : 'listNotesController'
            })

            
            .when('/addNote', {
                templateUrl : '/resources/partials/addNote.html',
                controller  : 'addNoteController'
            })

            
            .when('/editNote/:key', {
                templateUrl : '/resources/partials/editNote.html',
                controller  : 'editNoteController'
            });
    });


    notesApp.controller('listNotesController', function($scope,$http) {
    	$http.get('/note/').success(function(data) {
   		 $scope.notes = data;
    	});
    });

    notesApp.controller('addNoteController', function($scope,$http) {
    	$scope.noteData    = {};
    	$scope.addNote = function(){
    		$http({
    	        url: '/note/',
    	        method: "POST",
    	        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    	        data    : $.param($scope.noteData),
    	    })
    	    .then(function(data) {
    	    	 console.log(data);
    	    	 window.location.href = "#/";
    	    }, 
    	    function(response) { // optional
    	            // failed
    	    });
    	}
    });

    notesApp.controller('editNoteController', function($scope,$routeParams,$http,$location) {
    	$scope.key = $routeParams.key;
    	//Get note values to populate the form
    	$http.get('/note/'+$scope.key+'/').success(function(data) {
      		 $scope.noteData = data;
       	});
    	//Put note values to update the note Object
    	$scope.editNote = function(){
    		$http({
    	        url: '/note/'+$scope.key+'/',
    	        method: "PUT",
    	        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    	        data    : $.param($scope.noteData),
    	    })
    	    .then(function(data) {
    	    	 console.log(data);
    	    	 window.location.href = "#/";
    	    }, 
    	    function(response) { // optional
    	            // failed
    	    });
    	}
    	//Delete note
    	$scope.deleteNote = function(){
    		var canDelete = confirm("You sure you want to delete "+$scope.noteData.title+"?");
    		if(canDelete)
    		{
    			$http({
    		
    	        url: '/note/'+$scope.key+'/',
    	        method: "DELETE",
    	        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	    	    })
	    	    .then(function(data) {
	    	    	 console.log(data);
	    	    	 window.location.href = "#/";
	    	    }, 
	    	    function(response) { // optional
	    	            // failed
	    	    });
	    		}
    		}
    	
    	
    });