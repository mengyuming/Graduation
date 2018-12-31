//module
'use strict'
angular.module('app',['ui.router','validation','ngCookies'])
angular.module('app').service('cache',['$cookies',function ($cookies) {
    this.put = function (key,value) {
        $cookies.put(key,value)
    }
    this.get = function (key) {
        return $cookies.get(key)
    }
    this.remove = function (key) {
        $cookies.remove(key)
    }
}])
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
        }).state('site',{
            url:'/site',
            templateUrl:'view/site.html',
            controller:'siteCtrl'
        }).state('updatePassword',{
            url:'/updatePassword',
            templateUrl:'view/updatePassword.html',
            controller:'updatePasswordCtrl'
        })
        $urlRouterProvider.otherwise('login')

    }])
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
angular.module('app').controller('courseCtrl',['$scope','$http',function ($scope,$http) {

    //$scope.item = {id: 1,cname:'数据库', cno: "1", week: 'Tue', time: '5-6', tid: 'xxx',place:'A101'}
    $http({
        method: "get",
        url: "/course/getCourseList ",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept':'*/*'
        }
    }).then(
        function success(rep) {
            console.log('成功',rep);
        },function error(err) {
            console.log('失败',err);
        }
    )
}])
angular.module('app').controller('editPersonalCtrl',['$http','$scope','$state',function ($http,$scope,$state) {
    $http({
        method: "get",
        url: "/user/getInformation",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept':'*/*'
        }
    }).then(
        function success(rep) {
            console.log('成功');
            $scope.user = rep.data.all[0]
        },function error(err) {
            console.log('失败',err);
        }
    )
    $scope.submit = function () {
        $http({
            method: "post",
            url: "/user/updateinformation",
            params: {
                "gender":$scope.user.gender,
                "age":$scope.user.birth,
                "depart":$scope.user.institute,
                "professional":$scope.user.sign,
                "telephone":$scope.user.phone,
                "email":$scope.user.email,
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'Accept':'*/*'
            }
        }).then(
            function success(rep) {
                console.log('成功',rep);
                // $state.go('personal')
                $http({
                    method: "get",
                    url: "/user/getInformation",
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                        'Accept':'*/*'
                    }
                }).then(
                    function success(rep) {
                        console.log('成功');
                        $scope.user = rep.data.all[0]
                    },function error(err) {
                        console.log('失败',err);
                    }
                )
            },function error(err) {
                console.log('失败',err);
            }
        )
    }
}])
angular.module('app').controller('evaluationCtrl',['$scope','$http','cache',function ($scope,$http,cache) {
    $scope.type = cache.get('type')
    $scope.type==='学生'?'true':'false'
    console.log(cache.get('type'),$scope.type=='学生',$scope.type==='学生',$scope.type==='学生'?true:false)
    $scope.teachers = [
        {id:1,name:'XXX'},
        {id:2,name:'YYY'},
        {id:3,name:'ZZZ'},
        {id:4,name:'WWW'}
    ]
    $scope.submit = function () {
        $http({
            method: "get",
            url: "/course/choice",
            params: {
                "type":$scope.user.identify,
                "messages":$scope.user.name
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'Accept':'*/*'
            }
        }).then(
            function success(rep) {
                console.log('成功',rep);
            },function error(err) {
                console.log('失败',err);
            }
        )

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

    $scope.back = function () {
        window.history.back()
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
        }).then(
            function success(rep) {
                console.log('成功',rep);
            },function error(err) {
                console.log('失败',err);
            }
        )
    }
}])
angular.module('app').controller('loginCtrl',['cache','$scope','$http','$state',function (cache,$scope,$http,$state) {
    $scope.submit = function () {
        $http({
            method: "post",
            url: "/user/logins",
            params: {
                "username":$scope.user.id,
                'password':$scope.user.pw1,
                'type':$scope.user.identify
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'Accept':'*/*'
            }
        }).then(
            function success(rep) {
                console.log('成功',rep);
                cache.put('username',rep.config.params.username)
                cache.put('type',rep.config.params.type)
                $state.go('main')
            },function error(err) {
                console.log('失败',err);
            }
        )
    }
}])
angular.module('app').controller('mainCtrl',['$scope','$http',function ($scope,$http) {
    $scope.send = function(){
        $http({
            method: "get",
            url: "/course/choice",
            params: {
                "type":$scope.type,
                "messages":$scope.search
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'Accept':'*/*'
            }
        }).then(
            function success(rep) {
                console.log('成功',rep.data.all[0]);
                $scope.data = rep.data.all[0]
            },function error(err) {
                console.log('失败',err);
            }
        )
    }
}])
angular.module('app').controller('messageCtrl',['$scope','$http',function ($scope,$http) {

    $scope.data=[
        {id:1,title:'关于xxx的通知',postName:'aaa',time:'2018-1-10 13:24'},
        {id:2,title:'关于yyy的通知',postName:'bbb',time:'2018-1-4 06:26'},
        {id:3,title:'关于zzz的通知',postName:'ccc',time:'2018-3-6 09:44'},
        {id:4,title:'关于www的通知',postName:'ddd',time:'2018-5-8 12:22'}
    ]

    /*$http({
        method: "post",
        url: "/user/logins",
        params: {
            "stunum":$scope.user.id
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept':'*!/!*'
        }
    }).then(
        function success(rep) {
            console.log('成功',rep);
        },function error(err) {
            console.log('失败',err);
        }
    )*/
}])
angular.module('app').controller('messageDetailCtrl',['$scope','$http','$state',function ($scope,$http,$state) {
    $scope.data={
        id:1,
        title:'关于xxx的通知',
        postName:'aaa',
        time:'2018-1-10 13:24',
        msg:' “公共英语三级”课程1月30日晚的课程不上，调到1月21日晚上课。'

    }

    /*  $http.get('url',{
          params:{
            id:$state.params.id
          }
      }).success(function (rep) {
        console.log(rep);
        $scope.msg = rep
      }).error(function (err) {
          console.log('失败');
      })*/
    $http({
        method: "post",
        url: "/user/logins",
        params: {
            "email":$scope.user.email
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept':'*/*'
        }
    }).then(
        function success(rep) {
            console.log('成功',rep);
        },function error(err) {
            console.log('失败',err);
        }
    )
}])
angular.module('app').controller('mineCtrl',[function () {

}])
angular.module('app').controller('myTeacherCtrl',['$scope','$http',function ($scope,$http) {
    $scope.teachers = [
        {id:1,name:'XXX'},
        {id:2,name:'YYY'},
        {id:3,name:'ZZZ'},
        {id:4,name:'WWW'}
    ]
    /*$http({
        method: "get",
        url: "/course/choice",
        params: {
             "type":$scope.user.identify,
            "messages":$scope.user.name
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept':'*!/!*'
        }
    }).then(
        function success(rep) {
            console.log('成功',rep);
        },function error(err) {
            console.log('失败',err);
        }
    )*/
}])
angular.module('app').controller('personalCtrl',['$scope','$http',function ($scope,$http) {
    $http({
        method: "get",
        url: "/user/getInformation",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept':'*/*'
        }
    }).then(
        function success(rep) {
            console.log('成功',rep.data.all[0]);
            $scope.user = rep.data.all[0]
        },function error(err) {
            console.log('失败',err);
        }
    )
}])
angular.module('app').controller('rankCtrl',['$scope','$http',function ($scope,$http) {
    $http({
        method: "get",
        url: "/user/logins",
        params: {
            // "stunum":$scope.user.id
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept':'*/*'
        }
    }).then(
        function success(rep) {
            console.log('成功',rep);
        },function error(err) {
            console.log('失败',err);
        }
    )
}])
angular.module('app').controller('recordCtrl',['$scope','$http',function ($scope,$http) {
    $http({
        method: "post",
        url: "/user/logins",
        params: {
            // "stunum":$scope.user.id
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Accept':'*/*'
        }
    }).then(
        function success(rep) {
            console.log('成功',rep);
        },function error(err) {
            console.log('失败',err);
        }
    )
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
            $state.go('login')
        },function error(err) {
            console.log(err)
            console.log('失败')
        })
    }

    var count = 180;
    $scope.send = function () {
        $http({
            method: "get",
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
angular.module('app').controller('siteCtrl',['$state','$scope','cache',function ($state,$scope,cache) {
    $scope.logout = function () {
        cache.remove('username')
        cache.remove('type')
        console.log("退出登陆")
        $state.go('login')
    }
}])
angular.module('app').controller('updatePasswordCtrl',['$scope',function ($scope) {

}])
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
angular.module('app').directive("appHead",['$http',function ($http) {
    return{
        restrict:'A',
        replace:true,
        templateUrl:'view/template/head.html',
        link:function ($scope) {
            $scope.check=function (obj) {
                var reg = /^\d{8}$/;
                var reg2=/^[\u0391-\uFFE5]+$/;
                if(obj!=""){
                    if(!isNaN(obj)){
                        if(!reg.test(obj)){
                            console.log($scope.search)
                            alert('格式不正确，请输入八位工号！');
                            $scope.search='';
                        }
                    }else{
                        if(!reg2.test(obj)){
                            console.log($scope.search)
                            alert('格式不正确，请输入中文！');
                            $scope.search='';
                        }
                    }
                    return false;
                }
            }
            $scope.go = function () {
                $http({
                    method:'post',
                    url:'',
                    params:{
                        'serach':$scope.serach
                    },
                    header:{
                        'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8',
                        'Accept':'*/*'
                    }
                }).then(function success(rep) {
                    console.log('成功')
                },function error(err) {
                    console.log('失败',err)
                })
            }
        }
    }
}])
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
angular.module('app').directive('appScroll',[function () {
    return{
        restrict:'A',
        replace:true,
        templateUrl:'view/template/scroll.html',
        link:function () {

        }
    }
}])