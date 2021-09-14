package honglang.mapreduce.Partitoner_Writablecomparable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1.获取Job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置jar包
        job.setJarByClass(FlowDriver.class);

        //3.关联mapper和reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        //4.设置mapper输出的key和value类型
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);

        //5.设置最终数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //设置排序和分区文件，以及reduce任务数
        job.setPartitionerClass(Partitioner2.class);
        job.setNumReduceTasks(5);

        //6.设置数据的输入路径和输出路径
        FileInputFormat.setInputPaths(job,new Path("D:\\大数据相关学习\\Hadoop3.0学习\\test\\writable"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\大数据相关学习\\Hadoop3.0学习\\test\\writable_sorted_partitioner"));

        //7.提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result?0:1);
    }
}
