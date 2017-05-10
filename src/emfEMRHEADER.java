
public class emfEMRHEADER extends emfRecordType {

	private int offDescription;
	private int nDescription;
	private int cbPixelFormat; 
	private int offPixelFormat;
	private int bOpenGL;
	private int MicrometersX;
	private int MicrometersY;
	
	//constructor
	emfEMRHEADER() {
		super();
		this.name="EMR_HEADER";
	}
	emfEMRHEADER(byte[] v_emfbytes) {
		super(v_emfbytes);
		this.name="EMR_HEADER";
	}

	/**
	*Header
	* Type                 4 byte
	* Size                 4
	* EmfHeader            80
	* EmfHeaderExtension1  12
	* EmfHeaderExtension2   8
	* EmfDescriptionBuffer (variable)
	* EmfPixelFormatBuffer (variable)	
	*/
	/**
	 * Header Type
	 * @param indxstart index of the start of this record
	 * @return
	 * @throws Exception
	 */
	public int readHeaderType(int indxstart) throws Exception{
		int ret=indxstart + 4;
		setType(get4bytesInt(emfbytes,indxstart));
		return ret;
	}
	
	/**
	 * Header Size
	 * @param indxstart index of the start of this record
	 * @return
	 * @throws Exception
	 */
	public int readHeaderSize(int indxstart) throws Exception{
		int ret=indxstart + 4;
		setSize( get4bytesInt(emfbytes,indxstart) );
		return ret;
	}
	
	
	/**
	*EmfHeader
	* 80 byte
	* 
	* Bounds (16 bytes): 
    * Frame (16 bytes): 
    * RecordSignature (4 bytes): 
    * Version (4 bytes): 
    * Bytes (4 bytes): 
    * Records (4 bytes): 
    * Handles (2 bytes): 
    * Reserved (2 bytes): 
    * nDescription (4 bytes): 
    * offDescription (4 bytes): 
    * nPalEntries (4 bytes): 
    * Device (8 bytes):
    * Millimeters (8 bytes): 
	*  
    * @param bytes an allay of byte data from emf file
	* @param indxstart index number of the start of the buffer
	* @return index number of the next buffer 
	*/
	public int readEmfHeader(int indxstart) throws Exception{
		int index = indxstart;
		
		index = readBounds(index);         //16
		index = readFrame(index);          //16
		index = readRecordSignature(index);//4
		index = readVersion(index);        //4
		index = readBytes(index);          //4
		index = readRecords(index);        //4
		index = readHandles(index);        //2
		index = readReserved(index);       //2
		index = readnDescription(index);   //4
		index = readoffDescription(index); //4
		index = readnPalEntries(index);    //4
		index = readDevice(index);         //8
		index = Millimeters(index);        //8
		
		return index;
	}
	/**
	* Bounds (16 bytes): A WMF RectL object ([MS-WMF] section 2.2.2.19) 
	* that specifies the rectangular inclusive-inclusive bounds in device 
	* units of the smallest rectangle that can be drawn around the image 
	* stored in the metafile.
	*/
	public int readBounds(int indxstart) throws Exception{
		int ret=indxstart + 16;
		//todo: get object data 
		return ret;
	}
	/**
    * Frame (16 bytes): A WMF RectL object that specifies the rectangular 
    * inclusive-inclusive dimensions, in .01 millimeter units, of a rectangle 
    * that surrounds the image stored in the metafile.
	*/
	public int readFrame(int indxstart) throws Exception{
		int ret=indxstart + 16;
		//todo:get object data 
		return ret;
	}
	/**
    * RecordSignature (4 bytes): A 32-bit unsigned integer that specifies 
    * the record signature. This MUST be ENHMETA_SIGNATURE, from the FormatSignature 
    * enumeration (section 2.1.14).
	 */
	public int readRecordSignature(int indxstart) throws Exception{
		int ret=indxstart + 4;
		//todo:get object data 
		return ret;
	}
	/**
    * Version (4 bytes): A 32-bit unsigned integer that specifies EMF metafile interoperability. 
    * This SHOULD be 0x00010000.<57>
	*/
	public int readVersion(int indxstart) throws Exception{
		int ret=indxstart + 4;
		//todo:get object data 
		return ret;
	}
    /**
    * Bytes (4 bytes): A 32-bit unsigned integer that specifies 
    * the size of the metafile, in bytes.
    */
	public int readBytes(int indxstart) throws Exception{
		int ret=indxstart + 4;
		//todo:get object data 
		return ret;
	}
	/**
	* Records (4 bytes): A 32-bit unsigned integer that specifies 
    * the number of records in the metafile.
	*/
	public int readRecords(int indxstart) throws Exception{
		int ret=indxstart + 4;
		//todo:get object data 
		return ret;
	}
    /**
    * Handles (2 bytes): A 16-bit unsigned integer that specifies 
    * the number of graphics objects that will be used during 
    * the processing of the metafile.
   */
	public int readHandles(int indxstart) throws Exception{
		int ret=indxstart + 2;
		//todo:get object data 
		return ret;
	}	
   /**
    * Reserved (2 bytes): A 16-bit unsigned integer 
    * that MUST be 0x0000 and MUST be ignored.
    */
	public int readReserved(int indxstart) throws Exception{
		int ret=indxstart + 2;
		//todo:get object data 
		return ret;
	}	
	/**
	* nDescription (4 bytes): A 32-bit unsigned integer 
    * that specifies the number of characters in the array 
    * that contains the description of the metafile's contents. 
    * This is zero if there is no description string.
	*/
	public int readnDescription(int indxstart) throws Exception{
		int ret=indxstart + 4;
	 	setnDescription(get4bytesInt(emfbytes,indxstart));
		return ret;
	}		
    /**
    * offDescription (4 bytes): A 32-bit unsigned integer 
    * that specifies the offset from the beginning of this record to 
    * the array that contains the description of the metafile's contents.
    */
	public int readoffDescription(int indxstart) throws Exception{
		int ret=indxstart + 4;
	 	setOffDescription(get4bytesInt(emfbytes,indxstart));
		return ret;
	}
	/**
    * nPalEntries (4 bytes): A 32-bit unsigned integer 
    * that specifies the number of entries in the metafile palette. 
    * The palette is located in the EMR_EOF record.
	*/
	public int readnPalEntries(int indxstart) throws Exception{
		int ret=indxstart + 4;
		//todo:get object data 
		return ret;
	}
	/**
	* Device (8 bytes): A WMF SizeL object ([MS-WMF] section 2.2.2.22) 
    * that specifies the size of the reference device, in pixels.
	*/
	public int readDevice(int indxstart) throws Exception{
		int ret=indxstart + 8;
		//todo:内容を取得 		
		return ret;
	}
    /**
    * Millimeters (8 bytes): A WMF SizeL object that specifies 
    * the size of the reference device, in millimeters.	
    */
	public int Millimeters(int indxstart) throws Exception{
		int ret=indxstart + 8;
		//todo:内容を取得 		
		return ret;
	}
	
