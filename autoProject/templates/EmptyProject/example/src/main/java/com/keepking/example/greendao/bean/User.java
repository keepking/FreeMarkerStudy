package com.keepking.example.greendao.bean;

import java.util.List;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.keepking.example.greendao.gen.DaoSession;
import com.keepking.example.greendao.gen.SiteDao;
import com.keepking.example.greendao.gen.UserDao;

@Entity(
        // 如果你有超过一个的数据库结构，可以通过这个字段来区分
        // 该实体属于哪个结构
        //schema = "myschema",

        //  实体是否激活的标志，激活的实体有更新，删除和刷新的方法
        //active = true,

        // 确定数据库中表的名称
        // 表名称默认是实体类的名称
        //nameInDb = "user",

        // Define indexes spanning multiple columns here.
//        indexes = {
//                @Index(value = "name DESC", unique = true)
//        },

        // DAO是否应该创建数据库表的标志(默认为true)
        // 如果你有多对一的表，将这个字段设置为false
        // 或者你已经在GreenDAO之外创建了表，也将其置为false
        //createInDb = true
)
public class User {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "name")
    @NotNull
    @Index(unique = true)
    private String name;

    @Transient //不存入数据库
    private int tempUsageCount;

    @Unique
    private String authorTag;

    @ToMany(joinProperties = {
            @JoinProperty(name = "authorTag", referencedName = "ownerTag")
    })
    private List<Site> ownedSites;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1507654846)
private transient UserDao myDao;

@Generated(hash = 1732283933)
public User(Long id, @NotNull String name, String authorTag) {
        this.id = id;
        this.name = name;
        this.authorTag = authorTag;
}

@Generated(hash = 586692638)
public User() {
}

public Long getId() {
        return this.id;
}

public void setId(Long id) {
        this.id = id;
}

public String getName() {
        return this.name;
}

public void setName(String name) {
        this.name = name;
}

public String getAuthorTag() {
        return this.authorTag;
}

public void setAuthorTag(String authorTag) {
        this.authorTag = authorTag;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 707048939)
public List<Site> getOwnedSites() {
    if (ownedSites == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        SiteDao targetDao = daoSession.getSiteDao();
        List<Site> ownedSitesNew = targetDao._queryUser_OwnedSites(authorTag);
        synchronized (this) {
            if(ownedSites == null) {
                ownedSites = ownedSitesNew;
            }
        }
    }
    return ownedSites;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 2095874465)
public synchronized void resetOwnedSites() {
        ownedSites = null;
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
@Generated(hash = 2059241980)
public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
}
}