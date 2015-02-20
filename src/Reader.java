import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by John Griffith on 20/02/2015.
 */
public class Reader {


    private Set<Rules> rules;

    public Set<Rules> rules(){
        return rules;
    }

    void read(String filename) throws IOException{

        rules = new HashSet<Rules>();
        File file  = new File(filename);
        Scanner in = new Scanner(file);

        while(in.hasNextLine()) {
            String line = in.nextLine();
            String[] items = line.split(" ");

            if(items[0].contains("//")) continue;
            rules.add(new Rules(items[0], tail(items)));

        }

        in.close();


    }

    /*
        Creates a set of the tail of a string array
     */
    private Set<String> tail(String[] items){
        if(items.length<2) return null;

        Set<String> out = new HashSet<>();
        for(int i = 1; i<items.length; i++){
            out.add(items[i]);
        }
        return out;
    }

}
