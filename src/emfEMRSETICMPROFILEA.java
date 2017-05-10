public class emfEMRSETICMPROFILEA extends emfRecordType{

	private int dwFlags;
	private int cbName;
	private int cbData;
	private int Data;
	
	/**
	 * Contractor
	 * @param v_emfbytes
	 */
	emfEMRSETICMPROFILEA() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
		name = "EMR_SETICMPROFILEA";
	}
	emfEMRSETICMPROFILEA(byte[] v_emfbytes) {
		super(v_emfbytes);
		// TODO 自動生成されたコンストラクター・スタブ
		name = "EMR_SETICMPROFILEA";
	}
	/**
	 * EMR_SETICMPROFILEA Record
	 * The EMR_SETICMPROFILEA record specifies a color profile 
	 * in a file with a name consisting of ASCII characters, for graphics output.
	 * 
	 * @param index
	 * @return
	 */
	public int read(int index){
		int ret = index;
		//todo: cbNameがおかしい
		ret = readType(ret);
		ret = readSize(ret);
		ret = readDwFlags(ret);
		ret = readCbName(ret);
		ret = readCbData(ret);
		ret = readData(ret);
        //return ret;		
		clearBytesData();
		return index + this.getSize();
	}
	/**
	 * EMR_SETICMPROFILEA Record
	 * The EMR_SETICMPROFILEA record specifies a color profile 
	 * in a file with a name consisting of ASCII characters, for graphics output.
	 * 
	 * @param index
	 * @return
	 */
	public int readEMFSETICMPROFILEA(int index){
		int ret = index;
		int l = emfbytes.length;
		//todo: cbNameがおかしい
		ret = readType(ret);
		ret = readSize(ret);
		ret = readDwFlags(ret);
		ret = readCbName(ret);
		ret = readCbData(ret);
		ret = readData(ret);
        //return ret;		
		return index + this.getSize();
	}
    /**
    * Type (4 bytes): 
    * A 32-bit unsigned integer that identifies this record type 
    * as EMR_SETICMPROFILEA. This MUST be 0x00000070.
    * 
    * @param index
    * @return
    */
	public int readType(int index){
        int ret = index + 4;
		setType(this.get4bytesInt(emfbytes, index));
		return ret;
	}
	 /**
	 * Size (4 bytes): 
	 *  32-bit unsigned integer that specifies the size of this record, 
	 *  in bytes.
	 *  
	 * @param index
	 * @return
	 */
	public int readSize(int index){
        int ret = index + 4;
		setSize(this.get4bytesInt(emfbytes, index));
		return ret;
	 }
	 /**
	 * dwFlags (4 bytes): 
	 * A 32-bit unsigned integer that contains color profile flags.
	 * 
	 * @param index
	 * @return
	 */
	public int readDwFlags(int index){
        int ret = index + 4;
		setDwFlags(this.get4bytesInt(emfbytes, index));
		return ret;
	}
	/**
	 * cbName (4 bytes):
	 * A 32-bit unsigned integer that specifies the number of bytes
	 *  in the ASCII name of the desired color profile.
	 *  
	 * @param index
	 * @return
	 */
	public int readCbName(int index){
        int ret = index + 4;
        setCbName(this.get4bytesInt(emfbytes, index));
		return ret;
	}
	/**
	 * cbData (4 bytes): A 32-bit unsigned integer that specifies 
	 * the size of the color profile data, if it is contained in the Data field.
	 * 
	 * @param index
	 * @return
	 */
	public int readCbData(int index){
        int ret = index + 4;
        setCbData(this.get4bytesInt(emfbytes, index));
		return ret;
	}
	/**
	 * Data (variable): An array of size (cbName + cbData) in bytes, 
	 * which specifies the ASCII name and raw data of the desired color profile.
	 * 
	 * @param index
	 * @return
	 */
	public int readData(int index){
       
        int cbName = getCbName();
        int cbData = getCbData();
        int ret = cbName + cbData;
       
        //todo: get ASCII data
        
		return  index + ret;
	}
	
	
	//////////////////
	//Getter Setter
	////////////

	public int getDwFlags() {
		return dwFlags;
	}


	public void setDwFlags(int dwFlags) {
		this.dwFlags = dwFlags;
	}


	public int getCbName() {
		return cbName;
	}


	public void setCbName(int cbName) {
		this.cbName = cbName;
	}


	public int getCbData() {
		return cbData;
	}

	public void setCbData(int cbData) {
		this.cbData = cbData;
	}


	public int getData() {
		return Data;
	}


	public void setData(int data) {
		this.Data = data;
	}

	
	
}