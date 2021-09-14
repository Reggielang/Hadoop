package honglang.mapreduce.combiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1.获取JOB
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置Jar包路径
        job.setJarByClass(WordCountDriver.class);

        //3.关联mapper和reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //4.设置map的输出的K,V类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //5.设置最终输出的K,V类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

//        job.setCombinerClass(WordCountCombiner.class);
        //如果combiner不影响最终结果， 直接使用自己的Reducer就行了！
        job.setCombinerClass(WordCountReducer.class);

        //6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job,new Path("D:\\大数据相关学习\\Hadoop3.0学习\\data\\inputword"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\大数据相关学习\\Hadoop3.0学习\\test\\combiner"));

        //7.提交JOB
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
