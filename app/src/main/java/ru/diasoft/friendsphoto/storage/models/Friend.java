package ru.diasoft.friendsphoto.storage.models;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import ru.diasoft.friendsphoto.network.resources.FriendsItemRes;

@Entity(active = true, nameInDb = "FRIENDS")
public class Friend {

    @Id
    private Long id;

    @NotNull
    @Unique
    private int friendId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String photo;

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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1516049992)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFriendDao() : null;
    }

    /** Used for active entity operations. */
    @Generated(hash = 76285035)
    private transient FriendDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getFriendId() {
        return this.friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Friend(FriendsItemRes friendsItemRes) {
        this.friendId = friendsItemRes.getId();
        this.firstName = friendsItemRes.getFirstName();
        this.lastName = friendsItemRes.getLastName();
        this.photo = friendsItemRes.getPhoto50();
    }

    @Generated(hash = 387482210)
    public Friend(Long id, int friendId, @NotNull String firstName,
            @NotNull String lastName, String photo) {
        this.id = id;
        this.friendId = friendId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }

    @Generated(hash = 287143722)
    public Friend() {
    }

}
