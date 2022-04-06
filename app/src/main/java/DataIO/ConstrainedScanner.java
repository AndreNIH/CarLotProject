package DataIO;
import java.util.Scanner;

public class ConstrainedScanner{
    private String prompt;
    private int minVal;
    private int maxVal;
    private Scanner scanner = new Scanner(System.in);
    
    public ConstrainedScanner(String prompt, int minVal, int maxVal) {
        this.prompt = prompt;
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    public int getValue(){
        for(;;){
            try{
                System.out.print(prompt);
                int result = scanner.nextInt();
                if(result >= minVal && result <= maxVal) return result;
            }catch(java.util.InputMismatchException ex){}
        }
    }

}