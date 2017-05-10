import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//野球クイズ
public class ParseXMLtoUpdateExcellQuize extends ParseXMLtoUpdate{
	public String parse(){
	     StringBuilder stringbuffer = new StringBuilder();;
	     Document doc = Jsoup.parse(getXmltext());   
	     Elements elemsBody = doc.select("body div");
	     for(Element elembody:elemsBody){
	    	 Elements elems2 = elembody.getAllElements();
             int countVol=0;
	    	 for(Element elem:elems2){
	    		 String tagname = elem.tagName();
	    		 if(tagname.equals("h1")){
	    			 String title = elem.text();
	    			 stringbuffer.append(title);
	    			 stringbuffer.append(newline);
	    			 countVol++;
	    		 }else if(tagname.equals("tbody")){
	    			 Elements trElems = elems2.select("tr");
	    			 int countRow=0;
	    			 ArrayList<String>  listColumnTitle = new ArrayList<String>();
	    			 int answerColNum=6;
	    			 int multipulChoiceStart=2;
	    			 for(Element trElem:trElems){
	    				 Elements tdElems = trElem.select("td");
	    				 HashMap<String, String> trmap = new HashMap<String, String>();
	    				 for(int i=0;i<tdElems.size();i++ ){
	    					 Element tdElem = tdElems.get(i);
	    					 if(countRow ==0){
		    					 String columntitle = tdElem.text();
		    					 listColumnTitle.add(columntitle);
		    					 if(columntitle.contains("正解")){
		    						 answerColNum = i;
		    					 }else if( columntitle.contains("4択候補①") ){
		    						 multipulChoiceStart=i;
		    					 }
		    				 }
		    				 else if(countRow >0){
		    					 String cellValue = tdElem.text();
		    					 String columnKey = listColumnTitle.get(i);
		    			         trmap.put(columnKey, cellValue);		 
	    					 }
	    				 }
	    				 //登録text作成
	    				 if(countRow > 0){
		    				 String answerline="";
		    				 String columnLine="";
	    					 String correctAnswer = trmap.get(listColumnTitle.get(answerColNum) );
	    					 //正解を1にする。不正解は0. 例 0,0,1,0 
	    					 for(int ians=multipulChoiceStart;ians<answerColNum;ians++){
	    						 String choice = trmap.get(listColumnTitle.get(ians) );
	    						 boolean ans = choice.length() > correctAnswer.length() ? choice.contains(correctAnswer) :correctAnswer.contains(choice); 
	    						 if(ans){
	    							 answerline += "1,";
	    						 }else{
	    							 answerline += "0,";
	    						 }
	    					 }
	    					 for(int indxCol=0;indxCol<listColumnTitle.size();indxCol++){
	    						 String columnKey = listColumnTitle.get(indxCol);
	    						 String columnVal = trmap.get(columnKey);
	    						 columnVal = eliminateKataKanaAtEnd(columnVal);
	    						 if(indxCol == 0){
	    							 int indexq = Integer.parseInt(columnVal) - 1;
	    							 String tmp = "qa[" + Integer.toString(indexq) +"] = [";
	    							 columnLine = tmp; 
	    						 }else if(indxCol == answerColNum -1){
	    							 columnLine = columnLine + 
	    									         "\"" + columnVal + "\"," +
	    									         answerline ;
	    						 }else if(indxCol == listColumnTitle.size() -1){
	    							 columnLine = columnLine + 
									         "\"" + columnVal + "\"];" ;
	    						 }else{
	    							 columnLine = columnLine + 
									         "\"" + columnVal + "\"," ;
	    						 }
	    					 }
		    				 stringbuffer.append(columnLine);
		    				 stringbuffer.append(newline);
	    				 }
	    				 countRow++;	    				 	 
	    			 }
	    			 stringbuffer.append(newline);
	    		 }
	    	 }
	     }
	   	 setRetText(stringbuffer.toString() );	 
	   	 return this.getRetText();
	}
}
