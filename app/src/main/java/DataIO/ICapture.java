package DataIO;
import DataManager.Component;

public interface ICapture{
    public void onCreate();
    public void onUpdate();
    public void onDelete();
    public Component onBrowse(boolean shouldReturnComponent);
}