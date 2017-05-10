import java.util.HashMap;

public class emfRecordType extends emfUtilities implements emfTypeNameList, Cloneable{
		
	//EMF file
		protected byte[] emfbytes;

	// Type name
		protected String name="";
		protected int Type;
		protected int Size;
		//start index of the record
		protected int startIndex;
		//data {recordTitle,value}
		HashMap<String,String> datamap = new HashMap<String, String>();
		//{recordname,datamap}
		HashMap<String,HashMap<String,String>> recordDatamap = new HashMap<String,HashMap<String,String>>();	
		
	emfRecordType(){
	}

	emfRecordType(byte[] v_emfbytes){
		emfbytes = v_emfbytes;
	}

	public void setBytesData(byte[] v_emfbytes){
		emfbytes = v_emfbytes;
	}

	public void clearBytesData(){
		emfbytes = new byte[0];
	}
	
	public int read(byte[] emfbytes, int index){
		setBytesData(emfbytes);
		startIndex = index;
		int ret = index;
		ret = readType(ret);
		ret = readSize(ret);
		clearBytesData();
		return index + getSize();
	}
	
	public int read( int index) throws emfFileException{
		int ret = index;
		ret = readType(ret);
		ret = readSize(ret);
		clearBytesData();
		if(getSize() == 0){	
//			return index + 8;
			throw new emfFileException("Exception : size is 0");
		}else{
			return index + getSize();
		}
	}
	
	public int readType(int index){
        int ret = index + 4;
		setType(get4bytesInt(emfbytes, index));
		return ret;
	}
	
	public int readSize(int index){
        int ret = index + 4;
		setSize(get4bytesInt(emfbytes, index));
		return ret;
	 }

	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public HashMap<String,String> getDatamap(){
		this.datamap.put("name",getName());
		return this.datamap;
	}
	
	public HashMap<String,HashMap<String,String>> getRecordDatamap(){		
		this.recordDatamap.put(getName(),getDatamap());
		return this.recordDatamap;
	}
	
	//getter setter
	
		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public int getType() {
			return Type;
		}



		public void setType(int type) {
			Type = type;
		}


		public int getSize() {
			return Size;
		}



		public void setSize(int size) {
			Size = size;
		}
	
		
		public int getStartIndex() {
			return startIndex;
		}



		public void setStartIndex(int size) {
			Size = startIndex;
		}


}
