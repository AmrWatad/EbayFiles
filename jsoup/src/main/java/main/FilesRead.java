package main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;



public class FilesRead {
	private BufferedReader reader=null;
	//private composition object
	private ArrayList<String> FileTextList;
	String filename;
	public ArrayList<String> getFileTextList() {
		return FileTextList;
	}
	public void rpptx(String filename) throws IOException {
		 FileInputStream fis = new FileInputStream(filename);
	        try (XMLSlideShow ppt = new XMLSlideShow(fis)) {
	            fis.close();

	            for (XSLFSlide slide : ppt.getSlides()) {
	               // System.out.println("Title: " + slide.getTitle());

	                for (XSLFShape shape : slide.getShapes()) {
	                    if (shape instanceof XSLFTextShape) {
	                        XSLFTextShape tsh = (XSLFTextShape) shape;
	                        for (XSLFTextParagraph p : tsh) {
	                            //System.out.println("Paragraph level: " + p.getIndentLevel());
	                            for (XSLFTextRun r : p) {
	                                System.out.println(r.getRawText());
	                                FileTextList.add(r.getRawText());
//	                                System.out.println("  bold: " + r.isBold());
//	                                System.out.println("  italic: " + r.isItalic());
//	                                System.out.println("  underline: " + r.isUnderlined());
//	                                System.out.println("  font.family: " + r.getFontFamily());
//	                                System.out.println("  font.size: " + r.getFontSize());
//	                                System.out.println("  font.color: " + r.getFontColor());
	                            }
	                        }
	                    }
	                }
	            }
	        }
	}
	public void rdocx(String filename) {
		XWPFDocument document = null;
		FileInputStream fileInputStream = null;
		try {
			File fileToBeRead = new File(filename);
			fileInputStream = new FileInputStream(fileToBeRead);
			document = new XWPFDocument(fileInputStream);
			XWPFWordExtractor extractor = new XWPFWordExtractor(document);
//			System.out.println("The Contents of the Word File are ::");
//			System.out.println("--------------------------------------");
//			System.out.println(extractor.getText());
			String s= extractor.getText();
			String v[]=s.split("\\s");
			for(int i=0;i<v.length;i++) {
			System.out.print(v[i]+" ");
			FileTextList.add(v[i]+" ");
			}
			System.out.println();
		} catch (Exception e) {
			System.out.println("We had an error while reading the Word Doc");
		} finally {
			try {
				if (document != null) {
					document.close();
				}
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	FilesRead(String filename) throws Exception{
		super();
		this.filename=filename;
		FileTextList=new ArrayList<String>();
		init(filename);
	}
	public void processReader() throws IOException {
		String name=filename.replace("\\","/");
		Path path1=Paths.get(name);
		FileTextList=new ArrayList<String>();
		try {
			Files.lines(path1).forEach(s->{
				String values[]=s.split(" ");
				for(int i=0;i<values.length;i++)
					FileTextList.add(values[i]);
	});

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	private void init(String filename) throws Exception{
		if(!filename.endsWith(".docx") && !filename.endsWith(".pptx"))
		try {
			processReader();
			FileReader fileReader=new FileReader(filename);
			reader=new BufferedReader(fileReader);
		}catch(FileNotFoundException e) {
			throw new Exception(e.getMessage());
		}
		else if(!filename.endsWith(".enc") &&  filename.endsWith(".docx")) {
			rdocx(filename);
		}
		else if(!filename.endsWith(".enc") &&  filename.endsWith(".pptx")) {
			rpptx(filename);
		}
		else {
			System.out.println("Cant Read file encrypted");
		}
	}

	public void printAll() {
		System.out.print("Text: ");
		for(String b:FileTextList) {
			System.out.print(b+" ");
		}
		System.out.println();
	}
}

