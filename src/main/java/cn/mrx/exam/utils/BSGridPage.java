package cn.mrx.exam.utils;

import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * 针对bsGrid分页插件的封装
 *
 * @Author Mr.X
 * @Date 2016-08-10
 */
public class BSGridPage<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success = true;

    private int curPage;

    private int pageSize;

    private int totalRows;

    private String sortName;

    private String sortOrder;

    private List<T> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Page<T> getPage(int start, int length){
        int current = start != 1 ? start / length : start;
        return  new Page<T>(current, start);
    }

    @JsonIgnore
    public Page<T> getPage(){
        this.curPage = curPage == 0 ? 1 : curPage;
        this.pageSize = pageSize == 0 ? 10 : pageSize;
        Page<T> page = new Page<T>(curPage, pageSize);
        if (sortName != null && !"".equals(sortName.trim()) && !"null".equals(sortName)){
            page.setOrderByField(sortName);
            page.setAsc("asc".equals(sortOrder.toLowerCase()));
        }else {
            //没有排序就不操作！
//            page.setOrderByField("id");
//            page.setAsc(false);
        }
        return page;
    }

    public BSGridPage<T> parsePage(Page<T> page){
        this.totalRows = page.getTotal();
        this.data = page.getRecords();
        return this;
    }

    @JsonIgnore
    public String getOrderByField(){
        if (sortName == null || "".equals(sortName.trim())){
            return "";
        }
        sortOrder = ( sortOrder == null || "".equals(sortOrder.trim()) ) ? "ASC" : sortOrder;
        return sortName + " " + sortOrder;
    }

}
