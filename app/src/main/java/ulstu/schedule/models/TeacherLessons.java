package ulstu.schedule.models;

public class TeacherLessons {

    public Teacher Teacher;
    public Lesson[] Lessons;

    @Override
    public String toString() {
        return "Lessons of " + Teacher.Name;
    }
}
