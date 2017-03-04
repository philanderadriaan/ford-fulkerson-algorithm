/**
 * @author Stephen Mosby
 * CSS543 W14 Empirical Study of Max-Flow Algorithms
 * Pre-Flow Push Java Implementation
 */
package given_code;

import ford_fulkerson.FordFulkerson;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *This class represents an implementation of the pre-flow push algorithm as
 *described on page 360 and 361 of the text book.
 */
public class PreflowPush {

	/**The input graph for the algorithm */
	private SimpleGraph my_simple_graph;

	/** The source vertex of the input graph */
	private Vertex my_source;

	/** The sink vertex of the input graph */
	private Vertex my_sink;

	/**
	 * A simple constructor.
	 * @param the_file_path - the file path for the input graph
	 */
	public PreflowPush(final String the_file_path) {
		my_simple_graph = new SimpleGraph();
		GraphInput.LoadSimpleGraph(my_simple_graph, 
				the_file_path);	
		initResidualGraph();
	}
	
	/**
	 * A simple constructor for testing purposes
	 * @param the_graph - input graph
	 * @param the_source - the source vertex of the input graph
	 */
	/*This constructor created to try running the test*/
	public PreflowPush(SimpleGraph the_graph, Vertex the_source){
		my_simple_graph = the_graph;
		//my_source = the_source;
		initResidualGraph();
	}

	/**
	 * This method initializes all variables and data structures associated
	 * with the residual graph.
	 */
	private void initResidualGraph() {
		int num_vertices = my_simple_graph.vertexList.size();//no of vertices
		Iterator e_itr = my_simple_graph.edgeList.iterator();
		Iterator v_itr = my_simple_graph.vertexList.iterator();
		Edge e = null;
		while(e_itr.hasNext()) {
			e = (Edge) e_itr.next();
			e.setData(new EdgeData(((Number) e.getData()).intValue()));
		}

		while(v_itr.hasNext()) {
			//give all nodes their initial labeling
			Vertex v = (Vertex) v_itr.next();
			if(v.getName().equals("s")) {
				VertexData d = new VertexData(num_vertices);
				v.setData(d);	
				my_source = v;
			} else if (v.getName().equals("t")){
				VertexData d = new VertexData(0);
				my_sink = v;
				v.setData(d);
			} else {
				VertexData d = new VertexData(0);
				v.setData(d);
			}
		}
	}

	/**
	 * This is the actual implementation of the pre-flow push algorithm
	 * @return the maximum flow value
	 */
	public int findMaxFlow() {
		int max_flow = 0;
		List<Vertex> excess_list = new ArrayList<Vertex>();
		Iterator e_itr;
		Edge e;
		EdgeData e_data;
		Vertex v1,v2 = null;
		VertexData v1_data,v2_data = null;
		boolean pushable = false;
		int delta;

		//initial preflow out of source (should be no forward edges from s)
		e_itr = my_source.incidentEdgeList.iterator();

		while (e_itr.hasNext()) {
			int flow = 0;
			e = (Edge) e_itr.next();
			if (e.getFirstEndpoint() == my_source) {
				e_data = (EdgeData) e.getData();
				flow = e_data.getCapacity();
				e_data.setFlow(flow);
				e_data.setBackwardEdgeData(flow);
				e_data.setForwardEdgeData(0);
				v2 = e.getSecondEndpoint();
				v2_data = (VertexData) v2.getData();
				v2_data.setExcessFlow(flow);
				excess_list.add(v2);
			}
		}

		while(!excess_list.isEmpty()) {
			v1 = excess_list.remove(0);
			v1_data = (VertexData) v1.getData();
			e_itr = v1.incidentEdgeList.iterator();
			pushable = false;
			while(e_itr.hasNext()) {
				e = (Edge) e_itr.next();
				e_data = (EdgeData) e.getData();
				v2 = my_simple_graph.opposite(v1, e);
				v2_data =  (VertexData) v2.getData();
				//push
				if(v2_data.getHeight() < v1_data.getHeight()) {
					//v2_data.setHeight(v1_data.getHeight() - 1);
					if(e.getFirstEndpoint() == v1 
							&& e_data.getForwardEdgeData() > 0) {
						pushable = true;
						delta = Math.min(v1_data.getExcessFlow(), 
								e_data.getCapacity() - e_data.getFlow());
						e_data.setFlow(e_data.getFlow() + delta);
						v1_data.setExcessFlow(v1_data.getExcessFlow() - delta);
						if(v2 != my_source && v2 != my_sink 
								&&  v2_data.getExcessFlow() == 0) {
							excess_list.add(v2);
						}
						//						if(v1_data.getExcessFlow() > 0) {
						//							excess_list.add(v1);
						//						}
						if(v2 != my_source && v2 != my_sink) {
							v2_data.setExcessFlow(v2_data.getExcessFlow() + delta);
						}
						e_data.setForwardEdgeData(e_data.getCapacity() 
								- e_data.getFlow());
						e_data.setBackwardEdgeData(e_data.getFlow());
						break;
					} 

					if (e.getSecondEndpoint() == v1
							&& e_data.getBackwardEdgeData() > 0) {
						pushable = true;
						delta = Math.min(v1_data.getExcessFlow(), 
								e_data.getFlow());
						e_data.setFlow(e_data.getFlow() - delta);
						v1_data.setExcessFlow(v1_data.getExcessFlow() - delta);
						if(v2 != my_source && v2 != my_sink 
								&& v2_data.getExcessFlow() == 0) {
							excess_list.add(v2);
						}
						//						if(v1_data.getExcessFlow() > 0) {
						//							excess_list.add(v1);
						//						}
						if(v2 != my_source && v2 != my_sink) {
							v2_data.setExcessFlow(v2_data.getExcessFlow() + delta);
						}
						e_data.setForwardEdgeData(e_data.getCapacity() 
								- e_data.getFlow());
						e_data.setBackwardEdgeData(e_data.getFlow());
						break;
					} 
				}
			}

			if(v1_data.getExcessFlow() > 0 || !pushable) {
				excess_list.add(v1);
				//relabel
				v1_data.setHeight(v1_data.getHeight()+1);
			} 
		}

		e_itr = my_sink.incidentEdgeList.iterator();
		while(e_itr.hasNext()) {
			e = (Edge) e_itr.next();
			e_data = (EdgeData) e.getData();
			if(e.getSecondEndpoint() == my_sink) {
				max_flow += e_data.getFlow();
			}
		}
		return max_flow;
	}


