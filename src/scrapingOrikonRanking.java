import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class scrapingOrikonRanking {

    private final String ORIKON_RANKING ="http://www.sponichi.co.jp/entertainment/rank/";
    private final String TABLE_TITLE_ID ="midashi01";
    private final String TABLE_CLASS ="table_02";
    
    private StringBuilder singleRanking = new StringBuilder();
    private StringBuilder albumRanking = new StringBuilder();

	private int monthIndex=0;
	private int dayIndex=0;
    private String head_line="";
    private String article_title_single="";
    private String article_title_album="";
    
    private String singleRankingText=""; 
    private String albumRankingText="";
    
	private String url="";
    private String rankingday="";
    
    public void startOrinkonRanking() throws Exception{
    	Elements elems;
		try{	
			isWednesdayToday();
			setHomeUrl();
			scrapingOrikonRankingStart();
		} catch(Exception e){
			throw e;
		}
    }

    public void setHomeUrl() throws  Exception{
    	int year = Calendar.getInstance().get(Calendar.YEAR);
		String YYYY = Integer.toString(year);
		String orikonUrl = ORIKON_RANKING + YYYY + "/orikon/index.html";
		setUrl(orikonUrl);	
    }
    
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return this.url;
	}

	public Document getWebPageDoc(String url) throws Exception{
        try{
//    InputStream st= new URL(url).openStream();
	Document doc2 = Jsoup.connect(url).timeout(60*1000).get();
	return doc2;
        }catch(Exception e) {
         throw e;
        }
	}

	public Document getWebHomePageDoc() throws Exception{
        Document doc;
        try{
        	doc = this.getWebPageDoc(this.getUrl());
        	//doc = Jsoup.parse(new File(this.getUrl()), "ISO-8859-1");
//        	doc.outputSettings().charset("Shift_JIS");
//        	doc.outputSettings().charset("Windows-31J");
        	doc.outputSettings().escapeMode(EscapeMode.xhtml);
        	
        }catch(Exception e) {
         throw e;
        }
        return doc;
	}

	public Elements getListOfColumnbyClass(String className) throws Exception{
         Elements elems;
         try{
        	 Document doc = this.getWebHomePageDoc();
        	 elems = doc.getElementsByClass(className);
         }catch(Exception e) {
        	 throw e;
         }
         return elems;
	}
	
	public Element getElemById(String Id) throws Exception{
        Element elem;
        try{
       	 Document doc = this.getWebHomePageDoc();
       	 elem = doc.getElementById(Id);
       	 
        }catch(Exception e) {
       	 throw e;
        }
        return elem;
	}
    
   public void scrapingOrikonRankingStart() throws Exception{
	   try{		   
		   checkUpdateDayOrikon();
		   readOrikonRanking();
	   }catch(Exception e){
		   throw e;
	   }
   }    
   
   public void checkUpdateDayOrikon() throws Exception{
	   try{
		   Element elem = getElemById(this.TABLE_TITLE_ID);
		   String title = elem.select("h1").text();
		   title=title.replace("オリコンＣＤランキング　", "");
		   
		   for(int i=0;i<title.length();i++){
			   char ch = title.charAt(i);
			   if(ch=='月'){
				   monthIndex =i;
			   }
			   if(ch=='日'){
				   dayIndex =i;
			   }
		   }
		   String month = title.substring(0, monthIndex);
		   int intMonth = Integer.parseInt(month);
		   month = Integer.toString(intMonth);
		   if(month.length()==1){
			   month = "0"+month;
		   }
		   String day = title.substring(monthIndex+1, dayIndex);
		   if(day.length() ==1){
			   day = "0" + day;
		   }
		   this.head_line = month +"月"+ day +"日付け";
		   this.article_title_single = this.head_line + "CDシングル";
		   this.article_title_album = this.head_line + "CDアルバム";
		   
	   }catch(Exception e){
		   throw e;
	   }
   }
   
   public void readOrikonRanking() throws Exception{

	   try{

		   boolean single=true;
		   boolean album=false;
		   Elements elems = getListOfColumnbyClass(this.TABLE_CLASS);
		   Elements trelems = elems.select("tr");
		   int size = trelems.size();
		   int singleCnt = 0;
		   int albumCnt = 0;
		   for(Element elem:trelems){
			   Elements tdelems = elem.select("td");
			   int sizetr = tdelems.size();
			   if(sizetr==1){
				   String text = tdelems.select("span").text();
				   if(text.equals("シングル")){
					   single=true;
					   album=false;
				   }else if(text.equals("アルバム")){
					   single=false;
					   album=true;
				   }
			   }
			   if(sizetr==3){
				   if(single){
					  String pos = tdelems.get(0).text();
					  String cdname = tdelems.get(1).text();
					  String name = tdelems.get(2).text();
					  //HTML文字をエスケープする
					  String posCode = StringEscapeUtils.escapeHtml(pos);
					  String cdnameCode = StringEscapeUtils.escapeHtml(cdname);
					  String nameCode = StringEscapeUtils.escapeHtml(name);
					  String strFirst = posCode + "." + cdnameCode;
					  
					  byte[] byteData = name.getBytes("ISO_8859_1");
				      String val = new String(byteData, "Shift_JIS");					  
					  String test = convertToReferenceChar(name);
					  String ISO88591name = new String(name.getBytes("ISO-8859-1"), "ISO-8859-1");
					  String UTF8name = new String(name.getBytes("UTF8"), "UTF8");
					  
					  String eucjpStr = new String(cdname.getBytes("EUC_JP"), "EUC_JP");
					  String ShJisStr = new String(cdname.getBytes("Shift_JIS"), "Shift_JIS");


					  String eucjpStrCode = new String(cdnameCode.getBytes("EUC_JP"), "EUC_JP");
					  String ShJisStrCode = new String(cdnameCode.getBytes("Shift_JIS"), "Shift_JIS");
					  
					  
					  this.singleRanking.append(strFirst);
					  this.singleRanking.append("<br>");
					  this.singleRanking.append(nameCode);
					  this.singleRanking.append("<br>");
					  this.singleRanking.append("<br>");
					  singleCnt +=1;
					  
				   }else if(album){
					  String pos = tdelems.get(0).text();
					  String cdname = tdelems.get(1).text();
					  String name = tdelems.get(2).text();

					 // String ISO88591name = new String(name.getBytes("ISO-8859-1"), "ISO-8859-1");
					  String UTF8name = new String(name.getBytes("UTF8"), "UTF8");
					  String eucjpStr = new String(name.getBytes("EUC_JP"), "EUC_JP");
					  String ShJisStr = new String(name.getBytes("Shift_JIS"), "Shift_JIS");
					  String Windows31Str = new String(name.getBytes("Windows-31J"), "Windows-31J");
					  String Windows31ShiJisStr = new String(name.getBytes("Windows-31J"), "Shift_JIS");
					  String ShiJisWindows31Str = new String(name.getBytes("Shift_JIS"), "Windows-31J");
					  
					  
    				  //HTML文字をエスケープする
					  String posCode = StringEscapeUtils.escapeHtml(pos);
					  String cdnameCode = StringEscapeUtils.escapeHtml(cdname);
					  String nameCode = StringEscapeUtils.escapeHtml(name);
					  String strFirst = posCode + "." + cdnameCode;
					  
					  this.albumRanking.append(strFirst);
					  this.albumRanking.append("<br>");
					  this.albumRanking.append(nameCode);
					  this.albumRanking.append("<br>");
					  this.albumRanking.append("<br>");
					  albumCnt +=1;
				   }
			   }
			   
		   }
		   ////		    <hr size="2" color="#ED426B">オリコン調べ
		   String hrtab = "<hr size=\"2\" color=\"#ED426B\">オリコン調べ ";
		   this.singleRanking.append(hrtab);
		   singleRankingText = new String(this.singleRanking);		   
		   this.albumRanking.append(hrtab);
		   albumRankingText = new String(this.albumRanking);
			   
	   }catch(Exception e){
		   throw e;
	   }
   }
   
   public String convertToReferenceChar(String str) throws Exception{
          String retStr="";
          try{
        	  for(int i=0;i<str.length();i++){
        		  int code = str.codePointAt(i);
        		  retStr += "&#"+Integer.toString(code) +";";
        	  }
          }catch(Exception e){
        	  throw e;
          }
	   return retStr;
   }

   public boolean isWednesdayToday() throws Exception{
	   boolean boolReturn = true;
	   try{
		   Calendar cal = Calendar.getInstance();
           int week = cal.get(Calendar.DAY_OF_WEEK);
		   if(week != 4){
			   boolReturn = false;
		   }
	   }catch(Exception e){
		   throw e;
	   }   
	   return boolReturn;
   }
   
   public String formatDayWeekYYYYMMDD(String strYYYYMMDD) throws Exception {
	        String strReturn = null;
	        try {
	            Calendar cal = new GregorianCalendar();
	            int iYear = Integer.parseInt(strYYYYMMDD.substring(0, 4));
	            int iMonth = Integer.parseInt(strYYYYMMDD.substring(4, 6));
	            int iDay = Integer.parseInt(strYYYYMMDD.substring(6, 8));
	            cal.set(iYear, iMonth-1, iDay);

	            SimpleDateFormat weeks = new SimpleDateFormat ("EE"); 
	            Date currentTime = cal.getTime(); 
	            strReturn = weeks.format(currentTime); 
	        } catch(Exception e) {
	            throw e;
	        }
	        return strReturn;
   }   
}
