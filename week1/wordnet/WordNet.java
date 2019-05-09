/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 3/ 10 /2019
 *  Description: Princeton Algorithms Part II Assignment 1
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
    // stores the mapping from index to string
    private SeparateChainingHashST<Integer, String> idMap
            = new SeparateChainingHashST<Integer, String>();
    // stores the mapping from string to index
    private SeparateChainingHashST<String, Bag<Integer>> wordMap
            = new SeparateChainingHashST<String, Bag<Integer>>();

    // store sap
    private SAP sap;


    public WordNet(String synsets, String hypernyms) {
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            String[] words = tokens[1].split(" ");
            idMap.put(id, tokens[1]);

            for (int i = 0; i < words.length; i++) {
                if (!wordMap.contains(words[i])) {
                    wordMap.put(words[i], new Bag<Integer>());
                }
                wordMap.get(words[i]).add(id);
            }
        }
        // stores hypernyms digraph
        Digraph G;

        in = new In(hypernyms);
        G = new Digraph(idMap.size());
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int vStart = Integer.parseInt(tokens[0]);
            for (int i = 1; i < tokens.length; i++) {
                int vEnd = Integer.parseInt(tokens[i]);
                G.addEdge(vStart, vEnd);
            }
        }
        sap = new SAP(G);

    }

    public Iterable<String> nouns() {
        return wordMap.keys();

    }

    public boolean isNoun(String word) {
        return wordMap.contains(word);
    }

    public int distance(String nounA, String nounB) {
        Bag<Integer> clusterA = wordMap.get(nounA);
        Bag<Integer> clusterB = wordMap.get(nounB);
        int distance = sap.length(clusterA, clusterB);
        return distance;

    }

    public String sap(String nounA, String nounB) {
        Bag<Integer> clusterA = wordMap.get(nounA);
        Bag<Integer> clusterB = wordMap.get(nounB);
        int ancestor = sap.ancestor(clusterA, clusterB);
        return idMap.get(ancestor);

    }

    public static void main(String[] args) {
        String synsetsName = args[0];
        String hypernymsName = args[1];

        WordNet wn = new WordNet(synsetsName, hypernymsName);
        StdOut.printf("Please type two words ...\n");
        while (!StdIn.isEmpty()) {
            String word1 = StdIn.readString();
            String word2 = StdIn.readString();
            // verify both nouns are in the tree
            if (wn.isNoun(word1) && wn.isNoun(word2)) {
                int dist = wn.distance(word1, word2);
                String ancestor = wn.sap(word1, word2);
                StdOut.printf("The distance is %d and the common ancestor is %s", dist, ancestor);
            }
            else {
                StdOut.printf("Not all the input words are in the synset.");
            }
        }

    }
}
