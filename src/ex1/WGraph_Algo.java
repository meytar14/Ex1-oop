package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    private WGraph_DS g;
    public WGraph_Algo()
    {
        this.g=null;
    }
    public WGraph_Algo(weighted_graph g)
    {
        if(g instanceof WGraph_DS)
        {
            this.g=(WGraph_DS)g;
        }
    }

    @Override
    public void init(weighted_graph g) {
        if(g instanceof WGraph_DS)
        {
            this.g=(WGraph_DS)g;
        }
    }

    @Override
    public weighted_graph getGraph() { return g; }

    @Override
    public weighted_graph copy() {
        weighted_graph graph= new WGraph_DS(this.g);
        return graph;
    }

    @Override
    public boolean isConnected() {
        if(this.g.gethm().size()-1>this.g.edgeSize())
            return false;
        Iterator<node_info> iter =this.g.getV().iterator();
        node_info n;
        if(iter.hasNext())
        {
            n= iter.next();
        }
        else {
            return true;
        }
        for (node_info node:g.getV()) { // set all tags in the graph to 0 which means they are unvisited yet
            node.setTag(0);
        }
        LinkedList<node_info> nextToVisit =new LinkedList<node_info>();
        HashSet<node_info> visited =new HashSet<node_info>();//the nodes we already visited
        nextToVisit.add(n);
        visited.add(n);
        node_info temp;
        while(!nextToVisit.isEmpty())
        {
            temp=nextToVisit.poll();
            for(node_info ni: ((WGraph_DS.NodeInfo)temp).getNi())
            {
                if(ni.getTag()==0)
                {
                    nextToVisit.add(ni);
                    ni.setTag(1);
                    visited.add(ni);
                }
            }
        }
        if(this.g.gethm().size()==visited.size())
            return true;
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        shortestPath(src,dest);
        return g.getNode(dest).getTag();
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        HashMap<Integer,node_info> parent=new HashMap<Integer,node_info>();//<key of the node,his parent>
        WGraph_DS.NodeInfo c = (WGraph_DS.NodeInfo) g.getNode(src);
        PriorityQueue<WGraph_DS.NodeInfo> pq = new PriorityQueue<WGraph_DS.NodeInfo>(g.getV().size(),c);//storage in a min-qeue the next nodes we need to visit
       HashSet<node_info> visited=new HashSet<node_info>();
        for (node_info n:this.g.getV()) // set all the tags of the nodes in the graph to 0
        {
            n.setTag(-1);
        }
        c.setTag(0);
        pq.add(c);
        while(!pq.isEmpty())
        {
            WGraph_DS.NodeInfo n= pq.poll();
            if(!visited.contains(n)) {
                visited.add(n);
                if(n.getKey()==dest)
                    break;
                for (node_info ni:n.getNi())
                {
                    double dist=n.getTag()+g.getEdge(n.getKey(), ni.getKey());
                    if(ni.getTag()==-1||ni.getTag()>dist)
                    {
                        ni.setTag(dist);
                        parent.put(ni.getKey(), n);
                        pq.add((WGraph_DS.NodeInfo)ni);
                    }
                }
            }
        }
        if(parent.containsKey(dest))
        {
            ArrayList<node_info> RePath=new ArrayList<node_info>();//path from dest to src
            RePath.add(g.getNode(dest));
            node_info next=parent.get(dest);
            while(next!=null)
            {
                RePath.add(next);
                next=parent.get(next.getKey());
            }
            ArrayList<node_info> path=new ArrayList<node_info>();//path from src to dest
            int count=0;
            for(int i=RePath.size()-1;i>=0;i--)//insert the nodes of the shortest path from RePath to path
            {
                path.add(count,RePath.get(i));
                count++;
            }
            return path;
        }
        else if(visited.contains(g.getNode(dest)))
        {
            ArrayList<node_info> Path=new ArrayList<node_info>();//path from dest to src
            Path.add(g.getNode(dest));
            return Path;
        }
        return null;
    }

    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos =new ObjectOutputStream(fos);
            oos.writeObject(g);
            fos.close();
            oos.close();
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try{
            FileInputStream fos=new FileInputStream(file);
           ObjectInputStream oos=new ObjectInputStream(fos);
           this.g=(WGraph_DS)oos.readObject();
           fos.close();
           oos.close();
        }
        catch(IOException  | ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
