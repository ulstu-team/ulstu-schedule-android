package ru.ulstu_team.ulstuschedule.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Teacher extends RealmObject {

    @PrimaryKey
    private int Id;
    private String Name;

    private int CathedraId;
    private Cathedra Cathedra;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCathedraId() {
        return CathedraId;
    }

    public void setCathedraId(int cathedraId) {
        CathedraId = cathedraId;
    }

    public Cathedra getCathedra() {
        return Cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        Cathedra = cathedra;
    }
}
