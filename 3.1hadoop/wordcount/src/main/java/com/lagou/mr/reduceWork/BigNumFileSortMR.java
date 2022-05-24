package com.lagou.mr.reduceWork;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 *
 */
public class BigNumFileSortMR {
    private static int countnum = 0;
    private static int temNum = 0;
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
//		conf.addResource("hdfs-site.xml");//使用配置文件
//		System.setProperty("HADOOP_USER_NAME", "hadoop");//使用集群
        FileSystem fs = FileSystem.get(conf);//默认使用本地

        Job job = Job.getInstance(conf);
        job.setJarByClass(BigNumFileSortMR.class);
        job.setMapperClass(BigNumFileSortMR_Mapper.class);
        job.setReducerClass(BigNumFileSortMR_Reducer.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        Path inputPath = new Path("D:\\bigdata\\bigdataStudy\\3.1hadoop\\data");//读入多个文件
        Path outputPath = new Path("D:\\bigdata\\bigdataStudy\\3.1hadoop\\data\\outttt");//输出一个文件
        if (fs.exists(inputPath)) {
            fs.delete(outputPath, true);
        }

        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);
        boolean isdone = job.waitForCompletion(true);
        System.exit(isdone ? 0 : 1);
    }

    public static class BigNumFileSortMR_Mapper extends Mapper<LongWritable, Text, IntWritable, NullWritable>{
        @Override
        protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException {
            int num = Integer.parseInt(value.toString());
            context.write(new IntWritable(num), NullWritable.get());

        }
    }
    public static class BigNumFileSortMR_Reducer extends Reducer<IntWritable, NullWritable, Text, NullWritable>{
        Text kout = new Text();
        @Override
        protected void reduce(IntWritable key, Iterable<NullWritable> values, Context context)throws IOException, InterruptedException {
            for(NullWritable niv : values){
                if (key.get() > temNum) {
                    countnum++;//全局排序变量
                    temNum = key.get();//记录当前临时值
                }
                String kk = countnum + "\t" + key.toString();
                kout.set(kk);
                context.write(kout, NullWritable.get());
            }
        }

    }

}