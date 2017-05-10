
public class emfEMRSELECTOBJECT extends emfRecordType{

	private int ihObject;
	
	emfEMRSELECTOBJECT(){
		super();
		name = "EMRSELECTOBJECT";
	}
	
	emfEMRSELECTOBJECT(byte[] v_emfbytes){
		super(v_emfbytes);
		name = "EMRSELECTOBJECT";
	}

	/**
	 * ihObject (4 bytes): 
	 * A 32-bit unsigned integer that specifies either the index of a graphics 
	 * object in the EMF Object Table or the index of a stock object from 
	 * the StockObject enumeration.
	 * 
	 * This value MUST NOT be 0, which is a reserved index that refers to 
	 * the EMF metafile itself. 
	 * The object specified by this record MUST be used in drawing operations 
	 * that require such an object until a different object of the same type is 
	 * specified by another EMR_SELECTOBJECT record, or the object is removed by 
	 * an EMR_DELETEOBJECT record.
	 * 
	 * @param index
	 * @return
	 */
	public int readihObject(int index){
		setIhObject(this.get4bytesInt(emfbytes, index) );
		return index + this.getSize();
	}
	
	//setter getter
	public int getIhObject() {
		return ihObject;
	}

	public void setIhObject(int ihObject) {
		this.datamap.put("ihObject", Integer.toString(ihObject));
		this.ihObject = ihObject;
	}

	
	
	
}
