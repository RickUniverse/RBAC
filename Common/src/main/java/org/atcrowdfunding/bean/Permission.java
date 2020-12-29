package org.atcrowdfunding.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/25 - 18:28
 */
public class Permission {
    private Integer id;
    private String name;
    private String url;
    private Integer pid;
    private boolean open = true;
    private boolean checked = false;
    private String icon;
    private List<Permission> children = new ArrayList<>();

    public boolean isChecked() {
        return checked;
    }

    public Permission setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Permission setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }

    public Permission(Integer id, String name, String url, Integer pid, boolean open, List<Permission> children) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.pid = pid;
        this.open = open;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public Permission setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Permission setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getPid() {
        return pid;
    }

    public Permission setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public boolean isOpen() {
        return open;
    }

    public Permission setOpen(boolean open) {
        this.open = open;
        return this;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public Permission setChildren(List<Permission> children) {
        this.children = children;
        return this;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                ", open=" + open +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                '}';
    }
}
