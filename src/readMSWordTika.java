import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

//todo: class サーバーに移す時はutilitiesScrapingをこのファイルに同封する。

public class readMSWordTika  extends emfUtilities{
	private String filePath="./";
	private String fileName="";
	private String htmlfile="";
	private String fileext="";
	private String outputfile="tikatest";	
	private utilitiesScraping util = new utilitiesScraping();
	//emf Id = excel id + 1
	private HashMap<String,String> rIdEmfMap = new HashMap<String,String>();
	private final String HTML_FOOTER = "<hr size=\"2\" color=\"#0055A9\">DataStadium Inc.";
	private final String HTMLTAG_TABLE = "<table style= \"width:100%;margin:0 auto;background:#000000;\">"; 
	private final String HTMLTAG_EXCEL_TABLE_CAPTION = "<h4><font color=\"#0055A9\">" ; 
	private final String HTMLTAG_EXCEL_TABLE_CAPTION_CLOSE = "</font></h4>";
	private final String newline = System.getProperty("line.separator");	
	//private String emfBytes="";		
	public void startReadWord()throws Exception{
		try{
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty(
	                "com.apple.mrj.application.apple.menu.about.name",
	                "SponichiColumn");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//file 選択
			boolean isSelect=true;			
			   sellectFile();			
				String extention = getFileext();			
				//読み込みスタート
				//TODO wordのparserも別クラスにする
				if(extention.equals("docx")){
					openWordDoc();
					isSelect=false;
				}else if(extention.equals("xlsx")){
					String filename = this.getFileName();
					ParseXMLtoUpdate parser =new ParseXMLtoUpdate();
					if(filename.contains("なんでもランキング")){
						parser = new ParseXMLtoUpdateExcelNandemoRanking();
					}else if(filename.contains("野球クイズ")){
						parser = new ParseXMLtoUpdateExcellQuize();
					}else if(filename.contains("野球セオリー")){
						parser = new ParseXMLtoUpdateExcelBaseBallTheory();
					}else if(filename.contains("野球ニュース") || filename.contains("みどころ") ){
						parser = new ParseXMLtoUpdateExcellNewsMidokoro();
					}
					openExcelXls(parser);
					isSelect=false;
				}else{
					//TODO docx, xlsx以外を選択した時の処理。
					/*
					dialogWindow dialog = new dialogWindow();
					boolean isQuite = dialog.openDialog();
					if(!isQuite){
						isSelect=true;
					}
					*/
				}	
		}catch(Exception e){
			throw e;
		}
	}
	//fileを選択 batch処理の時は使用しない。
	//TODO: 拡張子が非対応の場合、alart 追加する.
	//https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
	public void sellectFile() throws Exception{		
//		fileChooseFrame  chooser = new fileChooseFrame();		
//		chooser.openChooserWindow();
		String name ="";
		String path ="";
		String pathDir="";
		String extention="";

		fileChoosePanel chooser = new fileChoosePanel();
		chooser.openChooserWindow();
		name = chooser.getFileName();
		path = chooser.getFilePath();
		pathDir = chooser.getFileDirPath();
		extention = util.getFileExtention(name);
			
		this.setFileName(name);
		this.setFileDirPath(pathDir);
		this.setFileext(extention);
	}
	
	public String getCurrentWorkingDir() throws Exception{
		String ret = "";
		try{
			Path currentRelativePath = Paths.get("");
			ret = currentRelativePath.toAbsolutePath().toString();
			return ret;
		}catch(Exception e){
			throw e;
		}
	}
	
	public void setFileDirPath(String path)throws Exception{
		try{
			if(path.isEmpty()){
				path=getCurrentWorkingDir();
			}
			this.filePath = path;
		}catch(Exception e){
			throw e;
		}
	}
	
	public String getFileDirPath()throws Exception{
		String path="";
		try{
		path = this.filePath;
		if(path.isEmpty()){
			throw new UnsetException("File path is not set yet.");
		}
		String last = path.substring( path.length() - 1);
		if(!last.equals("/")){
			path = path + "/";
		}
		return path;
		}catch(Exception e){
			throw e;
		}
	}

