package cn.mrx.exam.pojo;

/**
 * Author: Mr.X
 * Date: 2017/3/19
 * Description: 服务器信息
 */
public class SystemServer {

    private String hostName;//计算机名称
    private String local_ip;//计算机局域网ip地址
    private String v4_ip;//计算机广域网ip地址
    private String os_name;//操作系统名称
    private String os_version;//操作系统版本
    private String os_arch;//操作系统架构
    private String user_language;//操作系统语言种类
    private String memery;//内存使用率
    private String disk;//文件系统使用率
    private Integer cpu_number;//CPU数量
    private String file_separator;//计算机文件分隔符
    private String date;//当前时间
    private String java_version;//java运行环境版本
    private String java_vendor;//java运行环境供应商
    private String java_home;//java安装路径
    private String virtua_total_memory;//虚拟机中内存总量
    private String virtua_free_memory;//虚拟机中空闲内存量
    private String virtua_max_memory;//虚拟机中最大内存量
    private String user_dir;//用户当前目录

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getOs_name() {
        return os_name;
    }

    public void setOs_name(String os_name) {
        this.os_name = os_name;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getOs_arch() {
        return os_arch;
    }

    public void setOs_arch(String os_arch) {
        this.os_arch = os_arch;
    }

    public String getUser_language() {
        return user_language;
    }

    public void setUser_language(String user_language) {
        this.user_language = user_language;
    }

    public String getUser_dir() {
        return user_dir;
    }

    public void setUser_dir(String user_dir) {
        this.user_dir = user_dir;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemery() {
        return memery;
    }

    public void setMemery(String memery) {
        this.memery = memery;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public Integer getCpu_number() {
        return cpu_number;
    }

    public void setCpu_number(Integer cpu_number) {
        this.cpu_number = cpu_number;
    }

    public String getVirtua_total_memory() {
        return virtua_total_memory;
    }

    public void setVirtua_total_memory(String virtua_total_memory) {
        this.virtua_total_memory = virtua_total_memory;
    }

    public String getVirtua_free_memory() {
        return virtua_free_memory;
    }

    public void setVirtua_free_memory(String virtua_free_memory) {
        this.virtua_free_memory = virtua_free_memory;
    }

    public String getVirtua_max_memory() {
        return virtua_max_memory;
    }

    public void setVirtua_max_memory(String virtua_max_memory) {
        this.virtua_max_memory = virtua_max_memory;
    }

    public String getJava_version() {
        return java_version;
    }

    public void setJava_version(String java_version) {
        this.java_version = java_version;
    }

    public String getJava_vendor() {
        return java_vendor;
    }

    public void setJava_vendor(String java_vendor) {
        this.java_vendor = java_vendor;
    }

    public String getJava_home() {
        return java_home;
    }

    public void setJava_home(String java_home) {
        this.java_home = java_home;
    }

    public String getFile_separator() {
        return file_separator;
    }

    public void setFile_separator(String file_separator) {
        this.file_separator = file_separator;
    }

    public String getLocal_ip() {
        return local_ip;
    }

    public void setLocal_ip(String local_ip) {
        this.local_ip = local_ip;
    }

    public String getV4_ip() {
        return v4_ip;
    }

    public void setV4_ip(String v4_ip) {
        this.v4_ip = v4_ip;
    }
}
