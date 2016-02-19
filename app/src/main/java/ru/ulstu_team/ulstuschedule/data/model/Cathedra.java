package ru.ulstu_team.ulstuschedule.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Cathedra extends RealmObject {

    @PrimaryKey
    private int Id;
    private String Name;
    private int FacultyId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getFacultyId() {
        return FacultyId;
    }

    public void setFacultyId(int facultyId) {
        FacultyId = facultyId;
    }
}
