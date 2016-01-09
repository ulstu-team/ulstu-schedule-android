package ulstu.schedule.models;

public class Faculty {

    public int Id;
    public String Name;

    public Faculty() {
    }

    public Faculty(int Id, String Name) {
        this.Id = Id;
        this.Name = Name;
    }

    @Override
    public String toString() {
        return Name;
    }
}
