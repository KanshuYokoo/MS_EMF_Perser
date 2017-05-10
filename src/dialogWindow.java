import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class dialogWindow {
	private String objectmessage;
	private String titlemessage;
	
	dialogWindow(){}	
	dialogWindow(String v_objectmessage,String v_titlemessage){
		this.objectmessage = v_objectmessage;
		this.titlemessage = v_titlemessage;
	}
	public boolean openDialog() throws Exception
	{
		boolean ret = true;
		JFrame parent = new JFrame();
	    int value =  JOptionPane.showConfirmDialog(parent,
	    		                                  "終了しますか?", 
                                                  "ファイル未選択", 
                                                  JOptionPane.YES_NO_OPTION,
	    		                                  JOptionPane.QUESTION_MESSAGE);
	    if (value == JOptionPane.YES_OPTION) {
	    	System.exit(0);
	    } else if (value == JOptionPane.NO_OPTION) {
	    	ret = false;    	
	    }else if(value == JOptionPane.CLOSED_OPTION ){
	    	System.exit(0);
	    }
	    return ret;
	}
	//getter setter
	public String getObjectMessage() {
		return objectmessage;
	}
	public void setObjectmessage(String objectmessage) {
		this.objectmessage = objectmessage;
	}
	public String getTitleMessage() {
		return titlemessage;
	}
	public void setTitlemessage(String titlemessage) {
		this.titlemessage = titlemessage;
	}
}
