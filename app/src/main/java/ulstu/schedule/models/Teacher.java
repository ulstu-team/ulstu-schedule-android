package ulstu.schedule.models;

import org.json.JSONException;
import org.json.JSONObject;import java.lang.Override;import java.lang.String;import Models.IJsonInterface;

public class Teacher implements IJsonInterface {

    public int Id;
    public String Name;

    public int CathedraId;
    public Cathedra Cathedra;

    @Override
    public String toString(){
        return Name;
    }

    public Teacher getFromJson(JSONObject json){
        Teacher teacher = new Teacher();
        try{
            teacher.Id = json.getInt("id");
            teacher.Name = json.getString("Name");
            teacher.CathedraId = json.getInt("CathedraId");

            teacher.Cathedra = new Cathedra().getFromJson(json.getJSONObject("Cathedra"));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return teacher;
    }
}
