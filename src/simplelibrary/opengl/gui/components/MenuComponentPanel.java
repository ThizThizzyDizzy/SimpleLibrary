package simplelibrary.opengl.gui.components;
public class MenuComponentPanel extends MenuComponent{
    public MenuComponentPanel(double x, double y, double width, double height){
        super(x, y, width, height);
    }
    @Override
    public void render(){
        drawRect(x, y, x+width, y+height, 0);
    }
}