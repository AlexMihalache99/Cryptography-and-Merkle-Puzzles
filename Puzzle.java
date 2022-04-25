import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.crypto.SecretKey;
/**
 * Java class that creates Puzzles objects.
 * @author Alexandru Mihalache-986965
 *
 */
public class Puzzle {

    private int puzzleNumber;
    private SecretKey secretKey;
    
    /**
     * A Puzzle constructor instantiates Puzzle objects.
     * @param puzzleNumber number of the puzzle.
     * @param secretKey secret key of the puzzle.
     */
    public Puzzle(int puzzleNumber, SecretKey secretKey){

        this.puzzleNumber = puzzleNumber;
        this.secretKey = secretKey;
        
    }
    
    /**
     * Getter for the puzzle number.
     * @return int which is the puzzle number.
     */
    public int getPuzzleNumber(){
    	
        return this.puzzleNumber;
    }
    
    /**
     * Getter for the puzzle key.
     * @return SecretKey which is the key of the puzzle object.
     */
    public SecretKey getKey(){
    	
        return this.secretKey;
    }
    
    /**
     * Construct the puzzle into a byte array 
     * using the specified format.
     * @return byte array that represents the puzzle in bytes.
     */
    public byte[] getPuzzleAsBytes(){

        byte [] zeros = new byte[16];
        byte[] number = CryptoLib.smallIntToByteArray(puzzleNumber);
        byte[] key = this.secretKey.getEncoded();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
        	
            outputStream.write(zeros);
            outputStream.write(number);
            outputStream.write(key);
        } catch (IOException e) {
        }

        byte puzzle[] = outputStream.toByteArray();

        return puzzle;

    }
}
