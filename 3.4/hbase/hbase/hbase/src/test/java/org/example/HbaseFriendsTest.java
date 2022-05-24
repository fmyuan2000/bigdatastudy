package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class HbaseFriendsTest {
    Configuration conf = null;
    Connection conn = null;
    HBaseAdmin admin = null;

    @Before
    public void init() throws IOException {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "linux121,linux122");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conn = ConnectionFactory.createConnection(conf);
    }

    public void destroy() {
        if (admin != null) {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void createTable() throws IOException {
        admin = (HBaseAdmin) conn.getAdmin();
        //创建表描述器
        HTableDescriptor teacher = new
        HTableDescriptor(TableName.valueOf("friend"));
        //设置列族描述器
        teacher.addFamily(new HColumnDescriptor("friends"));
        //执行创建操作
        admin.createTable(teacher);
        System.out.println("teacher表创建成功！！");
    }

    //插入一条数据
    @Test
    public void putData() throws IOException {
//获取一个表对象
        Table t = conn.getTable(TableName.valueOf("friend"));
//设定rowkey
        Put put = new Put(Bytes.toBytes(UUID.randomUUID().toString()));
//列族，列，value
        put.addColumn(Bytes.toBytes("friends"), Bytes.toBytes(UUID.randomUUID().toString()),
                Bytes.toBytes(UUID.randomUUID().toString()));
//执行插入
        t.put(put);
// t.put();//可以传入list批量插入数据
//关闭table对象
        t.close();
        System.out.println("插入成功！！");
    }

}
