package com.graduation.tools;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util<T> {

	public static double[] string2doubles(String input) {
		String[] data = input.split(",");
		double[] array = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			array[i] = Double.parseDouble(data[i]);
		}
		return array;
	}
	public static int[] string2ints(String input) {
		String[] data = input.split(",");
		int[] array = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			array[i] = Integer.parseInt(data[i].trim());
		}
		return array;
	}
	

	
	public  static  String  array2string(double[] biases){
		String s = Arrays.toString(biases);
		return s.substring(1, s.length() - 1);		
	}
	
	public  static  String  array2string(int[] array){
		String s = Arrays.toString(array);
		return s.substring(1, s.length() - 1);		
	}
	
	public static int binaryArray2int(double[] array){
		int[] d = new int[array.length];
		for(int i=0;i<d.length;i++){
			if(array[i]>=0.500000001)
				d[i] = 1;
			else d[i]= 0;
		}
		String s = Arrays.toString(d);
		String binary = s.substring(1,s.length()-1).replace(", ", "");
		int data = Integer.parseInt(binary, 2);
		return data;
				
	}
	/**
	 * 读词列表
	 * @param fileName
	 * @return
	 */
	public static List<String> readAsList(String fileName){
        BufferedReader in=null;
        FileReader fileReader=null;
		try {
            fileReader = new FileReader(fileName);
            in = new BufferedReader(fileReader);
			String line= in.readLine() ;
			List<String> words = new ArrayList<String>();
			while(line!=null){
				words.add(line.trim());
				line= in.readLine() ;
			}

			return words;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static List<String> line2list(String line) {
		String[] lables = line.split(",");
		for (int i = 0; i < lables.length; i++)
			lables[i] = lables[i].trim();
		return Arrays.asList(lables);
	}
	
	public static String list2line(List<String> lables) {
	    String line = Arrays.toString(lables.toArray());
		return line.substring(1, line.length() - 1);
	}
	public static void main(String[] args) {
		int a = binaryArray2int(new double[]{0.008267535946453602, 8.4959559745344E-4, 0.0010722832444512126, 9.457106847049471E-4});
		System.out.println(a);
	}
}
