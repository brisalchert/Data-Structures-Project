package SearchMap;
import Attributes.Values;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class SearchMap {
    public CharNode head = new CharNode('#', new CharNode[26], new HashSet<Values>());
    public char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',};
    public HashMap<Character, Integer> integerHashMap = new HashMap<>();
    public HashSet<String> fillerWords = new HashSet<>();
    Scanner scan = new Scanner(new File("SearchMap/english"));

    public SearchMap() throws FileNotFoundException {
        for(int i = 0 ; i < 26; i++){
            integerHashMap.put(alphabet[i], i);
        }
        while(scan.hasNextLine()){
            fillerWords.add(scan.nextLine().toLowerCase());
        }
    }

    public HashSet<Values> suggestion(String string){
        HashSet<Values> results = new HashSet<>();
        suggestionHelper(head, head, string.toLowerCase().toCharArray(), 0, results);

        return results;
    }

    private void suggestionHelper(CharNode currentNode, CharNode prevNode, char[] string, int index, HashSet<Values> results ){
        if(index == string.length){ //if end of string is reached
            if(!currentNode.results.isEmpty()){ // and there are results
                results.addAll(currentNode.results);//return them
                return;
            }
            getResults(currentNode, results);
            return;
        }

        Integer address = integerHashMap.get(string[index]);
        if(currentNode.paths[address] == null){
            getResults(prevNode, results);
        }else {
            suggestionHelper(currentNode.paths[address], currentNode, string, index + 1, results); //found path move to it
        }
    }

    private void getResults(CharNode currentNode, HashSet<Values> results){
        for(CharNode path : currentNode.paths){// look for possible paths
            if(path != null){
                results.addAll(path.results);
                getResults(path, results);
            }
        }
    }

    public void addValue(char[] string, Values result){
        addNode(head, result, string, 0);
    }

    private void addNode(CharNode currentNode,Values result, char[] string, int index){
        if(index == string.length){
            currentNode.results.add(result);
            return;
        }
        Integer address = integerHashMap.get(string[index]);
        if(currentNode.paths[address] == null){
            CharNode newNode = new CharNode(string[index], new CharNode[26], new HashSet<Values>());
            currentNode.paths[address] = newNode;
            addNode(newNode, result, string, index + 1); //created new path follow it
        }else {
            addNode(currentNode.paths[address], result, string, index + 1); //found path move to it
        }
    }


    private class CharNode implements Comparable<CharNode> {
        private Character aChar;
        public CharNode[] paths = new CharNode[26];
        private HashSet<Values> results;

        CharNode(Character aChar, CharNode[] paths, HashSet<Values> results){
            this.aChar = aChar;
            this.paths = paths;
            this.results = results;
        }

        @Override
        public int compareTo(CharNode node) {
            return this.aChar.compareTo(node.aChar);
        }
    }


}
