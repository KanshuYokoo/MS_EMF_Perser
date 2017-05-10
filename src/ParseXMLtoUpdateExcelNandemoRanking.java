import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseXMLtoUpdateExcelNandemoRanking extends ParseXMLtoUpdate{
	
	public String parse(){
	     StringBuilder stringbuffer = new StringBuilder();;
	     Document doc = Jsoup.parse(getXmltext());   
	     Elements elems = doc.select("tbody tr");
	     boolean isTable=false;
	     int countRow=0;
	     int countNewLine=0;
	     ArrayList<Integer>  listColumnNum=new ArrayList<Integer>();
	     for(Element elem: elems){
	         ////
	        Elements tdElems = elem.select("td");
	        int sizeElem = tdElems.size();
	        if(sizeElem == 1){
	        	//文章 or タイトル
//	        	String textOrig = tdElems.text();
	        	String textOrig = eliminateKataKanaAtEnd(tdElems.text());
	          	String text = textOrig + newline;
	        	//半角カタカナを除外する
	 //         	String text =	eliminateHankakuKana(tdElems.text());
	          	/*
	        	if(!text.isEmpty() && (text.contains("*") || text.contains("※"))){
	        		isTable=false;
	        	}*/
	        	if(isTable){
	        		stringbuffer.append("</table>");
	        		isTable=false;
	        	}
	        	isTable=false;
	        	if(text.contains("＜")){
	        		// "＞" 以下は表示させない. 
	        		text = getTitletoShow(text);
	        		text =HTMLTAG_EXCEL_TABLE_CAPTION + 
	        			  text +
	        			  HTMLTAG_EXCEL_TABLE_CAPTION_CLOSE; 
	            	isTable=true;
	        	}
	        	stringbuffer.append(text);
	        }else if(sizeElem == 0){
	        	//改行
	        	if(isTable){
	        		stringbuffer.append("</table>");
	        	}
	        	isTable=false;
	        	countRow=0;
	        	countNewLine++;
	        	stringbuffer.append(newline);
	        }else{        	
	        	if(isTable){
	        	countRow++;
	        	int countColumn=0;
	            ArrayList<String>  listColumnText=new ArrayList<String>();
	        	for(Element tdElem:tdElems){
	        		String tagName = tdElem.tagName();
	        		if(tagName.equals("td")){
	        			countColumn++;
	        			String tdText = tdElem.text();
	        		listColumnText.add(tdText);
	        		}
	        	}
	        	int countColum = listColumnText.size();
	        	Double widthsize = (double) (100/countColum);
	        	String column = new String();
	        	if(countRow==1){
	        	//	HTMLTAG_TABLE;
	        		column = HTMLTAG_TABLE;
	        	}
	        	column = column +"<tr>"; 
	        	for(String valcell:listColumnText){
	        	String eachcell = "<td style=\"width:"+
	        			           widthsize.toString();
	        	if(countRow ==1){
		        	eachcell = eachcell + "%;background:#eeeeee;\">" + valcell +"</td>";	        		
	        	}else{
	        		eachcell = eachcell + "%;background:#ffffff;\">" + valcell +"</td>";
	        	}
	        	column = column +eachcell; 
	        	}
	        	column = column + "</tr>";
	        	stringbuffer.append(column);
	        	listColumnNum.add(countColumn);
	        	}        	
	        }
	     }
	     stringbuffer.append(newline);
	     stringbuffer.append(HTML_FOOTER);
	     retText = stringbuffer.toString();
	   	 setRetText(stringbuffer.toString() );
	 
	   	 return this.getRetText();
	}

}
