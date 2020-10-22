package simplelibrary.opengl.gui.components;
public class MenuComponentMulticolumnList extends MenuComponentScrollable{
    public double columnWidth;
    public double rowHeight;
    public int columnCount;
    public MenuComponentMulticolumnList(double x, double y, double width, double height, double columnWidth, double rowHeight){
        this(x, y, width, height, columnWidth, rowHeight, width/20);
    }
    public MenuComponentMulticolumnList(double x, double y, double width, double height, double columnWidth, double rowHeight, double scrollbarWidth){
        this(x, y, width, height, columnWidth, rowHeight, scrollbarWidth, false);
    }
    public MenuComponentMulticolumnList(double x, double y, double width, double height, double columnWidth, double rowHeight, double scrollbarWidth, boolean alwaysShowScrollbar){
        super(x, y, width, height, 0, scrollbarWidth, alwaysShowScrollbar, false);
        this.columnWidth=columnWidth;
        this.rowHeight=rowHeight;
        columnCount = Math.max(1, (int)((width-(width%columnWidth))/columnWidth));
    }
    @Override
    public void renderBackground() {
        double width = this.width-(hasVertScrollbar()?vertScrollbarWidth:0);
        columnCount = Math.max(1, (int)((width-(width%columnWidth))/columnWidth));
        int column = 0;
        double y = 0;
        for(MenuComponent c : components){
            c.x = column*columnWidth;
            c.y = y;
            c.width = columnWidth;
            c.height = rowHeight;
            column = (column+1)%columnCount;
            if(column==0) y+=rowHeight;
        }
        super.renderBackground();
    }
    public boolean scrollbarPresent(){
        return hasVertScrollbar();
    }
    public int getSelectedIndex(){
        return components.indexOf(selected);
    }
    public void setSelectedIndex(int index){
        if(index<0||index>=components.size()) selected = null;
        else selected = components.get(index);
    }
}
