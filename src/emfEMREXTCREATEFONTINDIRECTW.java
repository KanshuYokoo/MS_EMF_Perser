
public class emfEMREXTCREATEFONTINDIRECTW extends emfRecordType{

	private int ihFonts;
	
	emfEMREXTCREATEFONTINDIRECTW(){
		super();
		name="EMR_EXTCREATEFONTINDIRECTW";
	}
	emfEMREXTCREATEFONTINDIRECTW(byte[] v_emfbytes){
		super(v_emfbytes);
		name="EMR_EXTCREATEFONTINDIRECTW";
	}

	/**
	 * ihFonts (4 bytes):
	 * A 32-bit unsigned integer that specifies the index of the logical font 
	 * object in the EMF Object Table (section 3.1.1.1). This index MUST be saved 
	 * so that this object can be reused or modified.
	 * 
	 * @param index
	 * @return
	 */
	public int readIhFonts(int index){
		this.setIhFonts(get4bytesInt(emfbytes, index));
		return index + this.getSize();
	}

	//TODO elw
	/**
	 * elw (variable): 
	 * A LogFontExDv object (section 2.2.15), which specifies the logical font. 
	 * A LogFont object 2.2.13 MAY be present instead.<90>The process for 
	 * determining the type of object in this field is described below.
	 * 
	 * The logical font object defined by this record can be selected into 
	 * the playback device context by an EMR_SELECTOBJECT record (section 2.3.8.5), 
	 * which specifies the logical font to use in subsequent graphics operations.
	 * 
	 * The type of logical font object in the elw field of this record is determined 
	 * by the following algorithm (all size and length values are in bytes):
	 * 
	 * First,note that the size in bytes of the static part of this record—that is, 
	 * the sum of the sizes of its Type, Size, and ihFonts fields—is 12.
	 * 
	 * Next, note that because the size in bytes of the entire record is present 
	 * in its Size field, the size in bytes of the variable-length elw field can be 
	 * computed as follows.
	 * 
	 * Size - 12
	 * 
	 * If the size of the elw field is equal to or less than the size of aLog 
	 * FontPanoseobject(section 2.2.16), elw MUST be treated as a fixed-length LogFont 
	 * object. Bytes beyond the extent of the LogFont object, up to the end of the 
	 * elw field, MUST be ignored.
	 * 
	 * If the size of the elw field is greater than the size of a LogFontPanose object,
	 *  then elw MUST be treated as a variable-length LogFontExDv object.
	 *  
	 *  The size of a LogFontPanose object is 0x0140 (320 decimal). It is determined 
	 *  by adding up the sizes of its fields, as follows:
	 * 
	 * 
	 * @return
	 */
	
	
	
	
	//getter setter
	
	public int getIhFonts() {
		return ihFonts;
	}
	public void setIhFonts(int ihFonts) {
		this.datamap.put("ihFonts",Integer.toString(ihFonts) );
		this.ihFonts = ihFonts;
	}
	
}
