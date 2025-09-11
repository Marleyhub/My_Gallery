var app = angular.module('myApp', []);

app.controller('UserController', function($http) {
    var vm = this;
    vm.user = {};

    // Call Spring Boot API
	vm.submitForm = function() {
	        $http.post('/api/user', vm.user)
	            .then(function(response) {
	                alert('User sent successfully!');
	                console.log(response.data);
	            }, function(error) {
	                alert('Error sending user: ' + error);
	            });
	    };
});
