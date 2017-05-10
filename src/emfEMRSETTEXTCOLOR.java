public class emfEMRSETTEXTCOLOR extends emfRecordType{

	protected int red;
	protected int green;
	protected int blue;
	
	emfEMRSETTEXTCOLOR() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
		name = "EMR_SETTEXTCOLOR";
	}
	emfEMRSETTEXTCOLOR(byte[] v_emfbytes) {
		super(v_emfbytes);
		// TODO 自動生成されたコンストラクター・スタブ
		name = "EMR_SETTEXTCOLOR";
	}
	
	 /**
	 *ColorRef Object
	 * Red (1 byte):  An 8-bit unsigned integer that defines the relative intensity of red.
	 * Green (1 byte):  An 8-bit unsigned integer that defines the relative intensity of green.
	 * Blue (1 byte):  An 8-bit unsigned integer that defines the relative intensity of blue.
	 * Reserved (1 byte):  An 8-bit unsigned integer that MUST be 0x00	
	 * @return
	 */
	public int read(int index){
		int ret=index;
		ret = readType(ret);
		ret = readSize(ret);
		ret = readColor(ret);
		clearBytesData();
		return index + getSize();
	}
	
	public int readColor(int index){
		int ret=index;
		red = emfbytes[ret];
		green = emfbytes[ret++];
		blue = emfbytes[ret++];
		return index + 4;
	}
	
 /**
 *ColorRef Object
 * Red (1 byte):  An 8-bit unsigned integer that defines the relative intensity of red.
 * Green (1 byte):  An 8-bit unsigned integer that defines the relative intensity of green.
 * Blue (1 byte):  An 8-bit unsigned integer that defines the relative intensity of blue.
 * Reserved (1 byte):  An 8-bit unsigned integer that MUST be 0x00	
 * @return
 */
   public String getRGB(){
	 String strRed =  String.format("%02X", red);
	 String strGreen =  String.format("%02X", green);
	 String strBlue =  String.format("%02X", blue);
	 String ret = "#" + strRed +strGreen+strBlue;
	 return "#" + strRed +strGreen+strBlue;
   }
	
}
