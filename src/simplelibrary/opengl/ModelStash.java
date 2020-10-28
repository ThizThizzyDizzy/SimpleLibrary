package simplelibrary.opengl;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;
import simplelibrary.Sys;
import simplelibrary.error.ErrorCategory;
import simplelibrary.error.ErrorLevel;
import simplelibrary.opengl.model.Model;
import simplelibrary.opengl.model.loader.AdjacentFileProvider;
import simplelibrary.opengl.model.loader.OBJLoader;
import simplelibrary.opengl.model.loader.ModelLoader;
import simplelibrary.texture.TexturePack;
import simplelibrary.texture.TexturePackManager;
public class ModelStash{
    /**
     * The base instance; it is recommended to use this, but more can be made.
     */
    public static final ModelStash instance = new ModelStash();
    private final ArrayList<ModelLoader> loaders = new ArrayList<>();
    private Model missingModel = new Model();
    {
        loaders.add(new OBJLoader());
    }
    private final HashMap<String, Model> modelMap = new HashMap<>();
    private Model loadModel(Supplier<InputStream> streamSupplier, AdjacentFileProvider adjacentProvider) throws IOException{
        for(ModelLoader loader : loaders){//TODO allow multiple loaders!
            return loader.loadModel(streamSupplier.get(), adjacentProvider);
//            Model model = null;
//            InputStream stream = streamSupplier.get();
//            try{
//                model = loader.loadModel(stream, adjacentProvider);
//            }catch(IOException ex){}
//            try{
//                stream.close();
//            }catch(IOException ex){}//something looks off here
//            if(model!=null)return model;
        }
        return null;
    }
    public Model getModel(String filename){
        if(filename==null){
            return null;
        }
        TexturePack pack = TexturePackManager.instance.currentTexturePack;
        Model m = modelMap.get(filename);
        if(m!=null)return m;
        Model model;
        try{
            InputStream stream = pack.getResourceAsStream(filename);
            if(stream==null){
                Sys.error(ErrorLevel.warning, "Could not find model file "+filename+"! Allocating default model...", null, ErrorCategory.fileIO);
                model = missingModel;
            }else{
                model = loadModel(() -> {
                    return pack.getResourceAsStream(filename);
                }, new AdjacentFileProvider() {
                    @Override
                    public InputStream getAdjacentFile(String name){
                        return TexturePackManager.instance.currentTexturePack.getResourceAsStream(getAdjacentFilepath(name));
                    }
                    @Override
                    public String getAdjacentFilepath(String name){
                        String[] strs = filename.replace("\\", "/").split("/");
                        return filename.substring(0, filename.length()-strs[strs.length-1].length())+name;
                    }
                });
            }
            modelMap.put(filename, model);
            return model;
        }catch(Exception ex){
            Sys.error(ErrorLevel.warning, null, ex, ErrorCategory.fileIO);
            modelMap.put(filename, missingModel);
            return missingModel;
        }
    }
}