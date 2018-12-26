package com.graduation.service;

import com.graduation.bean.Index;
import com.graduation.bean.User;
import com.graduation.dao.IndexDao;
import com.graduation.tools.BPNetwork;
import com.graduation.tools.ConcurenceRunner;
import com.graduation.tools.MyRunner;
import com.graduation.tools.NeuralLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.concurrent.CountDownLatch;

@Service
@CacheConfig(cacheNames="index",cacheManager = "indexRedisCacheManager")
public class IndexService {

    @Autowired
    private IndexDao indexsystemDao;

    @Qualifier("indexRedisCacheManager")
    @Autowired
    CacheManager indexRedisCacheManager;

    @Autowired
    MyRunner myRunner;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames="index",allEntries=true)
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
                    List list=(List)weight.get(0);
                    NeuralLayer layer = (NeuralLayer)list.get(0);
                    double[][] weights = layer.getWeights();
                    final double[] outputs = layer.getOutputs();
                    final double[] theta = layer.getBiases();
                    double[] lastOutputs=new double[20];

                    lastOutputs[0]= index.getQ1();
                    lastOutputs[1]= index.getQ2();
                    lastOutputs[2]= index.getQ3();
                    lastOutputs[3]= index.getQ4();
                    lastOutputs[4]= index.getQ5();
                    lastOutputs[5]= index.getQ6();
                    lastOutputs[6]= index.getQ7();
                    lastOutputs[7]= index.getQ8();
                    lastOutputs[8]= index.getQ9();
                    lastOutputs[9]= index.getQ10();
                    lastOutputs[10]= index.getQ11();
                    lastOutputs[11]= index.getQ12();
                    lastOutputs[12]= index.getQ13();
                    lastOutputs[13]= index.getQ14();
                    lastOutputs[14]= index.getQ15();
                    lastOutputs[15]= index.getQ16();
                    lastOutputs[16]= index.getQ17();
                    lastOutputs[17]= index.getQ18();
                    lastOutputs[18]= index.getQ19();
                    lastOutputs[19]= index.getQ20();
                    double[] outPut = getOutPut(outputs, theta, weights, lastOutputs);

                    List list1=(List)weight.get(1);
                    NeuralLayer layer1 = (NeuralLayer)list1.get(0);
                    double[][] weights1 = layer1.getWeights();
                    final double[] outputs1 = layer1.getOutputs();
                    final double[] theta1 = layer1.getBiases();
                    double[] outPut1 = getOutPut(outputs1, theta1, weights1, outPut);
                    System.out.println(outPut1.length);
                    for(int i=0 ; i<outPut1.length ; i++){
                        System.out.println(outPut1[i]);
                    }
                    index.setTotal(outPut1[0]);
                    indexsystemDao.addSquestion(index);
                    return "success";
                }
            }else if(user.getType().equals("老师")){
                Map map = (Map)app.getAttribute("s");
                if(map!=null){
                    //获取权重集合
                    List weight =(List) map.get("weight");
                    List list=(List)weight.get(0);
                    NeuralLayer layer = (NeuralLayer)list.get(0);
                    double[][] weights = layer.getWeights();
                    final double[] outputs = layer.getOutputs();
                    final double[] theta = layer.getBiases();
                    double[] lastOutputs=new double[20];

                    lastOutputs[0]= index.getQ1();
                    lastOutputs[1]= index.getQ2();
                    lastOutputs[2]= index.getQ3();
                    lastOutputs[3]= index.getQ4();
                    lastOutputs[4]= index.getQ5();
                    lastOutputs[5]= index.getQ6();
                    lastOutputs[6]= index.getQ7();
                    lastOutputs[7]= index.getQ8();
                    lastOutputs[8]= index.getQ9();
                    lastOutputs[9]= index.getQ10();
                    lastOutputs[10]= index.getQ11();
                    lastOutputs[11]= index.getQ12();
                    lastOutputs[12]= index.getQ13();
                    lastOutputs[13]= index.getQ14();
                    double[] outPut = getOutPut(outputs, theta, weights, lastOutputs);

                    List list1=(List)weight.get(1);
                    NeuralLayer layer1 = (NeuralLayer)list1.get(0);
                    double[][] weights1 = layer1.getWeights();
                    final double[] outputs1 = layer1.getOutputs();
                    final double[] theta1 = layer1.getBiases();
                    double[] outPut1 = getOutPut(outputs1, theta1, weights1, outPut);
                    System.out.println(outPut1.length);
                    for(int i=0 ; i<outPut1.length ; i++){
                        System.out.println(outPut1[i]);
                    }
                    index.setTotal(outPut1[0]);
                    indexsystemDao.addTquestion(index);
                    return "success";
                }
            }
        }
        return "error";
    }

    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "#user.id+#user.type+#user.name")
    public CopyOnWriteArrayList<Index> getMyIndex(User user,HttpServletRequest request) {
        //User user = (User)request.getSession().getAttribute("user");
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
    @Cacheable(key = "#user.id+#user.name")
    public CopyOnWriteArrayList<Index> getOtherIndex(User user,HttpServletRequest request) {
   //     User user = (User)request.getSession().getAttribute("user");
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
    //@Cacheable(key="#all")
    public ConcurrentHashMap<String,List<Index>> getAllIndex(String all) {

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




    private abstract class Task implements Runnable {
        int start, end;

        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            process(start, end);
        }

        public abstract void process(int start, int end);

    }

    private double[] getOutPut(double[] outputs,double[] theta,double[][]weights,double[] lastOutputs){
        int cpuNum = ConcurenceRunner.cpuNum;
        cpuNum = cpuNum < outputs.length ? cpuNum : 1;// 比cpu的个数小一个时，只用一个线程
        final CountDownLatch gate = new CountDownLatch(cpuNum);
        int fregLength = outputs.length / cpuNum;
        for (int cpu = 0; cpu < cpuNum; cpu++) {
            int start = cpu * fregLength;
            int tmp = (cpu + 1) * fregLength;
            int end = tmp <= outputs.length ? tmp : outputs.length;
            System.out.println(start);
            System.out.println(end);
            Task task =new Task(start, end) {

                @Override
                public void process(int start, int end) {
                    for (int j = start; j < end; j++) {
                        double tmp = 0;

                        for (int i = 0; i < weights.length; i++) {
                            tmp += weights[i][j] * lastOutputs[i];
                        }
                        System.out.println(tmp);
                        outputs[j] = 1 / (1 + Math.pow(Math.E, -(tmp + theta[j])));

                    }
                    gate.countDown();
                }
            };
            myRunner.run(task);
        }

        try {
            gate.await();// 等待所有线程跑完一轮数据
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return outputs;
    }

}
