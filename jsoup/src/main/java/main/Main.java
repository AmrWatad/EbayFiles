package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.portable.InputStream;


public class Main extends Thread{
	public void run(){  
	  }  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		File file=new File("amr2");

	    FileReader fr = readFile(file);

		ArrayList<String> ebay = arrayOfProducts(fr);
	   
		Document doc = null;
		
		for(int z=0;z<ebay.size();z++) {
			System.out.println("haaaaaaalaaaaaaaaaaaaaaaa bl 5meeeeeeees          "+z );
		

        
        Runnable r = new MyRunnable(doc,ebay.get(z),z);
		new Thread(r).start();
        
	} 
		
		
	}           
	
	
	public static ArrayList<String> arrayOfProducts(FileReader fr) {
		// TODO Auto-generated method stub
		//int numberOfBugs=0;
		ArrayList<String> arr = new ArrayList<String>();
		 BufferedReader br = new BufferedReader( fr );
		 String line = null;

		    try
		    {
		        while( (line = br.readLine()) != null )
		    {
		        	//if ( line.toLowerCase().indexOf("Caused By".toLowerCase()) != -1 ) {
		        		//input = input.subString(input.indexOf("(")+1, input.lastIndexOf(")"));	
		        		arr.add(line);
		        		//numberOfBugs++;
		        	//	System.out.println( line);
		        		//} 
		    }
		    }
		    catch (Exception e) {
		        System.out.println( "error" );

		    }

		return arr;
	}
	public static FileReader readFile(File file) {
		// TODO Auto-generated method stub
		 FileReader fr = null;
		    try
		    {
		        fr = new FileReader( file );
		    } 
		    catch (FileNotFoundException e) 
		    {  
		        System.out.println( "File doesn't exists" );
		        e.printStackTrace();
		    }
		return fr;
	}


}

