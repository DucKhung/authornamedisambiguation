/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import uit.tkorg.and.models.Author;
import uit.tkorg.and.models.Publication;

/**
 *
 * @author tiendv
 */
public class ReadXML {
    
    public static Publication importPubFromXML (String fileName)
    {
        Publication result=null;
        List<Author> coauthor=null;
        Author mainAuthor = null;
       
         try {
 
		File fXmlFile = new File("c:\\"+fileName+".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
                result = new Publication();
                //Read Paper info
                Element rootElement = doc.getDocumentElement();
                result.setDigitalName(getTagValue("digital", rootElement));
                result.setNameSeach(getTagValue("nameseach", rootElement));
                result.setJournal(getTagValue("journal", rootElement));
                result.setPubAbstract(getTagValue("abstract", rootElement));
                result.setPubKeywords(getTagValue("keywords", rootElement));
                result.setPubTitle(getTagValue("title", rootElement));
                result.setPubYearPublish(getTagValue("year", rootElement));
                result.setPublisher(getTagValue("publisher", rootElement));
                result.setConference(getTagValue("conference", rootElement));
                // Read author info
             
                coauthor = new ArrayList<Author>();
               NodeList authorList = doc.getElementsByTagName("author");
               for (int temp = 0; temp < authorList.getLength(); temp++) {
                  Node nNode = authorList.item(temp);
                  Element eElement = (Element) nNode;
                  if(!getTagValue("name", eElement).equals("")){
                      mainAuthor = new Author();
                      mainAuthor.setAuthorName(getTagValue("name", eElement));
                      mainAuthor.setAuthorAfflication(getTagValue("afflication", eElement));
                      if(eElement.hasAttribute("email"))
                      mainAuthor.setAuthorEmail(getTagValue("email", eElement));
                      if(eElement.hasAttribute("order")&&getTagValue("order", eElement)!=null)
                      mainAuthor.setAuthorOrder(Integer.parseInt(getTagValue("order", eElement)));
                      
                      if(eElement.hasAttribute("result")&&getTagValue("result", eElement)!=null)
                      mainAuthor.setAuthorResult(Integer.parseInt(getTagValue("result", eElement)));
                      
                      mainAuthor.setInterest(getTagValue("interests", eElement));
                       if (nNode.getParentNode().getNodeName().equals("main")){
                           result.setMainAuthor(mainAuthor);
                       }
                       else
                           coauthor.add(mainAuthor);
                           
                  } 
               }
               result.setCoAuthor(coauthor);
              
		  
	  } catch (Exception e) {
		e.printStackTrace();
	  }
        return result;
    }
    
      private static String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        
        Node nValue = (Node) nlList.item(0);
        if (nValue!=null)
            return nValue.getNodeValue();
        else
            return null;
  }
  
//      public static void main(String[] args) {
//        // TODO code application logic here
//       Publication test;
//       test= importPubFromXML("1");
//       
//    }
}