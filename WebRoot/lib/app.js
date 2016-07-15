var app=angular.module('mainApp',['ui.router','ngStorage']);

app.config(function($stateProvider,$urlRouterProvider){
	console.log("here on vote");
	$stateProvider.state('vote',{
		
		url:'/vote-for',
		templateUrl:'pages/vote.html',
		//controller:'voteCtrl'
		
	})
	
	.state('apply_for_card',{
		
		url:'/apply-for-card',
		templateUrl:'pages/apply_for_card.html',
		controller:'VoterCardCtrl'
		
	})
	
	.state('result',{
		
		url:'/result-vote',
		templateUrl:'pages/result.html',
		controller:'resultElecCtrl'
		
	})
	
	.state('addCandidate',{
		
		url:'/add-candidate',
		templateUrl:'pages/addCandidate.html',
		controller:'candidateCtrl'
		
	})
	
	.state('verifyUser',{
		
		url:'/verify-user',
		templateUrl:'pages/verifyUser.html',
		controller:'verifyUserCtrl'
			
	})
	
    .state('sign_out',{
    	 
		controller:'signOut'
	});
	
	
	
	$urlRouterProvider.otherwise('http://hp:8086/AngularProject1/');
	
});


app.controller('signOut',function($window){
	
	$window.location.href='http://hp:8086/AngularProject1/';
	
});