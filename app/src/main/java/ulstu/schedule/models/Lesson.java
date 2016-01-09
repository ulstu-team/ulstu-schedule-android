package ulstu.schedule.models;

public class Lesson implements Comparable<Lesson> {

    public int Id;
    public int Number;
    public String Name;
    public int DayOfWeek;
    public int NumberOfWeek;
    public String Cabinet;

    public int GroupId;
    public Group Group;

    public int TeacherId;
    public Teacher Teacher;

    public String toString() {
        return "N:" + Number + " Name:" + Name + " DW:" + DayOfWeek +
                " NW:" + NumberOfWeek + " C:" + Cabinet + " G:" + Group.Name +
                " T:" + Teacher.Name;
    }

    @Override
    public int compareTo(Lesson another) {
        return another.getRate() - this.getRate();
    }

    private int getRate() {
        int rate = 0;
        rate += (3 - NumberOfWeek) * 100;
        rate += (7 - DayOfWeek) * 10;
        rate += (9 - Number);
        return rate;
    }
}
