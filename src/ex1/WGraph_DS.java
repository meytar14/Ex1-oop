package ex1;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class WGraph_DS implements weighted_graph , Serializable{
    private HashMap<Integer,node_info> nodes;
    private int numOfEdges=0;
    private int MC=0;

    public WGraph_DS()
    {
        this.nodes=new HashMap<Integer,node_info>();
    }
    public WGraph_DS(weighted_graph g){
        if(g instanceof WGraph_DS){

            HashMap<Integer,node_info> nodes=new HashMap<Integer,node_info>();
            NodeInfo temp;
            for(node_info n:g.getV()){
                temp=((NodeInfo)n).copy();
                nodes.put(temp.getKey(),temp);
            }
            for(node_info n:g.getV())
            {
                NodeInfo nodeInThisGraph =(NodeInfo)nodes.get(n.getKey());
                for(node_info ni:((NodeInfo)n).getNi())
                {
                    if(!nodeInThisGraph.ni.containsKey(ni.getKey())) {
                        nodeInThisGraph.addNi((NodeInfo)nodes.get(ni.getKey()),((NodeInfo)n).getWeight(ni.getKey()));
                    }
                }
            }
            this.nodes=nodes;
            this.MC=g.getMC();
            this.numOfEdges=g.edgeSize();
        }
    }
    @Override
    public node_info getNode(int key) {
        if(this.nodes.containsKey(key))
            return this.nodes.get(key);
        return null;
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if(((NodeInfo)this.nodes.get(node1)).getNi().contains(nodes.get(node2)))
            return true;
        if(node1==node2)
            return true;
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2))
        {
            return ((NodeInfo)this.nodes.get(node1)).weights.get(node2);
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(!this.nodes.containsKey(key))
        {
            NodeInfo n=new NodeInfo(key,"",0);
            nodes.put(n.getKey(),n);
        }
        MC++;
    }
    public void addNode(int key,String info,double tag) {
        if(!this.nodes.containsKey(key))
        {
            NodeInfo n=new NodeInfo(key,info,tag);
            nodes.put(n.getKey(),n);
        }
        MC++;
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if(nodes.get(node1)!=null &&nodes.get(node2)!=null&& node1!=node2 && w>=0) {
            if(!((NodeInfo) nodes.get(node1)).getNi().contains(nodes.get(node2))) {
                numOfEdges++;
            }
            ((NodeInfo) nodes.get(node1)).addNi((NodeInfo) nodes.get(node2), w);
            ((NodeInfo) nodes.get(node2)).addNi((NodeInfo) nodes.get(node1), w);
            MC++;
        }
    }

    @Override
    public Collection<node_info> getV() {
        return nodes.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        return ((NodeInfo)nodes.get(node_id)).getNi();
    }

    @Override
    public node_info removeNode(int key) {
        if(nodes.containsKey(key))
        {
            NodeInfo n=(NodeInfo)nodes.get(key);
            node_info temp;
            Iterator<node_info> it=n.getNi().iterator();
            while(it.hasNext())
            {
                temp=it.next();
                it.remove();
                n.removeNode(temp);
                numOfEdges--;
            }
            nodes.remove(key);
            MC++;
            return n;
        }
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if(nodes.get(node1)!=null) {
            if (((NodeInfo) nodes.get(node1)).getNi().contains(nodes.get(node2))){
                ((NodeInfo) nodes.get(node1)).removeNode(nodes.get(node2));
                numOfEdges--;
                MC++;
                ((NodeInfo) nodes.get(node2)).removeNode(nodes.get(node1));

            }
        }
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() { return numOfEdges; }

    @Override
    public int getMC() {
        return MC;
    }

    public HashMap gethm()
    {
        return this.nodes;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof WGraph_DS)
        {
            WGraph_DS other =(WGraph_DS)obj;
            for(node_info n:other.getV())
            {
                NodeInfo temp=(NodeInfo)n;
                if(!temp.equals(this.getNode(temp.getKey())))
                {
                    return false;
                }
            }
            for(node_info n:this.getV())
            {
                NodeInfo temp=(NodeInfo)n;
                if(!temp.equals(other.getNode(temp.getKey())))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static class NodeInfo implements node_info, Comparator<NodeInfo>, Serializable {
        private int key;
        private String info;
        private double tag;
        private int numofNodes=0;
        private HashMap<Integer,node_info> ni;
        private HashMap<Integer,Double> weights;
        public NodeInfo(int key)
        {
            this.key=key;
            this.info="";
            this.tag=0;
            ni=new HashMap<Integer,node_info>();
            weights=new HashMap<Integer,Double>();
            numofNodes++;
        }
        public NodeInfo(int key,String info, double tag)
        {
            this.key=key;
            this.info=info;
            this.tag=tag;
            ni=new HashMap<Integer,node_info>();
            weights=new HashMap<Integer,Double>();
            numofNodes++;
        }
        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info=s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag=t;
        }
        public double getWeight(int n)
        {
            if(weights.containsKey(n))
                return weights.get(n);
            return -1;
        }
        public void addNi(node_info n,double w)
        {
            if(!this.ni.containsKey(n.getKey())) {
                this.ni.put(n.getKey(), n);
                this.weights.put(n.getKey(), w);
            }
            else
            {
                this.weights.put(n.getKey(), w);
            }
        }
        public Collection<node_info> getNi()
        {
            return this.ni.values();
        }

        public void removeNode(node_info n) {
            if(ni.containsKey(n.getKey()))
            {
                ni.remove(n.getKey());
                weights.remove(n.getKey());
            }
        }
        public void setKey(int key) { this.key=key; }

        public int getNumofNodes()
        {
            return numofNodes;
        }
        public NodeInfo copy()
        {
            NodeInfo n=new NodeInfo(this.key,this.info,this.tag);
            return n;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof NodeInfo) {
                NodeInfo other = (NodeInfo) obj;
                if(this.getKey()==other.getKey())
                {
                    for(node_info ni:other.getNi())
                    {
                        if(!this.ni.containsKey(ni.getKey()))
                        {
                            return false;
                        }
                        if(other.getWeight(ni.getKey())!=this.getWeight(ni.getKey()))
                            return false;
                    }
                    return true;
                }
            }
            return false;
        }




        @Override
        public int compare(NodeInfo o1, NodeInfo o2) {
            return Double.compare(o1.getTag(),o2.getTag());
        }
    }
}
