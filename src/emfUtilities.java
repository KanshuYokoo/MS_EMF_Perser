
public class emfUtilities {

    public int get4byteRecord(int[] intes){
      	 int ret =(intes[0]&0xff) ; 
      	 int ret1 =( intes[1]&0xff)<<8;
      	 int ret2 =( intes[2]&0xff)<<16;
      	 int ret3 =( intes[3]&0xff)<<24;
      	return ret + ret1 + ret2 +ret3;
      }

       //32 bit integer
   	public int get4bytesInt(byte[] bytes, int inxstart){
   		int ret = 0;
   		try{
   		 int index =  inxstart;
   	   	 int ret0 = bytes[index++]&0xff ; 
   	   	 int ret1 = (bytes[index++]&0xff)<<8;
   	   	 int ret2 = (bytes[index++]&0xff)<<16;
   	   	 int ret3 = (bytes[index++]&0xff)<<24;
   	   	 
   	   	 ret = ret0 + ret1 + ret2 +ret3;
   		}catch(Exception e){
   			System.out.println("exception: "+ e.toString());
   		}
		return ret& 0xffffffff;
   	}   	
   	//16 bit integer
   	public int get2bytesInt(byte[] bytes, int inxstart){	
  		 int index =  inxstart;
   	   	 int ret = bytes[index++]&0xff ; 
   	   	 int ret1 = (bytes[index++]&0xff)<<8;
   	   	 return ret + ret1 ;
   	}
	
}
