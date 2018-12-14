package com.graduation.tools;

import java.util.Arrays;

public class Test {

	  public static void main(String[] args){
	        //初始化神经网络的基本配置
	        //第一个参数是一个整型数组，表示神经网络的层数和每层节点数，比如{3,10,10,10,10,2}表示输入层是3个节点，输出层是2个节点，中间有4层隐含层，每层10个节点
	        //第二个参数是学习步长，第三个参数是动量系数
		  TestShenJingWangLuo bp = new TestShenJingWangLuo(new int[]{2,10,2}, 0.15, 0.8);
	 
	        //设置样本数据，对应上面的4个二维坐标数据
	        double[][] data = new double[][]{{0.843,0.792,0.022,0.216,0.831,0.103,0.799,0.949,0.646,0.207},
	        	{0.414,0.056,0.592,0.279,0.226,0.999,0.281,0.976,0.405,0.167},
	        	{0.844,0.91,0.453,0.806,0.655,0.674,0.046,0.401,0.413,0.584},
	        	{0.791,0.365,0.298,0.825,0.981,0.016,0.296,0.278,0.713,0.083},
	        	{0.375,0.529,0.624,0.592,0.248,0.099,0.381,0.159,0.324,0.452},
	        	{0.956,0.754,0.643,0.978,0.521,0.096,0.294,0.152,0.596,0.681}};
	        //设置目标数据，对应4个坐标数据的分类
	        //double[][] target = new double[][]{{1},{2},{2},{3}};
	        	double[][] data1 = new double[][]{{1,2},{2,2},{1,1},{2,1}};
	            //设置目标数据，对应4个坐标数据的分类
	            double[][] target1 = new double[][]{{1,0},{0,1},{0,1},{1,0}};
	            double[][] target = new double[][] {{0.5},{0.5},{0.5},{0.5},{0.5},{0.5}};
	        //迭代训练5000次
	        for(int n=0;n<5000;n++)
	            for(int i=0;i<data.length;i++)
	                bp.train(data1[i], target1[i]);
	        
	        
	        //根据训练结果来检验样本数据
	        for(int j=0;j<data.length;j++){
	            double[] result = bp.computeOut(data[j]);
	            System.out.println(Arrays.toString(data[j])+":"+Arrays.toString(result));
	        }
	 
	        //根据训练结果来预测一条新数据的分类
	        double[] x = new double[]{0.843,0.792,0.022,0.216,0.831,0.103,0.799,0.949,0.646,0.207};
	        double[] result = bp.computeOut(x);
	        System.out.println(Arrays.toString(x)+":"+Arrays.toString(result));
	    }

}