	/**
	*EmfMetafileHeaderExtension1
    * 12 bytes
    * @param bytes an allay of byte data from emf file
	* @param indxstart index number of the start of the buffer
	* @return index number of the next buffer 
	*/
	public int readEmfMetafileHeaderExtension1(int indxstart) throws Exception{
         int ret = indxstart;
		 ret =  readcbPixelFormat(ret);
		 ret =  readoffPixelFormat(ret);
		 ret =  readbOpenGL(ret);
		return ret;
	}
	
	/**
	 * cbPixelFormat (4 bytes): 
	 * A 32-bit unsigned integer that specifies the size of 
	 * the PixelFormatDescriptor object. 
	 * This MUST be 0x00000000 if no pixel format is set.
	 * 
	 * @param indxstart
	 * @return 
	 * @throws Exception
	 */
	public int readcbPixelFormat(int indxstart) throws Exception{
		int ret=indxstart + 4;
		setCbPixelFormat(get4bytesInt(emfbytes,indxstart));
		return ret;
	}
	
	/**
	 * offPixelFormat (4 bytes): 
	 * A 32-bit unsigned integer that specifies the offset to 
	 * the PixelFormatDescriptor object. This MUST be 0x00000000 
	 * if no pixel format is set.
	 * @param indxstart
	 * @return
	 * @throws Exception
	 */
	public int readoffPixelFormat(int indxstart) throws Exception{
		int ret=indxstart + 4;
		setOffPixelFormat(get4bytesInt(emfbytes,indxstart));
		return ret;
	}

	/**
	 * bOpenGL (4 bytes): 
	 * A 32-bit unsigned integer that indicates whether OpenGL 
	 * commands are present in the metafile
	 * 
	 * 0x00000000 : OpenGL records are not present in the metafile.
     * 0x00000001 : OpenGL records are present in the metafile.
	 * 
	 * @param indxstart
	 * @return
	 * @throws Exception
	 */
	public int readbOpenGL(int indxstart) throws Exception{
		int ret=indxstart + 4;
		setbOpenGL(get4bytesInt(emfbytes,indxstart));
		return ret;
	}
		
	/**
	*EmfMetafileHeaderExtension2
    * 8 bytes
    * @param bytes an allay of byte data from emf file
	* @param indxstart index number of the start of the buffer
	* @return index number of the next buffer 
	*/
	public int readEmfMetafileHeaderExtension2(int indxstart) throws Exception{
		int ret=indxstart;
		//todo:内容を取得 		
		ret = readMicrometersX(ret);
		ret = readMicrometersY(ret);
		return ret;
	}	
	/**
	 * MicrometersX (4 bytes): 
	 * The 32-bit horizontal size of the display device for which 
	 * the metafile image was generated, in micrometers.
	 * 
	 * @param indxstart
	 * @return
	 * @throws Exception
	 */
	public int readMicrometersX(int indxstart) throws Exception{
		int ret=indxstart + 4;
        this.setMicrometersX(get4bytesInt(emfbytes,indxstart));		
		return ret;
	}
	/**
	 * MicrometersY (4 bytes): 
	 * The 32-bit vertical size of the display device for which 
	 * the metafile image was generated, in micrometers.
	 * 
	 * @param indxstart
	 * @return
	 * @throws Exception
	 */
	public int readMicrometersY(int indxstart) throws Exception{
		int ret=indxstart + 4;
        this.setMicrometersY(get4bytesInt(emfbytes,indxstart));		
		return ret;
	}
	

