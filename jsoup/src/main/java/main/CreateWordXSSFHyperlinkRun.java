package main;

import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;

public class CreateWordXSSFHyperlinkRun {

	 static XWPFHyperlinkRun createHyperlinkRun(XWPFParagraph paragraph, String uri) {
	  String rId = paragraph.getDocument().getPackagePart().addExternalRelationship(
	    uri, 
	    XWPFRelation.HYPERLINK.getRelation()
	   ).getId();

	  CTHyperlink cthyperLink=paragraph.getCTP().addNewHyperlink();
	  cthyperLink.setId(rId);
	  cthyperLink.addNewR();

	  return new XWPFHyperlinkRun(
	    cthyperLink,
	    cthyperLink.getRArray(0),
	    paragraph
	   );
	 }
}