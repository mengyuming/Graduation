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
  }).state('editPersonal',{
    url:'/editPersonal',
    templateUrl:'view/editPersonal.html',
    controller:'editPersonalCtrl'
  }).state('messageDetail',{
    url:'/messageDetail/:id',
    templateUrl:'view/messageDetail.html',
    controller:'messageDetailCtrl'
  }).state('evaluation',{
    url:'/evaluation/:id',
    templateUrl:'view/evaluation.html',
    controller:'evaluationCtrl'
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
angular.module('app').controller('courseCtrl',['$scope','$http',function ($scope,$http) {

  $scope.item = {id: 1,cname:'数据库', cno: "1", week: 'Tue', time: '5-6', tid: 'xxx',place:'A101'}
 /* $http({
    method:'post',
    url:'/course/getCourseList',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset:utf-8',
      'Accept':'*!/!*'
    }
  }).then(function success(rep) {
    console.log('成功',rep)
  },function error(err) {
      console.log(err)
  })*/

}])
'use strict'
angular.module('app').controller('editPersonalCtrl',[function () {

}])
'use strict'
angular.module('app').controller('evaluationCtrl',['$scope',function ($scope) {
  $scope.teachers = [
    {id:1,name:'XXX'},
    {id:2,name:'YYY'},
    {id:3,name:'ZZZ'},
    {id:4,name:'WWW'}
  ]
  $scope.submit = function () {
      // $http.post('')
    console.log('提交')
  }
  $scope.isShow1 = 'true';
  $scope.toggle1 = function () {
      this.isShow1= !this.isShow1;
  }
  $scope.isShow2 = 'true';
  $scope.toggle2 = function () {
    this.isShow2= !this.isShow2;
  }
  $scope.isShow3 = 'true';
  $scope.toggle3 = function () {
    this.isShow3= !this.isShow3;
  }
  $scope.isShow4 = 'true';
  $scope.toggle4 = function () {
    this.isShow4= !this.isShow4;
  }
  $scope.question1 = [
    {q:"1、材料发放是否及时？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"2、课前老师发放的材料内容是否充分？知识点全面？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"3、课件内容是否有明显错误？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"4、发放材料内容是否清晰易懂？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"5、材料形式是否丰富多样（如：ppt、视频、书籍、文献等）？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"6、教学内容能否反映或联系学科发展的新思想，新概念？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"7、学习内容数量是否合理？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"}
  ]
  $scope.question2=[
    {q:"1、老师是否有迟到早退的情况？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"2、讲课是否有热情？精神是否饱满？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"3、 老师是否能照顾到每一个同学？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"5、课前学习的疑惑是否得到解决？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"6、对你的疑惑的解释是否简练准确、重点突出、思路清晰？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"7、对你的疑惑的解释是否深入浅出，具有启发性？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"8、能否给予你思考、联想、创新的启迪？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"9、老师的提问是否有吸引力，同学之间能否围绕问题展开讨论，交流，合作学习？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
  ]
  $scope.question3 =[
    {q:"1、老师有没有照顾到你的学习和反应，有没有感受到区别对待？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"2、老师与你的互动能否激发你的学习热情？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"3、课堂氛围是否融洽，活跃？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"4、学习这门课程是否让你感到开心？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"}
  ]
  $scope.question4 = [
    {q:"1、老师是否能根据课程内容的学习特点和学生的教学内容掌握差异进行分层教学？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"2、老师是否有清晰的课堂规划？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"3、老师的教学是否能提高学生的学习积极性？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"4、老师的教学目标达成率？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"5、老师是否有耐心对学生的问题进行解答？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"},
    {q:"6、学生是否敢于主动提问？",a1:"优秀",a2:"良好",a3:"中等",a4:"差"}
  ]


}])
angular.module('app').controller('forgetCtrl',['$scope','$http','$interval','$state',function ($scope,$http,$interval,$state) {

  $scope.submit = function () {
      $http.post('url',$scope.user).success(function () {
        $state.go('login')
      }).error(function () {
        console.log('跳转失败');
      })
  }

    var count = 180;
    $scope.send = function () {
        $http.get('url').success(function (result) {
          console.log(result);
          if(1==result.status){
            $scope.time = '180s'
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

          $scope.time = '180s'
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
  $scope.submit = function () {
      // $http.post('/user/logins',$scope.user).success(function () {
      //   $state.go('main');
      // }).error(function () {
      //   console.log("失败")
      // })
      $http({
          method: "post",
          url: '/user/logins',
          params: {
              "stunum":$scope.user.id,
              "password":$scope.user.pw,
              "type":$scope.user.identify,
          },
          headers: {
              'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
              'Accept':'*/*'
          }
      }).then(function success(rep) {
        console.log("成功")
      },function error(err) {
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
    $scope.data = data;
}])
'use strict'
angular.module('app').controller('messageCtrl',['$scope',function ($scope) {
  $scope.data=[
    {id:1,title:'关于xxx的通知',postName:'aaa',time:'2018-1-10 13:24'},
    {id:2,title:'关于yyy的通知',postName:'bbb',time:'2018-1-4 06:26'},
    {id:3,title:'关于zzz的通知',postName:'ccc',time:'2018-3-6 09:44'},
    {id:4,title:'关于www的通知',postName:'ddd',time:'2018-5-8 12:22'}
  ]
}])
'use strict'
angular.module('app').controller('messageDetailCtrl',['$scope','$http','$state',function ($scope,$http,$state) {
  $scope.data={
    id:1,
    title:'关于xxx的通知',
    postName:'aaa',
    time:'2018-1-10 13:24',
    msg:' “公共英语三级”课程1月30日晚的课程不上，调到1月21日晚上课。'

  }

  $http.get('url',{
      params:{
        id:$state.params.id
      }
  }).success(function (rep) {
    console.log(rep);
    $scope.msg = rep
  }).error(function (err) {
      console.log('失败');
  })
}])
'use strict'
angular.module('app').controller('mineCtrl',[function () {

}])
'use strict'
angular.module('app').controller('myTeacherCtrl',['$scope',function ($scope) {
  $scope.teachers = [
    {id:1,name:'XXX'},
    {id:2,name:'YYY'},
    {id:3,name:'ZZZ'},
    {id:4,name:'WWW'}
  ]
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
angular.module('app').controller('registerCtrl',['$scope','$http','$interval','$state',function ($scope,$http,$interval,$state) {

  $scope.submit = function () {
    $http({
        method:"post",
        url:"/user/userRegister",
        params:{
            "type":$scope.user.identify,
            "stunum":$scope.user.id,
            "name":$scope.user.name,
            "school":$scope.user.school,
            "password":$scope.user.pw1,
            "grade":$scope.user.grade,
            "email":$scope.user.email,
            "text":$scope.user.msg,
        },
        headers:{
          'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8',
            'Accept':'*/*'
        }
    }).then(function success(rep) {
      console.log("成功")
    },function error(err) {
          console.log(err)
        console.log('失败')
      })
  }

  var count = 180;
  $scope.send = function () {
      $http({
          method: "post",
          url: "/email/getEmailMessage",
          params: {
              "to":$scope.user.email
          },
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
             'Accept':'*/*'
          }
      }).then(function success(rep) {
          if(200==rep.status){
              $scope.time = '180s'
              var interval = $interval(function () {
                  if(count<0){
                      $interval.cancel(interval)
                      $scope.time = '';
                  }else{
                      count--;
                      $scope.time = count+'s';
                  }
              },1000)
          }
          console.log("成功")
      },function error(err) {
          console.log(err)
          console.log("发送邮箱失败")
      })
  }
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
    templateUrl:'view/template/head.html',
      scope:{
        'data':'@'
      },
      link:function($scope,$http){
        $scope.send = function () {
            $http({
                method: "get",
                url: "/course/choice",
                params: {
                    "type":$scope.type,
                    "messages":$scope.search
                },
                //学院-专业-班级
                // headers: {
                //     'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                //     'Accept':'*/*'
                // }
            }).then(
                function success(rep) {
                    console.log('成功');
                    $scope.data = rep;
                    console.log(data)
                },function error(err) {
                    console.log('失败');
                }
            )
        }
      }
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
'use strict'
angular.module('app').directive('appScroll',[function () {
    return{
      restrict:'A',
      replace:true,
      templateUrl:'view/template/scroll.html',
      link:function () {
        
      }
    }
}])