package com.graduation.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import com.graduation.tools.Dataset.Record;
public class BPNetwork extends NeuralNetwork {
    private static final double LEARN_RATE = 0.2;
    // 神经网络的层数,包括输入层\输出层\隐藏层
    private int layerNum;
    // 每一层神经网络
    private List<NeuralLayer> layers;
    // 每一层神经单元的个数
    private int[] layerSize;
    // 并发调度器
    ConcurenceRunner runner;

    private static AtomicBoolean stopTrain = new AtomicBoolean(false);

    private int maxLable = 1;
    // 模型在训练集上的类别
    private List<String> lables;
    // 是否对目标值进行编码,如1编码成001，2编码成010
    private boolean encodeLable;

    // 传入的参数： new int[]{2,1000,1} , false
    public BPNetwork(int[] layerSize, boolean encodeLable) {
        // 初始化神经网络的层数 这里为3层
        this.layerNum = layerSize.length;

        // 神经网络层 长度为3 也就是三层神经网络
        this.layers = new ArrayList<NeuralLayer>(layerNum);

        // 每一层神经单元的个数
        // 根据输入的layerSize可知：
        // 第一层为两个神经元 第二层为100个 第三层为1个
        this.layerSize = layerSize;
        // 是否对目标值进行编码,如1编码成001，2编码成010 false
        this.encodeLable = encodeLable;
        // 创建一个新的并发运行工具 ConcurenceRunner
        // 因为一个线程运行太慢，所以采用此工具
        runner = new ConcurenceRunner();
    }

    public BPNetwork() {
        this.layers = new ArrayList<NeuralLayer>();
        runner = new ConcurenceRunner();
    }

    /**
     * 训练模型
     */
    // 传入的threshold为1
    @Override
    public  ConcurrentHashMap<String, List> trainModel(Dataset trainSet, double threshold) {
        List<List> list=new CopyOnWriteArrayList<>();
        List<Double> biasesList=new CopyOnWriteArrayList<>();
        ConcurrentHashMap<String, List> map = new ConcurrentHashMap<>();

        // 初始化神经层
        initLayer();

        new Lisenter().start();// 开启停止监听

        // 得到计算的值与预计值之间的差值
        double precison = test(trainSet);
        // double precison = 0.1;
        int count = 1;
        while (precison < threshold && !stopTrain.get()) {
            // 训练数据
            train(trainSet);
            // 得到一次训练之后的差值
            precison = test(trainSet);
            System.out.println(count++ + "th: " + precison);
            // 打印差值
            if (precison > 0.99) {
                Iterator<NeuralLayer> it = layers.iterator();
                while(it.hasNext()) {
                    int xxx=0;
                    NeuralLayer next = it.next();
                    double[][] score2 = next.getWeights();
                    if(score2!=null) {
                        System.out.println("------------------------------");
                        List<Double> list1=new CopyOnWriteArrayList<>();
                        for (int m = 0; m < score2.length; m++) {// 控制行数

                            for (int n = 0; n < score2[m].length; n++) {// 一行中有多少个元素（即多少列）
                                System.out.println(score2[m][n] + "权重 ");
                                xxx++;
                                list1.add(score2[m][n]);
                            }
                            System.out.println("------------------------------");
                        }
                        list.add(list1);
                    }
                    System.out.println("xxxxxx====="+xxx);
                    System.out.println("输出偏置量");
                    double[] biases = next.getBiases();
                    if(biases!=null&&biases.length>0) {
                        for(double biase:biases) {
                            System.out.println(biase+"偏置");
                            biasesList.add(biase);
                        }
                    }
                    System.out.println("开始下一轮");
                }

                map.put("weight",list);
                map.put("biase",biasesList);
                return map;
            }
        }
        return map;
    }

    private void initLayer() {
        // layerSize.length：3
        for (int i = 0; i < layerSize.length; i++) {
            // 初始化每一层神经元：
            // 这里保存的神经元的下表，个数与上层神经元的个数
            NeuralLayer layer = new NeuralLayer(i, layerSize[i], i > 0 ? layerSize[i - 1] : 0);
            layers.add(layer);
        }
    }

