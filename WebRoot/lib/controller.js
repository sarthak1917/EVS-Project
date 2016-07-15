var app=angular.module('mainApp');

//index.html controller
app.controller('loginCtrl',function($scope,$sessionStorage,$window,httpService){
	
	 $scope.$session=$sessionStorage;
	
	//sign in data
	$scope.user={};
	$scope.user.username="";
	$scope.user.password="";
	
    $scope.signIn=function(){
    	console.log('in signIn');
    	httpService.login(JSON.stringify($scope.user)).then(function(response){
    		console.log(response);
    		if(response.code==200){
    			if(response.type=='user'){
    				$scope.$session.status=response.status;
    				$window.location.href ='userPage.html#/vote-for';
    				
    			}else{
    				$window.location.href ='adminPage.html#/add-candidate';
    			}
    		}else{
    			alert("error "+response.data);
    		}
    		
    	});
    	
    } ;
    
    //new user registration data
    $scope.users={};
    $scope.users.name="";
    $scope.users.email="";
    $scope.users.dob="";
    $scope.users.type="";
    $scope.users.password="";
    $scope.users.confirmPass="";
    $scope.users.status="";
    
    $scope.save=function(){
    	console.log("in save");
    	if($scope.users.password==$scope.users.confirmPass){
    	    $scope.users.status="notapplied";
    		httpService.saveUser(JSON.stringify($scope.users)).then(function(response){
    			console.log(response);
    			if(response.code==200){
    				$('#myModal').modal("hide");
    			}else{
    				alert("error "+response.data);
    			}
    		});
    		
    	}else{
    		alert('password mismatch');
    	}
    };
});


//user controllers

//vote.html controller
app.controller('voteCtrl',function($scope,$sessionStorage,httpService){
	$scope.$session=$sessionStorage;
	console.log($scope.$session.status);
	$scope.status=$scope.$session.status;
		
	if($scope.status=='notapplied'){
		$scope.check=true;
		$scope.title="Please Apply for Voter Card";
	}else if($scope.status=='waitingforapproval'){
		$scope.check=true;
		$scope.title="Waiting for Approval by Admin";
	}else{
		$scope.check=false;
	}
	
	$scope.candidates=[];
	
    httpService.fetchCandidates().then(function(response){
	   if(response.code==500){
		   alert("error "+response.data);
	   }else{
		   $scope.candidates=response;
	   }
   });
    
    $scope.vote=function(){
    	$scope.data={name:$scope.votedCandidate};
    	httpService.vote(JSON.stringify($scope.data)).then(function(response){
    		console.log(response);
    		if(response.code==200){
    			alert("Thank you for voting");
    		}else{
    			alert("Please vote after some time");
    		}
    	});
    }
    
});


//VoterCardCtrl , aaply_for_card.html controller
app.controller('VoterCardCtrl',function($scope,httpService,$window,$sessionStorage){
	
	$scope.$session=$sessionStorage;
	
	$scope.voterCardData={};
	$scope.voterCardData.name=" ";
	$scope.voterCardData.fname=" ";
	$scope.voterCardData.email=" ";
	$scope.voterCardData.proof=" ";
	$scope.voterCardData.dob=" ";
	$scope.voterCardData.gender=" ";
	$scope.voterCardData.address=" ";
	$scope.voterCardData.proofNo=" ";
	$scope.voterCardData.status="waitingforapproval";
	
	$scope.$session.status=$scope.voterCardData.status;
	
	
	
	$scope.applyVoterCard=function(){
		alert("here in apply");
		httpService.applyVoterCard(JSON.stringify($scope.voterCardData)).then(function(response){
			console.log(response);
			if(response.code==200){
				$window.location.href = 'userPage.html#/vote-for';
			}else{
				alert("error "+response.data);
			}
		});
		
		$scope.voterCardData={};
	};
	
	$scope.reset=function(){
		$scope.voterCardData={};	
	};
	
});

//resultElecCtrl, result.html controller
app.controller('resultElecCtrl',function($scope,httpService){
    
	$scope.candidates=[];
	
    httpService.fetchCandidates().then(function(response){
	   if(response.code==500){
		   alert("error "+response.data);
	   }else{
		   $scope.candidates=response;
	   }
   });
	
});

//admin Controllers

//candidateCtrl, addCandidate.html controller to add candidate for elections
app.controller('candidateCtrl',function($scope,$window,httpService){
	
	$scope.candidate={ };
	$scope.candidate.name=" ";
	$scope.candidate.age=" ";
	$scope.candidate.pName=" ";
	$scope.candidate.pSymbol=" ";
	
	$scope.add=function(){
		httpService.addCandidate(JSON.stringify($scope.candidate)).then(function(response){
			if(response.code==200){
				console.log("success");
				$window.location.href = 'adminPage.html#/add-candidate';
				$scope.candidate={ };
			}else{
				alert("error "+response.data);
			}
		});
	}
	
	$scope.clear=function(){
		$scope.candidate={ };
	}
	
	
	
});

//verifyUserCtrl, verifyUser.html controller to verify user's voter card
app.controller('verifyUserCtrl',function($scope,$window,httpService){
	
	httpService.getAppliedUsers().then(function(response){
		console.log(response);
		if(response.code==500){
			alert("error "+response.data);
		}else{
			console.log("in else");
			$scope.appliedUsers=response;		
		}
	});
	
	//$scope.data={};
	
	$scope.approve=function(email){
		alert("in approve");
		$scope.data={email:email,status:"approved"};
		console.log($scope.data);
		httpService.approveVoterCard($scope.data).then(function(response){
			if(response.code==200){
				$window.location.href = 'adminPage.html#/verify-user';
			}else{
				alert(response.data);
			}
			
		});
	};
	
});