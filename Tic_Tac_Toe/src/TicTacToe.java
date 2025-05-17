import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    static int [] resElement(int[] element){
        element[0]=-1;element[1]=-1;element[2]=-1;
        return element;
    }

    static int[] rowStop(int i,int col,int countP,int [] element,String[][] game){
        //Bot Intelligence
        for (int a=col;a<3;a++) {
            boolean cellFilled = (game[i][a].contains("X") || game[i][a].contains("O"));
            if ((!cellFilled && countP == 2)) {
                element[0] = i;
                element[1] = a;
                element[2] = countP;
                break;
            }
        }
        return element;
    }

    static int[] colStop(int i,int col,int countP,int [] element,String[][] game){
        //Bot Intelligence
        for (;i<3;i++) {
            boolean cellFilled = (game[i][col].contains("X") || game[i][col].contains("O"));
            if ((!cellFilled && countP == 2)) {
                element[0] = i;
                element[1] = col;
                element[2] = countP;
                break;
            }
        }
        return element;
    }

    static void close(){
        System.exit(0);
    }

    static void winCheck(int countP,int countB){
        if (countP == 3) {
            System.out.println("\u001B[32m"+"You have Won!" + "\u001B[0m"); //To change color to green then reset it
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                        TicTacToe.class.getResource("smb3_game_over.wav")
                );
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start(); // Play the sound
                Thread.sleep(clip.getMicrosecondLength() / 1000); // Keep program alive
            } catch (Exception e) {
                e.printStackTrace();
            }
            close();
        }
        else if (countB == 3) {
            System.out.println("\u001B[31m"+"You have Lost!" + "\u001B[0m"); //To change color to red then reset it
            close();
        }
    }

    static int [] checkRow(int i,int col,String[][]game){
        int countP =0,countB=0;

        int [] element=new int [3];
        element[0]=-1;element[1]=-1;

        for (int j=col;j<3;j++){
            if (game[i][j].contains("X"))       //'i' (row) remains same and 'j' is changed,
                countP++;                                                                   // to check the row if it has 'X' or 'O'
            else if (game[i][j].contains("O"))
                countB++;
        }
        winCheck(countP,countB);
        return rowStop(i,col,countP,element,game);
    }

    static void checkColumn(int i,int col,String[][]game){
        int countP = 0,countB=0;

        for (;i<3;i++)
            if (game[i][col].contains("X"))                                 // 'j' the column remains same and 'i' changes (row's elements are being checked)
                countP++;
            else if (game[i][col].contains("O"))
                countB++;

       winCheck(countP,countB);
    }

    static void checkDiagonal(int i, int j, String[][]game){
        int countP = 0,countB=0;
        boolean Dgl = i == 0 && j == 0;//checks which diagonal is it

        while (i<3 && j<3){
            if (game[i][j].contains("X"))   // 'j' the column increases and 'i' also increases
                countP++;
            else if (game[i][j].contains("O"))
                countB++;
            i++; //Common part from if has been separated!
            if (Dgl){
                j++;
            }
            else{
                j--;
            }
        }
        winCheck(countP,countB);
    }

    static int[] winLoose( int [] element,String [][] game){

        for (int i=0;i<3;i++)
            if (element[i] == -1 || element[2] == 0)             //Stops the Win & Loose functions from crashing
             for (int j=0;j<3;j++){
                if (i == 0 & j==0){
                    element=checkRow(i,j,game);
                    checkColumn(i,j,game);
                    checkDiagonal(i,j,game);
                }
                if (i == 0 & j==1) {
                    checkColumn(i, j, game);
                }
                if (i == 0 & j==2){
                    checkDiagonal(i,j,game);
                    checkColumn(i,j,game);
                }
                if ((i == 1 || i==2) && j==0) {
                    element=checkRow(i, j, game);
                }
            }
        return element;
    }

    static int[] bot(int [] element,String [][] game){
        //Random Class
        Random random = new Random();
        int i,j;
        char B1='O';
        if (element[0] == -1 && element[1] == -1)           //If element wasn't found, you give random value
         do {
            i = random.nextInt(0, 3);
            j = random.nextInt(0, 3);
        } while (game[i][j].contains("X") || game[i][j].contains("O"));                 //IntellijIdea suggests to use do while loop (optimizing my code)

        else {
            i=element[0];j=element[1];
            System.out.println("BOT: I won't let you win wo easily!!!");
        }

        //System.out.println("The values of row and column are:" +element[0]+ " " +element[1] + "\nThe countP is: " + element[2]);
        performMove(i, j, B1,game);

        element= resElement(element);
        return element;
    }

    static void Player( int c,String [][]game){
        int i,j;
        char P1 = 'X';
        while(true){
                System.out.println("Enter Your Row(1,2,3):");
                i = scan.nextInt() - 1;
                System.out.println("Enter Your Column(a,b,c):");
                char t = scan.next().charAt(0);                 //Temporary Variable
                j = switch (t) {
                    case 'a' -> 0;                                      //case 'a': yield 0;
                    case 'b' -> 1;
                    case 'c' -> 2;
                    default -> -1;
                };

                //User Input Handling & prevent Overriding Values  In a Block

                boolean validBlock = !(i < 0 || i > 3|| j == -1);                                       //checks if i or j indexes are out of bound

                if (!validBlock)
                {
                    System.out.println("\u001B[31m" + "Choose Correct Block Please!" + "\u001B[0m");continue;
                }

                if (c>1) {
                        if ((game[i][j].contains("X") || game[i][j].contains("O"))) {
                            System.out.println("\u001B[31m" + "Choose Correct Block Please!" + "\u001B[0m");
                        } else break;
                }
                else break;
        }
        performMove(i, j, P1,game);  // Game Prints
    }

    static void performMove(int i,int j,char value,String [][] arr){
        //Value: whose turn is it, stores the value of the bot(O) or the player(X)
                System.out.println("╭───────┬───────┬───────╮");
                                arr[i][j] = "|   " + value + "   ";         //Element is assigned: |   X     OR |   O

            for (int a = 0; a < 3; a++) {
                        for (int b = 0; b < 3; b++) {
                            if (arr[a][b] == null)
                                arr[a][b] = "|       ";
                            System.out.print(arr[a][b]);
                        }
                        System.out.println("|");
                if (a!=2)
                    System.out.println("├───────┼───────┼───────┤");

            }
                System.out.println("╰───────┴───────┴───────╯\n\n\n\n\n");
    }

    //Scanner Class
    public static Scanner scan= new Scanner(System.in);

    public static void main(String[] args) {
        String instructions = """
                              a       b       c
                          ╭───────┬───────┬───────╮
                        1 │  1a   │  1b   │  1c   │
                          ├───────┼───────┼───────┤
                        2 │  2a   │  2b   │  2c   │
                          ├───────┼───────┼───────┤
                        3 │  3a   │  3b   │  3c   │
                          ╰───────┴───────┴───────╯
                        """;

        String [][] game= new String[3][3];
        int c; // counter variable for no. of moves
        int[] element= new int[3];
        element[0]=-1;element[1]=-1;

        System.out.println("Here's the controls of the Game:\n(1,2,3) are the rows & (a,b,c) are the columns\n" + instructions);

        for (c=1;c<=9;c++){
            if (c%2 != 0) {
                System.out.println("Player Turn:" + ((c/2)+1));
                Player( c,game);
            }
            else {
                System.out.println("Bot Turn:" + (c/2));
                element=bot( element,game);
            }
            if (c>=3)
               element = winLoose(element,game); //Checks after  moves if anyone Wins/Looses.
                                                // Also, logic Bot Intelligence is implemented using this.
        }
        System.out.println("It's a draw!");
    }
}
