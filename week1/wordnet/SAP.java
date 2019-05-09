/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 3/6/2019
 *  Description: Algorithms week 1 assignment SAP
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph G;
    private int V;
    private int E;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.G = G;
        V = G.V();
        E = G.E();
    }

    /*
    length and ancestor function need to be modified so that bfs would not be call in both.
     */
    public int length(int v, int w) {
        ConnectedComponent ccV = new ConnectedComponent(v);
        ConnectedComponent ccW = new ConnectedComponent(w);
        int shortestLen = Integer.MAX_VALUE;
        int ancestor = -1;


        SET<Integer> markedV = ccV.marked();
        SET<Integer> markedW = ccW.marked();

        for (int s : markedV) {
            if (markedW.contains(s)) {
                int length = ccV.distTo(s) + ccW.distTo(s);
                if (length < shortestLen) {
                    shortestLen = length;
                    ancestor = s;
                }
            }
        }
        // no such path
        if (ancestor == -1) {
            return -1;
        }
        else {
            return shortestLen;
        }

    }

    public int ancestor(int v, int w) {
        ConnectedComponent ccV = new ConnectedComponent(v);
        ConnectedComponent ccW = new ConnectedComponent(w);
        int shortestLen = Integer.MAX_VALUE;
        int ancestor = -1;

        SET<Integer> markedV = ccV.marked();
        SET<Integer> makredW = ccW.marked();
        for (int s : markedV) {
            // common ancestor found
            if (makredW.contains(s)) {
                int length = ccV.distTo(s) + ccW.distTo(s);
                if (length < shortestLen) {
                    shortestLen = length;
                    ancestor = s;
                }
            }
        }
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        ConnectedComponent ccV = new ConnectedComponent(v);
        ConnectedComponent ccW = new ConnectedComponent(w);

        int shortestLen = Integer.MAX_VALUE;
        int ancestor = -1;

        SET<Integer> markedV = ccV.marked();
        SET<Integer> markedW = ccW.marked();


        for (int s : markedV) {
            if (markedW.contains(s)) {
                int length = ccV.distTo(s) + ccW.distTo(s);
                if (length < shortestLen) {
                    shortestLen = length;
                    ancestor = s;
                }
            }
        }
        // no such path
        if (ancestor == -1) {
            return -1;
        }
        else {
            return shortestLen;
        }

    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        ConnectedComponent ccV = new ConnectedComponent(v);
        ConnectedComponent ccW = new ConnectedComponent(w);

        int shortestLen = Integer.MAX_VALUE;
        int ancestor = -1;

        SET<Integer> markedV = ccV.marked();
        SET<Integer> markedW = ccW.marked();

        for (int s : markedV) {
            if (markedW.contains(s)) {
                int length = ccV.distTo(s) + ccW.distTo(s);
                if (length < shortestLen) {
                    shortestLen = length;
                    ancestor = s;
                }
            }
        }
        return ancestor;

    }

    /*
    perform bfs on G from vertex v.
     */
    private class ConnectedComponent {
        private SET<Integer> marked = new SET<Integer>();
        private int[] distTo = new int[V];
        private int start;
        private Iterable<Integer> startMulti;

        public ConnectedComponent(int v) {
            validateVertex(v);
            start = v;
            bfsSingleSource();
        }

        public ConnectedComponent(Iterable<Integer> vertices) {
            validateVertex(vertices);
            startMulti = vertices;
            bfsMultiSource();
        }

        public SET<Integer> marked() {
            return marked;
        }

        // distance from v to w
        public int distTo(int w) {
            return distTo[w];
        }

        public int start() {
            return start;
        }

        private void bfsSingleSource() {
            Queue<Integer> q = new Queue<Integer>();
            marked.add(start);
            distTo[start] = 0;
            q.enqueue(start);

            while (!q.isEmpty()) {
                int s = q.dequeue();
                for (int v : G.adj(s)) {
                    if (!marked.contains(v)) {
                        q.enqueue(v);
                        distTo[v] = distTo[s] + 1;
                        marked.add(v);
                    }
                }
            }

        }

        private void bfsMultiSource() {
            Queue<Integer> q = new Queue<Integer>();
            for (int s : startMulti) {
                marked.add(s);
                distTo[s] = 0;
                q.enqueue(s);
            }
            while (!q.isEmpty()) {
                int s = q.dequeue();
                for (int v : G.adj(s)) {
                    if (!marked.contains(v)) {
                        q.enqueue(v);
                        distTo[v] = distTo[s] + 1;
                        marked.add(v);
                    }
                }
            }
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));

    }

    private void validateVertex(Iterable<Integer> vertices) {
        if (vertices == null) throw new IllegalArgumentException("input vertices cannot be null.");
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException(
                        "vertex " + v + "is not between 0 and " + (V - 1));
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            // test acestor methods works for iterable input
            /*
            SET<Integer> vCluster = new SET<Integer>();
            SET<Integer> wCluster = new SET<Integer>();

            vCluster.add(v);
            wCluster.add(w);

            int length = sap.length(vCluster, wCluster);
            int ancestor = sap.ancestor(vCluster, wCluster);
            */
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

    }
}
