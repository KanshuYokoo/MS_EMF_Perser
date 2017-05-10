import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class tableColumnDialogWindow extends JOptionPane implements PropertyChangeListener {
	//Labels
	private JLabel column1Label;
	private JLabel column2Label;
	
	//Field for text input
	private JFormattedTextField columnNum1Field;
	private JFormattedTextField columnNum2Field;
	
	//String for labels
	private  String clumns1LabelString="左側表の列数を入力";
	private  String clumns2LabelString="右側表の列数を入力";

	private int numColumns1=5;
	private int numColumns2=5;
	
	//button
	final JButton okay = new JButton("Ok");
	final JButton cancel = new JButton("Cancel");
	
	// panel to JoptionPane
	JPanel panel = new JPanel(new GridLayout(0, 1));

	public tableColumnDialogWindow(){
		super(new BorderLayout());
		//左側表
		column1Label = new JLabel(clumns1LabelString);
		columnNum1Field = new JFormattedTextField(numColumns1);
		columnNum1Field.setColumns(3);
		columnNum1Field.addPropertyChangeListener("value",this);
		
		//右側表
		column2Label = new JLabel(clumns2LabelString);
		columnNum2Field = new JFormattedTextField(numColumns2);
		columnNum2Field.setColumns(3);
		columnNum2Field.addPropertyChangeListener("value",this);
		
		//Layout labels
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(column1Label);
		labelPane.add(column2Label);
		
		//Layout text
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		fieldPane.add(columnNum1Field);
		fieldPane.add(columnNum2Field);
		
		//Button
		okay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane pane = getOptionPane((JComponent)e.getSource());
                pane.setValue(okay);
            }
        });
        okay.setEnabled(true);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane pane = getOptionPane((JComponent)e.getSource());
                pane.setValue(cancel);
            }
        });
        cancel.setEnabled(true);
        
		JPanel Fieldpanel = new JPanel(new GridLayout(1,1));
		Fieldpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		Fieldpanel.add(labelPane, BorderLayout.CENTER);
		Fieldpanel.add(fieldPane, BorderLayout.LINE_END);
		panel.add(Fieldpanel);

	}
	 
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object source = evt.getSource();
		Object obj1 = columnNum1Field.getValue();
		Object obj2 = columnNum2Field.getValue();
		if(source == columnNum1Field){
			if(obj1!=null){
			   int num1 = Integer.parseInt( obj1.toString() );
			   this.numColumns1 = num1;
			   if(obj2 != null){
			        okay.setEnabled(true);
			   }
			}else{
		        okay.setEnabled(false);
			}

		}else if(source == columnNum2Field){

			if(obj2!=null){
			   int num2 = Integer.parseInt( obj2.toString() );
			   this.numColumns2 = num2;
			   if(obj1 != null){
			        okay.setEnabled(true);
			   }
			}else{
		        okay.setEnabled(false);
			}	
		}	
	}
	
	protected JOptionPane getOptionPane(JComponent parent) {
        JOptionPane pane = null;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent)parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }
	
	public void openWindow(){
		
		int result =  JOptionPane.showOptionDialog(
                null, 
                panel, 
                "表が二つ並んでいます", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                new Object[]{okay, cancel}, 
                okay);
		
	        if (result == JOptionPane.OK_OPTION) {
	        	Object obj1 = getColumnNum1().getValue();
	        	int num1 = Integer.parseInt( obj1.toString() );
				this.numColumns1 = num1;
				
				Object obj2 = getColumnNum2().getValue();
	        	int num2 = Integer.parseInt( obj2.toString() );
				this.numColumns2 = num2;
				  		    	
	        } else {
	        	System.exit(0);
	        }
		
	}
	
	
	//getter setter
	public JFormattedTextField getColumnNum1() {
		return columnNum1Field;
	}

	public void setColumnNum1(JFormattedTextField columnNum1) {
		this.columnNum1Field = columnNum1;
	}

	public JFormattedTextField getColumnNum2() {
		return columnNum2Field;
	}

	public void setColumnNum2(JFormattedTextField columnNum2) {
		this.columnNum2Field = columnNum2;
	}

	public JLabel getColumn1Label() {
		return column1Label;
	}

	public void setColumn1Label(JLabel column1Label) {
		this.column1Label = column1Label;
	}

	public JLabel getColumn2Label() {
		return column2Label;
	}

	public void setColumn2Label(JLabel column2Label) {
		this.column2Label = column2Label;
	}

	public String getClumns1LabelString() {
		return clumns1LabelString;
	}

	public  void setClumns1LabelString(String clumns1Label) {
		clumns1LabelString = clumns1Label;
	}

	public  String getClumns2LabelString() {
		return clumns2LabelString;
	}

	public void setClumns2LabelString(String clumns2Label) {
		clumns2LabelString = clumns2Label;
	}

	public int getNumColumns1() {
		return this.numColumns1;
	}

	public void setNumColumns1(int numColumns1) {
		this.numColumns1 = numColumns1;
	}

	public int getNumColumns2() {
		return this.numColumns2;
	}

	public void setNumColumns2(int numColumns2) {
		this.numColumns2 = numColumns2;
	}

	
}
