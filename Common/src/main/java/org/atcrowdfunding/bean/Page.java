package org.atcrowdfunding.bean;

import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/24 - 15:19
 */
public class Page<T> {

    /**
     * 分页的数据
     */
    private List<T> datas;
    /**
     * 当前页
     */
    private int pageNo = 1;
    /**
     * 每页条数
     */
    private int pageSize  = 4;
    /**
     * 总条数
     */
    private int totalCount;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 下一页
     */
    private int nextPage;
    /**
     * 上一页
     */
    private int prevPage;

    public int getPageNo() {
        return pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public int getTotalPage() {
        this.totalPage = (this.getTotalCount() - 1) / this.pageSize + 1;
        return totalPage;
    }
    public int getNextPage() {
        return this.nextPage = this.getPageNo() >= this.getTotalPage() ? this.getTotalPage() : this.pageNo+1;
    }
    public int getPrevPage() {
        return this.prevPage = this.getPageNo() <= 1 ? 1 : this.pageNo-1;
    }
    public Page setPageNo(int pageNo) {
        if(this.pageNo >= this.getTotalPage()){
            this.pageNo = this.getTotalPage();
        }else{
            this.pageNo = pageNo;
        }
        return this;
    }
    public Page setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
    public Page setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }
    public Page setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        return this;
    }
    public Page setNextPage(int nextPage) {
        this.nextPage = nextPage;
        return this;
    }
    public Page setPrevPage(int prevPage) {
        this.prevPage = prevPage;
        return this;
    }

    public List<T> getDatas() {
        return datas;
    }

    public Page setDatas(List<T> datas) {
        this.datas = datas;
        return this;
    }
}
