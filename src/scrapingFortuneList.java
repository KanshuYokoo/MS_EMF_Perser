import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.ws.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.Connection.Base;
import org.jsoup.Connection.Method;

public class scrapingFortuneList{
	
//	private final String STMAXFORTUNE ="http://stmax.jp/stmax/FortuneTellingList.do?loginManagerPassword=debug&loginManagerAccount=debug&sortOrder=&leftMenuSelect=10&pageNumber=&sortKey=";
//	private final String STMAXFORTUNE ="http://stmax.jp/stmax/FortuneTellingList.do?loginManagerPassword=debug&loginManagerAccount=debug";
	private final String STMAXFORTUNE ="http://stmax.jp/stmax/FortuneTellingList.do?";
	private final String STAMXFORTUNETELLINGLISTDO = "http://stmax.jp/stmax/FortuneTellingList.do";
	private final String STMAXFORTUNEDATA = "http://stmax.jp/stmax/FortuneTellingSelect.do";
	private String url="";
	private final String ACCOUNT ="debug";
	private final String PASSWORD="debug";
	private final String LEFTMENU ="10";
	private final String SEARCHTELLINGDSTEKIND ="1";
	private String pageNumber ="1";
	//web管理画面に登録されている占いデータのページ数
	private String totalNumberofPages="0";
	//データベースに登録されている最後の日付
	private String lastYYYYMMDD="20160809";
	//fortune_telling_id:個別データページを表示する際に必要
	private String fortune_telling_id=""; 
	//DBにアップするデータ
	private ArrayList<Hashtable> data2up=new ArrayList<Hashtable>();
	//占いが１２月分登録されているか数える
	private  LinkedHashMap<Integer, Integer> countFortuneDaily = new LinkedHashMap<Integer, Integer>();
			
    public void startGetFortuneList() throws Exception{
		try{	
			scrapingFortuneListStart();
		} catch(Exception e){
			throw e;
		}
    }
	
