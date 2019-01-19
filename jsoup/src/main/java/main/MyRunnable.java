package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.poi.sl.draw.binding.CTHyperlink;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyRunnable implements Runnable {
	Document doc = null;
	int id_num;
	String url;
	String filePath="";
	

	   public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public MyRunnable(Object parameter, String url, int id_num) {
	       // store parameter for later user
		   this.id_num=id_num;
		   this.doc=doc;
		   this.url=url;
		   
	   }

	   public void run() {
		  // String url=ebay.get(id_num);
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Thread(new Runnable() {
				   public void run() {
						// rj3ha mshan alsowar !! //
					   //doc.select("#docs > div:eq(1) > h4 > a").attr("href");
					  // doc.select("#j-product-description> div > div:eq(4) >div").attr("href");
					  // doc.select("#j-product-description> div > div:eq(4) >div").attr("href");
		//      window.runParams.descUrl="//www.aliexpress.com/getDescModuleAjax.htm?productId=32961630907&t=1547242228814&transAbTest=&adminSeq=202138026";
					

					//System.out.println(str.substring(str.indexOf("detailDesc=")+"detailDesc=".length(), str.indexOf(";")));
					  downloadRightPhotos(doc,id_num);
				     showDitals(doc,url,id_num);
				       writeWord(doc,url,id_num);
				       
				       
				       /**   String str2=doc.data();
						// link for description important!! 
					   Pattern pattern = Pattern.compile("detailDesc=\"(.*?)\";");
					   Matcher matcher = pattern.matcher(str2);
					   while (matcher.find()) {
					       System.out.println(matcher.group(1));
					   }*/
					   }
					}).start();
	        	 

	   }
	   private static void showDitals(Document doc, String url, int id_num) {
			// TODO Auto-generated method stub
	 
			 File outputFile;
		        BufferedReader reader;
		        FileWriter fileWriter;

		      
		            try {
		                FileWriter fstream = new FileWriter("C:\\Users\\Amr\\Desktop\\ebay\\"+id_num+"\\"+id_num+".txt");
		                BufferedWriter info = new BufferedWriter(fstream);
	
			
			Document doc2 = Jsoup.parse(doc.html());
		        System.out.println(doc.title());
		        info.write(doc.title());
	            info.newLine();
		        Elements links = doc2.select("div div div div ul  li span"); //good for detail

		        	int  sidetoo=1;
		        for (Element element : links) {
		            System.out.format("%s  %s\n", element.attr("href"), element.text());
	                if(sidetoo%2==0) {
	    	            info.newLine();
	    	            info.newLine();

	                }
		            info.write(String.format("%s %s", element.attr("href"), element.text()));
	                sidetoo++;
		        }
	            info.newLine();
	            System.out.println("\n\n"+url);
		        info.write("\n\n"+url);
	            info.newLine();
		        
	        
	            
		        info.close();
		            } catch (Exception e) {
		                System.out.println("A write error has occurred");
		            }
		}


private static void downloadRightPhotos(Document doc, int id_num) {
			// TODO Auto-generated method stub
			String filePath="C:\\Users\\Amr\\Desktop\\ebay\\"+id_num;
			 File f = new File(filePath);
		        try{
		            if(f.mkdir()) { 
		                System.out.println("Directory Created");
		            } else {
		                System.out.println("Directory is not created");
		            }
		        } catch(Exception e){
		            e.printStackTrace();
		        } 
			
			
			
			Elements img = doc.getElementsByTag("img");
			Elements photos_links = null;
			Elements photos_links_typs = null;

	        for (Element el : img) {
	           //for each element get the srs url
	        	String src = el.absUrl("src");
	       	 photos_links_typs = doc.select("ul  li a img"); //gvariatsyot

	        	 photos_links = doc.select("ul  li span img"); //good for detail
	        }
	   Image image = null;
	   String imag;
	   int count=0;
	   for (Element element : photos_links) {
		   
		    imag=element.attr("src").replaceAll("50x50", "640x640");
	      System.out.format("%s  %s\n", imag, element.text());
	      URL url12 = null;
		try {
			url12 = new URL(imag);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      BufferedImage img123 =  new BufferedImage(640, 640, BufferedImage.TYPE_INT_RGB);

		try {
			img123 = ImageIO.read(url12);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//C:\Users\Amr\Desktop\ebay\0
	      count++;

	      File file123 = new File(filePath+"\\img"+element.hashCode()+count+".jpg");
	      count++;
	      try {
			ImageIO.write(resize(img123,640,640), "jpg", file123);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
	     //the same but about varyatsyot
	   count=0;
	   for (Element element : photos_links_typs) {
		   System.out.println("ttttttttt     "+element.attr("src"));
		    imag=element.attr("src").replaceAll("50x50", "640x640");
	     System.out.format("%s  %s\n", imag, element.text());
	     URL url12 = null;
		try {
			System.out.println("mmmmmmmmmmmmmmmmmmmm          "+imag);
			if(imag!=null)
			url12 = new URL(imag);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	     BufferedImage img123 =  new BufferedImage(640, 640, BufferedImage.TYPE_INT_RGB);
		try {
			if(url12!=null)
			img123 = ImageIO.read(url12);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//C:\Users\Amr\Desktop\ebay\0
	     File file123 = new File(filePath+"\\type"+count+".jpg");
	   
	     
	     try {
			ImageIO.write(resize(img123,640,640), "jpg", file123);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     count++;
	     	if(photos_links_typs.size()==count)
	     	{
	     		if(count>3) {
	     			System.out.println("change here ..............."+      count);
	     		//renameFile(f,"C:\\Users\\Amr\\Desktop\\ebay\\var"+id_num);
				f.renameTo(new File("C:\\Users\\Amr\\Desktop\\ebay\\var"+id_num));
	     		}
	     	}
	  }
	   
	   
	   
		}
public static void renameFile(File toBeRenamed, String new_name)
	    throws IOException {
	    //need to be in the same path
	    File fileWithNewName = new File(toBeRenamed.getParent(), new_name);
	    if (fileWithNewName.exists()) {
	        throw new IOException("file exists");
	    }
	    // Rename file (or directory)
	    boolean success = toBeRenamed.renameTo(fileWithNewName);
	    if (!success) {
	        // File was not successfully renamed
	    	System.out.println("we are fucked ............................");
	    }
	}
public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
   
   // BufferedImage dimg2 = new BufferedImage(300, 65, BufferedImage.TYPE_3BYTE_BGR);
    //.setColor(Color.blue);
    //TYPE_3BYTE_BGR  // orginal
    //TYPE_INT_ARGB   // m3 5lfyee zhryee
    //TYPE_BYTE_GRAY   sowar to5en shafafat 
    Graphics2D g2d = dimg.createGraphics();
  //  Graphics2D g2d2 = dimg2.createGraphics();
   // g2d2.setBackground(Color.BLUE);
    g2d.drawImage(tmp, 0, 0, null);

  ///////////////////////////////////////////////
    
    Image backGroundImage = new ImageIcon("C:\\Users\\Amr\\Desktop\\ebay\\logo.png").getImage();
    g2d.drawImage(backGroundImage, 0, 0, 300, 80, null);

    // for text 
   /* g2d2.setFont(g2d2.getFont().deriveFont(35f));
    g2d2.setFont(g2d2.getFont());

    g2d2.setColor(Color.white);
    g2d2.drawString("SpaceCore",50,40);
    ////////////////////////////////////////////////
    
 */   //g2d.drawImage(dimg2, 0, 0, null);

    
    g2d.dispose();
 
    

    return dimg;
}
private static void writeWord(Document doc, String url, int id_num) {
	XWPFDocument document1 = new XWPFDocument();

	// Write the Document in file system
	FileOutputStream out = null;
	try {
		out = new FileOutputStream(new File("C:\\Users\\Amr\\Desktop\\ebay\\"+id_num+"\\"+id_num+".docx"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		try {
			out = new FileOutputStream(new File("C:\\Users\\Amr\\Desktop\\ebay\\var"+id_num+"\\"+id_num+".docx"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//f.renameTo(new File("C:\\Users\\Amr\\Desktop\\ebay\\var"+id_num));

		e.printStackTrace();
	}

	// Create table


	// Iterate through rows
	XWPFParagraph paragraph = document1.createParagraph();
	XWPFParagraph paragraph1 = document1.createParagraph();
	XWPFParagraph paragraph2 = document1.createParagraph();
	XWPFParagraph paragraph3 = document1.createParagraph();

    XWPFRun run = paragraph.createRun();
    XWPFRun run1 = paragraph1.createRun();
   XWPFRun run2 = paragraph2.createRun();
    XWPFRun run3 = paragraph3.createRun();
  //  paragraph.setAlignment(ParagraphAlignment.CENTER);
    paragraph.setAlignment(ParagraphAlignment.LEFT);
    paragraph1.setAlignment(ParagraphAlignment.LEFT);

//     run2.set
    paragraph2.setAlignment(ParagraphAlignment.CENTER);

    String text="";
    
	
	Document doc2 = Jsoup.parse(doc.html());
        System.out.println(doc.title());
   
        run.setFontSize(12);
        run1.setFontSize(12);
        run.setFontFamily("Times New Roman");
        run1.setFontFamily("Times New Roman");

        String str1=doc.title().replaceAll("-in Tool Toys from Toys & Hobbies on Aliexpress.com | Alibaba Group", "");
        str1=str1.replaceAll("Aliexpress.com : Buy","");
        try {
       str1=str1.substring(0, str1.lastIndexOf("from Reliable"));}
        catch (Exception e) {
        	
        }
System.out.println(str1);
        //from Reliable 
        run.setText(str1);
        run.addBreak();
        run.addBreak();
        run.addBreak();
        
        String Pcs="";
        if(str1.toLowerCase().indexOf("PCS".toLowerCase()) != -1 /*str1.toLowerCase().contains("PCS".toLowerCase())|| str1.toLowerCase().contains("Pcs".toLowerCase())||str1.toLowerCase().contains("PCs".toLowerCase())*/) {
        	Pcs=(str1.substring(0,str1.indexOf("P")+3)+" ");
        }
        String sentec=creatStrongSentece(str1);
        run.setText(sentec); 
        run.addBreak();
        run.addBreak();
        String finallyWord=Pcs+sentec+finallySentec(str1).replaceAll("  ", " ");
        run1.setText((Pcs+sentec+finallySentec(str1)).replaceAll("  ", " "));   

	    description(run2,doc2);
run2.addBreak();run2.addBreak();

        CreateWordXSSFHyperlinkRun a=new CreateWordXSSFHyperlinkRun();
    
        XWPFHyperlinkRun hyperlinkrun = a.createHyperlinkRun(paragraph3, url);
        hyperlinkrun.setText(url);
        hyperlinkrun.setColor("0000FF");
        hyperlinkrun.setUnderline(UnderlinePatterns.SINGLE);
        
        importantDisc(doc,run2);


	// Write to word document and close file.        
	try {
		document1.write(out);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

private static void importantDisc(Document doc, XWPFRun run2) {
	// TODO Auto-generated method stub
	   String url=doc.data();
	// link for description important!! 
   Pattern pattern = Pattern.compile("detailDesc=\"(.*?)\";"); // this code take all sentence between detailDesc=" to ";
   Matcher matcher = pattern.matcher(url);
   while (matcher.find()) {
	   url=matcher.group(1);
       System.out.println(url);
   }
   
   Document d = null;
	try {
		d = Jsoup.connect(url).get();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
    Elements links = d.select("p");//good for detail

	int  sidetoo=1;
	String elm="";
for (Element element : links) {
	elm= element.text();
    System.out.println( elm);
    if ( elm.toLowerCase().indexOf("https".toLowerCase()) != -1 )
    	elm= element.text().replace(element.text(),"");

    run2.setText((elm)); 
    run2.addBreak();


	//System.out.format("%s  %s\n", element.attr("href"), element.text());
  /*  if(sidetoo%2==0) {
        info.newLine();
        info.newLine();

    }*/
    //System.out.println(  element.text());
    //sidetoo++;
}
	
}

private static void description(XWPFRun run2, Document doc2) {
	  run2.setFontSize(12);
      
      //Elements links = doc2.select("div div div div ul  li span"); //good for detail
	  Elements link= doc2.getElementsByClass(/*"ui-tab-body"*//*"product-desc"*/"product-property-list util-clearfix");
	    System.out.println("zzzzzzzzzzzzzzzz"+link.html());
	    Elements links=link.select("li span");
  	int  sidetoo=1;
  	int cou=0;
  for (Element element : links) {
      System.out.format("%s  %s\n", element.attr("href"), element.text());
      if(sidetoo%2!=0) {
          run2.addBreak();
          run2.addBreak();

      }
  
      run2.setText(   String.format("%s %s", element.attr("href"), element.text().replaceFirst("Stars","") ));
      sidetoo++;
   
  }
 
  run2.addBreak();
  run2.addBreak();
 
     
 
  
	
}

private static String finallySentec(String str1) {
	// TODO Auto-generated method stub
	
	  // the words that i wants to remove to get just the attribute for the product!!
	 File file=new File("keyWords");
	 File file2=new File("goodWords");
	 File file3=new File("badWords");
	 File file4=new File("kindWords");


	 Main m=new Main();
	 	    FileReader fr = m.readFile(file);
	ArrayList<String>ebay=m.arrayOfProducts(fr);
	   FileReader fr2 = m.readFile(file2);
		ArrayList<String>ebay2=m.arrayOfProducts(fr2);
		 FileReader fr3 = m.readFile(file3);
			ArrayList<String>ebay3=m.arrayOfProducts(fr3);
			 FileReader fr4 = m.readFile(file4);
				ArrayList<String>ebay4=m.arrayOfProducts(fr4);
		System.out.println("qqqqqqqqqqqq           array+ another");
	ebay.addAll(ebay2);
	ebay.addAll(ebay3);
	ebay.addAll(ebay4);
	str1 = str1.replaceAll("[0-9]","");

	for(int i=0;i<ebay.size();i++) {
		str1=str1.replace(ebay.get(i), "");
		System.out.println(ebay.get(i) );

	}
	
	
     // this func get all words not found in good and keys, the goal is to get spepcefic description in title
    ArrayList<String> goodWords = getArray(str1);
	ArrayList<Integer> numbers=new ArrayList();
		ArrayList<String> good4 = new ArrayList();


        for(int i = 0; i < goodWords.size(); i++)
        {
        numbers.add(i);
      }
        Collections.shuffle(numbers); /// get just 4 attribute to add!!
        for(int j =0; j<4; j++)
        {	if(goodWords.get(numbers.get(j)).toLowerCase().indexOf("PCS".toLowerCase()) == -1 )
        	good4.add((goodWords.get(numbers.get(j))));
        }
        
        for(int i=0;i<good4.size();i++) {
            for(int j=0;j<good4.size();j++) {
                if((good4.get(i).replace(" ", "").toLowerCase().equals(good4.get(j).replace(" ", "").toLowerCase()) &&i!=j))
                   // ebay.remove(ebay.get
                	good4.remove(i);

            }
        }
        
       String str11="";
        for(String str : good4){
            str11+=str;
        }
        //str1+=str11;
	return str11;
}

private static String creatStrongSentece(String str1) {
	// TODO Auto-generated method stub

	 File file=new File("keyWords"); // open key words file and get all key  words " i can take 3/4 random words from all!!"
	 Main m=new Main();
	 	    FileReader fr = m.readFile(file);
	ArrayList<String>ebay=m.arrayOfProducts(fr);
	
	
	 
	
	ArrayList<String>myw=getArray(str1);
      
			ebay.add(kind(myw));
	
        	ebay.add(random4());
      
        

for(int i=0;i<ebay.size();i++) {
    for(int j=0;j<ebay.size();j++) {
        if((ebay.get(i).replace(" ", "").toLowerCase().equals(ebay.get(j).replace(" ", "").toLowerCase()) &&i!=j))
           // ebay.remove(ebay.get
            ebay.remove(i);

    }
}
	   String str11="";
	        for(String str : ebay){
	              if(!str.equals(""))
	            str11+=str+" ";
	        }
	  
	      
	return str11;
}

private static ArrayList<String> getArray(String str1) {
	// TODO Auto-generated method stub
	int revah=0;
	int start=0;
	int i=0;
	ArrayList<String> arr1 = new ArrayList<String>();
	while(i!=str1.length()) {
		revah++;
		if(str1.charAt(i)==' ' || i==str1.length()-1) {
			
			arr1.add(str1.substring(start, revah));
			start=revah;
		}
		i++;
	}
	return arr1;
}

private static String kind(ArrayList<String> words) {
	// TODO Auto-generated method stub
	 File file=new File("kindWords");

	 Main m=new Main();
	 	    FileReader fr = m.readFile(file);
	ArrayList<String>ebay=m.arrayOfProducts(fr);
	
	String cword="";
	for(int i=0;i<words.size();i++) {
		 //cword=words.get(i).replace(" ", "");
			//System.out.println("@@@@@@@@@@@@    "+ cword);
				for(String word:ebay) {
					if(words.get(i).replace(" ", "").toLowerCase().equals(word.toLowerCase())/*|| cword.toLowerCase().equals("Bricks".toLowerCase())||
							cword.toLowerCase().equals("Wooden".toLowerCase())*/) {
								System.out.println("####################    "+ cword);

								 cword+=words.get(i)+" ";
							}
				}
		
	// cword="";
	}
	System.out.println("####################  fuck   "+ cword);

	return cword;
}

private static String random4() {
	 File file=new File("goodWords");
Main m=new Main();
	    FileReader fr = m.readFile(file);

		ArrayList<String> goodWords = m.arrayOfProducts(fr);
		ArrayList<Integer> numbers=new ArrayList();
   		ArrayList<String> good4 = new ArrayList();

    
	        for(int i = 0; i < goodWords.size(); i++)
	        {
	        numbers.add(i);
	      }
	        Collections.shuffle(numbers);
	        for(int j =0; j < 4; j++)
	        {
	        	good4.add((goodWords.get(numbers.get(j))));
	        }
           String str1="";
	        for(String str : good4){
	            str1+=str+" ";
	        }
		return str1;
}
}
