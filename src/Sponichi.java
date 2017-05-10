import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Sponichi {
		
	public static void main(String args[]){
		start();		
	}
	
	public static void start(){
				
			
		scrapingWebPage jsoupclass = new scrapingWebPage();
		scrapingOrikonRanking orikon = new scrapingOrikonRanking();
		scrapingFortuneList fortune = new scrapingFortuneList();
		readWordDoc readingWord = new readWordDoc();
		readMSWordTika readingWordTika = new readMSWordTika();
		try {
//			jsoupclass.openColumnHome();
//			orikon.startOrinkonRanking();
//			fortune.startGetFortuneList();
//			readingWord.startReadWord();  //POI
			readingWordTika.startReadWord();  //Tika
		} catch (Exception e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		

	}

	
}


