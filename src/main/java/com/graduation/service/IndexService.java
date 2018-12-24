package com.graduation.service;

import com.graduation.bean.Index;
import com.graduation.bean.User;
import com.graduation.dao.IndexDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class IndexService {

    @Autowired
    private IndexDao indexsystemDao;

    @Transactional(rollbackFor = Exception.class)
    public String addAllIndex(Index index, HttpServletRequest request) {
        ServletContext app = request.getServletContext();
        index.setTimes(LocalDate.now().toString());
        User user = (User)request.getSession().getAttribute("user");
//        UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String usernumber1 = userDetail.getUsername();
//        System.out.println(usernumber1);
        String usernumber = user.getStunum();
        if(usernumber!=null && user!=null){
            index.setPnumber(usernumber);
            if(user.getType().equals("学生")){
                Map map = (Map)app.getAttribute("s");
                if(map!=null){
                    //获取权重集合
                    List weight =(List) map.get("weight");
                    //获取输入层到隐含层的权重
                    List<Double> list1 = (List<Double>)weight.get(0);
                    //获取隐含层到输出层的权重
                    List<Double> list2 = (List<Double>)weight.get(1);
                    //计算temp
                    Double temp1=0.0;
                    Double temp2=0.0;
                    double d1 = list1.get(0) * index.getQ1();
                    double d2 = list1.get(1) * index.getQ2();
                    double d3 = list1.get(2) * index.getQ3();
                    double d4 = list1.get(3) * index.getQ4();
                    double d5 = list1.get(4) * index.getQ5();
                    double d6 = list1.get(5) * index.getQ6();
                    double d7 = list1.get(6) * index.getQ7();
                    double d8 = list1.get(7) * index.getQ8();
                    double d9 = list1.get(8) * index.getQ9();
                    double d10 = list1.get(9) * index.getQ10();
                    double d11 = list1.get(10) * index.getQ11();
                    double d12= list1.get(11) * index.getQ12();
                    double d13= list1.get(12) * index.getQ13();
                    double d14= list1.get(13) * index.getQ14();
                    double d15= list1.get(14) * index.getQ15();
                    double d16= list1.get(15) * index.getQ16();
                    double d17= list1.get(16) * index.getQ17();
                    double d18= list1.get(17) * index.getQ18();
                    double d19= list1.get(18) * index.getQ19();
                    double d20= list1.get(19) * index.getQ20();
                    temp1=d1+d2+d3+d4+d5+d6+d7+d8+d9+d10+d11+d12+d13+d14+d15+d16+d17+d18+d19+d20;
                    //获取偏置
                    List<Double> biase =(List) map.get("biase");
                    double hideLayer = 1 / (1 + Math.pow(Math.E, -(temp1 + biase.get(0))));
                    temp2=hideLayer*list2.get(0);
                    System.out.println(temp2);
                    double output = 1 / (1 + Math.pow(Math.E, -(temp2 + biase.get(1))));
                    index.setTotal(output);
                    System.out.println(output);
                    System.out.println(index);
                    indexsystemDao.addSquestion(index);

                    return "success";
                }
            }else if(user.getType().equals("老师")){
                Map map = (Map)app.getAttribute("t");
                if(map!=null){
                    //获取权重集合
                    List weight =(List) map.get("weight");
                    //获取输入层到隐含层的权重
                    List<Double> list1 = (List<Double>)weight.get(0);
                    //获取隐含层到输出层的权重
                    List<Double> list2 = (List<Double>)weight.get(1);
                    //计算temp
                    Double temp1=0.0;
                    Double temp2=0.0;
                    double d1 = list1.get(0) * index.getQ1();
                    double d2 = list1.get(1) * index.getQ2();
                    double d3 = list1.get(2) * index.getQ3();
                    double d4 = list1.get(3) * index.getQ4();
                    double d5 = list1.get(4) * index.getQ5();
                    double d6 = list1.get(5) * index.getQ6();
                    double d7 = list1.get(6) * index.getQ7();
                    double d8 = list1.get(7) * index.getQ8();
                    double d9 = list1.get(8) * index.getQ9();
                    double d10 = list1.get(9) * index.getQ10();
                    double d11 = list1.get(10) * index.getQ11();
                    double d12= list1.get(11) * index.getQ12();
                    double d13= list1.get(12) * index.getQ13();
                    double d14= list1.get(13) * index.getQ14();
                    temp1=d1+d2+d3+d4+d5+d6+d7+d8+d9+d10+d11+d12+d13+d14;
                    //获取偏置
                    List<Double> biase =(List) map.get("biase");
                    double hideLayer = 1 / (1 + Math.pow(Math.E, -(temp1 + biase.get(0))));
                    temp2=hideLayer*list2.get(0);
                    double output = 1 / (1 + Math.pow(Math.E, -(temp2 + biase.get(1))));
                    index.setTotal(output);
                    indexsystemDao.addTquestion(index);
                    return "success";
                }
            }
        }
        return "error";
    }

    @Transactional(rollbackFor = Exception.class)
    public CopyOnWriteArrayList<Index> getMyIndex(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        //从springsecurity中获取登陆用户的信息
        //String usernumber1 = SecurityContextHolder.getContext().getAuthentication().getName();
        //获取登陆的用户账号
        ;
        //System.out.println(usernumber1);
        String usernumber = user.getStunum();

        //获取用户的权限，本系统则为用户的角色，即老师还是学生
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            System.out.println(role);
        }
        if(usernumber!=null){
                if(user.getType().equals("学生")){
                    CopyOnWriteArrayList<Index> myIndex = indexsystemDao.getSquestionMyIndex(usernumber);
                    return myIndex;
                }else if(user.getType().equals("老师")){
                    CopyOnWriteArrayList<Index> myIndex = indexsystemDao.getTquestionMyIndex(usernumber);
                    return myIndex;
                }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public CopyOnWriteArrayList<Index> getOtherIndex(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
//        String usernumber1 = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println(usernumber1);
        String usernumber=user.getStunum();
        if(usernumber!=null){
                if(user.getType().equals("学生")){
                    CopyOnWriteArrayList<Index> myIndex = indexsystemDao.getSquestionOtherIndex(usernumber);
                    return myIndex;
                }else if(user.getType().equals("老师")){
                    CopyOnWriteArrayList<Index> myIndex = indexsystemDao.getTquestionOtherIndex(usernumber);
                    return myIndex;
                }
            return null;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ConcurrentHashMap<String,List<Index>> getAllIndex() {

        String usernumber = SecurityContextHolder.getContext().getAuthentication().getName();
        ConcurrentHashMap<String,List<Index>> map=new ConcurrentHashMap<>();
        if(usernumber!=null){
            CopyOnWriteArrayList<Index> mySIndex = indexsystemDao.getAllSquestionIndex();
            CopyOnWriteArrayList<Index> myTIndex = indexsystemDao.getAllTquestionIndex();
            map.put("s",mySIndex);
            map.put("t",myTIndex);
            return map;
        }
        return null;
    }
}
