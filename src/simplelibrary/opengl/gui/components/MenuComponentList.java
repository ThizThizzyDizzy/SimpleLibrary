package simplelibrary.opengl.gui.components;
public class MenuComponentList extends MenuComponentScrollable{
    public MenuComponentList(double x, double y, double width, double height){
        this(x, y, width, height, height/20);
    }
    public MenuComponentList(double x, double y, double width, double height, double scrollbarWidth){
        this(x, y, width, height, scrollbarWidth, false);
    }
    public MenuComponentList(double x, double y, double width, double height, double scrollbarWidth, boolean alwaysShowScrollbar){
        super(x, y, width, height, 0, scrollbarWidth, alwaysShowScrollbar, false);
    }
    public boolean hasScrollbar(){
        return hasVertScrollbar();
    }
    @Override
    public void renderBackground() {
        double y = 0;
        for (MenuComponent c : components) {
            c.x = 0;
            c.y = y;
            y+=c.height;
            c.width = width-(hasScrollbar()?vertScrollbarWidth:0);
        }
        super.renderBackground();
    }
    public int getSelectedIndex(){
        return components.indexOf(selected);
    }
    public void setSelectedIndex(int index){
        if(index<0||index>=components.size()) selected = null;
        else selected = components.get(index);
    }
}
