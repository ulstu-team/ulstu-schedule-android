package ulstu.schedule.models;

public class Teacher {

    public int Id;
    public String Name;

    public int CathedraId;
    public Cathedra Cathedra;

    @Override
    public String toString(){
        return Name;
    }
}
