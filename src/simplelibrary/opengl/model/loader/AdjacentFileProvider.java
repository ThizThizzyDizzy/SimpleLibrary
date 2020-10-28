package simplelibrary.opengl.model.loader;
import java.io.InputStream;
public interface AdjacentFileProvider{
    public InputStream getAdjacentFile(String name);
    public String getAdjacentFilepath(String name);
}