package com.im.user.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version: v1.８.0
 * @author: zmz
 * @date: 2022/6/17 00:57
 */
public class CommonThreadPool {

    private static  ExecutorService mqRedundantUpdate = Executors.newFixedThreadPool(10);


    public static ExecutorService mqRedundantUpdate(){
        return mqRedundantUpdate;
    }
}