	public void setFileName(String name)throws Exception{
		try{
			if(name.isEmpty()){
				throw new UnsetException("File name is empty.");
			}
			this.fileName = name;
		}catch(Exception e){
			throw e;
		}
	}

	public String getFileName()throws Exception{
		String name="";
		try{
		name = this.fileName;
		if(name.isEmpty()){
			throw new UnsetException("File name is not set yet.");
		}		
		return name;
		}catch(Exception e){
			throw e;
		}
	}
	
	public String getFileFullPath()throws Exception{
		try{
			String ret = this.getFileDirPath() + this.getFileName();
			return ret;
		}catch(Exception e){
			throw e;
		}
	}
	
	//TikainputStream
	public TikaInputStream getTikaStream(String filePath)throws Exception{
		TikaInputStream inputstreamTika = TikaInputStream.get(new FileInputStream(new File(filePath)));
		return inputstreamTika;		
	}
	
	//from https://tika.apache.org/1.13/examples.html
	/**
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public String parseToHTML(String filePath) throws IOException, SAXException, TikaException {
    ContentHandler handler = new ToXMLContentHandler();	 
		
	   // ContentHandler handler = new BodyContentHandler( new ToXMLContentHandler() );	 
	    AutoDetectParser parser = new AutoDetectParser();
	    Metadata metadata = new Metadata();
	    String ret="";
	    try  {
	    	TikaInputStream inputstreamTika = getTikaStream(filePath);
	        parser.parse(inputstreamTika, handler, metadata);	     
	        ret = handler.toString(); 
	        inputstreamTika.close(); 
	    }catch(Exception e){
	    	System.out.println("Exception :" + e.toString());
	    }
	    return ret;
	}

	//text parser
	public String parseToText(String filePath)throws Exception {
	    OOXMLParser  msofficeparser = new OOXMLParser();
	    BodyContentHandler bodyContenthandler = new BodyContentHandler(-1);
	    ParseContext parsecontext2 = new ParseContext();
	    parsecontext2.set(Parser.class, new OOXMLParser());
	    Metadata metadata2 = new Metadata();
	    TikaInputStream inputstreamTika = getTikaStream(filePath);
	    msofficeparser.parse(inputstreamTika, bodyContenthandler, metadata2, parsecontext2);
	    String ret = bodyContenthandler.toString();
	    inputstreamTika.close();
		return ret;
	}
	
	//取得したxmlをパースしてDBに登録する形式に変換する
	/**
	 * Word なんでもコラム
	 * @param v_xml
	 * @return
	 */
     public String parseXMLtoUpdateForm(String v_xml){
    	 String ret="";
    	 String author = new String();
    	 StringBuilder cloumnStringBuilder = new StringBuilder();
    	 try{    		 
    	     Document doc = Jsoup.parse(v_xml);    		     	        	     
    	     Elements bodyElem1 = doc.getElementsByTag("body");
    	     for(Element elemBody : bodyElem1){
        	     
        	     Elements elemsChild = elemBody.children();
        	     for(Element elemCh :elemsChild){
        	    	  String elemChTag = elemCh.tagName();
        	    	  String elemClassAtrr = elemCh.attr("class");
        	    	 if(elemChTag.equals("p")){
        	    		 String toAdd = elemCh.text();
        	    		 if(!toAdd.isEmpty()){
        	    			 if(toAdd.contains("執筆者")){
        	    				 author = toAdd; 
        	    			 }else{
        	    				 cloumnStringBuilder.append(toAdd);
        	    				 cloumnStringBuilder.append(newline);    
        	    				 cloumnStringBuilder.append(newline);
        	    			 }
        	    		 }
        	    	 }else if(elemClassAtrr.equals("embedded")){        	  
        	    		 String rlID = elemCh.attr("id");
        	    		 System.out.println("xml parse from body embedded ID : " + rlID );
        	 	         //String excel =  getStringbyIrID(rlID);
        	 	         String excel1 = rIdEmfMap.get(rlID);        	 	        
        	 	         cloumnStringBuilder.append(excel1);
        	    	 }
        	     }        	     
    	     }    	         	         	 
    	 }catch(Exception e){
    	 }    	 
    	 cloumnStringBuilder.append(newline);
    	 cloumnStringBuilder.append(HTML_FOOTER);
    	 cloumnStringBuilder.append(newline);
    	 if(!author.isEmpty()){
    		 cloumnStringBuilder.append(author);
    	 }
    	 cloumnStringBuilder.append(newline);
    	 ret = cloumnStringBuilder.toString();
    	 return ret;
     }
     
