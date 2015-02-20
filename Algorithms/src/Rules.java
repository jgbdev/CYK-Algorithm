import java.util.Set;

/**
 * Created by John Griffith on 20/02/2015.
 */
public class Rules {
    private String node;
    private Set<String> neighbors;

    public Rules(String node, Set<String> neighbors){
        this.node  = node;
        this.neighbors = neighbors;
    }

    public String getNode(){
        return node;
    }
    public Set<String> getNeighbors(){
        return neighbors;
    }
}
