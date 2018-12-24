package com.graduation.tools;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyModelTrain {

	public void testLogFunc(String fileName,String split,Integer lableindex,Integer inputNumber){
		//获取训练数据的路径
		//fileName = "data/func_train.txt";
		//创建一个Dataset对象，其里面有一个内部类Record来暂时获取训练数据
		//最终训练数据保存在集合中，集合中是一个个Recore
		//Recore 的attr为每一行数据的前两个数据，  lable为一行的最后一个数据
		Dataset dataset = Dataset.load(fileName, split, lableindex,false);
		
		//创建一个新的BPNetwork对象
		BPNetwork bp = new BPNetwork(new int[]{inputNumber,1,1},false);
		
		//调用BPNetwork的训练方法
		bp.trainModel(dataset,1);
		dataset= null;
//		String testName = "data/func_test.txt";
//		Dataset testset = Dataset.load(testName, "\t",2,false);
//		String outName = "data/func_test.predict";
//		bp.predict(testset,outName);
	}
	public ConcurrentHashMap<String, List> train(String testName,Integer lableindex){
        //获取训练数据的路径
        //创建一个Dataset对象，其里面有一个内部类Record来暂时获取训练数据
        //最终训练数据保存在集合中，集合中是一个个Recore
        //Recore 的attr为每一行数据的前两个数据，  lable为一行的最后一个数据
        Dataset dataset = Dataset.load(testName, "-", lableindex,false);

        //创建一个新的BPNetwork对象
        BPNetwork bp = new BPNetwork(new int[]{lableindex,1,1},false);

        //调用BPNetwork的训练方法
        ConcurrentHashMap<String, List> map = bp.trainModel(dataset, 1);
        return map;
  //      dataset= null;
//        Dataset testset = Dataset.load(outname, "-",3,false);
//        List<Double> list=bp.predict(testset,outname);
//        return list;
    }

}
