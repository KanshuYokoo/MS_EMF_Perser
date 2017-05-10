import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class textEditorWindow extends JFrame {

	private JFrame frame = new JFrame();
    private JTextArea inputUML;
    private String inputText="";
    private String labelTitle="docx to text";
    private String filePath;
    private String filename;
    private String fileext;
    private utilitiesScraping util = new utilitiesScraping();
    
    textEditorWindow(){    	
    }
    
    textEditorWindow(String text){
    	this.inputText = text;
    }
    
	public void openWindow(){
        frame.setLocationByPlatform(true);  
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setTitle(getLabelTitle());
        setFilename(getLabelTitle());             
        JPanel creationPanel = new JPanel();
        creationPanel.setLayout(new BorderLayout());
        
//        instructionlabel = new JLabel(getLabelTitle());
//        CreationPanel.add(instructionlabel,BorderLayout.NORTH);
		
        inputUML = new JTextArea("",100,80);
        inputUML.setFont(new Font("Osaka",Font.PLAIN, 9));
        inputUML.setLineWrap(true);
        inputUML.setWrapStyleWord(true);
        
        creationPanel.add(new JScrollPane(inputUML),BorderLayout.CENTER);
        frame.add(creationPanel);
        frame.pack();   
        frame.setLocationByPlatform(true);
        
        //File Menu 
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        //File - Save
        //TODO     
        JMenuItem itemSave = new JMenuItem("Save As");       
        itemSave.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		savefile();
        	}
        });        
        //fileMenu.add(itemSave);
        
        //File - Save As
        JMenuItem itemSaveAs = new JMenuItem("Save As");
        itemSaveAs.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		saveAs();
        	}
        });
        fileMenu.add(itemSaveAs);
        ////////////////////////
        //File - Open 
        JMenuItem itemOpen = new JMenuItem("Open New");
        itemOpen.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		readMSWordTika readingWordTika = new readMSWordTika();        	
			try {
				readingWordTika.startReadWord();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
				 
        	}
        });
        fileMenu.add(itemOpen);
        /////////////////////
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
                
        frame.setVisible(true);
        inputUML.append(getText());
        inputUML.setCaretPosition( inputUML.getText().length() );
	}
	/**
	 * save file
	 */
	//TODO 上書き保存できるように
	public void savefile(){
		
		String filename = this.getFilename();
		try {
			String fileext = util.getFileExtention(filename);
			String filepath = this.getFilePath();
			if(fileext.equals("docx") || fileext.equals("xlsx")){
				this.saveAs();
			}
			
			
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			this.saveAs();
		}
	}
	
	/**
	 * TexAreaのテキストを名前とつけて保存する
	 */
	public void saveAs(){
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Text File", "txt");
	      final JFileChooser saveAsFileChooser = new JFileChooser();
	      saveAsFileChooser.setAcceptAllFileFilterUsed(true);	      	      	      
	      saveAsFileChooser.setApproveButtonText("Save");
	      saveAsFileChooser.setFileFilter(extensionFilter);
//	      int actionDialog = saveAsFileChooser.showOpenDialog(this);
	      int actionDialog = saveAsFileChooser.showSaveDialog( frame);	     
	      if (actionDialog != JFileChooser.APPROVE_OPTION) {
	         return;
	      }
	      File file = saveAsFileChooser.getSelectedFile();    	  
	      if (!file.getName().endsWith(".txt")) {
	         file = new File(file.getAbsolutePath() + ".txt");
	      }
	      BufferedWriter outFile = null;
	      try {
	         outFile = new BufferedWriter(new FileWriter(file));
	         inputUML.write(outFile);
	         String filename = file.getName();
	         this.frame.setTitle(filename);
	         this.setFilename(filename);
	         this.setFilePath(file.getAbsolutePath() );	         
	      } catch (IOException ex) {
	         ex.printStackTrace();
	      } finally {
	         if (outFile != null) {
	            try {
	               outFile.close();
	            } catch (IOException e) {
	            	
	            }
	         }
	      }	
	}
	
	public void setText(String text){
		this.inputText = text;
	}
	
	public String getText(){
		return this.inputText;
	}
	
	public void setLabelTitle(String title){
		this.labelTitle=title;
	}
	
	public String getLabelTitle(){
		return this.labelTitle;
	}

	public String getFilePath() throws Exception {
		if(filePath.isEmpty()){
			throw new IOException();
		}
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileext() {
		return fileext;
	}

	public void setFileext(String fileext) {
		this.fileext = fileext;
	}
	
}
