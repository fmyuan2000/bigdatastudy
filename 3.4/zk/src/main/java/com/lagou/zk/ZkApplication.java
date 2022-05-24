package com.lagou.zk;

import com.lagou.zk.entity.AdShowTj;
import com.lagou.zk.service.AdShowTjService;
import com.zaxxer.hikari.HikariDataSource;
import org.I0Itec.zkclient.ContentWatcher;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class ZkApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ZkApplication.class, args);

        for (int i = 0; i < 200; i++) {
            AdShowTjService adShowTjService = applicationContext.getBean(AdShowTjService.class);
            AdShowTj tj = new AdShowTj();
            tj.setFid(2222);
            adShowTjService.save(tj);
            System.out.println(i);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
