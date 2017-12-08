package gr.iti.mklab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaRunCommandPython {

	 public static String execute(String pythonScript, String postText) {

	        String s = null;
	        String output_vector = "";
	        try {	            
	            // using the Runtime exec method:
	            Process p = Runtime.getRuntime().exec("python " + pythonScript + " " + postText);
	            System.out.println("   -- Python cmd  " + pythonScript + " " + postText);
	            BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));
	            BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));
	            // read the output from the command
	            System.out.println("Here is the standard output of the command:\n");	        
	            while ((s = stdInput.readLine()) != null) {
	            	 output_vector = s;
	            	 System.out.println(s);
	            }            
	            // read any errors from the attempted command
	            while ((s = stdError.readLine()) != null) {
	                System.out.println(s);
	            }	            
	        }
	        catch (IOException e) {
	            System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	           // System.exit(-1);
	        }
	        return output_vector;
	    }
}
