
public class ParseXMLtoUpdate {

	protected final String HTML_FOOTER = "<hr size=\"2\" color=\"#0055A9\">DataStadium Inc.";
	protected final String HTMLTAG_TABLE = "<table style= \"width:100%;margin:0 auto;background:#000000;\">"; 
	protected final String HTMLTAG_EXCEL_TABLE_CAPTION = "<h4><font color=\"#0055A9\">" ; 
	protected final String HTMLTAG_EXCEL_TABLE_CAPTION_CLOSE = "</font></h4>";
	protected final String newline = System.getProperty("line.separator");	
	
	protected String xmltext;
	protected String retText;
	ParseXMLtoUpdate(){
	}
	
	public String parse() throws Exception{	
		return this.getXmltext();
	}
	
    /**
     * タイトルは＜＞に囲まれた部分のみ表示する
     * @param texOrig
     * @return
     */
    public String getTitletoShow(String texOrig){
    	String ret = "";
    	int indexOfstart = texOrig.indexOf("＜");
    	int indexOfend = texOrig.indexOf("＞")+1;
    	ret = texOrig.substring(indexOfstart, indexOfend);
    	return ret;
    }
    
    /**
     * 文の最後（〜。と ~? 以下）に出現するカタカナを削除する。
     * @param textOrig
     * @return
     */
    public String eliminateKataKanaAtEnd(String textOrig){
    	String ret = textOrig;
    	try{
    		int kanaIndex = ret.lastIndexOf("。");
    		int questionIndex = ret.lastIndexOf("？");
    		if(kanaIndex >0 && kanaIndex > questionIndex){    			
     		  ret = ret.substring(0, kanaIndex+1);	
    		}else if(questionIndex>0 && questionIndex > kanaIndex ){    			
       		  ret = ret.substring(0, questionIndex+1);	
      		}
        	return ret;    		
    	}catch(Exception e){
    		return textOrig;	
    	}
    }
    
	/**
	 * 半角カタカナを削除する
	 * @param textOrig
	 * @return
	 */
    public String eliminateHankakuKana(String textOrig){
    	String ret;
    	StringBuilder stringbuilder = new StringBuilder();
    	try{
    	int lengthText = textOrig.length();
    	for(int count=0;count<lengthText;count++){
    		char charTmp = textOrig.charAt(count);
    		if(charTmp >=  0xff61 && charTmp <= 0xff9f ){
    		  String test = String.valueOf(charTmp);
    		  System.out.println("Hankaku Katakana is eliminated: "+test);
    		}else{
    			stringbuilder.append(charTmp);
    		}
    		//byte charByte =  
    	}
    	
    	return stringbuilder.toString();
    	}catch(Exception e){
    		return textOrig;
    	}
    }
	
	//getter setter
	public String getXmltext() {
		return xmltext;
	}
	public void setXmltext(String xmltext) {
		this.xmltext = xmltext;
	}
	public String getRetText() {
		return retText;
	}
	public void setRetText(String retText) {
		this.retText = retText;
	}	
}
