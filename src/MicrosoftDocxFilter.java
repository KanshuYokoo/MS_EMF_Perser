import java.io.File;
import javax.swing.filechooser.*;

//import com.adobe.xmp.impl.Utils;

public class MicrosoftDocxFilter extends FileFilter{

	private utilitiesScraping Utils = new utilitiesScraping();	
	
	
	@Override
	public boolean accept(File f){
		if (f.isDirectory()) {
            return true;
        }
        
        String extension;
			String filename = f.getName();
			try {
				extension = Utils.getFileExtention(f.getName());
		        if (extension != null) {
		            if (extension.equals("docx") ){
		                    return true;
		            } else {
		                return false;
		            }
		        }else{
		            return false;
		        }				
				
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			return true;

	}

	@Override
	public String getDescription() {
		// TODO 自動生成されたメソッド・スタブ
		return "Microsft docx";
	}

	
}
