package ru.ulstu_team.ulstuschedule.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Favorite extends RealmObject{

    @PrimaryKey
    private int Id;
    private int OwnerId;
    private String Type;
    private String Name;
    private boolean IsSaved;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean getIsSaved() {
        return IsSaved;
    }

    public void setIsSaved(boolean saved) {
        IsSaved = saved;
    }
}
