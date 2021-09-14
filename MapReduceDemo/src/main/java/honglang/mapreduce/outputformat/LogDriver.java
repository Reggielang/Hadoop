package honglang.mapreduce.outputformat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
public class LogDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(LogDriver.class);
        job.setMapperClass(LogMapper.class);
        job.setReducerClass(LogReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置自定义的 outputformat
        job.setOutputFormatClass(LogOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path("D:\\大数据相关学习\\Hadoop3.0学习\\data\\inputoutputformat"));
        // 虽然自定义了outputformat ， 但是因为outputformat继承自fileoutputformat
        //而 fileoutputformat 要输出一个_SUCCESS文件，所以在这还得指定一个输出目录来输出这个文件
        FileOutputFormat.setOutputPath(job, new Path("D:\\大数据相关学习\\Hadoop3.0学习\\test\\logoutput"));
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
