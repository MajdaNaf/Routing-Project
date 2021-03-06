package org.neo4j.neo4j;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.neo4j.OSMRoutingImporter;


public class OSMRoutingImporterTEST {
	/**
	 * @param args
	 */
	public static String graphDbPath = "target/osmImport-db";
	public static String osmXmlFilePath = "C:\\Users\\Carol\\Desktop\\GSoC\\osm\\liechtenstein.osm";
	//liechtenstein test nodes to route...
	public static String startNodeID = "278451834";
	public static String endNodeID = "268222979";
	
	/*
	//delaware test nodes to route...
	String startNode = "178741192";
	String endNode = "178722292";
	*/
	
	public static GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( graphDbPath );
	
	public static void main(String[] args) throws Exception 
	{
		registerShutdownHook();
		OSMRoutingImporter importer = new OSMRoutingImporter(graphDb);

		importer.importXML(osmXmlFilePath);
		
		OSMRouting router = new OSMRouting (graphDb, startNodeID, endNodeID);
		
		System.out.println("Creating route...");
		router.createRoute();
		
		System.out.println( "Shutting down database ..." );
        shutdown();
		
	}//end main

	
	  private static void shutdown()
	  {
	      graphDb.shutdown();
	  }
	
	  
		private static void registerShutdownHook()
		{
		        // Registers a shutdown hook for the Neo4j and index service instances
		        // so that it shuts down nicely when the VM exits (even if you
		        // "Ctrl-C" the running example before it's completed)
		      Runtime.getRuntime().addShutdownHook( new Thread()
		      {
		          @Override
		          public void run()
		          {
		              shutdown();
		          }
		       } );
		}//end registerShutdownHook()
	  
}//end TEST