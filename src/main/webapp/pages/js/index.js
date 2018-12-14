//module
'use strict'
angular.module('app',['ui.router','validation'])

'use strict'
angular.module('app')
  .config(['$stateProvider','$urlRouterProvider',function ($stateProvider,$urlRouterProvider) {
  $stateProvider.state('main',{
    url:'/main',
    templateUrl:'view/main.html',
    controller:'mainCtrl'
  }).state('rank',{
    url:'/rank',
    templateUrl:'view/rank.html',
    controller:'rankCtrl'
  }).state('mine',{
    url:'/mine',
    templateUrl:'view/mine.html',
    controller:'mineCtrl'
  }).state('course',{
    url:'/course',
    templateUrl:'view/course.html',
    controller:'courseCtrl'
  }).state('message',{
    url:'/message',
    templateUrl:'view/message.html',
    controller:'messageCtrl'
  }).state('personal',{
    url:'/personal',
    templateUrl:'view/personal.html',
    controller:'personalCtrl'
  }).state('record',{
    url:'/record',
    templateUrl:'view/record.html',
    controller:'recordCtrl'
  }).state('myTeacher',{
    url:'/myTeacher',
    templateUrl:'view/myTeacher.html',
    controller:'myTeacherCtrl'
  }).state('login',{
    url:'/login',
    templateUrl:'view/login.html',
    controller:'loginCtrl'
  }).state('register',{
    url:'/register',
    templateUrl:'view/register.html',
    controller:'registerCtrl'
  }).state('forget',{
    url:'/forget',
    templateUrl:'view/forget.html',
    controller:'forgetCtrl'
  })
  $urlRouterProvider.otherwise('main')

}])


'use strict'
angular.module('app').config(['$validationProvider',function ($validationProvider) {
  var expression = {
    required:function (value) {
      return !!value;
    },
    pw:/^(?!([a-zA-Z]+|\d+)$)[a-zA-Z\d]{6,18}$/,

    chinese:/^[\u0391-\uFFE5]+$/,
    id:/^\d{8}$/,
    email:/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/,
    phone:/^1[34578]\d{9}$/,
    birth:/^((19[2-9]\d{1})|(20((0[0-9])|(1[0-8]))))\-((0?[1-9])|(1[0-2]))\-((0?[1-9])|([1-2][0-9])|30|31)$/

  }
  var defaultMsg = {
    required:{
      success:'',
      error:'不能为空'
    },
    pw:{
      success:'',
      error:'要求数字与字母相结合'
    },

    chinese:{
      success:'',
      error:'必须是中文'
    },
    id:{
      success:'',
      error:'格式不正确'
    },
    email:{
      success:'',
      error:'格式不正确'
    },
    phone:{
      success:'',
      error:'格式不正确'
    },
    birth:{
      success:'',
      error:'格式不正确'
    }
  }
  $validationProvider.setExpression(expression).setDefaultMsg(defaultMsg);
}])
'use strict'
angular.module('app').controller('courseCtrl',[function () {

}])
angular.module('app').controller('forgetCtrl',['$scope','$http','$interval','$state',function ($scope,$http,$interval,$state) {

  $scope.submit = function () {
      $http.post('url',$scope.user).success(function () {
        $state.go('login')
      }).error(function () {
        console.log('跳转失败');
      })
  }

    var count = 60;
    $scope.send = function () {
        $http.get('url').success(function (result) {
          console.log(result);
          if(1==result.status){
            $scope.time = '60s'
            var interval = $interval(function () {
              if(count<0){
                $interval.cancel(interval)
                $scope.time = '';
              }else{
                count--;
                $scope.time = count+'s';
                console.log(count);
              }
            },1000)
          }
        }).error(function (err) {
          console.log("失败");

          $scope.time = '60s'
          var interval = $interval(function () {
            if(count<=0){
              $interval.cancel(interval)
              $scope.time = '';
            }else{
                count--;
                $scope.time = count+'s';
            }
          },1000)

        })
    }
}])
angular.module('app').controller('loginCtrl',['$scope','$http','$state',function ($scope,$http,$state) {
  $scope.post = function () {
      $http.post('url',$scope.user).success(function () {
        $state.go('main');
      }).error(function () {
        console.log("失败")
      })
  }
}])
'use strict'
angular.module('app').controller('mainCtrl',['$scope',function ($scope) {
  // $scope.items=[
  //   {name:"aaa",id:"11111111",cour:"hhhhhhh"},
  //   {name:"xxxccccc",id:"11111111",cour:"jjjjjjj"},
  //   {name:"sss",id:"11111111",cour:"uuuuuuuuuu"},
  // ]
}])
'use strict'
angular.module('app').controller('messageCtrl',[function () {

}])
'use strict'
angular.module('app').controller('myTeacherCtrl',[function () {

}])
'use strict'
angular.module('app').controller('mineCtrl',[function () {

}])
'use strict'
angular.module('app').controller('personalCtrl',[function () {

}])
'use strict'
angular.module('app').controller('rankCtrl',[function () {

}])
'use strict'
angular.module('app').controller('recordCtrl',[function () {

}])
angular.module('app').controller('registerCtrl',[function () {
    
}])
'use strict'
angular.module('app').directive('appContent',[function () {
  return{
    restrict:'A',
    replace:true,
    templateUrl:'view/template/content.html',
    scope:{
      data:'=',
      isSearch:'='
    },
    link:function ($scope) {

      // $http.get('').success(function (data) {
      //     console.log(data);
      // }).error(function (err) {
      //     console.log(err);
      // })

    }
  }
}])
angular.module('app').directive('appFooter',[function () {
  return{
    restrict:'A',
    replace:true,
    templateUrl:'view/template/footer.html'
  }
}])
'use strict'
angular.module('app').directive("appHead",[function () {
  return{
    restrict:'A',
    replace:true,
    templateUrl:'view/template/head.html'
  }
}])
'use strict'
angular.module('app').directive("appHeadBar",[function () {
  return{
    restrict:'A',
    replace:true,
    templateUrl:'view/template/headBar.html',
    scope:{
      text:'@'
    },
    link:function ($scope) {
      $scope.back = function () {
        window.history.back();
      }
    }
  }
}])