import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class emfEMREXTTEXTOUTW extends emfRecordType{
	
	private int Bonds;          //16
	private int iGraphicsMode;  //4
	private int exScale;        //4
	private int eyScale;        //4
///EMRTextObject
	private int Chars;
	private int offString;
	private int Options;
	private int offDx;
// text
	private String outText;
//A WMF PointL object
	private int pointLx;
	private int pointLy;	
	
	/**
	 * Constructor
	 */
	emfEMREXTTEXTOUTW(){
		super();
		name="EMR_EXTTEXTOUTW";
	}
	emfEMREXTTEXTOUTW(byte[] v_emfbytes){
		super(v_emfbytes);
		name="EMR_EXTTEXTOUTW";
	}
	public int read(int index){
		startIndex = index;
		int ret = index;
		ret = readType(ret);
		ret = readSize(ret);
		ret = readBounds(ret);
		ret = readIGraphicsMode(ret);
		ret = readExScale(ret);
		ret = readEyScale(ret);
		///EMRTextObject
		ret = readReference(ret);
		ret = readChars(ret);
		ret = readOffString(ret);
		ret = readOption(ret);
		ret = this.readRectangle(ret);
		ret = this.readOffDx(ret);
		ret = readStringBuffer(ret);		
		clearBytesData();
		return index + this.getSize();
	}
	/**
	 * Bounds (16 bytes): 
	 * A WMF RectL object ([MS-WMF] section 2.2.2.19). 
	 * It is not used and MUST be ignored on receipt.
	 * @param index
	 * @return
	 */
	public int readBounds(int index){		
		return index + 16;
	}
	/**
	 * iGraphicsMode (4 bytes): 
	 * A 32-bit unsigned integer that specifies the current graphics mode 
	 * from the GraphicsMode enumeration (section 2.1.16).
	 * @param index
	 * @return
	 */
	public int readIGraphicsMode(int index){
		this.setiGraphicsMode(get4bytesInt(emfbytes, index));
		return index + 4;
	}
	/**
	 * exScale (4 bytes): 
	 * A 32-bit floating-point value that specifies the scale factor 
	 * to apply along the X axis to convert from page space units to .01mm units. 
	 * This is used only if the graphics mode specified by iGraphicsMode is GM_COMPATIBLE.
	 * @param index
	 * @return
	 */
	public int readExScale(int index){
		this.setExScale(get4bytesInt(emfbytes, index));
		return index + 4;
	} 
	/**
	 * eyScale (4 bytes): 
	 * A 32-bit floating-point value that specifies the scale factor 
	 * to apply along the Y axis to convert from page space units to .01mm units. 
	 * This is used only if the graphics mode specified by iGraphicsMode is GM_COMPATIBLE.
	 * @param index
	 * @return
	 */
	public int readEyScale(int index){
		this.setEyScale(get4bytesInt(emfbytes, index));
		return index + 4;
	}
	/**
	 * Reference (8 bytes): 
	 * A WMF PointL object ([MS-WMF] section 2.2.2.15) that specifies 
	 * the coordinates of the reference point used to position the string. 
	 * The reference point is defined by the last EMR_SETTEXTALIGN record 
	 * (section 2.3.11.25). 
	 * If no such record has been set, the default alignment is TA_LEFT,TA_TOP.
	 * @param index
	 * @return
	 */
	public int readReference(int index){
		//TODO read A WMF PointL object
		int ret = readPointLObject(index);
		return ret;
	}
	/**
	 * The PointL Object defines the coordinates of a point.
	 * @param index
	 * @return
	 */
	public int readPointLObject(int index){
		 int x = get4bytesInt(emfbytes, index);
		 int y = get4bytesInt(emfbytes, index + 4);
		 this.setPointLx(x);
		 this.setPointLy(y);
		 return index + 8;
	}
	
	/**
	 * Chars (4 bytes): 
	 * A 32-bit unsigned integer that specifies the number of characters 
	 * in the string.
	 * @param index
	 * @return
	 */
	public int readChars(int index){
		setChars(get4bytesInt(emfbytes, index));
		return index +4;
	}
	/**
	 * offString (4 bytes): 
	 * A 32-bit unsigned integer that specifies the offset to the output string, 
	 * in bytes, from the start of the record in which this object is contained. 
	 * This value MUST be 8- or 16-bit aligned, according to the character format.
	 * @param index
	 * @return
	 */
	public int readOffString(int index){
		setOffString(get4bytesInt(emfbytes, index));
		return index +4;
	}
	/**
	 * Options (4 bytes): 
	 * A 32-bit unsigned integer that specifies how to use the rectangle specified 
	 * in the Rectangle field. This field can be a combination of more than one 
	 * ExtTextOutOptions enumeration values.
	 * @param index
	 * @return
	 */
	public int readOption(int index){
		setOptions(get4bytesInt(emfbytes, index));
		return index + 4;
	}
	/**
	 * Rectangle (16 bytes): An optional WMF RectL object 
	 * that defines a clipping and/or opaquing rectangle in logical units. 
	 * This rectangle is applied to the text output performed by the containing record.
	 * @param index
	 * @return
	 */
	public int readRectangle(int index){
		//TODO read An optional WMF RectL object ([MS-WMF] section 2.2.2.19) 
		return index + 16;
	}
	/**
	 * offDx (4 bytes): A 32-bit unsigned integer that specifies the offset to
	 *  an intercharacter spacing array, in bytes, from the start of the record 
	 *  in which this object is contained. This value MUST be 32-bit aligned.
	 * @param index
	 * @return
	 */
	public int readOffDx(int index){
		this.setOffDx(get4bytesInt(emfbytes, index));
		return index + 4;
	}
	/**
	 * StringBuffer (variable): 
	 * The character string buffer.
	 * 
	 * UndefinedSpace1 (variable): 
	 * An optional number of unused bytes. The OutputString field is not required
	 *  to follow immediately the preceding portion of this structure.
	 * 
	 * OutputString (variable): 
	 * An array of characters that specify the string to output. The location of 
	 * this field is specified by the value of offString in bytes from the start of 
	 * this record. The number of characters is specified by the value of Chars.
	 * @param index
	 * @return
	 */
	public int readStringBuffer(int index){
		int m_offstring = getOffString();	
		int increased = readOutputString(startIndex + m_offstring);
		return increased;
	}	
	/**
	 * 
	 * OutputString (variable): 
	 * An array of characters that specify the string to output. The location of 
	 * this field is specified by the value of offString in bytes from the start of 
	 * this record. The number of characters is specified by the value of Chars.
	 * @param index
	 * @return
	 */
	public int readOutputString(int index){
		int numchar = getChars();
		byte[] outstringBytes = Arrays.copyOfRange(emfbytes, index,index + numchar*2);
		for(int i=0;i<outstringBytes.length ;i++){
			byte temp =outstringBytes[i];
			outstringBytes[i]=(byte) (temp&0xff) ;
		}
		String outstring = new String(outstringBytes,StandardCharsets.UTF_16LE );
		this.setOutText(outstring);
		return index + numchar*2; 
	}

	/**
	 *  The optional character spacing buffer.
	 * @return
	 */
	public int readDxBuffer(int index){
		int offDx = this.getOffDx();
		
		int increased = startIndex + offDx;
		return increased ;
	}
	
	public int readOutputDx(int index){
		int numchar = getChars();
		
		return index + numchar*4;
	}
	
	//setter getter
	
	public int getBonds() {
		return Bonds;
	}
	public void setBonds(int bonds) {
		this.datamap.put("bonds",Integer.toString(bonds) );
		Bonds = bonds;
	}
	public int getiGraphicsMode() {
		return iGraphicsMode;
	}
	public void setiGraphicsMode(int iGraphicsMode) {
		this.datamap.put("iGraphicsMode",Integer.toString(iGraphicsMode) );
		this.iGraphicsMode = iGraphicsMode;
	}
	public int getExScale() {
		return exScale;
	}
	public void setExScale(int exScale) {
		this.datamap.put("exScale",Integer.toString(exScale) );
		this.exScale = exScale;
	}
	public int getEyScale() {
		return eyScale;
	}
	public void setEyScale(int eyScale) {
		this.datamap.put("eyScale",Integer.toString(eyScale) );
		this.eyScale = eyScale;
	}
	public int getChars() {
		return Chars;
	}
	public void setChars(int chars) {
		this.datamap.put("chars",Integer.toString(chars) );
		Chars = chars;
	}
	public int getOffString() {
		return offString;
	}
	public void setOffString(int offString) {
		this.datamap.put("offString",Integer.toString(offString) );
		this.offString = offString;
	}
	public int getOptions() {
		return Options;
	}
	public void setOptions(int options) {
		this.datamap.put("options",Integer.toString(options) );
		Options = options;
	}
	public int getOffDx() {
		return offDx;
	}
	public void setOffDx(int offDx) {
		this.datamap.put("offDx",Integer.toString(offDx) );
		this.offDx = offDx;
	}
	public String getOutText() {
		return outText;
	}
	public void setOutText(String outText) {
		this.datamap.put("outText",outText );
		this.outText = outText;
	}
	public int getPointLx() {
		return pointLx;
	}
	public void setPointLx(int pointLx) {
		this.datamap.put("pointLx", Integer.toString(pointLx) );
		this.pointLx = pointLx;
	}
	public int getPointLy() {
		return pointLy;
	}
	public void setPointLy(int pointLy) {
		this.datamap.put("pointLy", Integer.toString(pointLy) );
		this.pointLy = pointLy;
	}	

}
