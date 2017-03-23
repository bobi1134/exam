package cn.mrx.exam.utils;

import cn.mrx.exam.pojo.SystemServer;
import com.sun.management.OperatingSystemMXBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Mr.X
 * Date: 2017/3/19
 * Description:
 */
public class SystemMessUtil {

    /**
     * 获取内存使用率
     * @return
     */
    public static String getMemery() {
        OperatingSystemMXBean osmxb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long totalvirtualMemory = osmxb.getTotalSwapSpaceSize(); // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
        String str = compare.intValue() + "%";
        return str;
    }


    /**
     * 获取文件系统使用率
     * @return
     */
    public static List<String> getDisk() {
        List<String> list = new ArrayList<String>();
        for (char c = 'A'; c <= 'Z'; c++) {
            String dirName = c + ":/";
            File win = new File(dirName);
            if (win.exists()) {
                long total = (long) win.getTotalSpace();
                long free = (long) win.getFreeSpace();
                Double compare = (Double) (1 - free * 1.0 / total) * 100;
                String str = c + ":盘已使用" + compare.intValue() + "%";
                list.add(str);
            }
        }
        return list;
    }

    /**
     * 为SystemMess赋初值
     * @return
     */
    public static SystemServer getSystemServer(){
        try {
            SystemServer systemMess = new SystemServer();
            systemMess.setHostName(InetAddress.getLocalHost().getHostName().toString());//服务器计算机名
            systemMess.setLocal_ip(InetAddress.getLocalHost().getHostAddress().toString());//服务器局域网IP地址
            systemMess.setV4_ip(IPUtil.getV4IP());////服务器广域网IP地址
            systemMess.setUser_dir(System.getProperty("user.dir"));//用户当前目录
            systemMess.setOs_name(System.getProperty("os.name"));//服务器系统的名称，Windows 10
            systemMess.setOs_version(System.getProperty("os.version"));//服务器的版本
            systemMess.setOs_arch(System.getProperty("os.arch"));//操作系统架构
            systemMess.setUser_language(System.getProperty("user.language"));//服务器的语言种类
            systemMess.setMemery(SystemMessUtil.getMemery());//服务器内存使用率
            systemMess.setDisk(SystemMessUtil.getDisk().toString().replace("[","").replace("]",""));//服务器文件系统使用率
            systemMess.setCpu_number(Runtime.getRuntime().availableProcessors());//服务器CPU数量
            systemMess.setFile_separator(System.getProperty("file.separator"));//文件分隔符
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            systemMess.setDate(formatter.format(new Date()));//服务器当前时间
            systemMess.setJava_version(System.getProperty("java.version"));//Java的运行环境版本
            systemMess.setJava_vendor(System.getProperty("java.vendor"));//Java的运行环境供应商
            systemMess.setJava_home(System.getProperty("java.home"));//Java的安装路径
            systemMess.setVirtua_total_memory(Runtime.getRuntime().totalMemory() / 1024 / 1024 +"M");//服务器虚拟机中的内存总量
            systemMess.setVirtua_free_memory(Runtime.getRuntime().freeMemory() / 1024 / 1024 +"M");//服务器虚拟机中的空闲内存量
            systemMess.setVirtua_max_memory(Runtime.getRuntime().maxMemory() / 1024 / 1024 +"M");//服务器虚拟机中的最大内存量
            return systemMess;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
