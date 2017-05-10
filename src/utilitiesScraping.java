public class utilitiesScraping{

	//array に値があるか
	public static <T> boolean contains(final T[] array, final T v) {
	    if (v == null) {
	        for (final T e : array)
	            if (e == null)
	                return true;
	    } else {
	        for (final T e : array)
	            if (e == v || v.equals(e))
	                return true;
	    }
	    return false;
	}	
	
	//file拡張子を取得
	public String getFileExtention(String file) throws Exception{
		String ext ="";
		
			String[] filepart = file.split("\\.",0);
			int lnt = filepart.length;
			if(lnt == 0){
				throw new Exception("File extension is not set yet.");
			}
			ext = filepart[lnt - 1];
			return ext;	
		
	}
	
}