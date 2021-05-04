package simplelibrary.opengl.gui;
public class ActionEvent{
    public final Object source;
    public final int id;
    public final String command;
    public ActionEvent(Object source, int id, String command){
        this.source = source;
        this.id = id;
        this.command = command;
    }
}