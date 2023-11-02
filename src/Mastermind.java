import java.util.Scanner;


public class Mastermind {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Type 1 for Part One or 2 for Part Two: ");
        String action = input.next();

        if (action.equals("1")){
            playPartOne();
        }
        else{
            playPartTwo();
        }

        
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
    public static int apparitions(String s, char c) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) { // if the character at the index is the same as c
                result++;
            }
        }
        return result;
    } // end of apparitions method


    // Gets the number of misses with a given guess (correct character in wrong place)
    public static int getMisses(String codeword, String guess) {
        int misses, d;
        misses = 0;
        for (d = 1; d <= 6; d++) {
            char charD = (char)(d + '0'); // converts the integer d to a character
            misses += Math.min(apparitions(guess, charD ), apparitions(codeword, charD )); // adds the minimum apparitions of the character in both strings
        }
        return misses - getHits(codeword, guess); // subtraction of the hits from the total misses

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
    public static boolean anotherGame( Scanner in ){
        System.out.print("Another game (y/n)? "); // Replay
        String action;

        while (true) {
            action = in.next();
            if (action.charAt(0) == 'y' || action.charAt(0) == 'n') {
                return action.charAt(0) == 'y';
            }
            else{
                System.out.println("Invalid character, try again");
            }
        }


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
        for (i = 0; i < 4; i++){ // Checks for chars 1,2,3,4,5,6
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
        
        while ( moreGames ) {
            int round = 1;
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
                
                if (!codeword.equals(guess)){ // give hit-and-miss info if guess is incorrect
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


    // Generates all possible codewords and stores them in an array
    public static String[] generateAllCodewords() {
        String[] array = new String[1296]; // Set length

        int pos = 0; // position

        for (int i = 1; i <= 6; i++){
            for (int j = 1; j <= 6; j++){
                for (int k = 1; k <= 6; k++){
                    for (int l = 1; l <= 6; l++){ //4 nested loops, each one corresponds to one position
                        array[pos++] = "" + i + j + k + l; //concatenate and add 1 to the position
                    }
                }
            }
        }

        return array;
    } // end of generateAllCodewords method


    // deletes an element from an array
    public static String[] delete(String[] array, int pos){
        String[] newArray = new String[array.length - 1]; // create new array with one less element

        for (int i = 0; i < array.length; i++){ //iterate through every element in array
            if (i < pos) { // if less than position to be deleted, same position in new array
                newArray[i] = array[i];
            }
            else if (i > pos){ // if position is greater than deleted element, element goes to i - 1
                newArray[i-1] = array[i];
            }
        }
        return newArray;
    } // end of delete method


    // Finds the position of a codeword in the array
    public static int findPosition(String codeword, String[] array){
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(codeword)){
                return i;
            }
        }
        return -1;
    } // end of findPosition method


    // Eliminates candidates based on hits and misses
    public static String[] siftCandidates(int hits, int misses, String[] array, String codeword){
        int starCount = 0;
        for (int i = 0; i < array.length; i++){ // iterates through every element
            int elementHits = getHits(codeword, array[i]);
            int elementMisses = getMisses(codeword, array[i]);
            if (( elementHits != hits) || (elementMisses != misses)){
                array[i] = "*";
                starCount += 1;
            }
        }

        String[] newArray = new String[array.length-starCount];
        int j = 0;
        for (int i = 0; i < array.length; i++){
            if (!array[i].equals("*")) {
                newArray[j++] = array[i];
            }
        }


        return newArray;
    } // end of siftCandidates method


    // Gets a valid codeword from the user
    public static String inputCodeword(Scanner input){
        String codeword;
        while (true){
            System.out.print("Enter your codeword: ");
            codeword = input.next(); // input from user

            if (guessValid(codeword)) { // validates the codeword
                break;
            }

            System.out.println("This is an invalid codeword, try again."); // only executes if invalid
        }
        return codeword;
    } // end of inputCodeword method


    // Asks the user if it wants to input feedback
    public static boolean askForFeedback(Scanner input){
        System.out.print("Enter feedback yourself (y/n)? ");
        String action;

        while (true) {
            action = input.next();
            if (action.charAt(0) == 'y' || action.charAt(0) == 'n') {
                return action.charAt(0) == 'y';
            }
            else{
                System.out.println("Invalid character, try again");
            }
        }
    }

    // Executes the code for Part 2 of the project
    public static void playPartTwo(){
        Scanner keyboard = new Scanner(System.in);
        boolean moreGames = true;
        boolean feedback;

        while (moreGames) {
            System.out.println("---- Let's Play Mastermind (Part 2) ----");
            String codeword = inputCodeword(keyboard); // get codeword from user
            feedback = askForFeedback(keyboard); // feedback?
            String[] array = generateAllCodewords();
            int round = 1;

            while (true) {
                int hits;
                int misses;
                int position = (int)(Math.random() * array.length);
                String guess = array[position]; // get a random element from the array
                System.out.printf("Round %d, Size = %d, Guess = %s%n", round, array.length, guess);
                round++;

                if (array.length == 1){
                    System.out.println("The computer found the codeword!");
                    System.out.printf("The codeword was %s%n", array[0]);
                    break;
                }

                if (!feedback) {
                    hits = getHits(codeword, guess);
                    misses = getMisses(codeword, guess);
                } else {
                    System.out.print("Enter feedback: ");
                    String userFeedback = keyboard.next();

                    hits = userFeedback.charAt(0);
                    misses = userFeedback.charAt(1);
                }


                array = siftCandidates(hits, misses, array, guess);


                if (array.length == 0){
                    System.out.println("Error. No more candidates.");
                    break;
                }


            }

            if (!anotherGame(keyboard)) { // asks if user wants to play another game
                moreGames = false;
            }
        }

    } // end of playPartTwo method
} // end of Mastermind class
