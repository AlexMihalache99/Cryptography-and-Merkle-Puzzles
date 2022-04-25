import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Java class that creates 4096 random puzzles.
 * Every puzzle needs a key so a random key generator was made.
 * Using the random key and the DES cipher the puzzles are being encrypted.
 * The encrypted puzzles are parsed into a binary file.
 * A method to find the key of a common puzzle has been developed.
 * @author Alexandru Mihalache-986965
 *
 */

public class PuzzleCreator {

    private ArrayList<Puzzle> puzzles;//contains all the 4096 random generated puzzles
    
    /**
     * Empty constructor of the class.
     */
    public PuzzleCreator(){

    }
    
    /**
     * Creates an ArrayList array with all the 4096 random generated puzzles.
     * @return an ArrayList<Puzzle> with all the random generated puzzles.
     */
    public ArrayList<Puzzle> createPuzzles(){

        puzzles = new ArrayList<Puzzle>();

        for(int i = 1; i <= 4096; i++){
            Puzzle puzzle = null;
            try {
                puzzle = new Puzzle(i, CryptoLib.createKey(createRandomKey()));
            } catch (InvalidKeyException e) {

            } catch (InvalidKeySpecException e) {

            } catch (NoSuchAlgorithmException e) {

            }
            puzzles.add(puzzle);
        }

        return puzzles;
    }
    
    /**
     * Method to generate a random key as a byte array.
     * @return a byte array containing a random generated key.
     */
    public byte[] createRandomKey(){

        byte[] randomArray = new byte[2];
        new Random().nextBytes(randomArray);

        byte[] zerosArray = new byte[6];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(randomArray);
            outputStream.write(zerosArray);
        } catch (IOException e) {
            
        }

        byte[] key = outputStream.toByteArray();
        return key;

    }
    
    /**
     * Encrypt the puzzle object into a byte array.
     * @param key used for the encryption of the puzzle.
     * @param puzzle puzzle to be encrypted.
     * @return a byte array with the encryption of the puzzle.
     */
    public byte[] encryptPuzzle(byte[] key, Puzzle puzzle){

        byte[] puzzleAsBytes = puzzle.getPuzzleAsBytes();
        byte[] encryptedPuzzle = null;

        try {
            Cipher ecipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, CryptoLib.createKey(key));
            encryptedPuzzle = ecipher.doFinal(puzzleAsBytes);

        } catch (NoSuchAlgorithmException e1) {

        } catch (NoSuchPaddingException e2) {

        } catch (InvalidKeyException e3) {

        } catch (InvalidKeySpecException e4) {

        } catch (IllegalBlockSizeException e5) {

        } catch (BadPaddingException e6) {
    
        }

        return encryptedPuzzle;
        
    }
    
    /**
     * Method to parse all the encrypted puzzles to a
     * binary file.
     * @param filename binary file with the encrypted puzzles. 
     */
    public void encryptPuzzlesToFile(String filename){
    	
        try{
        	
            File file = new File(filename);
            FileOutputStream fos = new FileOutputStream(file);

            for(int i = 0; i < puzzles.size(); i++){
                fos.write(encryptPuzzle(puzzles.get(i).getKey().getEncoded(), puzzles.get(i)));
            }

            fos.close();
            
        }catch(IOException e){
            
        }


    }
    
    /**
     * Method to find the SecretKey of a specific puzzle.
     * @param number the puzzle number
     * @return the SecretKey of the puzzle.
     */
    public SecretKey findKey(int number){

        SecretKey key = puzzles.get(number - 1).getKey();

        return key;
    }


}
