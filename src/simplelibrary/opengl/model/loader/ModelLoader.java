package simplelibrary.opengl.model.loader;
import java.io.IOException;
import java.io.InputStream;
import simplelibrary.opengl.model.Model;
public interface ModelLoader{
    public Model loadModel(final InputStream stream, AdjacentFileProvider adjacentProvider) throws IOException;
}