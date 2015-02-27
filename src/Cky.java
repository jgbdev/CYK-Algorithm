import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Cky {

    Set<Rules> rulesSet;

    public static void main(String[] args) {
        Cky program = new Cky();
        program.run(args);
    }

    public Cky(){
        rulesSet = new HashSet<>();
    }

    private void run(String[] args){
        if(args.length!=2){
            System.err.print("Please supply two arguments, rule file and string");
            System.exit(1);
        }

        try{
            Reader reader = new Reader();
            reader.read(args[0]);
            rulesSet = reader.rules();
        }catch (IOException e){
            System.err.print(e.getMessage());
        }
        List<String> strings = stringSplitter(args[1]);

        int len = strings.size();

        Set[][] grid = new HashSet[len][len];
        for(int x=0;x<len;x++){
            for(int y=0;y<len;y++){
                grid[x][y] = new HashSet<>();
            }
        }

        int n = 0;
        for(String str: strings){
            Set<String> foundStrings = parseString(str);
            grid[n][0] = foundStrings;
            n++;
        }

        int j = 1;

        for( ; j<len;j++){

            for(int i = 0; i < len ; i++) {
                int x = 1;

                Set<String> joint = new HashSet<>();

                while (x <= j ) {
                    if( i+x >= len) break;
                    Set<String> joined = joinSet( grid[i][x-1], grid[i+x][j-x]  );
                    for(String seek : joined){
                        joint.addAll(parseString(seek));
                    }
                    x++;
                }
                grid[i][j] = joint;
            }
        }
        printSet(grid,len);


    }

    /*
    Splits the input string into individual variables
     */
    private List<String> stringSplitter(String s){
        StringBuilder stringBuilder = new StringBuilder();
        int len = s.length();
        List<String> output = new ArrayList<>();

        for(int i = 0; i<len; i++){
            stringBuilder.append(s.charAt(i));
            if(isValid(stringBuilder.toString())){
                output.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }

        }
        return output;
    }

    //Checks if a string is accepted by the grammar
    private boolean isValid(String s){
        for(Rules rules: rulesSet){
            if(rules.getNeighbors().contains(s)){
                return true;
            }
        }
        return false;
    }

    private void printSet(Set<String>[][] sets, int len){
        int[] colWidth = new int[len];

        //Gets the maximum column width to align output
        for(int y = 0; y<len;y++){
            for(int x = 0; x<len ; x++){
                if(colWidth[y]<sets[x][y].size()){
                    colWidth[y] = sets[x][y].size();
                }
            }
            System.out.println();

        }

        for(int y = len -1 ; y>=0;y-- ){
            for(int x = 0; x<len; x++){
                int size = colWidth[x] - sets[x][y].size();
                int fillerStart = size/2;
                int fillerEnd = size - fillerStart;
                System.out.print(" "+filler(fillerStart)+ setToString(sets[x][y]) + filler(fillerEnd) + " " );
            }
            System.out.println();

        }


    }

    //Return a string of n spaces
    private String filler(int n){
        if(n>0) {
            char[] array = new char[n];
            Arrays.fill(array,' ');
            return new String(array);
        }
        return "";
    }


    private String setToString(Set<String> set){
        StringBuilder s = new StringBuilder();
        set.forEach( (str)->s.append(str) );
        return s.toString();
    }

    /*
        Concatenates two sets of strings
     */
    private Set<String> joinSet(Set<String> set1, Set<String> set2){

        Set<String> combination = new HashSet<>();

        if(set1.size()==0)return set2;
        if(set2.size()==0)return set1;

        set1.forEach(setF -> set2.forEach( setT -> combination.add(setF + setT) ) );

        return combination;
    }

    private Set<String> parseString(String string){

        return rulesSet.stream().filter( rule -> rule.getNeighbors().contains(string) ).map(Rules::getNode).collect(Collectors.toSet());

    }
















}
