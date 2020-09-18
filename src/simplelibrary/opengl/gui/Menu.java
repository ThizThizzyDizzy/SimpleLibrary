package simplelibrary.opengl.gui;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import simplelibrary.font.FontManager;
import simplelibrary.game.GameHelper;
import simplelibrary.opengl.ImageStash;
import simplelibrary.opengl.Renderer2D;
import simplelibrary.opengl.gui.components.MenuComponent;
import simplelibrary.opengl.gui.components.MenuComponentButton;
import simplelibrary.opengl.gui.components.MenuComponentScrollable;
public abstract class Menu extends Renderer2D{
    private static boolean hasBackgroundTexture;
    public Menu parent;
    public ArrayList<MenuComponent> components = new ArrayList<>();
    public MenuComponent selected;
    public GUI gui;
    private static final String menuBackground = "/gui/menuBackground.png";
    
    public int tooltipTime = 15;
    public int tooltipTimer = 0;
    public boolean tooltipShowing = false;
    private boolean mouseMoving = false;
    static{
        try(InputStream in = Menu.class.getResourceAsStream(menuBackground)){
            if(in!=null) hasBackgroundTexture = true;
        }catch(IOException ex){
        }
    }
    public Menu(GUI gui, Menu parent){
        this.gui = gui;
        this.parent = parent;
    }
    public <V extends MenuComponent> V add(V component){
        components.add(component);
        component.gui = gui;
        component.parent = this;
        component.onAdded();
        return component;
    }
    public void tick(){
        for(int i = components.size()-1; i>=0; i--){
            if(i<components.size()) components.get(i).tick();
        }
        if(tooltipShowing){
            boolean hasMouseover = false;
            for(MenuComponent c : components){
                if(c.isMouseOver&&c.getTooltip()!=null){
                    hasMouseover = true;
                    break;
                }
            }
            if(hasMouseover)tooltipTimer--;
            else tooltipTimer++;
            if(tooltipTimer>=tooltipTime){
                tooltipShowing = false;
                tooltipTimer = 0;
            }
        }else{
            if(!mouseMoving){
                boolean hasMouseover = false;
                for(MenuComponent c : components){
                    if(c.isMouseOver&&c.getTooltip()!=null){
                        hasMouseover = true;
                        break;
                    }
                }
                if(hasMouseover)tooltipTimer++;
                else tooltipTimer--;
            }else tooltipTimer--;
            if(tooltipTimer>=tooltipTime){
                tooltipShowing = true;
                tooltipTimer = 0;
            }
        }
        tooltipTimer = Math.min(tooltipTime, Math.max(0, tooltipTimer));
        mouseMoving = false;
    }
    public void render(int millisSinceLastTick){
        renderBackground();
        for(MenuComponent component : components){
            component.render(millisSinceLastTick);
        }
        renderForeground();
    }
    public void renderBackground(){
        if(!hasBackgroundTexture) return;
        switch(gui.type){
            case GameHelper.MODE_2D:
            case GameHelper.MODE_HYBRID:
                drawRect(0, 0, gui.helper.displayWidth(), gui.helper.displayHeight(), ImageStash.instance.getTexture(menuBackground));
                break;
            case GameHelper.MODE_2D_CENTERED:
                drawRect(-gui.helper.displayWidth()/2, -gui.helper.displayHeight()/2, gui.helper.displayWidth()/2, gui.helper.displayHeight()/2, ImageStash.instance.getTexture(menuBackground));
                break;
            case GameHelper.MODE_3D:
                drawRect(-gui.helper.displayWidth()/gui.helper.displayHeight(), -1, gui.helper.displayWidth()/gui.helper.displayHeight(), 1, ImageStash.instance.getTexture(menuBackground));
                break;
            default:
                throw new AssertionError(gui.type);
        }
    }
    public void renderForeground(){
        if(tooltipShowing){
            for(MenuComponent c : components){
                if(c.isMouseOver&&c.getTooltip()!=null){
                    renderTooltip(c);
                    break;
                }
            }
        }
    }
    public void renderTooltip(MenuComponent component){
        double textHeight = 20;
        double textSpacing = textHeight/10;
        double borderSpacing = textHeight/4;
        double borderWidth = textHeight/10;
        String[] tooltips = component.getTooltip().split("\\\n");
        double textWidth = 0;
        for(String s : tooltips){
            textWidth = Math.max(textWidth, FontManager.getLengthForStringWithHeight(s, textHeight));
        }
        double tooltipWidth = borderWidth+borderSpacing+textWidth+borderSpacing+borderWidth;
        double tooltipHeight = borderWidth+borderSpacing+textHeight*tooltips.length+textSpacing*(tooltips.length-1)+borderSpacing+borderWidth;
        double tooltipX = component.x+component.getTooltipOffsetX();
        double tooltipY = component.y+component.getTooltipOffsetY();
        MenuComponent comp = component;
        while(comp.parent!=null&&comp.parent instanceof MenuComponent){
            comp = (MenuComponent)comp.parent;
            if(comp instanceof MenuComponentScrollable){
                tooltipX-=((MenuComponentScrollable)comp).getHorizScroll();
                tooltipY-=((MenuComponentScrollable)comp).getVertScroll();
            }
            tooltipX+=comp.x;
            tooltipY+=comp.y;
        }
        tooltipX = Math.min(tooltipX, gui.helper.displayWidth()-tooltipWidth);
        tooltipY = Math.min(tooltipY, gui.helper.displayHeight()-tooltipHeight);
        if(tooltipWidth>gui.helper.displayWidth())tooltipX = 0;
        if(tooltipHeight>gui.helper.displayHeight())tooltipY = 0;
        GL11.glColor3f(component.tooltipBorderColor.getRed()/255F, component.tooltipBorderColor.getGreen()/255F, component.tooltipBorderColor.getBlue()/255F);
        drawRect(tooltipX, tooltipY, tooltipX+tooltipWidth, tooltipY+tooltipHeight, 0);
        GL11.glColor3f(component.tooltipBackgroundColor.getRed()/255F, component.tooltipBackgroundColor.getGreen()/255F, component.tooltipBackgroundColor.getBlue()/255F);
        drawRect(tooltipX+borderWidth, tooltipY+borderWidth, tooltipX+tooltipWidth-borderWidth, tooltipY+tooltipHeight-borderWidth, 0);
        GL11.glColor3f(component.tooltipTextColor.getRed()/255F, component.tooltipTextColor.getGreen()/255F, component.tooltipTextColor.getBlue()/255F);
        for(int i = 0; i<tooltips.length; i++){
            String tt = tooltips[i];
            drawText(tooltipX+borderWidth+borderSpacing, tooltipY+borderWidth+borderSpacing+(textHeight+textSpacing)*i, tooltipX+tooltipWidth, tooltipY+borderWidth+borderSpacing+(textHeight+textSpacing)*i+textHeight, tt);
        }
    }
    public void buttonClicked(MenuComponentButton button){
        throw new UnsupportedOperationException("Override missing- "+getClass().getName()+" has buttons but never handles events!");
    }
    public boolean onTabPressed(MenuComponent component){return false;}
    public boolean onReturnPressed(MenuComponent component){return false;}
    public void onGUIOpened(){}
    public void onGUIClosed(){}
    public void keyEvent(int key, int scancode, boolean isPress, boolean isRepeat, int modifiers) {
        if(selected!=null){
            selected.keyEvent(key, scancode, isPress, isRepeat, modifiers);
        }
    }
    public void onCharTyped(char c){
        if(selected!=null){
            selected.onCharTyped(c);
        }
    }
    public void onMouseMove(double x, double y) {
        for(MenuComponent component : components){
            if(isClickWithinBounds(x, y, component.x, component.y, component.x+component.width, component.y+component.height)){
                component.onMouseMove(x-component.x, y-component.y);
            }else{
                component.onMouseMovedElsewhere(x-component.x, y-component.y);
            }
        }
        mouseMoving = true;
    }
    void onMouseButton(int button, boolean pressed, int mods) {
        onMouseButton(gui.mouseX, gui.mouseY, button, pressed, mods);
    }
    public void onMouseButton(double x, double y, int button, boolean pressed, int mods){
        boolean clicked = false;
        for(int i = components.size()-1; i>=0; i--){
            if(i>=components.size()) continue;
            MenuComponent component = components.get(i);
            if(!Double.isNaN(x)&&!clicked&&isClickWithinBounds(x, y, component.x, component.y, component.x+component.width, component.y+component.height)){
                if(selected!=component&&pressed&&button==0){
                    if(selected!=null) selected.onDeselected();
                    selected = component;
                    component.onSelected();
                }
                clicked = true;
                component.onMouseButton(x-component.x, y-component.y, button, pressed, mods);
            }else if(!pressed){
                component.onMouseButton(Double.NaN, Double.NaN, button, false, mods);
            }
        }
    }
    void onMouseScrolled(double dx, double dy) {
        onMouseScrolled(gui.mouseX, gui.mouseY, dx, dy);
    }
    public boolean onMouseScrolled(double x, double y, double dx, double dy){
        //Pass scrollwheel event first to whatever the mouse is over, then to the selected component.
        for(MenuComponent component : components){
            if(isClickWithinBounds(x, y, component.x, component.y, component.x+component.width, component.y+component.height)){
                if(component.onMouseScrolled(x-component.x, y-component.y, dx, dy)) return true;
            }
        }
        return false;
    }
    public void onWindowFocused(boolean focused) {}
    void onFilesDropped(String[] files){
        onFilesDropped(gui.mouseX, gui.mouseY, files);
    }
    public boolean onFilesDropped(double x, double y, String[] files){
        for(MenuComponent component : components){
            if(isClickWithinBounds(x, y, component.x, component.y, component.x+component.width, component.y+component.height)){
                if(component.onFilesDropped(x-component.x, y-component.y, files)) return true;
            }
        }
        return false;
    }
}
