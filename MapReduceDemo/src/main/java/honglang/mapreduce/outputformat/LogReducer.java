package honglang.mapreduce.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class LogReducer extends Reducer<Text, NullWritable,Text, NullWritable>{
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        //www.baidu.com
        //www.baidu.com
        //防止丢数据
        for (NullWritable value : values) {
            context.write(key,NullWritable.get());
        }
    }
}
