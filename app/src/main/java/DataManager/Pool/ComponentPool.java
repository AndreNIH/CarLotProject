package DataManager.Pool;
import java.util.ArrayList;
import DataManager.Component;

public class ComponentPool{
    private ArrayList<Component> componentObjects = new ArrayList<Component>();
    private ComponentPool nextPool = null;
    
    public ComponentPool(ComponentPool nextPool){
        this.nextPool = nextPool;
    }

    public void registerComponent(Component object){
        if(!componentObjects.contains(object)){
            componentObjects.add(object);
        }
    }

    protected void cleanupOrphanedChildren(){
        if(nextPool == null) return;
        for(int i=0;i < nextPool.countRegisterdComponents();){
            Component childComponent = nextPool.getComponentAt(i);
            if(!componentIsRegisteredAtPool(childComponent.getParent())){
                nextPool.unregisterComponent(childComponent);
            }else{
                i++;
            }
        }
        nextPool.cleanupOrphanedChildren();
    }

    public void unregisterComponent(Component object){
        if(componentObjects.contains(object)){
            object.removeFromParent();
            object.removeAllChildrenRecursively();
            componentObjects.remove(object);
        }
        cleanupOrphanedChildren();
    }
    
    public void updateComponent(Component oldObject, Component newObject){
        final int oldObjectIndex = componentObjects.indexOf(oldObject);
        if(oldObjectIndex == -1) return;
        for(int i=0; i < oldObject.countChildren(); i++){
            Component child = oldObject.getChildAt(i);
            newObject.addChild(child);
        }
        var parent = oldObject.getParent();
        if(parent != null) parent.addChild(newObject);
        componentObjects.set(oldObjectIndex, newObject);
        oldObject.removeAllChildren();
        oldObject.removeFromParent();
    }

    public Component getComponentAt(int index){
        return componentObjects.get(index);
    }
    
    public int countRegisterdComponents(){
        return componentObjects.size();
    }

    public boolean componentIsRegisteredAtPool(Component object){
        return componentObjects.contains(object);
    }
    
}
