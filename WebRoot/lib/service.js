var app=angular.module('mainApp');

app.factory('httpService',function($http,$q){
	
	var obj={
			login:login,
			saveUser:saveUser,
			applyVoterCard:applyVoterCard,
			getAppliedUsers:getAppliedUsers,
			approveVoterCard:approveVoterCard,
			addCandidate:addCandidate,
			fetchCandidates:fetchCandidates,
			vote:vote,
			getStatus:getStatus
			};
	return obj;
	
	function login(data){
		return request("POST","main?path=login",data,null);
	}
	
	function saveUser(data){
		return request("POST","main?path=saveUser",data,null);
	}
	
	function applyVoterCard(data){
		return request("POST","users?path=voterCard",data,null);
	}
	
	function getAppliedUsers(){
		return request("GET","admin?path=getApplyUsers",null,null);
	}
	
	function approveVoterCard(data){
		return request("POST","admin?path=approveCard",data,null);
	}
	
	function addCandidate(data){
		return request("POST","admin?path=addCandidate",data,null);
	}
	
	function fetchCandidates(){
		return request("GET","users?path=getCandidates",null,null);
	}
	
	function vote(data){
		return request("POST","users?path=vote",data,null);
	}
	
	function getStatus(){
		return request("GET","users?path=getStatus",null,null);
	}
	
	function request(method,url,data,headers){
        var defered=$q.defer();
        console.log(url);
        console.log(data);
		$http({
			method:method,
			headers:headers,
			url:url,
			contentType: 'application/json',
			data:data
		}).success(function(response){
			defered.resolve(response);
		})
		  .error(function(error){
			  defered.resolve(error);
		  });
		
		return defered.promise;
	}
	
	
});


