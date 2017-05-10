import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

//JPanel
public class fileChoosePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filePath;
	private String fileDirPath="./";
	private String fileName="";
	
	public void openChooserWindow() throws Exception{
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		//Handle open button action.
		boolean isOpen=true;
		while(isOpen){
	        int returnVal = fc.showOpenDialog(this);			
	        isOpen= !(savefilename(returnVal,fc));
		}
	}
	/**
	 * 
	 * @param returnVal
	 * @param fc
	 * @return
	 * @throws Exception
	 */
	public boolean savefilename(int returnVal, JFileChooser fc) throws Exception{
        boolean ret=true;
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File fileSlected = fc.getSelectedFile();
            this.setFileName(fileSlected.getName());
            this.setFileDirPath(fileSlected.getParentFile().getPath());
            this.setFilePath(fileSlected.getPath());
            
			ret = true;
		}else{
			try{
				openDialog();
				ret = false;
			}catch(Exception exc){
				this.openChooserWindow();
			}finally{
				ret = false;
			}
		}
		return ret;				
	}
	/**
	 * dialog 表示
	 * @throws Exception
	 */
	public void openDialog() throws Exception{
		dialogWindow dialog = new dialogWindow();
		dialog.openDialog();
	}

	//setter getter
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
	
	public void setFileDirPath(String path)throws Exception{
		try{
			if(path.isEmpty()){
				path=getCurrentWorkingDir();
			}
			this.fileDirPath = path;
		}catch(Exception e){
			throw e;
		}
	}
	
	public String getFileDirPath()throws Exception{
		String path="";
		try{
		path = this.fileDirPath;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
