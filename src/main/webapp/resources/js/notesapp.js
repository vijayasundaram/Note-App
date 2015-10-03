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

            
            .when('/editNote', {
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
    	    }, 
    	    function(response) { // optional
    	            // failed
    	    });
    	}
    });

    notesApp.controller('editNoteController', function($scope) {
        
    });