import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * 
 * The main class to test all the implementation done in the helper classes.
 * Creates a collection of puzzles which are then encrypted in a binary file.
 * Then from the encrypted puzzles, one of them is cracked and we found its
 * SecretKey, which will act like a shared Key.
 * And then using the shared key, we encrypt a message("Testing Merkles Puzzles")
 * and then the message is decrypted.
 * @author Alexandru Mihalache-986965
 *
 */

public class Merkle {


    public static void main(String[] args) throws Exception {
    	
    	//Helpful variables
    	Random rand = new Random();
    	int upperbound = 4096;
    	int byteSize = 32;
    	
    	//Generates 4096 random puzzles and parses them to a binary file
        PuzzleCreator creator = new PuzzleCreator();
        creator.createPuzzles();
        creator.encryptPuzzlesToFile("alice.bin");
        
        //Cracks a random puzzle from the binary file
        PuzzleCracker puzzlecracker = new PuzzleCracker("alice.bin");
        Puzzle crackedPuzzle = puzzlecracker.crack(rand.nextInt(upperbound));
        
        //Finds the shared key
        SecretKey key = creator.findKey(crackedPuzzle.getPuzzleNumber());
        
        //Encrypts a message using the key and DES cipher.
        byte [] encryption = new byte[byteSize];
        try {
        	 Cipher ecipher = Cipher.getInstance("DES");
             ecipher.init(Cipher.ENCRYPT_MODE, key);
             encryption = ecipher.doFinal("Testing Merkles Puzzles".getBytes());
        }catch(Exception e) {
        	
        }
        
        //decrypts the message
        puzzlecracker.decryptMessage(encryption);

;    }
}
