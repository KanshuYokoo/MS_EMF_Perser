import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.HeaderStories;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.POIXMLDocumentPart;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.RichTextString;

//Apatche Poiによるmicrosft wordの読み込み

public class readWordDoc{
	private String filePath="./";
	private String fileName="";
	private utilitiesScraping util = new utilitiesScraping();

	public void startReadWord()throws Exception{
		try{

			//todo:ファイル設定は別の場所で
			this.setFilePath("/Users/cccfrontier/work/POI_Test/");
			this.setFileName("【野球オフ企画】なんでもコラム_160224.docx");
			
			String documentfile = this.getFileName();
			String extention = util.getFileExtention(documentfile);
			if(extention.equals("doc")){
				this.openWordDocHMPF();				
			}else if(extention.equals("docx")){
				this.openWordDocXMPF();			
			}
	
		}catch(Exception e){
			throw e;
		}
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
	
	public void setFilePath(String path)throws Exception{
		try{
			if(path.isEmpty()){
				path=getCurrentWorkingDir();
			}
			this.filePath = path;
		}catch(Exception e){
			throw e;
		}
	}
	
	public String getFilePath()throws Exception{
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
			String ret = this.getFilePath() + this.getFileName();
			return ret;
		}catch(Exception e){
			throw e;
		}
	}
	
	//*.doc file using POI
	public void openWordDocHMPF()throws Exception{
		try{
			//https://poi.apache.org/document/quick-guide.html
			String filePath = this.getFileFullPath();
			FileInputStream micrsofDoc = new FileInputStream(new File(filePath));
//			POIFSFileSystem POIfs = new POIFSFileSystem(micrsofDoc);		
			HWPFDocument file = new HWPFDocument(micrsofDoc);
			StringBuilder textDoc = file.getText();
			String test = textDoc.toString();
            file.getParagraphTable();
			
		}catch(Exception e){
			throw e;
		}
	}
	//rlIdからデータ取得 POI使用	 
	public String getStringbyIrID(String vId) throws Exception{
		String ret="";		
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
    			  CellStyle styleRow = row.getRowStyle();        			  
    			  Iterator<Cell> iteCell = row.cellIterator();     			  
    			  String strRow ="";
    			  while(iteCell.hasNext()){
    				  Cell cell = iteCell.next();
    				  int cellType = cell.getCellType();		    
    				  xlsCell cellclass = new xlsCell(cell);        				  
    				  CellStyle styleCell =  cell.getCellStyle();
    				  String strCell2 = cellclass.getText();
    				  strRow = strRow +" " +"<td>"+ strCell2 +"</td>"; 
    				  cell.getAddress();
    			  }
    			  strRow = "<tr>" + strRow +"</tr>";
    			  System.out.println("getStringbyIrID : "+strRow);
        		  ret = ret + strRow;
    		  }
    		  ret = "<table>" + ret + "</table>";
    	   }
        }		
		return ret;
	}
	
	//*.docx file using POI
	public void openWordDocXMPF()throws Exception{
		try{
			//https://poi.apache.org/document/quick-guide.html
			String filePath = this.getFileFullPath();
			InputStream fis = new FileInputStream(filePath);
			XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
			
			System.out.println("para.getText() start ---");					
			List<XWPFParagraph> paragraphs = docx.getParagraphs();
			for (XWPFParagraph para : paragraphs) {
				String strPara = para.getText();
                System.out.println(strPara);

            }
			System.out.println("para.getText() --- end");
			//GetTable ダメ?
			//http://apache-poi.1045710.n5.nabble.com/read-table-from-word-document-using-XWPF-in-Aapche-poi-td4345743.html		
			Iterator<XWPFTable> ite = docx.getTablesIterator();
			if(ite.hasNext()){
				XWPFTable table=ite.next();
				String testTable = table.getText();
			}
           
			List<POIXMLDocumentPart> relations = docx.getRelations();
            for(POIXMLDocumentPart poixmlDocpart :relations){
            	PackagePart part = poixmlDocpart.getPackagePart();
            	String relID =  docx.getRelationId(poixmlDocpart);

            	System.out.println("XWPFRelation.DOCUMENT rel ID : "+relID);
            	if (part.getContentType().equals(XWPFRelation.DOCUMENT.getContentType())) {
         		   InputStream inpstream = part.getInputStream();
         		   String packagePart = part.toString();
         		   System.out.println("XWPFRelation.DOCUMENT POIXMLpackage : "+packagePart);
         		}
         	   String packageExt = part.getPartName().getExtension();
         	   System.out.println("packageEXT POIXML: "+packageExt);
         	  if(packageExt.equals("xlsx")){
        		  Workbook workbook = WorkbookFactory.create(part.getInputStream());
        		  Sheet sheet = workbook.getSheetAt(0);
        		  Iterator<Row> iteRow = sheet.iterator();
        		  
        		  while(iteRow.hasNext()){
        			  Row row = iteRow.next();
        			  CellStyle styleRow = row.getRowStyle();        			  
        			  Iterator<Cell> iteCell = row.cellIterator();     			  
        			  String strRow ="";
        			  while(iteCell.hasNext()){
        				  Cell cell = iteCell.next();
        				  int cellType = cell.getCellType();		    
        				  xlsCell cellclass = new xlsCell(cell);        				  
        				  CellStyle styleCell =  cell.getCellStyle();
        				  String strCell2 = cellclass.getText();
        				  strRow = strRow +" " + strCell2; 
        				  cell.getAddress();
        			  }
        			  System.out.println("poixmDoc : "+strRow);
        		  }
        	   }
            }
			
			
//           PackageRelationship rel = docx.getPackageRelationship();           
           OPCPackage pack = docx.getPackage();
           for (PackagePart part : pack.getParts()) {
              
        	   String packageExt = part.getPartName().getExtension();
        	   if (part.getContentType().equals(XWPFRelation.DOCUMENT.getContentType())) {
        		   InputStream inpstream = part.getInputStream();
        		   String packagePart = part.toString();
        		   System.out.println("XWPFRelation.DOCUMENT package : "+packagePart);
        		}  
        	   //part.getContentType().equals を使うようにする
        	   if(packageExt.equals("xlsx")){
        		  Workbook workbook = WorkbookFactory.create(part.getInputStream());
        		  Sheet sheet = workbook.getSheetAt(0);
        		  Iterator<Row> iteRow = sheet.iterator();
        		  
        		  while(iteRow.hasNext()){
        			  Row row = iteRow.next();
        			  CellStyle styleRow = row.getRowStyle();        			  
        			  Iterator<Cell> iteCell = row.cellIterator();     			  
        			  String strRow ="";
        			  while(iteCell.hasNext()){
        				  Cell cell = iteCell.next();
        				  int cellType = cell.getCellType();
        				    
        				  xlsCell cellclass = new xlsCell(cell);        				  
        				  CellStyle styleCell =  cell.getCellStyle();
        				  String strCell2 = cellclass.getText();
        				  strRow = strRow +" " + strCell2; 
        				  cell.getAddress();
        			  }
        			  System.out.println(strRow);
        		  }
        	   }	   
           }
           docx.close();	
		}catch(Exception e){
			String error = e.toString();
			throw e;
		}
	}
}

