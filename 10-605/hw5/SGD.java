import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 * @author Shannon Quinn
 *
 */
public class SGD {

    public static final String TEMP1 = "tempfile1.txt";
    
    /**
     * Runs the SGD algorithm.
     * @param args
     */
    public static void main(String[] args) {
        // Parse out a few of the arguments.
        int documents = Integer.parseInt(args[1]);
        String tempDir = args[2];
        String encoding = (args.length == 4 ? args[3] : "UTF-8");

        ArrayList<Integer> lengths = new ArrayList<Integer>(documents);
        ArrayList<Long> offsets = new ArrayList<Long>(documents);
        
        String tempfile = tempDir + SGD.TEMP1;
        
        // Run through the corpus once.
        Scanner input = new Scanner(System.in);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(new File(tempfile));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        long sum = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine() + "\r\n";
            byte [] bytes = null;
            try {
                bytes = line.getBytes(encoding);
            } catch (UnsupportedEncodingException e) {
                System.err.println(encoding + " is unsupported. Try another charset.");
                e.printStackTrace();
                System.exit(1);
            }
            // Oh yes. I'm doing what you think I'm doing.
            int length = bytes.length;
            Long offset = new Long(sum);
            lengths.add(new Integer(length));
            offsets.add(offset);
            sum += length;
            try {
                output.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        // Now for each subsequent run, we read the data as follows.
        Random r = new Random();
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile(new File(SGD.TEMP1), "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (offsets.size() > 0) {
            int index = r.nextInt(offsets.size());
            long offset = offsets.get(index).longValue();
            int length = lengths.get(index).intValue();
            byte [] buffer = new byte[length];
            try {
                reader.seek(offset);
                reader.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            String document = null;
            try {
                document = new String(buffer, encoding).trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.exit(1);
            }
            
            // We have our random document!
        }
    }

}
