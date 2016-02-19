package ru.ulstu_team.ulstuschedule.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Lesson extends RealmObject {

    @PrimaryKey
    private int Id;
    private int Number;
    private String Name;
    private int DayOfWeek;
    private int NumberOfWeek;
    private String Cabinet;

    private int GroupId;
    private Group Group;

    private int TeacherId;
    private Teacher Teacher;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public int getNumberOfWeek() {
        return NumberOfWeek;
    }

    public void setNumberOfWeek(int numberOfWeek) {
        NumberOfWeek = numberOfWeek;
    }

    public String getCabinet() {
        return Cabinet;
    }

    public void setCabinet(String cabinet) {
        Cabinet = cabinet;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public Group getGroup() {
        return Group;
    }

    public void setGroup(Group group) {
        Group = group;
    }

    public int getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(int teacherId) {
        TeacherId = teacherId;
    }

    public Teacher getTeacher() {
        return Teacher;
    }

    public void setTeacher(Teacher teacher) {
        Teacher = teacher;
    }
}
