package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.SystemMess;
import cn.mrx.exam.utils.SystemMessUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Author: Mr.X
 * Date: 2017/3/18
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    /**
     * 打开index.jsp
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String admin(){
        return "admin/index";
    }

    /**
     * 打开welcome.jsp
     * @param model
     * @return
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(Model model){
        model.addAttribute("systemMess", setMess());//准备数据
        return "admin/welcome";
    }

    /**
     * 为SystemMess赋初值
     * @return
     */
    public SystemMess setMess(){
        try {
            SystemMess systemMess = new SystemMess();
            systemMess.setHostName(InetAddress.getLocalHost().getHostName().toString());//服务器计算机名
            systemMess.setLocal_ip(InetAddress.getLocalHost().getHostAddress().toString());//服务器局域网IP地址
            systemMess.setV4_ip(SystemMessUtils.getV4IP());////服务器广域网IP地址
            systemMess.setUser_dir(System.getProperty("user.dir"));//用户当前目录
            systemMess.setOs_name(System.getProperty("os.name"));//服务器系统的名称，Windows 10
            systemMess.setOs_version(System.getProperty("os.version"));//服务器的版本
            systemMess.setOs_arch(System.getProperty("os.arch"));//操作系统架构
            systemMess.setUser_language(System.getProperty("user.language"));//服务器的语言种类
            systemMess.setMemery(SystemMessUtils.getMemery());//服务器内存使用率
            systemMess.setDisk(SystemMessUtils.getDisk().toString().replace("[","").replace("]",""));//服务器文件系统使用率
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