	/**
	 * Private class needed for graph and residual graph structures and 
	 * variable such as capacity, flow, and forward and backward edge values 
	 * @author Stephen Mosby
	 */
	private class EdgeData {
		private int my_capacity;
		private int my_flow;
		private int my_forward_edge_data;
		private int my_backward_edge_data;

		public EdgeData (final int the_capacity) {
			my_capacity = the_capacity;
			my_flow = 0;
			my_forward_edge_data = the_capacity;
			my_backward_edge_data = 0;
		}

		public int getFlow() {
			return my_flow;
		}

		public void setFlow(int the_flow) {
			my_flow = the_flow;
		}

		public int getCapacity() {
			return my_capacity;
		}

		public void setCapacity(int the_capacity) {
			my_capacity = my_capacity;
		}

		public int getForwardEdgeData() {
			return my_forward_edge_data;
		}

		public void setForwardEdgeData(int the_forward_edge) {
			my_forward_edge_data = the_forward_edge;
		}

		public int getBackwardEdgeData() {
			return my_backward_edge_data;
		}

		public void setBackwardEdgeData(int the_backward_edge) {
			my_backward_edge_data = the_backward_edge;
		}
	}

	/**
	 * Data structure needed for storing residiual graph data such as height and
	 * excess flow
	 * @author Stephen Mosby
	 *
	 */
	private class VertexData {
		private int my_height;
		private int my_excess_flow;

		public VertexData (final int the_height) {
			my_height = the_height;
			my_excess_flow = 0;
		}

		public int getHeight() {
			return my_height;
		}

		public void setHeight(int the_height) {
			my_height = the_height;
		}

		public int getExcessFlow() {
			return my_excess_flow;
		}

		public void setExcessFlow(int the_excess_flow) {
			my_excess_flow = the_excess_flow;
		}
	}
	/**
	 * Code to test the methods of this class.
	 */
	public static void main (String args[]) {
		//correct 28
//		SimpleGraph g = new SimpleGraph();
//		Vertex v1 = g.insertVertex(null, "s");
//		Vertex v2 = g.insertVertex(null, "2");
//		Vertex v3 = g.insertVertex(null, "3");
//		Vertex v4 = g.insertVertex(null, "4");
//		Vertex v5 = g.insertVertex(null, "5");
//		Vertex v6 = g.insertVertex(null, "6");
//		Vertex v7 = g.insertVertex(null, "7");
//		Vertex v8 = g.insertVertex(null, "t");
//
//
//		g.insertEdge(v1, v2, 10, null);
//		g.insertEdge(v1, v3, 5, null);
//		g.insertEdge(v1, v4, 15, null);
//		g.insertEdge(v2, v5, 9, null);
//		g.insertEdge(v2, v6, 15, null);
//		g.insertEdge(v2, v3, 4, null);
//		g.insertEdge(v3, v4, 4, null);
//		g.insertEdge(v3, v6, 8, null);
//		g.insertEdge(v4, v7, 30, null);
//		g.insertEdge(v5, v8, 10, null);
//		g.insertEdge(v5, v6, 15, null);
//		g.insertEdge(v6, v8, 10, null);
//		g.insertEdge(v6, v7, 15, null);
//		g.insertEdge(v7, v3, 6, null);
//		g.insertEdge(v7, v8, 10, null);
//
//		PreflowPush f = new PreflowPush(g, v1);
//		System.out.println(f.findMaxFlow());
//		
		
		SimpleGraph G = new SimpleGraph();
        Vertex s, a, b, c, d, t;
        Edge e, k, l, m, n;
        //Correct Max Flow: 120
        s = G.insertVertex(null, "s");
        a = G.insertVertex(null, "a");
        G.insertEdge(s, a, 90, "s-a");
        b = G.insertVertex(null, "b");
        G.insertEdge(a, b, 60, "a-b");
        c = G.insertVertex(null, "c");
        G.insertEdge(s, c, 110, "K");
        d = G.insertVertex(null, "d");
        G.insertEdge(c, d, 30, "c-d");
        t = G.insertVertex(null, "t");
        G.insertEdge(b, t, 50, "b-t");
        G.insertEdge(c, a, 10, "c-a");
        G.insertEdge(d, t, 100, "d-t");
        G.insertEdge(a, d, 30, "a-d");
        G.insertEdge(b, d, 40, "b-d");
        PreflowPush f1 = new PreflowPush(G, s);
       
        System.out.println(f1.findMaxFlow());
	}

}
