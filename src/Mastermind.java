// import java.util.Scanner;


public class Mastermind {
    public static void main(String[] args) {
        
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

} // end of Mastermind class