//cellクラス
class xlsCell {
	public Cell m_cell;
	public CellStyle m_styleCell;
	public String m_cellText ="";
	public int m_cellType=1;
	
	xlsCell(Cell cell){
		m_cell = cell;
		m_cellType = cell.getCellType();
		m_styleCell =  cell.getCellStyle();
		  switch (m_cellType) {
			case Cell.CELL_TYPE_STRING:
				String strCell = cell.getStringCellValue();
				m_cellText = strCell;
				break;
			case Cell.CELL_TYPE_NUMERIC:
				Double doubleCell = cell.getNumericCellValue();
				String format = m_styleCell.getDataFormatString();
				if(format.equals("General")){
					format="0";
				}
				DecimalFormat myFormatter = new DecimalFormat(format);
				String output = myFormatter.format(doubleCell);
//				m_cellText = Double.toString(doubleCell);
				m_cellText = output;
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				 String boolenCell = Boolean.toString(cell.getBooleanCellValue());
				 m_cellText = boolenCell;
				 break;
			case Cell.CELL_TYPE_FORMULA:
				 String strFormula = cell.getCellFormula();
				 m_cellText = strFormula;
				 break;
			case Cell.CELL_TYPE_BLANK:
				 m_cellText = "";
				 break;
			default:
				m_cellText = "";
				break;
			}	
	}
	
	public void setText(String text){
		this.m_cellText = text;
	}
	public String getText(){
		return this.m_cellText;
	}
}

//例外
class UnsetException extends Exception{
	public UnsetException(String message){
	      super(message);
	   }
}