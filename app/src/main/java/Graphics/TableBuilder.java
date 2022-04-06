package Graphics;
import Graphics.TableCell;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Collections;

public class TableBuilder{
    private ArrayList<TableCell> cellContainer;
    private ArrayList<ArrayList<String>> tableMatrix;
    private ArrayList<String> rowBuffer;
    
    public TableBuilder(TableCell... cells) {
        cellContainer =  new ArrayList<TableCell>(Arrays.asList(cells));
        tableMatrix = new ArrayList<ArrayList<String>>();
        rowBuffer = new ArrayList<String>(Collections.nCopies(cells.length, null));    
        
    
    }

    public<T> void bindColumnValue(int columnNum, T value){
        StringBuilder builder = new StringBuilder();
        TableCell targetCell = cellContainer.get(columnNum);
        builder.append(value);
        String result = builder.toString();
        rowBuffer.set(columnNum, result);
        if(targetCell.getMinimumLength() < result.length()){
            targetCell.setMinimumLength(result.length());
        }
    }

    public void commitRow(){
        if(rowBuffer.size() != cellContainer.size()) 
            throw new RuntimeException("Size of comitted row must be equal to the ammount of header cells");
        
        tableMatrix.add((ArrayList<String>) rowBuffer.clone());    
        rowBuffer = new ArrayList<String>(Collections.nCopies(cellContainer.size(), null));    
    }
    
    private String getRibbon(){
        String result = new String();
        for(TableCell headerCell : cellContainer){
            for(int c =0; c < headerCell.getTotalSpace(); c++)  result += '=';
            result += '+';
        }
        return result.concat("\n");
    }

    public String getHeader(){
        String result = new String();
        result += getRibbon();
        for(TableCell headerCell : cellContainer){
            result += headerCell.getText();
            for(int pad = 0; pad < headerCell.getPadding(); pad++) result += ' ';
            result += '|';
            
        }
        result += '\n';
        result += getRibbon();
        return result;
    }
    

    public String getTable(){
        String result = getHeader();
        for(ArrayList<String> row : tableMatrix){
            int columnCounter=0;
            for(String column : row){
                int padding = cellContainer.get(columnCounter).getTotalSpace() - column.length(); 
                result += column;
                for(int pad = 0; pad < padding; pad++) result += ' ';
                result += '|';
                columnCounter++;
            }
            result += "\n" + getRibbon();
        }
        return result;
    }
    
}
