package Graphics;

public class TableCell{
    private String text = new String();
    private int minimumLength;
    
    public TableCell(String text, int minimumLength){
        this.text = text;
        this.minimumLength = minimumLength;
    }
    
    public TableCell(String text){
        this.text = text;
        this.minimumLength = text.length();
    }



    public String getText() {
        return this.text;
    }

    public int getMinimumLength() {
        return this.minimumLength;
    }
    public int getPadding(){
        int deltaLenght = text.length() - minimumLength;
        return (deltaLenght > 0) ? 0 : minimumLength - text.length();
    }

    public int getTotalSpace(){
        return getPadding() + text.length();
    }

    public void setMinimumLength(int minimumLength){
        this.minimumLength = minimumLength;
    }

    
    
}