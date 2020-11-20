package ex1.test;
import ex1.WGraph_DS;
import ex1.node_info;
import ex1.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_WGraphDS {
    private static Random _rnd = null;
    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);

        g.addNode(0);
        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(1);
        g.removeNode(4);//there isnt a node in the graph with the key 4
        int nodeSize=g.nodeSize();
        assertEquals(2, nodeSize);
    }
    @Test
    void getNode()
    {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.removeNode(2);
        WGraph_DS.NodeInfo n=(WGraph_DS.NodeInfo)g.getNode(2);
        assertEquals(null, n);
    }
    @Test
    void hasEdge(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,3,1);
        g.connect(0,1,2);
        assertFalse(g.hasEdge(3,0));
        assertTrue(g.hasEdge(1,0));
    }
    @Test
    void MC(){
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        int MC1= g0.getMC();
        g0.removeNode(12);//there isnt a node in the graph with the key 12 so the MC doesnt supose to change
        g0.connect(1,11,3);//there isnt a node in the graph with the key 11 so the MC doesnt supose to change
        g0.removeEdge(0,1);//there isnt an edge between node 0 and 1 so the MC doesnt supose to change.
        int MC2= g0.getMC();
        assertEquals(MC1,MC2);
    }
    @Test
    void getEdge(){
        weighted_graph g0 = WGraph_DSTest.graph_creator(5,7,1);
        double e=g0.getEdge(3,4);
        assertEquals(e,-1);
        g0.connect(3,4,1.5);
        e=g0.getEdge(3,4);
        assertEquals(e,1.5);
    }
    @Test
    void edgeSize(){
        weighted_graph g0 = WGraph_DSTest.graph_creator(5,7,1);
        int edgeSize=g0.edgeSize();
        g0.removeEdge(0,2);//there isnt an edge between node 0 and 2 so the edgeSize doesnt supose to change.
        g0.removeNode(7);//there isnt a node with a key-7 so the edgeSize doesnt supose to change.
        assertEquals(edgeSize,g0.edgeSize());
    }
    @Test
    void addNode()
    {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(2);
        assertEquals(3,g.nodeSize());
    }
    @Test
    void getv()
    {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,20,1);
        assertEquals(g0.getV().size(),g0.nodeSize());
    }
    @Test
    void removeNode()
    {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,20,1);
        int niSize=((WGraph_DS.NodeInfo)g0.getNode(1)).getNi().size();//number of neighbors that node 1 have
        int numofEdges= g0.edgeSize();
        g0.removeNode(1);
        assertEquals(numofEdges-niSize,g0.edgeSize());
    }
    @Test
    void connect()
    {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,20,1);
        g0.connect(2,12,3);
        assertFalse(g0.hasEdge(2,12));
        g0.connect(3,4,3);
        assertEquals(g0.getEdge(3,4),3);
    }


















    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}
