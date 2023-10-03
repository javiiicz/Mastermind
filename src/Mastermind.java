import java.util.Scanner;


public class Mastermind {
    public static void main(String[] args) {
        playPartOne();
        
    } // end of main method


    // Gets the number of hits with a given guess (correct character in correct place)
    public static int getHits(String codeword, String guess) { 
        int hits;
        hits = 0;
        for (int i = 0; i < codeword.length(); i++) {
            if (codeword.charAt(i) == guess.charAt(i)) { // if the character at the index is the same in both strings
                hits++;
            }
        }
        return hits;
    } // end of getHits method


    // Returns the number of times a character appears in a string
    public static int aparitions(String s, char c) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) { // if the character at the index is the same as c
                result++;
            }
        }
        return result;
    } // end of aparitions method


    // Gets the number of misses with a given guess (correct character in wrong place)
    public static int getMisses(String codeword, String guess) {
        int misses, d;
        misses = 0;
        for (d = 1; d <= 6; d++) {
            char charD = (char)(d + '0'); // converts the integer d to a character
            misses += Math.min(aparitions(guess, charD ), aparitions(codeword, charD )); // adds the minimum aparitions of the character in both strings
        }
        return misses - getHits(codeword, guess); // substraction of the hits from the total misses

        // This uses the method specified in the project instructions

    } // end of getMisses method


    // Generates a 4 character codeword as a string (chars are nums 1-6)
    public static String generateCodeword() {
        String result = "";
        for ( int i = 0; i<4; i++) {
            int num = (int)Math.ceil(Math.random()*6); // get random int from 1 to 6
            result += num; // append to result
        }
        return result;
    } // end of generateCodeword method


    // Executes the code to play Part 1 of the project
    public static void playPartOne(){
        Scanner keyboard = new Scanner(System.in);
        boolean moreGames = true;
        
        while ( moreGames ) {
            System.out.println("---- Lets play Mastermind ---");
            String codeword = generateCodeword(); // creates codeword
            
            System.out.println(codeword);


            if (!anotherGame(keyboard)) { // asks if user wants to play another game
                moreGames = false;
            }
        } 
    } // end of playPartOne method


    // Checks validity of responses and returns true if 'y', false if otherwise
    public static boolean anotherGame( Scanner in){
        System.out.print("Another game (y/n)? "); // Replay
        String action = "";
        boolean responseValid = false;

        while (!responseValid) {
            action = in.next();
            if (action.charAt(0) == 'y' || action.charAt(0) == 'n') {
                responseValid = true;
            }
            else{
                System.out.println("Invalid character, try again");
            }
        }

        return action.charAt(0) == 'y';
    } // end of anotherGame method
    

} // end of Mastermind class
