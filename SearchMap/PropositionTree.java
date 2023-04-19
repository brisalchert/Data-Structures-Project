package SearchMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class PropositionTree {
    public char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',};
    public PropositionTree.SearchElement head = new PropositionTree.SearchElement('#', new PropositionTree.SearchElement[26], new HashSet<>());
    public HashMap<Character, Integer> characterToInteger = new HashMap<>();
    public HashSet<String> fillerWords = new HashSet<>();

    /**
     * Constructor
     */
    public PropositionTree() {
        for(int i = 0 ; i < 26; i++){ //set up conversion table
            characterToInteger.put(alphabet[i], i);
        }
    }

    /**
     * returns a hashset of possible propositions for the searched string
     *
     * @param string searched string
     * @return hashset of possible propositions for the searched string
     */
    public Proposition proposition(String string){
        HashSet<Proposition> results = new HashSet<>();
        if(Objects.equals(string, "")) { // if nothing is entered
            return null;
        }
        propHelper(head, string.toLowerCase().toCharArray(), 0, results); //get results

        Proposition bestMatch = null;
        int bestDistance = 1000;
        for(Proposition prop: results){ //find closest possible result if one exists
            int distance = levenshteinDistance(string.toLowerCase(), prop.name().toLowerCase());
            if (distance < bestDistance && distance <= string.length()/2){
                bestMatch = prop;
            }
        }

        return bestMatch;
    }

    /**
     * adds the results that are possible propositions for the searched characterPath to results
     * @param searchElement current searchElement being handled
     * @param characterPath character array representing the path to travel down the PropositionTree
     * @param pathIndex current index of the characterPath
     * @param results the possible results found by following the characterPath
     */
    private void propHelper(PropositionTree.SearchElement searchElement, char[] characterPath, int pathIndex, HashSet<Proposition> results ){
        if(pathIndex == characterPath.length-1){ //if end of characterPath is reached
            if(!searchElement.results.isEmpty()){  // and there are results
                results.addAll(searchElement.results); //return them
                return;
            }
            getResults(searchElement, results); // else look for other possible results
            return;
        }

        Integer address = characterToInteger.get(characterPath[pathIndex]);
        if(searchElement.paths[address] == null){ //if there is not a path to follow see what results are valid
            getResults(searchElement, results);
        }else {
            propHelper(searchElement.paths[address], characterPath, pathIndex + 1, results); //found path move to it
        }
    }

    /**
     * finds the distance between two words based on the edits required to make them similar
     * @param a string one
     * @param b string two
     * @return distance tween a and b
     */
    public static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1));
                }
            }
        }
        return dp[a.length()][b.length()];
    }


    /**
     * updates results with possible results based at current searchElement
     * @param searchElement current searchElement
     * @param results hashset to add potential results too
     */
    private void getResults(PropositionTree.SearchElement searchElement, HashSet<Proposition> results){
        for(PropositionTree.SearchElement path : searchElement.paths){// look for possible paths
            if(path != null){
                results.addAll(path.results);
                getResults(path, results);
            }
        }
    }

    /**
     * Adds the characterPath as a valid path in the tree
     * @param characterPath array of characters representing the path to be added
     * @param result the value that lies at the end of the characterPath
     */
    public void addCharacterPath(char[] characterPath, Proposition result){
        addSearchElement(head, result, characterPath, 0);
    }

    /**
     *  Adds a search element into PropositionTree
     * @param element current searchElement
     * @param result the result of this characterPath
     * @param characterPath array of searchElements that define the path through the proposition tree
     * @param pathIndex current index of the characterPath
     */
    private void addSearchElement(PropositionTree.SearchElement element, Proposition result, char[] characterPath, int pathIndex){
        if(pathIndex == characterPath.length){
            element.results.add(result);
            return;
        }else {
            Integer address = characterToInteger.get(characterPath[pathIndex]);
            if (element.paths[address] == null) {
                PropositionTree.SearchElement newNode = new PropositionTree.SearchElement(characterPath[pathIndex], new PropositionTree.SearchElement[26], new HashSet<Proposition>());
                element.paths[address] = newNode;
                addSearchElement(newNode, result, characterPath, pathIndex + 1); //created new path follow it
            } else {
                addSearchElement(element.paths[address], result, characterPath, pathIndex + 1); //found path move to it
            }
        }
    }


    private class SearchElement {
        private Character aChar; //character represented by element
        private PropositionTree.SearchElement[] paths = new PropositionTree.SearchElement[26]; //other possible paths
        private HashSet<Proposition> results; //results ending with this element

        SearchElement(Character aChar, PropositionTree.SearchElement[] paths, HashSet<Proposition> results){
            this.aChar = aChar;
            this.paths = paths;
            this.results = results;
        }

    }

}
