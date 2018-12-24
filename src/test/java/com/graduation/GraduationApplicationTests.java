package com.graduation;

import com.graduation.dao.CourseDao;
import com.graduation.service.UserService;
import com.graduation.tools.MyModelTrain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GraduationApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CourseDao courseDao;

    @Autowired
    MyModelTrain myModelTrain;
	@Test
	public void contextLoads() {
        //生成随机验证码
        Integer i = (int) ((Math.random() * 9 + 1) * 100000);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("517868436@qq.com");
        message.setSubject("注册验证码");
        message.setText(i.toString());
        message.setTo("517868436@qq.com");
        System.out.println(javaMailSender);
        javaMailSender.send(message);

        String encode = passwordEncoder.encode("111");
        System.out.println("第一次对111加密"+encode);
        String encode1=passwordEncoder.encode("111");
        System.out.println("第二次对111加密"+encode1);

        boolean matches1 = passwordEncoder.matches("111", encode);
        System.out.println("比较没加密的跟第一次加密之后得到的密文"+matches1);
        boolean matches2 = passwordEncoder.matches("222", encode1);
        System.out.println("比较没加密的跟第二次加密之后得到的密文"+matches2);
//        IPage<Course> id = courseDao.selectPage(
//                new Page<Course>(2, 3),
//                new QueryWrapper<Course>()
//                        .eq("id", 2)
//        );
//
//        System.out.println(id.getCurrent());
//        System.out.println(id.getPages());
//        System.out.println(id.getRecords());
//        System.out.println(id.getSize());
//        System.out.println(id.getTotal());
    }

    @Test
    public void testJava8(){
        testJava("qqqqq",(w) -> w.toUpperCase());
    }

    public void testJava(String s,Function<String,String> f){
        System.out.println(f.apply(s));
    }

    @Test
    public void testBp(){
        BufferedWriter bufferedWriter=null;
        File newFile1= new File("E:/bp/ttt1.txt");

        FileWriter fileWriter=null;
        try {
//            if(!newFile1.getParentFile().exists()){
//                newFile1.getParentFile().mkdirs();
//            }
//
//            if(!newFile2.exists()){
//                newFile2.createNewFile();
//            }
//            if(!newFile1.exists()){
//                newFile1.createNewFile();
//            }
//            fileWriter = new FileWriter(newFile1);
//            bufferedWriter=new BufferedWriter(fileWriter);
//            bufferedWriter.write("0.2,");
//            bufferedWriter.write("0.4,");
//            bufferedWriter.write("0.6,");
//            bufferedWriter.write("0.5\n");
//            bufferedWriter.write("0.3,");
//            bufferedWriter.write("0.1,");
//            bufferedWriter.write("0.6,");
//            bufferedWriter.write("0.5\n");
//            bufferedWriter.write("0.4,");
//            bufferedWriter.write("0.2,");
//            bufferedWriter.write("0.5,");
//            bufferedWriter.write("0.6\n");
//            bufferedWriter.write("0.8,");
//            bufferedWriter.write("0.2,");
//            bufferedWriter.write("0.3,");
//            bufferedWriter.write("0.6\n");
//            fileWriter=new FileWriter(newFile2);
//            bufferedWriter=new BufferedWriter(fileWriter);
//            bufferedWriter.write("0.4-");
//            bufferedWriter.write("0.4-");
//            bufferedWriter.write("0.4\n");
            ConcurrentHashMap<String, List> map = myModelTrain.train(newFile1.getPath(), 20);
            List<List<Double>> weight = map.get("weight");
            for(List list:weight){
                list.stream().forEach(System.out::println);
            }
            List biase = map.get("biase");
            biase.stream().forEach(System.out::println);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            try {
////                fileWriter.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
        }

    }

}
