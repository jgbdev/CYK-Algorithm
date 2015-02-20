import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cky {

    public static void main(String[] args) {
        Cky program = new Cky();
        program.run(args);
    }

    private void run(String[] args){
        if(args.length!=2){
            System.err.print("Please supply two arguments, rule file and string");
            System.exit(1);
        }

        Set<Rules> rulesSet = new HashSet<>();

        try{
            Reader reader = new Reader();
            reader.read(args[0]);
            rulesSet = reader.rules();
        }catch (IOException e){
            System.err.print(e.getMessage());
        }

        int len = args[1].length();
        String input = args[1];

        Set[][] grid = new HashSet[len][len];
        for(int x=0;x<len;x++){
            for(int y=0;y<len;y++){
                grid[x][y] = new HashSet<>();
            }
        }

        int j = 0;
        for(int i = 0; i < len ; i ++ ){
            char a = input.charAt(i);
            String string = String.valueOf(a);
            Set<String> foundStrings = parseString(string,rulesSet);
            grid[i][0] = foundStrings;
        }

        j = 1;

        for( ; j<len;j++){

            for(int i = 0; i < len ; i++) {
                int x = 1;

                Set<String> joint = new HashSet<>();

                while (x <= j ) {
                    if( i+x >= len) break;
                    Set<String> joined = joinSet( grid[i][x-1], grid[i+x][j-x]  );
                    for(String seek : joined){
                        joint.addAll(parseString(seek, rulesSet));
                    }
                    x++;
                }
                grid[i][j] = joint;
            }
        }
        printSet(grid,len);


    }

    private void printSet(Set<String>[][] sets, int len){
        int[] colWidth = new int[len];

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
                System.out.print(" "+filler(fillerStart)+ setToString(sets[x][y]) + filler(fillerEnd) + " "  );
            }
            System.out.println();

        }


    }

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
        for(String string : set){
            s.append(string);
        }
        return s.toString();
    }

    private Set<String> joinSet(Set<String> set1, Set<String> set2){

        Set<String> combination = new HashSet<>();

        if(set1.size()==0)return set2;
        if(set2.size()==0)return set1;

        for(String s1 : set1){
            for(String s2 : set2){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(s1);
                stringBuilder.append(s2);
                combination.add(stringBuilder.toString());
            }
        }
        return combination;
    }

    private Set<String> parseString(String string, Set<Rules> rulesSet){
        Set<String> output = new HashSet<>();
        for(Rules rules : rulesSet){
            for(String neighbor : rules.getNeighbors()){
                if(neighbor.equals(string)){
                    output.add( rules.getNode());
                }
            }
        }

        return output;
    }
















}
