package honglang.mapreduce.Partitoner_Writablecomparable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Partitioner2 extends Partitioner<FlowBean, Text> {
    @Override
    public int getPartition(FlowBean flowBean, Text text, int i) {
        String phone = text.toString();

        String prePhone = phone.substring(0, 3);

        int partitioner;
        if ("136".equals(prePhone)){
            partitioner=0;
        }else if("137".equals(prePhone)){
            partitioner=1;
        }else if("138".equals(prePhone)){
            partitioner=2;
        }else if("139".equals(prePhone)){
            partitioner=3;
        }else{
            partitioner=4;
        }

        return partitioner;
    }
}
