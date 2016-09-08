package com.keepking.example.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class JoinSiteToUser {
    @Id
    private Long id;
    private Long siteId;
    private Long userId;
    @Generated(hash = 1145090686)
    public JoinSiteToUser(Long id, Long siteId, Long userId) {
        this.id = id;
        this.siteId = siteId;
        this.userId = userId;
    }
    @Generated(hash = 1162634229)
    public JoinSiteToUser() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSiteId() {
        return this.siteId;
    }
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}