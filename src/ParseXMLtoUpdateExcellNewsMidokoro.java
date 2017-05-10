import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//野球ニュース＆みどころ
public class ParseXMLtoUpdateExcellNewsMidokoro extends ParseXMLtoUpdate{
	
	private enum TextType{
		NEWSTITLE,
		MIDOKOROTITLE,
		NEWSTEXT,
		MIDOKOROTEXT
	};
	
	private final String HTML_FONT_COLOR = "<font color=\"#0055A9\">";
	private final String HTML_HR_SIZE_COLOR = "<hr size=\"2\" color=\"#0055A9\">";	
	
	
//TODO parse を実装する
	public String parse() throws Exception{
	     StringBuilder stringbuffer = new StringBuilder();;
	     Document doc = Jsoup.parse(getXmltext());   
	     Elements elemsBodyDiv = doc.select("body div");
	     for(Element elemDiv:elemsBodyDiv){
	    	 Elements elemH1 = elemDiv.select("h1");
	    	 String divh1 = elemH1.get(0).text();
	    	 //volのあるシートを読み込む	    	 
	    	 int countVol=0;
	    	 if(divh1.contains("vol")){
	    		 countVol++;
	    		 String voltitle ="--------------" + divh1 + "--------------";
	    		 stringbuffer.append(newline);
	    		 stringbuffer.append(voltitle);
	    		 stringbuffer.append(newline);
	    		 stringbuffer.append(newline);
	    		 Elements elems = elemDiv.getAllElements();
	    		 Element elemTbody = elems.select("table tbody").get(0);
	    		 Elements elemsTr = elemTbody.select("tr");
	    		 ArrayList<String> newsTitles = new ArrayList<String>();
	    		 ArrayList<String> midokoroTitles = new ArrayList<String>();
	      		 ArrayList<String> newsTexts = new ArrayList<String>();
	    		 ArrayList<String> midokoroTexts = new ArrayList<String>();
	    		 TextType textype = TextType.NEWSTITLE;
	    		 // <tr>
	    		 for(Element elemTr:elemsTr){
	    			 Elements elemsTd = elemTr.select("td");
	    			 int numTd = elemsTd.size();
	    			 if(numTd == 2){
	    				 //title
		    			String firstCol  =  elemsTd.get(0).text();
	    				String secondCol = elemsTd.get(1).text();
	    				secondCol = eliminateKataKanaAtEnd(secondCol);
	    				if(secondCol.equals("＜ニュース＞")){
	    					textype = TextType.NEWSTITLE;
	    				}else if(secondCol.equals("＜みどころ＞")){
	    					textype = TextType.MIDOKOROTITLE;
	    				}else if(firstCol.contains("文字数")){
	    					//do nothing
	    					//文字数
	    				}else{
	    					if(textype == TextType.NEWSTITLE){
	    						newsTitles.add(secondCol);
	    					}else if(textype == TextType.MIDOKOROTITLE){
	    						midokoroTitles.add(secondCol);
	    					} 
	    				}
	    			 }else if(numTd ==1){
	    				 String firstCol  =  elemsTd.get(0).text();
	    				 firstCol = eliminateKataKanaAtEnd(firstCol);
	    				 if(firstCol.contains("ニュース")){
	    					 textype = TextType.NEWSTEXT;
	    				 }else if(firstCol.contains("みどころ")){
	    					 textype = TextType.MIDOKOROTEXT;
	    				 }else{
	    					 if(textype == TextType.NEWSTEXT){
	    						 newsTexts.add(firstCol);
	    					 }else if(textype == TextType.MIDOKOROTEXT){
	    						 midokoroTexts.add(firstCol);
	    					 }
	    				 }
	    			 }
	    		 }//<tr> end
	    		 //HTML作成する
	    		 int sizeTitleNews = newsTitles.size();
	    		 int sizeTitleMido = midokoroTitles.size();
	    		 int sizeTextNews = newsTexts.size();
	    		 int sizeTextMido = midokoroTexts.size();
	    		 String strTable="";
	    		 if(sizeTitleNews != sizeTextNews){
	    			 throw new Exception("The number of titles dosen't math the number of texts of news.");
	    		 }
	    		 if(sizeTitleMido != sizeTextMido){
	    			 throw new Exception("The number of titles dosen't math the number of texts of midokoro.");
	    		 }
	    		 if(sizeTitleNews > 0){
	    		 strTable = "<h4>" + HTML_FONT_COLOR + "ニュース" + "</font></h4>" + newline;
	    		 stringbuffer.append(strTable);
	    		 for(int inews=0;inews<sizeTitleNews;inews++){
	    			 String title ="<h4>" + HTML_FONT_COLOR +
	    					        newsTitles.get(inews) + 
	    					        "</font></h4>";
	    			 String text ="   " + newsTexts.get(inews);
	    			 String str = 
	    					     HTML_HR_SIZE_COLOR +
	    					     newline +
	    					     title    +
	    					     newline +
	    					     text + 
	    					     newline;
	    			 stringbuffer.append(str);
	    		 }
	    		 }
	    		 if(sizeTitleMido > 0){
		    		 strTable =HTML_HR_SIZE_COLOR + 
		    				   newline +
		    				   "<h4>" + HTML_FONT_COLOR + "みどころ" + "</font></h4>" + 
		    				   newline;
		    		 stringbuffer.append(strTable);
		    		 for(int imidokoro=0;imidokoro<sizeTitleMido;imidokoro++){
		    			 String title ="<h4>" + HTML_FONT_COLOR +
		    					        midokoroTitles.get(imidokoro) + 
		    					        "</font></h4>";
		    			 String text =" " + midokoroTexts.get(imidokoro);
		    			 String str = 
		    					     HTML_HR_SIZE_COLOR +
		    					     newline +
		    					     title    +
		    					     newline +
		    					     text + 
		    					     newline;
		    			 stringbuffer.append(str);
		    		 } 
	    		 }
	    		 //"vol"終了
	    		 stringbuffer.append(HTML_FOOTER); 
	    		 stringbuffer.append(newline);
	    	 }
	     }
	     
	     return stringbuffer.toString();
	}
	
}
