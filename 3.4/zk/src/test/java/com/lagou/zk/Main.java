package com.lagou.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ZkClient zkClient=new ZkClient("101.201.150.244:2181");
        //zkClient.createPersistent("/datasource/jdbc",true);
        zkClient.writeData("/datasource/jdbc","jdbc:mysql://localhost:3306/edu_ad2?useSSL=false&charsetEncoding=utf8");
        //zkClient.createPersistent("/datasource/username","root");
        //zkClient.createPersistent("/datasource/password","root");

//        zkClient.create( "/datasource/rand","", CreateMode.PERSISTENT);
//        for (int i = 0; i < 10; i++) {
//            zkClient.writeData("/datasource/rand",i);
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        try {
//            Thread.sleep(Integer.MAX_VALUE);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
