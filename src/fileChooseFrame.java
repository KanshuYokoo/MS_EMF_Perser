import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
//JFrame
public class fileChooseFrame extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String filePath;
	private String fileDirPath;
	
	public void openChooserWindow() throws Exception{
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("docxを選択");
		boolean isOpen = true;
		while(isOpen){
		int returnVal = fc.showSaveDialog(null);
		isOpen = !(savefilename(returnVal,fc)); 
		}	
	}
	
	public boolean savefilename(int returnVal, JFileChooser fc) throws Exception{
        boolean ret=true;
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File fileSlected = fc.getSelectedFile();
			this.setFileName( fileSlected.getName() );
			this.setFilePath( fileSlected.getPath() );
			this.setFileDirPath(fileSlected.getParentFile().getPath());
			ret = true;
		}else{
			try{
				dialogWindow dialog = new dialogWindow();
				dialog.openDialog();
				ret = false;
			}catch(Exception exc){
				this.openChooserWindow();
			}finally{
				ret = false;
			}
		}
		return ret;				
	}
	
	//getter setter
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
}