    //読み込み開始
    public void scrapingFortuneListStart() throws Exception{
 	   try{		   
 		  getThisMonth();
 		  readFirstPage();
 		  readAllFortuneTellingPages();
 		 checkTheData();
 	   }catch(Exception e){
 		   throw e;
 	   }
    }  
	
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return this.url;
	}
	
	public Document getWebPageDoc(String url) throws Exception{
        try{     	
        	Document doc = Jsoup.connect(url).timeout(60*1000)
        			.data("loginManagerPassword","debug")
        			.data("loginManagerAccount","debug")
        			.get();

        	
        	doc.outputSettings().charset("SJIS");
        	return doc;
        }catch(Exception e) {
         throw e;
        }
	}

	public Document getWebHomePageDoc() throws Exception{
        Document doc;
        try{
        	doc = this.getWebPageDoc(getUrl());
        	//doc.outputSettings().charset("SJIS");
        }catch(Exception e) {
         throw e;
        }
        return doc;
	}

	//最初のページを開く。ページ数を取得しておく
	public void readFirstPage()throws Exception{
		try{
			//釣りマックス管理画面
			this.setUrl(this.STMAXFORTUNE);
			getTotalNumberofPage();
		}catch(Exception e){
			throw e;
		}
	}

	public void setPageNumber(String num)throws Exception{
		try{
			this.pageNumber = num;
		}catch(Exception e){
			throw e;
		}
	}
	
	public String getPageNumber()throws Exception{
		try{
			if(this.pageNumber.isEmpty()){
				throw new EmptyStackException("pageNumber is empty");
			}
			return this.pageNumber;
		}catch(Exception e){
			throw e;
		}
	}
	
	public void setfortunetellingId(String id)throws Exception{
		try{
		this.fortune_telling_id = id;
		}catch(Exception e){
			throw e;
		}
	}	
	
	public String getfortunetellingId()throws Exception{
		try{
			if(this.fortune_telling_id.isEmpty()){
				throw new EmptyStackException("fortune_telling_id is empty");
			}
		return this.fortune_telling_id;
		}catch(Exception e){
			throw e;
		}
	}
	
	//STMAXページのurlを生成する. this.pageNumberを設定してから使用する。
	public String getSTMAXFoutunePageUrl() throws Exception {
		String url = this.STAMXFORTUNETELLINGLISTDO
				+ "?loginManagerAccount=" + this.ACCOUNT
				+ "&loginManagerPassword=" + this.PASSWORD
				+ "&amp;leftMenuSelect=" + this.LEFTMENU
				+ "&amp;search_telling_date_kind="+ this.SEARCHTELLINGDSTEKIND
		        + "&pageNumber=" + getPageNumber();
		return url;
	}
 
  //個別データ編集ページurl. this.fortune_telling_idを設定してから使用する。	
  //例:http://stmax.jp/stmax/FortuneTellingSelect.do?loginManagerPassword=debug&loginManagerAccount=debug&fortune_telling_id=37248
	public String getDataPageUrl() throws Exception{
	   String url = this.STMAXFORTUNEDATA
			      + "?loginManagerAccount=" + this.ACCOUNT
				  + "&loginManagerPassword=" + this.PASSWORD
                  +"&fortune_telling_id="+ getfortunetellingId();
	   return url;
   }	
		
	//全ページ数を取得
	public void getTotalNumberofPage() throws Exception{
		String total="1";
		try{
			Document doc = getWebHomePageDoc();
			String class_total = "list_current";
			Elements elemsList = doc.getElementsByClass(class_total);
			Element elemParent = elemsList.get(0);
			String num = elemParent.text();
			String[] totalnum = num.split("/");
			this.totalNumberofPages =totalnum[1];
		}catch(Exception e){
			throw e;
		}
	}
	
	//this.pageNumberを更新して、次ページurlを開くurlに準備する。
	public boolean getFortuneNextPages(boolean ret) throws Exception{
		//boolean ret=true;
		try{
			String currentPage = this.pageNumber;
            int cur = Integer.parseInt(currentPage);
            int total = Integer.parseInt(this.totalNumberofPages);
			if(cur < total){
				int next = cur + 1;
				this.pageNumber = Integer.toString(next);
				this.url = this.getSTMAXFoutunePageUrl();
			}else{
				ret = false;
			}
		}catch(Exception e){
			throw e;
		}
		return ret;
	}
		
	//ページ数をループさせて最終更新日まで占いを登録する
	public void readAllFortuneTellingPages()throws Exception{
		try{
			int intTotal = Integer.parseInt(this.totalNumberofPages);
			int updateDay = Integer.parseInt(this.lastYYYYMMDD);
			boolean iscontinue=true;
			while(iscontinue){
				iscontinue = readAllFortuneTellingPage();
				iscontinue = getFortuneNextPages(iscontinue);					
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	//ページ内の全占いを読み込んでDBにUPする
	//登録済みの日時まできたらfalseを返す.	
	//bottom_right_list_inner
	 public boolean readAllFortuneTellingPage()throws Exception{
		 boolean ret=true;
         try{
        	 Document doc = getWebHomePageDoc();
        	 Element elem_list_table = doc.getElementById("list_table");
        	 Elements elems = elem_list_table.select("tr.bottom_right_list_inner");
        	 int totalnum =  elems.size();        	 
        	 for(int i=0;i<totalnum;i++){
        		 Element elemtr = elems.get(i);        		 
        		 String fortuneID = elemtr.getElementById("fortune_telling.list.fortune_telling_id.td").text();
        		 String fortuneDate = elemtr.getElementById("fortune_telling.list.telling_date.td").text();
        		 //釣りマックスに登録されている日時
        		 int intDateWeb = Integer.parseInt(fortuneDate);
        		 
        		 //DBに登録されている最終日
        		 int intLastDate = Integer.parseInt(this.lastYYYYMMDD);        		 
        		 if(intDateWeb > intLastDate){
            		 //日ごとの占いを数える（１２ヶ月分ある)
            		 if( this.countFortuneDaily.containsKey(intDateWeb) ){
            			 this.countFortuneDaily.put(intDateWeb, this.countFortuneDaily.get(intDateWeb)+1);
            		 }else{
            			 this.countFortuneDaily.put(intDateWeb,1);
            		 }
        			         			 
            		 String fortuneCode = elemtr.getElementById("fortune_telling.list.highest_lowest_code.td").text();  
            		 if(fortuneCode.equals("なし")){
            			 fortuneCode = "0";
            		 }else{
            			 fortuneCode = "1";
            		 }
            		 String fortuneMonth = elemtr.getElementById("fortune_telling.list.birth_month.td").text();
            		 String fortuneBusiness = elemtr.getElementById("fortune_telling.list.business.td").text();
            		 String fortuneMoney = elemtr.getElementById("fortune_telling.list.money.td").text();
            		 String fortuneLove = elemtr.getElementById("fortune_telling.list.love.td").text();
            		 String fortuneAmatch = elemtr.getElementById("fortune_telling.list.amatch.td").text();
            		 String fortuneHealth = elemtr.getElementById("fortune_telling.list.health.td").text();
            		 String fortuneText = elemtr.getElementById("fortune_telling.list.text.td").text();
            		 //String fortuneModifyDate = elemtr.getElementById("fortune_telling.list.modify_date.td").text();            		             		     			 
        	         //DB編集ページ
        			 this.setfortunetellingId(fortuneID);
        			 String dataUrl = getDataPageUrl();
        			 Document docDb = getWebPageDoc(dataUrl);
        			 Elements elements = docDb.select("table.bottom_right_input > tbody > tr > td > table > tbody > tr");
        			 //int num = elements.size();
        			 //幸運数
        			 Element luckyelem = elements.select("input[name=lucky_number]").first();
        			 String luckyNUmber = luckyelem.attr("value");
        			 //幸運色
        			 Element element12 = elements.select("input[name=color]").first();
        			 String luckyColor = element12.attr("value");
        			 //幸運方位
        			 Element element13 = elements.select("input[name=direction]").first();
        			 String direction = element13.attr("value");
        			 //幸運時間
        			 Element element14 = elements.select("input[name=time_zone]").first();
        			 String timeZone = element14.attr("value");
        			 //幸運飲食
        			 Element element15 = elements.select("input[name=food]").first();
        			 String food = element15.attr("value");
        			 //collect DB to update
        			 Hashtable<String,String> dataHash = new Hashtable();
        			 dataHash.put("yyyymmdd", encodeString(fortuneDate));
        			 dataHash.put("highest_code", encodeString(fortuneCode));
        			 dataHash.put("fortune_month",encodeString(fortuneMonth));
        			 dataHash.put("work_mark",encodeString(fortuneBusiness));
        			 dataHash.put("money_mark",encodeString(fortuneMoney));
        			 dataHash.put("love_mark",encodeString(fortuneLove));
        			 dataHash.put("game_mark",encodeString(fortuneAmatch));
        			 dataHash.put("health_mark", encodeString(fortuneHealth));
        			 dataHash.put("lucky_number",encodeString(luckyNUmber));
        			 dataHash.put("lucky_color",encodeString(luckyColor));
        			 dataHash.put("lucky_direction",encodeString(direction));
        			 dataHash.put("lucky_time",encodeString(timeZone));
        			 dataHash.put("lucky_food",encodeString(food));
        			 dataHash.put("comment",encodeString(fortuneText));        			         			         			 
        			 this.data2up.add(dataHash);
        		 }else{
        			 ret = false;
        			 break;
        		 }
        	 } 
          }catch(Exception e){
        	 throw e;
         }		 
		 return ret;
	 }	
	 
	 //Windows-31Jにエンコードする。
	 public String encodeString(String text1) throws Exception{
		 try{
		    String text = new String(text1.getBytes("Shift_JIS"),"Windows-31J");
		    return text;
		 }catch(Exception e){
			 throw e;
		 }
	 }
	 
	 public boolean checkTheData()throws Exception{
		 boolean ret=true;
		 try{
			 int numUpDays = this.countFortuneDaily.size();
			 Set setkey = this.countFortuneDaily.keySet();
			 ArrayList<Integer> keys = new ArrayList<Integer>(this.countFortuneDaily.keySet());
			 for(int i=0;i<keys.size() ;i++){
				 
				 int day1 = keys.get(i);
				 int day2=1;
				 int numFortune = this.countFortuneDaily.get(day1);
				 if(numFortune < 12){
					 String message = "No entry between. Only "+ numFortune+" months in" + Integer.toString(day1) ;
					 throw new EmptyStackException(message);
				 }
				 
				 if(i == keys.size()-1){
					 //DBの付けとの間に間隔を開けないように.
					 day2 = Integer.parseInt( this.lastYYYYMMDD);					 
				 }else{
					 day2 = keys.get(i+1);
				 }
				 Date date1 =     new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(Integer.toString(day1) );
				 Date date2 =     new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(Integer.toString(day2) );
				 long days = TimeUnit.MILLISECONDS.toDays( abs(date1.getTime() - date2.getTime()) );

				 if(days>1){
					 //todo:例外発生
					 String message = "No entry between " + Integer.toString(day1) + " to " + Integer.toString(day2);
					 throw new EmptyStackException(message);
				 }
			 }
		 }catch(Exception e){
			 throw e;
		 }
		 return ret;
	 }
	 //intのabs関数
	private int abs(int i) {
		return (i <= 0) ? 0 - i : i;
	}
	 //longのabs関数
	private long abs(long i) {
		return (i <= 0) ? 0 - i : i;
	}
	
	//DB登録
	public void uploadAllDate() throws Exception{
		try{
			int num = this.data2up.size();
			for(int i=0;i<this.data2up.size();i++){
				Hashtable<String,String> dataHash = this.data2up.get(i);
				
			}
		}catch( Exception e){
			throw e;
		}
	}
	
	//DB検索の時に使用する。
	public int getThisMonth() throws Exception{
		int month=0;
        try{
          SimpleDateFormat format = new SimpleDateFormat("yyyyMM00");
          Calendar cal = Calendar.getInstance();
          String stringWebYYYYMM = format.format(cal.getTime()); 
          month = Integer.parseInt(stringWebYYYYMM);
        }catch(Exception e){ 
          throw e;
        }   
        return month;
        }  
	
}

//例外
class EmptyStackException extends Exception{
	public EmptyStackException(String message){
	      super(message);
	   }
}


