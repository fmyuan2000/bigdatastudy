package com.lagou.zk.config;

import com.zaxxer.hikari.HikariDataSource;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HikariConfig implements ApplicationContextAware {

    private ZkClient zkClient=new ZkClient("101.201.150.244:2181");

    @Bean
    public HikariDataSource hikariDataSource()
    {

        Object username = zkClient.readData("/datasource/username",true);

        Object password = zkClient.readData("/datasource/password",true);

        Object jdbc = zkClient.readData("/datasource/jdbc", true);

        com.zaxxer.hikari.HikariConfig hikariConfig = new com.zaxxer.hikari.HikariConfig();
        hikariConfig.setJdbcUrl(jdbc.toString());
        hikariConfig.setUsername(username.toString());
        hikariConfig.setPassword(password.toString());
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        zkClient.subscribeDataChanges("/datasource", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("changed："+s);
//                DefaultListableBeanFactory defaultListableBeanFactory=(DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//
//                defaultListableBeanFactory.removeBeanDefinition("hikariDataSource");
//                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(HikariDataSource.class);
//                defaultListableBeanFactory.registerBeanDefinition("hikariDataSource",beanDefinitionBuilder.getBeanDefinition());
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });
    }
}