    /**
     * 预测数据
     */
    @Override
    public List<Double> predict(Dataset testSet, String outName) {
        List<Double> list=new CopyOnWriteArrayList<>();
        PrintWriter out=null;
        try {

            out = new PrintWriter(new File(outName));
            Iterator<Record> iter = testSet.iter();
            int rightCount = 0;
            int max = maxLable;
            boolean hasLable = testSet.getLableIndex() != -1 ? true : false;
            System.out.println("hasLable:"+hasLable);
            double sum = 0.0;
            while (iter.hasNext()) {
                Record record = iter.next();
                // 设置输入层的输出值为当前记录值
                getLayer(0).setOutputs(record);
                // 更新后面各层的输出值
                updateOutput();
                double[] output = getLayer(layerNum - 1).getOutputs();
                if (hasLable) {// 有目标值则输出
                    //out.write(record.getLable() + "\t");
                    sum += Math.abs(output[0] - record.getLable());
                }
                if (!encodeLable)
                {
                    list.add(output[0]);
                    //out.write(output[0] + "\n");
                    System.out.println("预测值="+output[0] + "\n");
                }
                else {

                    int lable = Util.binaryArray2int(output);
                    if (lable > max)
                        lable = lable - (1 << (output.length - 1));
                    out.write(lable + "\n");
                    if (hasLable && isSame(output, record)) {
                        rightCount++;
                    }

                }
            }
            //out.flush();
            if (encodeLable)
                System.out.println("precision  ," + (rightCount * 1.0 / testSet.size()));
            else if (hasLable)
                System.out.println("error rate    ," + (sum / testSet.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
        return list;
    }

    /**
     * 预测数据
     */

    @SuppressWarnings("unused")
    private List<String> predict(Dataset testSet) {
        try {
            List<String> result = new ArrayList<String>();
            int max = maxLable;
            boolean hasLable = testSet.getLableIndex() != -1 ? true : false;
            Iterator<Record> iter = testSet.iter();
            int rightCount = 0;
            while (iter.hasNext()) {
                Record record = iter.next();
                // 设置输入层的输出值为当前记录值
                getLayer(0).setOutputs(record);
                // 更新后面各层的输出值
                updateOutput();
                double[] output = getLayer(layerNum - 1).getOutputs();
                int lable = Util.binaryArray2int(output);
                if (lable > max)
                    lable = lable - (1 << (output.length - 1));
                if (hasLable && isSame(output, record)) {
                    rightCount++;
                }
                result.add(lables.get(lable));
            }
            System.out.println("precision   ," + (rightCount * 1.0 / testSet.size()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 训练一个神经网络,输入层已经给出

     */
    private void train(Dataset dataSet) {
        Iterator<Record> iter = dataSet.iter();
        while (iter.hasNext()) {
            try {
                train(iter.next());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Lisenter extends Thread {

        @Override
        public void run() {
            System.out.println("输入&结束迭代");
            while (true) {
                try {
                    int a = System.in.read();
                    if (a == '&') {
                        stopTrain.compareAndSet(false, true);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Lisenter stop");
        }

    }

    /**
     * 测试准确率
     *
     *
     */
    public double test(Dataset dataSet) {
        helpCount = 0;
        // System.out.println("errors ",
        // Arrays.toString(getLayer(2).getErrors()));
        int rightCount = 0;
        double sum = 0;
        try {
            // 返回一个迭代器
            Iterator<Record> iter = dataSet.iter();
            while (iter.hasNext()) {
                // 获取每一行输入的记录
                Record record = iter.next();
                // 设置输入层的输出值为当前记录值
                // 获取数据的前两列值
                getLayer(0).setOutputs(record);
                // 更新后面各层的输出值
                updateOutput();
                // 获取最后一层网络的输出值
                double[] output = getLayer(layerNum - 1).getOutputs();
                if (!encodeLable) {
                    // 用计算得到的值减去预计想要得到的值，然后累加到sum上面
                    sum += Math.abs(output[0] - record.getLable());
                } else if (isSame(output, record)) {
                    rightCount++;
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double p;
        if (!encodeLable)
            // 用1减去累加的sum与Record的数量 即1000
            // 即用1减去1000次计算的平均差值
            p = 1 - sum / dataSet.size();
        else
            p = rightCount * 1.0 / dataSet.size();
        // 返回差值
        return p;
    }

    private int helpCount = 0;

    private boolean isSame(double[] output, Record record) {
        double[] target = record.getTarget(output.length, encodeLable);
        boolean r = true;
        for (int i = 0; i < output.length; i++) {
            if (encodeLable && Math.abs(output[i] - target[i]) > 0.5) {
                r = false;
                break;
            }
            // 不编码时，直接比较与目标值的差
            if (!encodeLable && Math.abs(output[i] - target[i]) > 0.01) {
                r = false;
                break;
            }
        }
        if (helpCount++ % 30 == -1)
            System.out.println("isSame  ,"+ lables.get((int) (record.getLable().intValue())) + " " + record.getLable() + " "
                    + Arrays.toString(output) + " " + Arrays.toString(target) + " " + r);

        return r;
    }

    /***
     * 使用一条记录训练网络
     *
     * @param record
     * @throws InterruptedException
     */
    private void train(Record record) throws InterruptedException {
        // 设置输入层的输出值为当前记录值
        getLayer(0).setOutputs(record);
        // 更新后面各层的输出值
        updateOutput();
        // 更新输出层的错误率
        updateOutputLayerErrors(record);
        // 更新每一层的错误率
        updateErrors();
        // 更新权重
        updateWeights();
        // 更新偏置
        updateBiases();

    }

    /**
     * 更新偏置
     *
     * @throws InterruptedException
     */

    private void updateBiases() throws InterruptedException {

        for (int layerIndex = 1; layerIndex < layerNum; layerIndex++) {
            NeuralLayer layer = getLayer(layerIndex);
            final double[] biases = layer.getBiases();
            final double[] errors = layer.getErrors();
            int cpuNum = ConcurenceRunner.cpuNum;
            cpuNum = cpuNum < errors.length ? cpuNum : 1;// 比cpu的个数小时，只用一个线程
            final CountDownLatch gate = new CountDownLatch(cpuNum);
            int fregLength = errors.length / cpuNum;
            for (int cpu = 0; cpu < cpuNum; cpu++) {
                int start = cpu * fregLength;
                int tmp = (cpu + 1) * fregLength;
                int end = tmp <= errors.length ? tmp : errors.length;
                Task task = new Task(start, end) {
                    @Override
                    public void process(int start, int end) {
                        for (int j = start; j < end; j++) {
                            biases[j] += LEARN_RATE * errors[j];
                        }
                        gate.countDown();
                    }
                };
                runner.run(task);
            }
            gate.await();
        }

    }

    /**
     * 利用记录的类标，反馈更新输出值得错误率
     *
     */
    private void updateOutputLayerErrors(Record record) throws InterruptedException {
        NeuralLayer outputLayer = getLayer(layerNum - 1);

        // 训练网络时使用类标来计算误差
        double[] tar = record.getTarget(layerSize[layerNum - 1], encodeLable);
        final double[] target = tar;
        final double[] errors = outputLayer.getErrors();
        final double[] outputs = outputLayer.getOutputs();
        int cpuNum = ConcurenceRunner.cpuNum;
        cpuNum = cpuNum < outputs.length ? cpuNum : 1;// 比cpu的个数小时，只用一个线程
        final CountDownLatch gate = new CountDownLatch(cpuNum);
        int fregLength = outputs.length / cpuNum;
        for (int cpu = 0; cpu < cpuNum; cpu++) {
            int start = cpu * fregLength;
            int tmp = (cpu + 1) * fregLength;
            int end = tmp <= outputs.length ? tmp : outputs.length;
            Task task = new Task(start, end) {

                @Override
                public void process(int start, int end) {
                    for (int j = start; j < end; j++) {
                        errors[j] = outputs[j] * (1 - outputs[j]) * (target[j] - outputs[j]);

                    }
                    gate.countDown();
                }
            };
            runner.run(task);
        }

        gate.await();// 等待各个线程跑完

    }

    /**
     * 更新权重
     *
     * @throws InterruptedException
     */
    private void updateWeights() throws InterruptedException {
        for (int layerIndex = 1; layerIndex < layerNum; layerIndex++) {
            NeuralLayer layer = getLayer(layerIndex);
            NeuralLayer lastLayer = getLayer(layerIndex - 1);
            final double[] lastOutputs = lastLayer.getOutputs();
            final double[][] weights = layer.getWeights();
            final double[] errors = layer.getErrors();
            // 并发运行
            int cpuNum = ConcurenceRunner.cpuNum;
            cpuNum = cpuNum < errors.length ? cpuNum : 1;// 比cpu的个数小一个时，只用一个线程
            final CountDownLatch gate = new CountDownLatch(cpuNum);
            int fregLength = errors.length / cpuNum;
            for (int cpu = 0; cpu < cpuNum; cpu++) {
                int start = cpu * fregLength;
                int tmp = (cpu + 1) * fregLength;
                int end = tmp <= errors.length ? tmp : errors.length;
                Task task = new Task(start, end) {
                    @Override
                    public void process(int start, int end) {
                        for (int j = start; j < end; j++) {
                            for (int i = 0; i < weights.length; i++) {
                                weights[i][j] += (LEARN_RATE * errors[j] * lastOutputs[i]);
                            }
                        }
                        gate.countDown();
                    }

                };
                runner.run(task);
            }
            gate.await();

        }
    }

    /**
     * 更新错误率
     *
     * @throws InterruptedException
     */
    private void updateErrors() throws InterruptedException {
        for (int i = layerNum - 2; i > 0; i--) {// 2014年7月9日19:52:35
            // 将i>=0修改为i>0，未测试
            NeuralLayer layer = getLayer(i);
            NeuralLayer nextLayer = getLayer(i + 1);
            final double[] nextErrors = nextLayer.getErrors();
            final double[] errors = layer.getErrors();
            final double[] outputs = layer.getOutputs();
            final double[][] weights = nextLayer.getWeights();
            // System.out.println("weights:"+weights.length+" next "+weights[0].length);
            // 并发运行
            int cpuNum = ConcurenceRunner.cpuNum;
            cpuNum = cpuNum < outputs.length ? cpuNum : 1;// 比cpu的个数小一个时，只用一个线程
            final CountDownLatch gate = new CountDownLatch(cpuNum);
            int fregLength = outputs.length / cpuNum;
            for (int cpu = 0; cpu < cpuNum; cpu++) {
                int start = cpu * fregLength;
                int tmp = (cpu + 1) * fregLength;
                int end = tmp <= outputs.length ? tmp : outputs.length;
                // System.out.println("start:"+start+" end:"+end+"
                // outputs.length"+outputs.length);
                Task task = new Task(start, end) {
                    @Override
                    public void process(int start, int end) {
                        for (int j = start; j < end; j++) {
                            double tmp = 0.0;
                            for (int k = 0; k < nextErrors.length; k++) {
                                tmp += nextErrors[k] * weights[j][k];
                            }
                            errors[j] = outputs[j] * (1 - outputs[j]) * tmp;

                        }
                        gate.countDown();
                    }
                };
                runner.run(task);
            }
            gate.await();

        }
    }

    /**
     * 更新输出值.同时对输出层的输出进行01规整，方便输出编码
     *
     * @throws InterruptedException
     */
    private void updateOutput() throws InterruptedException {
        // layerNum 神经网络层数 3
        for (int k = 1; k < layerNum; k++) {
            NeuralLayer layer = getLayer(k);
            // 获取上层网络
            NeuralLayer lastLayer = getLayer(k - 1);
            // 获取上层网络的输出值
            final double[] lastOutputs = lastLayer.getOutputs();
            // 获取本层网络的输出值
            final double[] outputs = layer.getOutputs();
            // 获取本层网络的权重
            final double[][] weights = layer.getWeights();
            // 获取本层网络的偏置
            final double[] theta = layer.getBiases();

            // 并发运行
            int cpuNum = ConcurenceRunner.cpuNum;
            cpuNum = cpuNum < outputs.length ? cpuNum : 1;// 比cpu的个数小一个时，只用一个线程
            final CountDownLatch gate = new CountDownLatch(cpuNum);
            int fregLength = outputs.length / cpuNum;
            for (int cpu = 0; cpu < cpuNum; cpu++) {
                int start = cpu * fregLength;
                int tmp = (cpu + 1) * fregLength;
                int end = tmp <= outputs.length ? tmp : outputs.length;
                Task task = new Task(start, end) {

                    @Override
                    public void process(int start, int end) {
                        for (int j = start; j < end; j++) {
                            double tmp = 0;

                            for (int i = 0; i < weights.length; i++) {
                                tmp += weights[i][j] * lastOutputs[i];
                            }

                            outputs[j] = 1 / (1 + Math.pow(Math.E, -(tmp + theta[j])));

                        }
                        gate.countDown();
                    }
                };
                runner.run(task);
            }

            gate.await();// 等待所有线程跑完一轮数据

        }
    }

    /**
     * 获取第index层的神经层，index从0开始
     *
     * @param index
     * @return
     */
    private NeuralLayer getLayer(int index) {
        return layers.get(index);
    }

    public static BPNetwork loadModel(String fileName) {
        FileReader fileReader=null;
        BufferedReader bufferedReader = null ;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            return loadModel(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    private static BPNetwork loadModel(BufferedReader in) {
        try {
            BPNetwork bp = new BPNetwork();
            String line = in.readLine();// 第一行表示有多少层
            bp.layerNum = Integer.parseInt(line);
            bp.layerSize = Util.string2ints(in.readLine());
            bp.lables = Util.line2list(in.readLine());
            bp.maxLable = bp.lables.size() - 1;
            for (int i = 0; i < bp.layerNum; i++) {// 初始化每一层
                NeuralLayer layer = new NeuralLayer();
                layer.setSize(bp.layerSize[i]);// 设置神经单元的个数
                layer.setLayerIndex(i);// 设置层号
                line = in.readLine();
                if (!line.equals("null"))// 读偏置
                    layer.setBiases(Util.string2doubles(line));
                line = in.readLine();
                if (!line.equals("null")) {// 读权重
                    int[] weightSize = Util.string2ints(line);// 权重有多少行和列
                    double[][] weights = new double[weightSize[0]][weightSize[1]];
                    for (int j = 0; j < weightSize[0]; j++) {
                        weights[j] = Util.string2doubles(in.readLine());
                    }
                    layer.setWeights(weights);
                }
                layer.setErrors(new double[layer.getSize()]);
                layer.setOutputs(new double[layer.getSize()]);
                bp.layers.add(layer);
            }
            in.close();
            return bp;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存模型,只需要保存偏置和权重即可
     *
     * @param fileName
     */
    public void saveModel(String fileName) {
        PrintWriter out =null;
        try {
            out = new PrintWriter(fileName);
            out.write(layerNum + "\n");// 第一行是层数
            out.write(Util.array2string(layerSize) + "\n");
            out.write(Util.list2line(lables) + "\n");// 第三层是类标
            for (int i = 0; i < layerNum; i++) {// 保存每一层
                NeuralLayer layer = getLayer(i);
                double[] biases = layer.getBiases();
                if (biases == null)
                    out.write("null\n");
                else {
                    out.write(Util.array2string(biases) + "\n");
                }
                double[][] weights = layer.getWeights();
                if (weights == null)
                    out.write("null\n");
                else {
                    out.write(weights.length + ", " + weights[0].length + "\n");
                    for (int k = 0; k < weights.length; k++) {
                        out.write(Util.array2string(weights[k]) + "\n");
                    }
                }

            }
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    /**
     * 设置类标
     *
     * @param fileName
     */
    public void setLables(String fileName) {
        BufferedReader reader=null;
        FileReader fileReader=null;
        try {
            fileReader = new FileReader(fileName);
            lables = new ArrayList<String>();
            reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                lables.add(line.trim());
                line = reader.readLine();
            }

            maxLable = lables.size();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
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

}