	/**
	 * EmfDescriptionBuffer (variable)
	 *An optional array of bytes that contains 
	 *the EMF description string, which is not 
	 *required to be contiguous with the fixed 
	 *portion of the EmfMetafileHeader record. 
	 *Accordingly, the field in this buffer that 
	 *is labeled "UndefinedSpace" is optional 
	 *and MUST be ignored. 
	 *
	 *UndefinedSpace (variable)
	 *EmfDescription (variable)
	 *
	 * @param indxstart index number of the start of the record
	 * @return index number of the next buffer 
	 */
	public int readEmfDescriptionBuffer(int indxstart){
		int ret=0;
		//todo: to understand the true size of variable.
		//currently I am guessing the totally the nDescription*2 + OffDescription.
		int varsize = 
		getnDescription()*2 + 
		getOffDescription();
		ret = indxstart + varsize; 
		return ret;
	}
   /**
    * EmfPixelFormatBuffer
    * 
    * EmfPixelFormatBuffer (variable): 
    * An optional array of bytes that contains the EMF pixel format descriptor
    * ,which is not required to be contiguous with the fixed portion of 
    * the EmfMetafileHeaderExtension1 record or with the EMF description string. 
    * Accordingly, the field in this buffer that is labeled "UndefinedSpace" 
    * is optional and MUST be ignored.
    * 
    * EmfPixelFormat (40 bytes): 
    * An optional PixelFormatDescriptor object that specifies the last pixel 
    * format that was defined when the metafile was recorded. Its size and 
    * location in the record are specified by the cbPixelFormat and 
    * offPixelFormat fields, respectively, in EmfHeaderExtension1. If the 
    * value of either field is zero, no pixel format descriptor is present.
    * 
    * UndefinedSpace2 (variable)
    * EmfPixelFormat (40 bytes, optional)
    * 
    * 
    * @param indxstart index number of the start of the record
	* @return index number of the next buffer 
    */	
	public int readEmfPixelFormatBuffer(int indxstart){
		int ret=indxstart;
		
		int offset = getOffPixelFormat();
		if(offset !=0){
		readPixelFormatDescriptor(indxstart);
		ret = ret + offset + 40;
		}		
		return ret;
	}
	/**
	 * PixelFormatDescriptor Object
	 * The PixelFormatDescriptor object specifies the pixel format of a drawing surface.
	 * 
	 * @param indxstart
	 * @return
	 */
	public int readPixelFormatDescriptor(int indxstart){
		int ret = indxstart;
		//todo:get pixel format 
		int pixelnSize;
		ret = ret +2;
		int pixelnVersion;
		pixelnVersion = get2bytesInt( emfbytes , ret);
		return ret;
	}
		
	//////////////////////
	//setter getter
	public int getnDescription() {
		return nDescription;
	}

	public void setnDescription(int nDescription) {
		this.nDescription = nDescription;
	}

	public int getOffDescription() {
		return offDescription;
	}

	public void setOffDescription(int offDescription) {
		this.offDescription = offDescription;
	}

	/*
	public int getHeader_type() {
		return header_type;
	}

	public void setHeader_type(int header_type) {
		this.header_type = header_type;
	}

	public int getHeader_size() {
		return header_size;
	}

	public void setHeader_size(int header_size) {
		this.header_size = header_size;
	}*/

	public int getCbPixelFormat() {
		return cbPixelFormat;
	}

	//cdPixelFormat : HeaderExtention1 Object
	public void setCbPixelFormat(int cbPixelFormat) {
		this.cbPixelFormat = cbPixelFormat;
	}

	public int getOffPixelFormat() {
		return offPixelFormat;
	}

	public void setOffPixelFormat(int offPixelFormat) {
		this.offPixelFormat = offPixelFormat;
	}

	public int getbOpenGL() {
		return bOpenGL;
	}

	public void setbOpenGL(int bOpenGL) {
		this.bOpenGL = bOpenGL;
	}

	public int getMicrometersX() {
		return MicrometersX;
	}

	public void setMicrometersX(int micrometersX) {
		MicrometersX = micrometersX;
	}

	public int getMicrometersY() {
		return MicrometersY;
	}

	public void setMicrometersY(int micrometersY) {
		MicrometersY = micrometersY;
	}
	
}
