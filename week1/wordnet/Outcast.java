/* *****************************************************************************
 *  Name: Xinlin
 *  Date: 05/09/2019
 *  Description:Outcast detection. Given a list of WordNet nouns x1, x2, ..., xn,
 *  which noun is the least related to the others? To identify an outcast,
 *  compute the sum of the distances between each noun and every other one:
 *  di   =   distance(xi, x1)   +   distance(xi, x2)   +   ...   +   distance(xi, xn)
 *  and return a noun xt for which dt is maximum.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        int n = nouns.length;
        // maintain the maximum distance and current outcast index
        int d = 0;
        int index = 0;

        for (int i = 0; i < n; i++) {
            String noun = nouns[i];
            // distance accumulator
            int distSum = 0;

            for (String other : nouns) {
                if (!noun.equals(other)) {
                    distSum += wordnet.distance(noun, other);
                }

            }
            if (distSum > d) {
                d = distSum;
                index = i;
            }
        }

        return nouns[index];


    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }
}
