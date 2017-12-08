package gr.iti.mklab.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Mongodb configuration
 * Authentication is used - provide user and password
 * @author olgapapa
 *
 */

public class MongoConfiguration {

	    public static String MONGO_HOST; // mogno host
	    public static String USER; // username
	    public static String PWD; // user password 
	    public static String ROOT_DB; // contextual verification database
	    public static String VIDEO_CONTEXT_DB_FEATURES; // contextual verification database
	    public static String VIDEO_CONTEXT_DB_RESULTS;
	    public static String LOG_COLLECTION; // collection for storing log information 
	    public static String TRAINING_DATA_COLLECTION; // youtube collection
	    public static String GT_COLLECTION; // twitter collection
	    public static String ADMIN_DB; // admin database (for authentication)
	    public static String FEATURES_COLLECTION;  // tweet collection

	   
	    public static void load(String file) throws ConfigurationException {
	        PropertiesConfiguration conf = new PropertiesConfiguration(file);
	        MONGO_HOST = conf.getString("mongohostip");
	        USER = conf.getString("username");
	        PWD =  conf.getString("password");	 
	        ADMIN_DB = conf.getString("admin_db");
	        ROOT_DB =  conf.getString("root_db");
	        LOG_COLLECTION =  conf.getString("log_collection");
	        TRAINING_DATA_COLLECTION =  conf.getString("training_data_collection");
	        GT_COLLECTION =  conf.getString("ground_truth_collection");
	        FEATURES_COLLECTION =  conf.getString("features_collection");
	        VIDEO_CONTEXT_DB_FEATURES = conf.getString("video_contex_db_features");
	        VIDEO_CONTEXT_DB_RESULTS = conf.getString("video_context_db_results");
	        
	    }

	    public static void load(InputStream stream) throws ConfigurationException, IOException {
	        Properties conf = new Properties();
	        conf.load(stream);
	
	        MONGO_HOST = conf.getProperty("mongohostip");
	        USER = conf.getProperty("username");
	        PWD = conf.getProperty("password");	
	        ROOT_DB = conf.getProperty("root_db");	
	        ADMIN_DB = conf.getProperty("admin_db");
	        LOG_COLLECTION = conf.getProperty("log_collection");	
	        TRAINING_DATA_COLLECTION = conf.getProperty("training_data_collection");	
	        GT_COLLECTION = conf.getProperty("ground_truth_collection");	
	        FEATURES_COLLECTION = conf.getProperty("features_collection");	
	        VIDEO_CONTEXT_DB_FEATURES = conf.getProperty("video_contex_db_features");
	        VIDEO_CONTEXT_DB_RESULTS = conf.getProperty("video_context_db_results");
	     
	    }
}