	/**
	 * Word 内のexcel表、なんでもコラム     
	 * @throws Exception
	 */
     //docxのemfファイルを取り出す。 
     public void getEmfData() throws Exception{
 		String filePath = this.getFileFullPath();
 		InputStream fis = new FileInputStream(filePath);
 		XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
		//test emf 
 		getTextFromEmf(docx); 			
 		}          

    public void getTextFromEmf(XWPFDocument docx) throws Exception{	
		List<POIXMLDocumentPart> relations = docx.getRelations();		
        for(POIXMLDocumentPart poixmlDocpart :relations){
        	PackagePart part = poixmlDocpart.getPackagePart();        	
        	String relID =  docx.getRelationId(poixmlDocpart);
        	System.out.println("rlID : " + relID);
        	String type = part.getContentType();
        	if(type.contains("emf")){
        		XWPFPictureData picfromId = docx.getPictureDataByID(relID);
            	System.out.println("type.string : " + type.toString());
        		System.out.println("XWPFPictureData.string : " + picfromId.toString());
        		byte[] emfbyte = picfromId.getData();
        		//read emf file: work on progress... 
        		emfReader emfreader = new emfReader(emfbyte);
        		emfreader.readEMF();        
        		//List<HashMap<String,String>> gerRecordDataList()
        		List<HashMap<String,String>> recordDataList = emfreader.getRecordDataList();
        		String emftext = getEmfText(recordDataList);
        		int tmp = getIntfromId(relID) +1;   
        		String emfIrd = "rId" + Integer.toString(tmp);
        		this.rIdEmfMap.put(emfIrd,emftext);
        	}
        }
    } 

