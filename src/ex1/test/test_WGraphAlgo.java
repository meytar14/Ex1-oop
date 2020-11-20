package ex1.test;
import ex1.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_WGraphAlgo {
    private static Random _rnd = null;
    @Test
    void copy()
    {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        weighted_graph g1=ag0.copy();
        assertEquals(g1,g0);
    }
    @Test
    void isConnected()
    {
        WGraph_DS g1=new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.connect(1,2,2);
        weighted_graph_algorithms ag1 = new WGraph_Algo();
        ag1.init(g1);
        assertFalse(ag1.isConnected());

        WGraph_DS g2=new WGraph_DS();
        g2.addNode(1);
        weighted_graph_algorithms ag2 = new WGraph_Algo();
        ag2.init(g2);
        assertTrue(ag2.isConnected());
    }
    @Test
    void shortestPathDist()
    {
        WGraph_DS g1=new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);
        g1.addNode(5);
        g1.addNode(6);
        g1.connect(1,2,2);
        g1.connect(2,4,3);
        g1.connect(2,3,1);
        g1.connect(3,6,0.5);
        g1.connect(4,6,2);
        weighted_graph_algorithms ag1 = new WGraph_Algo();
        ag1.init(g1);
        assertEquals(-1,ag1.shortestPathDist(1,5));//there isnt a path from node 1 to 3
        assertEquals(0,ag1.shortestPathDist(1,1));
        assertEquals(3.5,ag1.shortestPathDist(1,6));
    }
    @Test
    void shortestPath()
    {
        WGraph_DS g1=new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);
        g1.addNode(5);
        g1.addNode(6);
        g1.connect(1,2,2);
        g1.connect(2,4,3);
        g1.connect(2,3,1);
        g1.connect(3,6,0.5);
        g1.connect(4,6,2);
        weighted_graph_algorithms ag1 = new WGraph_Algo();
        ag1.init(g1);
        assertEquals(null,ag1.shortestPath(1,5));//there isnt a path from node 1 to 5
        ArrayList<node_info> Path=new ArrayList<node_info>();//path from dest to src
        Path.add(g1.getNode(1));
        assertEquals(Path,ag1.shortestPath(1,1));
    }
    @Test
    void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(15,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = WGraph_DSTest.graph_creator(15,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
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


