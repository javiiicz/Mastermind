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
            int num = 1 + (int)(Math.random()*6); // get random int from 1 to 6
            result += num; // append to result
        }
        return result;
    } // end of generateCodeword method


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


    // Asks the user if he wants the codeword revealed and executes the action
    public static void revealCodeword( Scanner in, String codeword){
        System.out.print("Reveal the codeword (y/n)? ");
        String input;
        
        while (true) {
            input = in.next();
            input = input.toLowerCase();
            if (input.charAt(0) == 'y'){
                System.out.printf("The codeword is %s.%n", codeword);
                break;
            }
            else if (input.charAt(0) == 'n') {
                break;
            }
            else{
                System.out.println("Invalid character, try again");
            }
        }
    } // end of revealCodeword method


    // Checks for validity of a guess 
    public static boolean guessValid(String guess){
        if (guess.length() != 4){ // Checks length
            return false;
        }

        int i;
        for (i = 0; i < 4; i++){ // Cheks for chars 1,2,3,4,5,6
            if (guess.charAt(i) > '6' || guess.charAt(i) < '1') {
                return false;
            }
        }
        return true;
    } // end of guessValid method


    // Executes the code to play Part 1 of the project
    public static void playPartOne(){
        Scanner keyboard = new Scanner(System.in);
        boolean moreGames = true;
        String guess;
        int round = 1;
        
        while ( moreGames ) {
            System.out.println("---- Lets play Mastermind ---");
            String codeword = generateCodeword(); // creates codeword
            
            revealCodeword( keyboard, codeword); // Asks for reveal

            while (true) {  // Round loop
                System.out.printf("Round = %d, Your guess (0 to stop) ", round);
                
                guess = keyboard.next(); // get guess
                
                if (guess.equals("0")) { // cancel game when guess is 0
                    System.out.printf("The codeword was %s.%n", codeword);
                    break;
                } 
                
                if (!guessValid(guess)) { // validate guess
                    System.out.println("An invalid guess");
                    continue;
                }
                
                if (!codeword.equals(guess)){ // give hit and miss info if guess is incorrect
                    System.out.printf("%d hits, %d misses.%n", getHits(codeword, guess), getMisses(codeword, guess));
                    round += 1;
                    continue;
                }
                
                // give win feedback when it matches.
                System.out.println("You Won !!!!");
                break;
            }



            if (!anotherGame(keyboard)) { // asks if user wants to play another game
                moreGames = false;
            }
        } 
    } // end of playPartOne method
    

} // end of Mastermind class
