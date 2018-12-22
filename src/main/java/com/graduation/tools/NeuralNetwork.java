package com.graduation.tools;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public abstract class NeuralNetwork {

	public abstract ConcurrentHashMap<String, List> trainModel(Dataset trainSet, double threshold);
	public abstract List<Double> predict(Dataset testSet, String outName);
	

}
