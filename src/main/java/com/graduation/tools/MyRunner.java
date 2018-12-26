package com.graduation.tools;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Component
public class MyRunner {

        private static ExecutorService exec;
        public static int cpuNum;
        static {
            cpuNum = Runtime.getRuntime().availableProcessors();
            exec = Executors.newFixedThreadPool(cpuNum);
        }

        public void run(Runnable task) {
            exec.execute(task);
        }

        public static void stop() {
            exec.shutdown();
        }

}
