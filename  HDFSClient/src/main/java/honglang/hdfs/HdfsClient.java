package honglang.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.yarn.webapp.hamlet2.Hamlet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/*
客户端代码常用套路
1.获取一个客户端对象
2.执行相关操作命令
3.关闭资源
HDFS zookeeper
 */
public class HdfsClient {

    private FileSystem fs;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        //连接的集群nn地址
        URI uri = new URI("hdfs://hadoop102:8020");
        //创建一个配置文件
        Configuration configuration = new Configuration();

        configuration.set("dfs.replication","2");

        //用户
        String user = "honglang";

        //1.获取到了客户端对象
        fs = FileSystem.get(uri,configuration,user);
    }

    @After
    public void close() throws IOException {
        //3.关闭资源
        fs.close();
    }

    //创建目录
    @Test
    public void testmkdir() throws URISyntaxException, IOException, InterruptedException {

        //2.创建一个文件夹
        fs.mkdirs(new Path("/xiyou/huaguoshan1"));
    }

    //上传操作
    /*
    参数优先级
    hdfs-default.xml => hdfs.site.xml =>在项目资源目录下的配置文件优先级高 =>代码里面的配置优先级最高


     */

    @Test
    public void testPut() throws IOException {
        //参数解读；参数1表示删除原始数据； 参数二表示是否允许覆盖； 参数三原数据路径；参数四：目的地路径
        fs.copyFromLocalFile(false,true,new Path("D:\\大数据相关学习\\Hadoop3.0学习\\sunwukong.txt"),new Path("hdfs://hadoop102/xiyou/huaguoshan"));
    }

    //文件下载
    @Test
    public void testGet() throws IOException {
        //参数解读；参数1：是否删除原文件；参数二：原文件的路径；参数三：目标地址路径Win,参数四
//        fs.copyToLocalFile(true,new Path("/xiyou/huaguoshan"),new Path("D:\\大数据相关学习\\Hadoop3.0学习"),true);
        fs.copyToLocalFile(true,new Path("/a.txt"),new Path("D:\\大数据相关学习\\Hadoop3.0学习"),false);
    }

    //文件删除
    @Test
    public void testRm() throws IOException {
        //参数解读：参数1：删除的路径,参数2：是否递归删除
        //删除文件
        //fs.delete(new Path("/xiyou/sunwukong.txt"),false);

        //删除空目录
        //fs.delete(new Path("/xiyou"),false);

        //删除非空目录
        fs.delete(new Path("/jinguo"),true);
    }

    //文件的更名和移动
    @Test
    public void testmv() throws IOException {
        //参数解读：参数1：原文件路径，参数2：目标文件路径
        //对文件名称的修改
        //fs.rename(new Path("/input/word.txt"),new Path("/input/ss.txt"));
        //文件的移动和更名
        //fs.rename(new Path("/input/ss.txt"),new Path("/cls.txt"));
        //目录的更名
        fs.rename(new Path("/input"),new Path("/output"));

    }

    //获取文件详情信息
    @Test
    public void fileDetail() throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        //遍历文件
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();

            System.out.println("============="+fileStatus.getPath()+"==========");

            System.out.println(fileStatus.getPermission());

            System.out.println(fileStatus.getOwner());

            System.out.println(fileStatus.getGroup());

            System.out.println(fileStatus.getLen());

            System.out.println(fileStatus.getModificationTime());

            System.out.println(fileStatus.getReplication());

            System.out.println(fileStatus.getBlockSize());

            System.out.println(fileStatus.getPath().getName());

            //获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println(Arrays.toString(blockLocations));

        }
    }

    //文件夹和文件的判断
    @Test
    public void testFile() throws IOException {
        FileStatus[] listStatuses = fs.listStatus(new Path("/"));

        for (FileStatus status : listStatuses) {
            if (status.isFile()) {
                System.out.println("文件:"+status.getPath().getName());
            }else {
                System.out.println("目录:"+status.getPath().getName());
            }
        }
    }
}
