import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class scrapingWebPage {

    private final String SOCCER_COLUMN_INDEX ="http://www.sponichi.co.jp/soccer/yomimono/column/index.html";

	private String url="";
	private Document urlDoc;
	private String classnameofcolumnList;
	private String lastupdateYYYYMMDD="20160600";
    
	//著者メイトauthor_Idの対応
	public static final Map<String, String> authorMap;
	static
	{
		authorMap = new HashMap<String,String>();
		authorMap.put("kaneko","1");
		authorMap.put("nishibe", "2");
		authorMap.put("totsuka", "3");
		authorMap.put("yamauchi", "4");
		authorMap.put("kamo", "5");
		authorMap.put("kawamoto", "6");
		authorMap.put("kazama", "7");
		authorMap.put("jyo", "8");
		authorMap.put("mizunuma", "9");
		authorMap.put("iwamoto", "10");
		authorMap.put("girolamo","11");
		authorMap.put("kaigai", "12");
	}
	
	public void openColumnHome() throws Exception {
		//setUrl("http://www.sponichi.co.jp/soccer/yomimono/column/index.html");
		setUrl(SOCCER_COLUMN_INDEX);
		Elements elems;
		try{	
			elems = getListOfColumnbyClass("news-listS01");
			getListofColumn(elems);
		} catch(Exception e){
			throw e;
		}
 }
		
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return this.url;
	}

	public void setClassNameColumnList(String name){
		this.classnameofcolumnList=name;
	}
	public String getClassNameColumnList(){
		return this.classnameofcolumnList;
	}
	
	public Document getWebPageDoc(String url) throws Exception{
		Document doc2 = Jsoup.connect(url).get();
		return doc2;
	}
	
	public Document getWebHomePageDoc() throws Exception{
		Document doc = this.getWebPageDoc(this.getUrl());
		return doc;
	}
	
	public Elements getListOfColumnbyClass(String className) throws Exception{
		Document doc = this.getWebHomePageDoc();
		Elements elems = doc.getElementsByClass(className);
		return elems;
	}
	
	public Elements getListOfColumn() throws Exception{
		Document doc = this.getWebHomePageDoc();
		String name = this.getClassNameColumnList();
		Elements elems = doc.getElementsByClass(name);
		return elems;
	}
	
	public int getColumnTotalNumber(Elements elems){
		int num=0;
	    num = elems.size();
		return num;
	}

	
	public void getListofColumn(Elements elems){
		ArrayList<Hashtable<String,String>> data = new ArrayList<Hashtable<String,String>>();
		try{
		Element elem = elems.first();
		Elements columns = elem.getElementsByTag("li");
        Element column = columns.first();
        String url = "";
        String date = "";
        String title = "";
        String title2 = "";
        String authorID="";
        String text="";
        for(Element col:columns){      	
        	url = col.select("a").attr("href");
        	title2 = col.select("a").text();
        	String dateKanji = col.ownText();
        	authorID = getAuthorID(url);        	
        	String[] columnkey = url.split("/");
        	int num = columnkey.length -1;
            String day = columnkey[num];
            date = day.substring(1, 9);  
            
            int dateInt = Integer.parseInt(date);
            int lastUpdateInt = Integer.parseInt(lastupdateYYYYMMDD);
            if(dateInt <= lastUpdateInt){
            	break;
            }
            
            Document docColumn = getColumnBodyDoc(url);
            text = getColumnNewsText(docColumn);
            title = getColumnHeading(docColumn);
            Hashtable<String,String> columnHash = new Hashtable();
    		columnHash.put("fk_soccer_column_author_id", authorID);
    		columnHash.put("yyyymmdd", date);
   	 	    columnHash.put("column_title", title);
    		columnHash.put("column_text_1", text);
    		columnHash.put("disp_code", "1");
    		data.add(columnHash);
            
        }
		}catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		ArrayList<Hashtable> data2 = new ArrayList<Hashtable>(data);
/*		
		Collections.sort(data, (o1, o2) -> {
			String o1yyyymmdd = (String)o1.get("yyyymmdd");
			String o2yyyymmdd = (String)o2.get("yyyymmdd");		    	
			int o1yyyymmddInt = Integer.parseInt(o1yyyymmdd);
			int o2yyyymmddInt = Integer.parseInt(o2yyyymmdd);
			if(o1yyyymmddInt > o2yyyymmddInt){
				return 1;
			}else if(o1yyyymmddInt < o2yyyymmddInt){
				return -1;
			}else{
				return 0;
			}
		});*/
		
		Collections.sort(data, new Comparator<Hashtable<String,String>>() {
			@Override
		    public int compare(Hashtable<String,String> o1, Hashtable<String,String> o2) {
				String o1yyyymmdd = (String)o1.get("yyyymmdd");
				String o2yyyymmdd = (String)o2.get("yyyymmdd");		    	
				int o1yyyymmddInt = Integer.parseInt(o1yyyymmdd);
				int o2yyyymmddInt = Integer.parseInt(o2yyyymmdd);
				if(o1yyyymmddInt > o2yyyymmddInt){
					return 1;
				}else if(o1yyyymmddInt < o2yyyymmddInt){
					return -1;
				}else{
					return 0;
				}		    	
		    }
		});
		
		ArrayList<Hashtable> data3 = new ArrayList<Hashtable>(data);
		 updateDatabase(data);
	}
	
	public static void updateDatabase(ArrayList<Hashtable<String,String>> data){
	for(int i=0; i<data.size();i++){
		Hashtable<String,String> columnHash = data.get(i);
		String test = columnHash.get("yyyymmdd");
	}	
	}
	
	public static String getAuthorID(String url) throws Exception{
		String ID="";
		String[] columnkey = url.split("/");
    	ID = authorMap.get(columnkey[4]);
		return ID;
	}
	
	//コラム本文Document取得
	public Document getColumnBodyDoc(String url) throws Exception{
		String urlabsolutepath = "http://www.sponichi.co.jp"+url;
		Document doc = Jsoup.connect(urlabsolutepath).timeout(60*1000).get();
		removeComments(doc);	
		return doc;
	}
	
	//コラム本文取得
	public String getColumnNewsText(Document doc) throws Exception{
		String heading="";
		heading = getTextbyClass(doc,"news-textB01");
		return heading;				
	}
	
	public String getTextbyClass(Document doc,String classname) throws Exception{
		String text="";
		Elements elems = doc.getElementsByClass(classname);		
		Element elemNewsBody = elems.first();
	    //text = elemNewsBody.ownText();
		text = elemNewsBody.html();
		return text;
	}

	//コラムタイトル取得
	public String getColumnHeading(Document doc) throws Exception{
		String heading="";
		Elements elems = doc.getElementsByClass("heading-newsB01");
		Elements elems2 = elems.first().getElementsByTag("h1");
		heading = elems2.first().ownText();
		return heading;				
	}
	
	//コメント欄削除
	private static void removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }
	
	/*
	//コメント欄削除
		private static void removeComments(Element node) {
	        for (int i = 0; i < node.childNodes().size();) {
	        	Element child = node.child(i);
	            //Node child = node.childNode(i);
	            if (child.nodeName().equals("#comment"))
	                child.remove();
	            else {
	                removeComments(child);
	                i++;
	            }
	        }
	    }*/
	
}