    public int getIntfromId(String rId) throws Exception{
    	int ret;
    	try{
    	String strNumber=rId.replace("rId", "");
    	ret = Integer.parseInt(strNumber);
    	}catch(Exception e){
    		throw e; 
    	}
    	return ret;
    }
/**
 * emfファイルからテキストを読み取る 
 * Wordに添付された表を読む
 * @param recordDataList
 * @return
 */
    public String getEmfText(List<HashMap<String,String>> recordDataList){
    	String ret="";
    	StringBuilder stringbilder = new StringBuilder();
//    	String tableString=new String();
//    	String newline = System.getProperty("line.separator");
        //test write
        int countRow=0;
     	//TODO get the font size from emf data.
     	int fontSizePoint=15;
     	int currPy=0;
     	int nextPy=0;
     	List<String> textlist = new ArrayList<String>();
     	List<String> captionlist = new ArrayList<String>();
 		String textrow =new String();
 		int countColum=1;
        for(HashMap<String,String> dataHash :recordDataList){
         	String name = dataHash.get("name");         	     		
         	if(name.equals("EMR_EXTTEXTOUTW")){
         		//text
         		String outtext = dataHash.get("outText");
         		System.out.println("emf text: " + outtext);  
         		//text position point
         		String pointLy = dataHash.get("pointLy");         		
        		int pointY = Integer.parseInt(pointLy);        		
        		System.out.println("pointLy : " + pointLy);
         		if(countRow == 0){
         			currPy=pointY;	
         			outtext.replace("<", "");
         			outtext.replace(">", "");
         			textlist.add(outtext);
         	     	textlist.add(HTMLTAG_TABLE);
         			++countRow;
         			countColum=1;
         		}else{
         			nextPy = pointY;
                    if(Math.abs(nextPy - currPy) < fontSizePoint){
         				//same row
                    	textrow = textrow + "<td style=\"width:xxxxx%\">"+outtext +"</td> ";
                    	countColum++;
                    	} 
         			else
         			{
         				//new line 改行
         				if(!textrow.isEmpty()){
         					if(countColum ==1){
         						textrow = textrow.replace("<td style=\"width:xxxxx%\">", "");
         						textrow = textrow.replace("</td>", "");
         						captionlist.add(textrow);
         					}else{
         					String strRow;         				
         					//tcolumnのwithを計算する.
         					Double widthsize = (double) (100/countColum);
         					Double roundSize =  round(widthsize,2);
         					String withpercent = Double.toString(roundSize);
         					textrow = textrow.replace("xxxxx", withpercent);
         					if(countRow ==2){
         						strRow = "<tr style=\"background:#eeeeee\">" + textrow +"</tr>";
         					}else{
         						strRow = "<tr style=\"background:#ffffff\">" + textrow +"</tr>";         					
         					}
             				textlist.add(strRow);
         					}
         				}
         				String firstcolum;
         				firstcolum = "<td style=\"width:xxxxx%\">" + outtext + "</td>";
             			textrow = new String(firstcolum);
             			++countRow ;
             			countColum=1;
             			currPy = nextPy;
         			}
         		}
         	}
         	//EMR_EOF : file end
         	if(name.equals("EMR_EOF")){
         		if(countColum==1){
         			//columnの数が一つの時は表の要素ではない
					textrow = textrow.replace("<td style=\"width:xxxxx%\">", "");
					textrow = textrow.replace("</td>", "");
         			captionlist.add(textrow);
         		}else{
         			Double widthsize = (double) (100/countColum);
         			Double roundSize =  round(widthsize,2);
         			String withpercent = Double.toString(roundSize);
         			textrow = textrow.replace("xxxxx", withpercent);
         			String strRow = "<tr style=\"width:25%;background:#ffffff\">" + textrow +"</tr>";  			    
         			textlist.add(strRow);
         			}
    			textlist.add("</table>");
         	}
        }    
    	for(String text: textlist){
    		if(!text.isEmpty()){
    			System.out.println(text);         			
    			stringbilder.append(text);
    		}
 		}
    	for(String caption: captionlist){
    		if(!caption.isEmpty()){
    			stringbilder.append(caption);
    			stringbilder.append(newline);
    		}
    	}
    	
		stringbilder.append(newline);	
    	ret = stringbilder.toString();
    	return ret;
    }
    /**
     * ダブル丸め
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
        
	//*.doc file using TIKA
    /**
     * Word docx
     * @throws Exception
     */
	public void openWordDoc()throws Exception{
		try{
			//test emf
	        getEmfData();
	        //call parsetohtml
	        String html1 = parseToHTML(this.getFileFullPath());
	        String textToUp = parseXMLtoUpdateForm(html1);	        	        		
	        System.out.println("parsed text to upload:");
	        System.out.println(textToUp);
	        
	        //open in window
	        //batchの時にはいらない
	        showEditorWindow(textToUp);
	        
	        }catch(Exception e){
			 throw e;
		    }
	    }
	
	//*.doc file using TIKA
	/**
	 * Excel
	 * @throws Exception
	 */
	public void openExcelXls(ParseXMLtoUpdate parser)throws Exception{
		String textToUp = new String();
		String html1 = new String();
		try{
	        //call parsetohtml
	        html1 = parseToHTML(this.getFileFullPath());
	        parser.setXmltext(html1);	   
	        textToUp = parser.parse();	        	        		
	        
	        System.out.println("parsed text to upload:");
	        System.out.println(textToUp);
	        //open in window
	        //batchの時にはいらない
	        showEditorWindow(textToUp);
	        }catch(Exception e){
			  textToUp = e.toString() + 
					     newline +
					     html1;
		    }
	}
	    
	/**
	 * TextAreaに表示する
	 * @param textToUp　TextAreaに表示する変換後テキスト
	 */
   public void showEditorWindow(String textToUp){
	   textEditorWindow window = new  textEditorWindow(textToUp);
       window.setLabelTitle(this.fileName);
       window.openWindow();
   } 

