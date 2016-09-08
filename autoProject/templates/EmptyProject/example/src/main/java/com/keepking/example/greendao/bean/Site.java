package com.keepking.example.greendao.bean;

import java.util.List;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.keepking.example.greendao.gen.DaoSession;
import com.keepking.example.greendao.gen.UserDao;
import com.keepking.example.greendao.gen.SiteDao;

@Entity
public class Site {
    @Id
    private Long id;
    @NotNull
    private String ownerTag;

    @ToMany
    @JoinEntity(
            entity = JoinSiteToUser.class,
            sourceProperty = "siteId",
            targetProperty = "userId"
    )
    private List<User> authors;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 828649250)
    private transient SiteDao myDao;

    @Generated(hash = 277094653)
    public Site(Long id, @NotNull String ownerTag) {
        this.id = id;
        this.ownerTag = ownerTag;
    }

    @Generated(hash = 1136322986)
    public Site() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerTag() {
        return this.ownerTag;
    }

    public void setOwnerTag(String ownerTag) {
        this.ownerTag = ownerTag;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 680981209)
    public List<User> getAuthors() {
        if (authors == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            List<User> authorsNew = targetDao._querySite_Authors(id);
            synchronized (this) {
                if(authors == null) {
                    authors = authorsNew;
                }
            }
        }
        return authors;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 405652703)
    public synchronized void resetAuthors() {
        authors = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1285407712)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSiteDao() : null;
    }
}