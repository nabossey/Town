import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TownGraphTest_STUDENT {
	private GraphInterface<Town,Road> studentGraph;
	private Town[] townStudent;

	@Before
	public void setUp() throws Exception {
		 studentGraph = new TownGraph();
		  townStudent = new Town[12];
		  
		  for (int i = 1; i < 12; i++) {
			  townStudent[i] = new Town("Town" + i);
			  studentGraph.addVertex(townStudent[i]);
		  }
		  
		  studentGraph.addEdge(townStudent[1], townStudent[2], 2, "Road9");
		  studentGraph.addEdge(townStudent[1], townStudent[3], 4, "Road12");
		  studentGraph.addEdge(townStudent[1], townStudent[5], 6, "Road7");
		  studentGraph.addEdge(townStudent[3], townStudent[7], 1, "Road3");
		  studentGraph.addEdge(townStudent[3], townStudent[8], 2, "Road8");
		  studentGraph.addEdge(townStudent[4], townStudent[8], 3, "Road6");
		  studentGraph.addEdge(townStudent[6], townStudent[9], 3, "Road2");
		  studentGraph.addEdge(townStudent[9], townStudent[10], 4, "Road11");
		  studentGraph.addEdge(townStudent[8], townStudent[10], 2, "Road10");
		  studentGraph.addEdge(townStudent[5], townStudent[10], 5, "Road4");
		  studentGraph.addEdge(townStudent[10], townStudent[11], 3, "Road1");
		  studentGraph.addEdge(townStudent[2], townStudent[11], 6, "Road5");
	}

	@After
	public void tearDown() throws Exception {
		studentGraph = null;
	}

	@Test
	public void testGetEdge() {
		assertEquals(new Road(townStudent[2], townStudent[11],6, "Road12"), studentGraph.getEdge(townStudent[2], townStudent[11]));
		assertEquals(new Road(townStudent[3], townStudent[7],1, "Road4"), studentGraph.getEdge(townStudent[3], townStudent[7]));
	}

	@Test
	public void testAddEdge() {
		assertEquals(false, studentGraph.containsEdge(townStudent[3], townStudent[5]));
		studentGraph.addEdge(townStudent[3], townStudent[5], 1, "Road13");
		assertEquals(true, studentGraph.containsEdge(townStudent[3], townStudent[5]));
	}

	@Test
	public void testAddVertex() {
		Town newTown = new Town("Town12");
		assertEquals(false, studentGraph.containsVertex(newTown));
		studentGraph.addVertex(newTown);
		assertEquals(true, studentGraph.containsVertex(newTown));
	}

	@Test
	public void testContainsEdge() {
		assertEquals(true, studentGraph.containsEdge(townStudent[2], townStudent[11]));
		assertEquals(false, studentGraph.containsEdge(townStudent[3], townStudent[5]));
	}

	@Test
	public void testContainsVertex() {
		assertEquals(true, studentGraph.containsVertex(new Town("Town2")));
		assertEquals(false, studentGraph.containsVertex(new Town("Town12")));
	}

	@Test
	public void testEdgeSet() {
		Set<Road> roads = studentGraph.edgeSet();
		ArrayList<String> roadArrayList = new ArrayList<String>();
		for(Road road : roads)
			roadArrayList.add(road.getName());
		Collections.sort(roadArrayList);
		assertEquals("Road1", roadArrayList.get(0));
		assertEquals("Road10", roadArrayList.get(1));
		assertEquals("Road11", roadArrayList.get(2));
		assertEquals("Road12", roadArrayList.get(3));
		assertEquals("Road2", roadArrayList.get(4));
		assertEquals("Road8", roadArrayList.get(10));
	}

	@Test
	public void testEdgesOf() {
		Set<Road> roads = studentGraph.edgesOf(townStudent[1]);
		ArrayList<String> roadArrayList = new ArrayList<String>();
		for(Road road : roads)
			roadArrayList.add(road.getName());
		Collections.sort(roadArrayList);
		assertEquals("Road12", roadArrayList.get(0));
		assertEquals("Road7", roadArrayList.get(1));
		assertEquals("Road9", roadArrayList.get(2));
	}
	
	
	@Test
	public void testRemoveEdge() {
		assertEquals(true, studentGraph.containsEdge(townStudent[2], townStudent[11]));
		studentGraph.removeEdge(townStudent[2], townStudent[11], 6, "Road5");
		assertEquals(false, studentGraph.containsEdge(townStudent[2], townStudent[11]));
	}

	
	@Test
	public void testRemoveVertex() {
		assertEquals(true, studentGraph.containsVertex(townStudent[2]));
		studentGraph.removeVertex(townStudent[2]);
		assertEquals(false, studentGraph.containsVertex(townStudent[2]));
	}

	@Test
	public void testVertexSet() {
		Set<Town> roads = studentGraph.vertexSet();
		assertEquals(true,roads.contains(townStudent[1]));
		assertEquals(true, roads.contains(townStudent[10]));
		assertEquals(true, roads.contains(townStudent[11]));
		assertEquals(true, roads.contains(townStudent[2]));
		assertEquals(true, roads.contains(townStudent[3]));
	}

	 @Test
	  public void testTown1ToTown11() {
		  String beginTown = "Town1", endTown = "Town11";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = studentGraph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town townStudent = iterator.next();
			  if(townStudent.getName().equals(beginTown))
				  beginIndex = townStudent;
			  if(townStudent.getName().equals(endTown))
				  endIndex = townStudent;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = studentGraph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town1 via Road9 to Town2 2 miles",path.get(0).trim());
			  assertEquals("Town2 via Road5 to Town11 6 miles",path.get(1).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
	  
	  
	  @Test
	  public void testTown1ToTown10() {
		  String beginTown = "Town1", endTown = "Town10";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = studentGraph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town townStudent = iterator.next();
			  if(townStudent.getName().equals(beginTown))
				  beginIndex = townStudent;
			  if(townStudent.getName().equals(endTown))
				  endIndex = townStudent;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = studentGraph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town1 via Road12 to Town3 4 miles",path.get(0).trim());
			  assertEquals("Town3 via Road8 to Town8 2 miles",path.get(1).trim());
			  assertEquals("Town8 via Road10 to Town10 2 miles",path.get(2).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
	  
	  @Test
	  public void testTown4ToTown11() {
		  String beginTown = "Town4", endTown = "Town11";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = studentGraph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town townStudent = iterator.next();
			  if(townStudent.getName().equals(beginTown))
				  beginIndex = townStudent;
			  if(townStudent.getName().equals(endTown))
				  endIndex = townStudent;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = studentGraph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town4 via Road6 to Town8 3 miles",path.get(0).trim());
			  assertEquals("Town8 via Road10 to Town10 2 miles",path.get(1).trim());
			  assertEquals("Town10 via Road1 to Town11 3 miles",path.get(2).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
	 

}