	public String getHtmlfile() {
		return htmlfile;
	}
	public void setHtmlfile(String htmlfile) {
		this.htmlfile = htmlfile;
	}
	public String getOutputfile() {
		return outputfile;
	}
	public void setOutputfile(String outputfile) {
		this.outputfile = outputfile;
	}
	public String getFileext() {
		return fileext;
	}
	public void setFileext(String fileext) {
		this.fileext = fileext;
	}
	
	//rlIdからExcelデータ取得 POI使用
	//いらない
	/*
	public String getStringbyIrID(String vId) throws Exception{
		String ret="";		
		StringBuilder stringbilder = new StringBuilder();		
		//出力はHTMLの<table>で囲む
		stringbilder.append(HTMLTAG_TABLE);
		
		String filePath = this.getFileFullPath();
		InputStream fis = new FileInputStream(filePath);
		XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
				
		List<POIXMLDocumentPart> relations = docx.getRelations();
        for(POIXMLDocumentPart poixmlDocpart :relations){
        	PackagePart part = poixmlDocpart.getPackagePart();
        	String relID =  docx.getRelationId(poixmlDocpart);
              	   
     	  if(!relID.isEmpty() && vId.equals(relID)){
    		  Workbook workbook = WorkbookFactory.create(part.getInputStream());
    		  Sheet sheet = workbook.getSheetAt(0);
    		  Iterator<Row> iteRow = sheet.iterator();    		  
    		  
    		  while(iteRow.hasNext()){
    			  Row row = iteRow.next();    			  
    			  Iterator<Cell> iteCell = row.cellIterator();     			  
    			  String strRow ="";
    			  while(iteCell.hasNext()){
    				  Cell cell = iteCell.next();
//    				  int cellType = cell.getCellType();		    
    				  xlsCell cellclass = new xlsCell(cell);        				  
//    				  CellStyle styleCell =  cell.getCellStyle();
    				  String strCell2 = cellclass.getText();
    				  strRow = strRow +" " +"<td>"+ strCell2 +"</td>"; 
    				  //cell.getAddress();
    			  }
    			  strRow = "<tr style=\"width:20%;background:#ffffff\">" + strRow +"</tr>";
    			  stringbilder.append(strRow);
    			  stringbilder.append(System.getProperty("line.separator"));
    		  }
    		  stringbilder.append("</table>");
    		  ret = stringbilder.toString();
    	   }
        }
        fis.close();
		return ret;
	}
*/	
}

class PerseDocmentClass{
	private TikaInputStream inputstreamTika;
	private ContentHandler contentHandler;
	private Metadata metadata;
	private ParseContext context;
	private List<Metadata> metadatas;
	
	PerseDocmentClass(TikaInputStream v_inputstreamTika,
			          ContentHandler v_contentHandler,
			          Metadata v_metadata,
			          ParseContext v_context,
			          List<Metadata> v_metadatas){
		this.inputstreamTika = v_inputstreamTika;
		this.contentHandler = v_contentHandler;
		this.metadata = v_metadata;
		this.context = v_context;
		this.metadatas = v_metadatas;
	}
	
	public void setInputStream(TikaInputStream inputstreamTika){
		this.inputstreamTika = inputstreamTika;
	}
	public TikaInputStream getInputStream(){
		return this.inputstreamTika;
	}
	public void setContentHolder(ContentHandler contentHolder){
		this.contentHandler = contentHolder;
	}
	public ContentHandler getContentHolder(){
		return this.contentHandler;
	}
	public void setMetadata(Metadata metadata){
		this.metadata = metadata;
	}
	public Metadata getMetadata(){
		return this.metadata;
	}
	public void setParseContext(ParseContext context){
		this.context = context;
	}
	public ParseContext getParseContext(){
		return this.context;
	}
	public void setMetadatas(List<Metadata> metadatas){
		this.metadatas = metadatas;
	}
	public List<Metadata> getMetadatas(){
		return this.metadatas;
	}	
}

