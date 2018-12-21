package com.graduation.tools;

import org.springframework.stereotype.Component;


public abstract class NeuralNetwork {

	public abstract void trainModel(Dataset trainSet,double threshold);
	public abstract void predict(Dataset testSet,String outName);
	

}
