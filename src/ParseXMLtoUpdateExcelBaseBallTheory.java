import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseXMLtoUpdateExcelBaseBallTheory extends ParseXMLtoUpdate{
//TODO 
   public enum TextType{
	   TITLE,
	   TEXT,
	   TABLESINGLE,
	   TABLEPARREL
   }	
   private int numRowTable1;
   private int numRowTable2;
   /**
    *
    */
   public String parse() throws Exception{
		TextType type =TextType.TITLE;
	     StringBuilder stringbuffer = new StringBuilder();;
	     Document doc = Jsoup.parse(getXmltext());   
	     Elements elems = doc.select("tbody tr");
	     boolean isTable=false;
	     boolean isDoubleTable=false;
	     int countRow=0;
	     int countNewLine=0;
         //表内の脚注
     	String noteTable1 = new String();
     	String noteTable2 = new String();
//	     ArrayList<Integer>  listColumnNum=new ArrayList<Integer>();
	     ArrayList<ArrayList<String> > listTableValue = new ArrayList<ArrayList<String> >(); 
	     for(Element elem: elems){
	         //// 
	        Elements tdElems = elem.select("td");
	        int sizeElem = tdElems.size();
	        if(sizeElem == 1){
	        	//文章 or タイトル
	          	String text = eliminateKataKanaAtEnd( tdElems.text());
	          	if(!isTable){
	          		text = text + newline;
	          	}
	        	if(isTable){
	        		stringbuffer.append("</table>");
	        		isTable=false;
	        		type = TextType.TEXT;
	        		stringbuffer.append(text);
	        	}else if(type == TextType.TABLEPARREL){
	        		type = TextType.TABLEPARREL;
	        		noteTable1 = noteTable1 + text;
	        	}else if(text.contains("＜")){
	        		// "＞" 以下は表示させない. 
	        		text = getTitletoShow(text);
	        		text =HTMLTAG_EXCEL_TABLE_CAPTION + 
	        			  text +
	        			  HTMLTAG_EXCEL_TABLE_CAPTION_CLOSE; 
	            	isTable=true;
	            	type = TextType.TABLESINGLE;
	            	stringbuffer.append(text);
	        	}else{
		        	isTable=false;
	        		type = TextType.TEXT;	  
	        		stringbuffer.append(text);
	        	}
		        }else if(sizeElem == 2){
	        	//
	        	String text1 = tdElems.get(0).text();
	        	String text2 = tdElems.get(1).text();
	        	if(text1.contains("タイトル")){
	        		//タイトル
	        		stringbuffer.append(text2);
	        		stringbuffer.append(newline);
	        	}else if(text1.contains("＜") && text2.contains("＜")){
	        		String tbtitle1 = getTitletoShow(text1);
	        		String tbtitle2 = getTitletoShow(text2);
	        		isDoubleTable=true;	    
	        		type = TextType.TABLEPARREL;
	        		ArrayList<String> listcolValue = new ArrayList<String>();
	        		listcolValue.add(tbtitle1);
	        		listcolValue.add(tbtitle2);
	        		listTableValue = new ArrayList<ArrayList<String> >();
	        		listTableValue.add(listcolValue);
	        	}else if(text1.contains("文字数")){
	        	
	        		////////////////////////
	        		if(	type == TextType.TABLEPARREL ){
	        		/////////////////////////////////////////////
	        		//表が二つ. 表データを全て読み込んでから表HTMLを作る。
		        	isTable=false;
		        	type = TextType.TEXT;
		        	//listTableValueの一行目はタイトル
		        	ArrayList<String> titleList = listTableValue.get(0);
		        	String table1;
		        	String table2;
		        	String title1 =(String) titleList.get(0);
		        	String title2 =(String) titleList.get(1);
		        	table1 = HTMLTAG_EXCEL_TABLE_CAPTION + 
		        			 title1 +
		        			 HTMLTAG_EXCEL_TABLE_CAPTION_CLOSE +
		        			 HTMLTAG_TABLE;
		        	table2 = HTMLTAG_EXCEL_TABLE_CAPTION + 
		        			 title2 +
		        			 HTMLTAG_EXCEL_TABLE_CAPTION_CLOSE +
		        			 HTMLTAG_TABLE;		        	
		        	tableColumnDialogWindow window = new tableColumnDialogWindow();
		        	window.setClumns1LabelString("左側表の列数を入力");
		        	window.openWindow();
		        	numRowTable1 = window.getNumColumns1();
		        	numRowTable2 = window.getNumColumns2();
		        	
		        	for(int i=1;i<listTableValue.size();i++){
		        		ArrayList<String> cellList = listTableValue.get(i);
		        		int numCol = cellList.size();
		        		Double widthRow = (double)(100/numCol);
		        		String colwidth = widthRow.toString();
		        		table1 = table1 + "<tr>";
		        		table2 = table2 + "<tr>";
		        		//列の数が合わない時
		        		if(numRowTable1 + numRowTable2 != numCol){
		        			if(numRowTable1 == numCol){
				        		if(i==1){
				        			for(int icol1=0;icol1 < numRowTable1;icol1++){
				        				table1 = table1 +
				        					"<td style=\"width:" + 
				        					 colwidth +
				        			         "%;background:#eeeeee;\">" +
				        					 cellList.get(icol1) +
				        				    "</td>";
				        				}
		        			    }else{
				        			for(int icol1=0;icol1 < numRowTable1;icol1++){
				        				table1 = table1 +
				        					"<td style=\"width:" + 
				        					 colwidth +
				        			         "%;background:#ffffff;\">" +
				        					 cellList.get(icol1) +
				        				    "</td>";
				        				}  
		        				 }
				        		table1 = table1 + "</tr>";

		        			 }else if(numRowTable2 == numCol){
					        		if(i==1){
					        			for(int icol1=0;icol1 < numRowTable2;icol1++){
					        				table2 = table1 +
					        					"<td style=\"width:" + 
					        					 colwidth +
					        			         "%;background:#eeeeee;\">" +
					        					 cellList.get(icol1) +
					        				    "</td>";
					        				}
			        			    }else{
					        			for(int icol1=0;icol1 < numRowTable2;icol1++){
					        				table2 = table1 +
					        					"<td style=\"width:" + 
					        					 colwidth +
					        			         "%;background:#ffffff;\">" +
					        					 cellList.get(icol1) +
					        				    "</td>";
					        				}  
			        				 }	
					        		table2 = table2 + "</tr>";
			        		}else if(numRowTable1 + 1 == numCol){
			        			if(i==1){
				        			for(int icol1=0;icol1 < numRowTable1;icol1++){
				        				table1 = table1 +
				        					"<td style=\"width:" + 
				        					 colwidth +
				        			         "%;background:#eeeeee;\">" +
				        					 cellList.get(icol1) +
				        				    "</td>";
				        				}
		        			    }else{
				        			for(int icol1=0;icol1 < numRowTable1;icol1++){
				        				table1 = table1 +
				        					"<td style=\"width:" + 
				        					 colwidth +
				        			         "%;background:#ffffff;\">" +
				        					 cellList.get(icol1) +
				        				    "</td>";
				        				}
			        		   }
				        		table1 = table1 + "</tr>";
			        			//
			        			noteTable2 = cellList.get(numRowTable1);
			        		}else if(numCol ==2){
			        			noteTable1 = cellList.get(0)+newline;
			        			noteTable2 = cellList.get(1)+newline;
			        		}else if(numCol ==1){
			        			noteTable1 = cellList.get(0)+newline;
			        		}else{
			        			/*
			        			for(int icol=0;icol<numCol;icol++){
			        				noteTable1 += cellList.get(icol)+newline;
			        			}*/
			        			
			        			//表が二つ必要 error
			        			System.out.println("Error at converting Excell file to update Html format");
			        			return "Error at converting Excell file to update Html"+ newline + this.getXmltext();
			        		}		        			
		        		}else{
		        		//////
		        		if(i==1){
		        			for(int icol1=0;icol1 < numRowTable1;icol1++){
		        				table1 = table1 +
		        					"<td style=\"width:" + 
		        					 colwidth +
		        			         "%;background:#eeeeee;\">" +
		        					 cellList.get(icol1) +
		        				    "</td>";
		        				}
		        			for(int icol2=numRowTable1;icol2 < numCol;icol2++){
		        				table2 = table2 +
			        					"<td style=\"width:" + 
			        					 colwidth +
			        			         "%;background:#eeeeee;\">" +
			        					 cellList.get(icol2) +
			        				    "</td>";
		        				}
		        			
		        		   }else{
		        			for(int icol1=0;icol1 < numRowTable1;icol1++){
		        				table1 = table1 +
		        					"<td style=\"width:" + 
		        					 colwidth +
		        			         "%;background:#ffffff;\">" +
		        					 cellList.get(icol1) +
		        				    "</td>";
		        				}
		        			for(int icol2=numRowTable1;icol2 < numCol;icol2++){
		        				table2 = table2 +
			        					"<td style=\"width:" + 
			        					 colwidth +
			        			         "%;background:#ffffff;\">" +
			        					 cellList.get(icol2) +
			        				    "</td>";
		        				}
		        			}
		        		table1 = table1 + "</tr>";
		        		table2 = table2 + "</tr>";
		        		//////
		        		}
		        	}
	        		table1 = table1 + "</table>";
	        		table2 = table2 + "</table>";
	        		stringbuffer.append(table1);
	        		if( !noteTable1.isEmpty() ){
	        			stringbuffer.append(noteTable1);
	        		}
	        		stringbuffer.append(newline);
	        		stringbuffer.append(table2);
	        		if( !noteTable2.isEmpty() ){
	        			stringbuffer.append(noteTable2);
	        			stringbuffer.append(newline);
	        		}
	//	        	stringbuffer.append(newline);
	        		type = TextType.TEXT;
		        	//////////////////////////////////////////
	        		}

	        	}	        	
	        }else if(sizeElem == 0){
	        	//表が一つ
	        	if(type == TextType.TABLESINGLE ){
	        		stringbuffer.append("</table>");
//		        	stringbuffer.append(newline);
		        	isTable=false;
		        	type = TextType.TEXT;
	        	}else if(type == TextType.TABLEPARREL){
	        	//do nothing	
	        	}
	        	
	        	//改行
	        	isTable=false;
	        	//type = TextType.TEXT;
	        	countRow=0;
	        	countNewLine++;
	        }else{        	
	        	//表が一つ
	        	if(type == TextType.TABLESINGLE){
	        		countRow++;
	        //		int countColumn=0;
	        		ArrayList<String>  listColumnText=new ArrayList<String>();
	        		for(Element tdElem:tdElems){
	        			String tagName = tdElem.tagName();
	        			if(tagName.equals("td")){
	       // 				countColumn++;
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
	        		//listColumnNum.add(countColumn);
	           }else if(type == TextType.TABLEPARREL){
	        	   //表が二つ
	        		countRow++;
	        		int countColumn=0;
	        		ArrayList<String>  listColumnText=new ArrayList<String>();
	        		for(Element tdElem:tdElems){
	        			String tagName = tdElem.tagName();
	        			if(tagName.equals("td")){
	        				countColumn++;
	        				String tdText = eliminateKataKanaAtEnd( tdElem.text() );
	        				listColumnText.add(tdText);
	        			}
	        		}
	        		listTableValue.add(listColumnText);
//	        		if(countColumn%2 ==0){
//	        			listTableValue.add(listColumnText);
//	        		}else{
	        			//表が二つ必要 error
//	        			System.out.println("Error at converting Excell file to update Html format");
//	        			return "Error at converting Excell file to update Html"+ newline + this.getXmltext();
//	        		}
	           }
	        }
	     }
	     stringbuffer.append(newline);
	     stringbuffer.append(HTML_FOOTER);
	     retText = stringbuffer.toString();
	   	 setRetText(stringbuffer.toString() );	 
	   	 return this.getRetText();
	}

	/**
	 * 表が二つ並列している場合に表の列数を入力してもらう
	 */
	public void showDialogtoGetNumColumns(){
		
	}

	public int getNumRowTable1() {
		return numRowTable1;
	}


	public void setNumRowTable1(int numRowTable1) {
		this.numRowTable1 = numRowTable1;
	}


	public int getNumRowTable2() {
		return numRowTable2;
	}


	public void setNumRowTable2(int numRowTable2) {
		this.numRowTable2 = numRowTable2;
	}
	
}
