import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class emfReader extends emfUtilities{	
	
	//EMF file
	protected byte[] emfbytes;

    //data list
	protected List<HashMap<String,String>> recordDataList = new ArrayList<HashMap<String,String>>();
	protected List<HashMap<String,String>> outtextWDataList = new ArrayList<HashMap<String,String>>();

	
	emfReader(byte[] v_emfbytes) {
		emfbytes = v_emfbytes;
	}	
	public void setBytesData(byte[] v_emfbytes){
		emfbytes = v_emfbytes;
	}	
	public void readEMF() throws Exception{
		emfTypeSelect typeSelect = new emfTypeSelect(emfbytes);
		int nextType;
		String nameTitle;		
        int bytelenth = emfbytes.length;
//        System.out.println("index size: " + bytelenth );
        int i =0;
        while(i<bytelenth){
//        	System.out.println("index: " + i );
        	nextType = get4bytesInt(emfbytes, i);
    		nameTitle = typeSelect.getTheTitleString(nextType);
    		emfRecordType reader = (emfRecordType) typeSelect.getTypeInstance(nextType).clone();
    		reader.setName(nameTitle);
    		String className = reader.getName();    		
    		System.out.println("Type: " + className);
    		reader.setBytesData(emfbytes);
    		int itmp = reader.read(i);			
    		recordDataList.add(new HashMap<String,String>(reader.getDatamap()));    		
    		i = itmp;
    		
    			
        }
	}

	/**
	 * 
	 * @return List<HashMap<String,String>> recordDataList
	 * @throws emfFileException
	 */
	public List<HashMap<String,String>> getRecordDataList() throws emfFileException{
		if(recordDataList.isEmpty()){
			throw new emfFileException("emfReader EMF text data is not set yet.");
		}
		return recordDataList;
	}
	
}
