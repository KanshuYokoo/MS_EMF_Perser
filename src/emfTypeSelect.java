import java.util.HashMap;
import java.util.Map;

public class emfTypeSelect extends emfUtilities implements  emfTypeNameList {
	//EMF file
    protected byte[] emfbytes;
	public Map<Integer,String> typemap = new HashMap<Integer,String>();
	public Map<Integer,emfRecordType> typeObjectmap = new HashMap<Integer,emfRecordType>();

	emfTypeSelect(){
		setInitTypeMap();
		setinitObjectMap();
	}
	
	emfTypeSelect(byte[] v_emfbytes){
		emfbytes = v_emfbytes;
		setInitTypeMap();
		setinitObjectMap();
	}
		
	public void setInitTypeMap(){
		typemap.put(EMR_HEADER,"EMR_HEADER");
		typemap.put(EMR_POLYBEZIER,"EMR_POLYBEZIER"); 
		typemap.put(EMR_POLYGON,"EMR_POLYGON" ); 
		typemap.put(EMR_POLYLINE,"EMR_POLYLINE"); 
		typemap.put(EMR_POLYBEZIERTO,"EMR_POLYBEZIERTO");
		typemap.put(EMR_POLYLINETO,"EMR_POLYLINETO"); 
		typemap.put(EMR_POLYPOLYLINE,"EMR_POLYPOLYLINE"); 
		typemap.put(EMR_POLYPOLYGON,"EMR_POLYPOLYGON"); 
		typemap.put(EMR_SETWINDOWEXTEX,"EMR_SETWINDOWEXTEX"); 
		typemap.put(EMR_SETWINDOWORGEX,"EMR_SETWINDOWORGEX"); 
		typemap.put(EMR_SETVIEWPORTEXTEX,"EMR_SETVIEWPORTEXTEX"); 
		typemap.put(EMR_SETVIEWPORTORGEX,"EMR_SETVIEWPORTORGEX"); 
		typemap.put(EMR_SETBRUSHORGEX,"EMR_SETBRUSHORGEX"); 
		typemap.put(EMR_EOF,"EMR_EOF"); 
		typemap.put(EMR_SETPIXELV,"EMR_SETPIXELV"); 
		typemap.put(EMR_SETMAPPERFLAGS,"EMR_SETMAPPERFLAGS"); 
		typemap.put(EMR_SETMAPMODE,"EMR_SETMAPMODE"); 
		typemap.put(EMR_SETBKMODE,"EMR_SETBKMODE"); 
		typemap.put(EMR_SETPOLYFILLMODE,"EMR_SETPOLYFILLMODE"); 
		typemap.put(EMR_SETROP2,"EMR_SETROP2"); 
		typemap.put(EMR_SETSTRETCHBLTMODE,"EMR_SETSTRETCHBLTMODE"); 
		typemap.put(EMR_SETTEXTALIGN,"EMR_SETTEXTALIGN"); 
		typemap.put(EMR_SETCOLORADJUSTMENT,"EMR_SETCOLORADJUSTMENT"); 
		typemap.put(EMR_SETTEXTCOLOR,"EMR_SETTEXTCOLOR"); 
		typemap.put(EMR_SETBKCOLOR,"EMR_SETBKCOLOR"); 
		typemap.put(EMR_OFFSETCLIPRGN,"EMR_OFFSETCLIPRGN"); 
		typemap.put(EMR_MOVETOEX,"EMR_MOVETOEX"); 
		typemap.put(EMR_SETMETARGN,"EMR_SETMETARGN"); 
		typemap.put(EMR_EXCLUDECLIPRECT,"EMR_EXCLUDECLIPRECT"); 
		typemap.put(EMR_INTERSECTCLIPRECT,"EMR_INTERSECTCLIPRECT"); 
		typemap.put(EMR_SCALEVIEWPORTEXTEX,"EMR_SCALEVIEWPORTEXTEX"); 
		typemap.put(EMR_SCALEWINDOWEXTEX,"EMR_SCALEWINDOWEXTEX"); 
		typemap.put(EMR_SAVEDC,"EMR_SAVEDC"); 
		typemap.put(EMR_RESTOREDC,"EMR_RESTOREDC"); 
		typemap.put(EMR_SETWORLDTRANSFORM,"EMR_SETWORLDTRANSFORM"); 
		typemap.put(EMR_MODIFYWORLDTRANSFORM,"EMR_MODIFYWORLDTRANSFORM"); 
		typemap.put(EMR_SELECTOBJECT,"EMR_SELECTOBJECT"); 
		typemap.put(EMR_CREATEPEN,"EMR_CREATEPEN"); 
		typemap.put(EMR_CREATEBRUSHINDIRECT,"EMR_CREATEBRUSHINDIRECT"); 
		typemap.put(EMR_DELETEOBJECT,"EMR_DELETEOBJECT"); 
		typemap.put(EMR_ANGLEARC,"EMR_ANGLEARC"); 
		typemap.put(EMR_ELLIPSE,"EMR_ELLIPSE"); 
		typemap.put(EMR_RECTANGLE,"EMR_RECTANGLE"); 
		typemap.put(EMR_ROUNDRECT,"EMR_ROUNDRECT"); 
		typemap.put(EMR_ARC,"EMR_ARC"); 
		typemap.put(EMR_CHORD,"EMR_CHORD"); 
		typemap.put(EMR_PIE,"EMR_PIE"); 
		typemap.put(EMR_SELECTPALETTE,"EMR_SELECTPALETTE");
		typemap.put(EMR_CREATEPALETTE,"EMR_CREATEPALETTE");
		typemap.put(EMR_SETPALETTEENTRIES,"EMR_SETPALETTEENTRIES");
		typemap.put(EMR_RESIZEPALETTE,"EMR_RESIZEPALETTE");
		typemap.put(EMR_REALIZEPALETTE,"EMR_REALIZEPALETTE");
		typemap.put(EMR_EXTFLOODFILL ,"EMR_EXTFLOODFILL");
		typemap.put(EMR_LINETO,"EMR_LINETO");
		typemap.put(EMR_ARCTO,"EMR_ARCTO");
		typemap.put(EMR_POLYDRAW,"EMR_POLYDRAW");
		typemap.put(EMR_SETARCDIRECTION,"EMR_SETARCDIRECTION");
		typemap.put(EMR_SETMITERLIMIT,"EMR_SETMITERLIMIT");
		typemap.put(EMR_BEGINPATH,"EMR_BEGINPATH");
		typemap.put(EMR_ENDPATH,"EMR_ENDPATH");
		typemap.put(EMR_CLOSEFIGURE,"EMR_CLOSEFIGURE");
		typemap.put(EMR_FILLPATH,"EMR_FILLPATH");
		typemap.put(EMR_STROKEANDFILLPATH,"EMR_STROKEANDFILLPATH");
		typemap.put(EMR_STROKEPATH,"EMR_STROKEPATH");
		typemap.put(EMR_FLATTENPATH,"EMR_FLATTENPATH");
		typemap.put(EMR_WIDENPATH,"EMR_WIDENPATH");
		typemap.put(EMR_SELECTCLIPPATH,"EMR_SELECTCLIPPATH");
		typemap.put(EMR_ABORTPATH,"EMR_ABORTPATH");
		typemap.put(EMR_COMMENT,"EMR_COMMENT");
		typemap.put(EMR_FILLRGN,"EMR_FILLRGN");
		typemap.put(EMR_FRAMERGN,"EMR_FRAMERGN");
		typemap.put(EMR_INVERTRGN,"EMR_INVERTRGN");
		typemap.put(EMR_PAINTRGN,"EMR_PAINTRGN");
		typemap.put(EMR_EXTSELECTCLIPRGN,"EMR_EXTSELECTCLIPRGN");
		typemap.put(EMR_BITBLT,"EMR_BITBLT");
		typemap.put(EMR_STRETCHBLT,"EMR_BITBLT");
		typemap.put(EMR_MASKBLT,"EMR_MASKBLT");
		typemap.put(EMR_PLGBLT,"EMR_PLGBLT");
		typemap.put(EMR_SETDIBITSTODEVICE,"EMR_SETDIBITSTODEVICE");
		typemap.put(EMR_STRETCHDIBITS,"EMR_STRETCHDIBITS");
		typemap.put(EMR_EXTCREATEFONTINDIRECTW,"EMR_EXTCREATEFONTINDIRECTW");
		typemap.put(EMR_EXTTEXTOUTA,"EMR_EXTTEXTOUTA");
		typemap.put(EMR_EXTTEXTOUTW,"EMR_EXTTEXTOUTW");
		typemap.put(EMR_POLYBEZIER16,"EMR_POLYBEZIER16");
		typemap.put(EMR_POLYGON16,"EMR_POLYGON16");
		typemap.put(EMR_POLYLINE16,"EMR_POLYLINE16");
		typemap.put(EMR_POLYBEZIERTO16,"EMR_POLYBEZIERTO16");
		typemap.put(EMR_POLYLINETO16,"EMR_POLYLINETO16");
		typemap.put(EMR_POLYPOLYLINE16,"EMR_POLYPOLYLINE16");
		typemap.put(EMR_POLYPOLYGON16,"EMR_POLYPOLYGON16");
		typemap.put(EMR_POLYDRAW16,"EMR_POLYDRAW16");
		typemap.put(EMR_CREATEMONOBRUSH,"EMR_CREATEMONOBRUSH");
		typemap.put(EMR_CREATEDIBPATTERNBRUSHPT,"EMR_CREATEDIBPATTERNBRUSHPT");
		typemap.put(EMR_EXTCREATEPEN,"EMR_EXTCREATEPEN");
		typemap.put(EMR_POLYTEXTOUTA,"EMR_POLYTEXTOUTA");
		typemap.put(EMR_POLYTEXTOUTW,"EMR_POLYTEXTOUTW");
		typemap.put(EMR_SETICMMODE,"EMR_SETICMMODE");
		typemap.put(EMR_CREATECOLORSPACE,"EMR_CREATECOLORSPACE");
		typemap.put(EMR_SETCOLORSPACE,"EMR_SETCOLORSPACE");
		typemap.put(EMR_DELETECOLORSPACE,"EMR_DELETECOLORSPACE");
		typemap.put(EMR_GLSRECORD,"EMR_GLSRECORD");
		typemap.put(EMR_GLSBOUNDEDRECORD,"EMR_GLSBOUNDEDRECORD");
		typemap.put(EMR_PIXELFORMAT,"EMR_PIXELFORMAT");
		typemap.put(EMR_DRAWESCAPE,"EMR_DRAWESCAPE");
		typemap.put(EMR_EXTESCAPE,"EMR_EXTESCAPE");
		typemap.put(EMR_SMALLTEXTOUT,"EMR_SMALLTEXTOUT");
		typemap.put(EMR_FORCEUFIMAPPING,"EMR_FORCEUFIMAPPING");
		typemap.put(EMR_NAMEDESCAPE,"EMR_NAMEDESCAPE");
		typemap.put(EMR_COLORCORRECTPALETTE,"EMR_COLORCORRECTPALETTE");
		typemap.put(EMR_SETICMPROFILEA,"EMR_SETICMPROFILEA");
		typemap.put(EMR_SETICMPROFILEW,"EMR_SETICMPROFILEW");
		typemap.put(EMR_ALPHABLEND,"EMR_ALPHABLEND");
		typemap.put(EMR_SETLAYOUT,"EMR_SETLAYOUT");
		typemap.put(EMR_TRANSPARENTBLT,"EMR_TRANSPARENTBLT");
		typemap.put(EMR_GRADIENTFILL,"EMR_GRADIENTFILL");
		typemap.put(EMR_SETLINKEDUFIS,"EMR_SETLINKEDUFIS");
		typemap.put(EMR_SETTEXTJUSTIFICATION,"EMR_SETTEXTJUSTIFICATION");
		typemap.put(EMR_COLORMATCHTOTARGETW,"EMR_COLORMATCHTOTARGETW"); 
		typemap.put(EMR_CREATECOLORSPACEW,"EMR_CREATECOLORSPACEW");
	}
	
	public void setinitObjectMap(){
		typeObjectmap.put(EMR_HEADER, new emfEMRHEADER());
		typeObjectmap.put(EMR_POLYBEZIER,new emfRecordType()); 
		typeObjectmap.put(EMR_POLYGON,new emfRecordType() ); 
		typeObjectmap.put(EMR_POLYLINE,new emfRecordType()); 
		typeObjectmap.put(EMR_POLYBEZIERTO,new emfRecordType());
		typeObjectmap.put(EMR_POLYLINETO,new emfRecordType()); 
		typeObjectmap.put(EMR_POLYPOLYLINE,new emfRecordType()); 
		typeObjectmap.put(EMR_POLYPOLYGON,new emfRecordType()); 
		typeObjectmap.put(EMR_SETWINDOWEXTEX,new emfRecordType()); 
		typeObjectmap.put(EMR_SETWINDOWORGEX,new emfRecordType()); 
		typeObjectmap.put(EMR_SETVIEWPORTEXTEX,new emfRecordType()); 
		typeObjectmap.put(EMR_SETVIEWPORTORGEX,new emfRecordType()); 
		typeObjectmap.put(EMR_SETBRUSHORGEX,new emfRecordType()); 
		typeObjectmap.put(EMR_EOF,new emfRecordType()); 
		typeObjectmap.put(EMR_SETPIXELV,new emfRecordType()); 
		typeObjectmap.put(EMR_SETMAPPERFLAGS,new emfRecordType()); 
		typeObjectmap.put(EMR_SETMAPMODE,new emfRecordType()); 
		typeObjectmap.put(EMR_SETBKMODE,new emfRecordType()); 
		typeObjectmap.put(EMR_SETPOLYFILLMODE,new emfRecordType()); 
		typeObjectmap.put(EMR_SETROP2,new emfRecordType()); 
		typeObjectmap.put(EMR_SETSTRETCHBLTMODE,new emfRecordType()); 
		typeObjectmap.put(EMR_SETTEXTALIGN,new emfRecordType()); 
		typeObjectmap.put(EMR_SETCOLORADJUSTMENT,new emfRecordType()); 
		typeObjectmap.put(EMR_SETTEXTCOLOR,new emfEMRSETTEXTCOLOR()); 
		typeObjectmap.put(EMR_SETBKCOLOR,new emfRecordType()); 
		typeObjectmap.put(EMR_OFFSETCLIPRGN,new emfRecordType()); 
		typeObjectmap.put(EMR_MOVETOEX,new emfRecordType()); 
		typeObjectmap.put(EMR_SETMETARGN,new emfRecordType()); 
		typeObjectmap.put(EMR_EXCLUDECLIPRECT,new emfRecordType()); 
		typeObjectmap.put(EMR_INTERSECTCLIPRECT,new emfRecordType()); 
		typeObjectmap.put(EMR_SCALEVIEWPORTEXTEX,new emfRecordType()); 
		typeObjectmap.put(EMR_SCALEWINDOWEXTEX,new emfRecordType()); 
		typeObjectmap.put(EMR_SAVEDC,new emfRecordType()); 
		typeObjectmap.put(EMR_RESTOREDC,new emfRecordType()); 
		typeObjectmap.put(EMR_SETWORLDTRANSFORM,new emfRecordType()); 
		typeObjectmap.put(EMR_MODIFYWORLDTRANSFORM,new emfEMRMODIFYWORLDTRANSFORM()); 
		typeObjectmap.put(EMR_SELECTOBJECT,new emfRecordType()); 
		typeObjectmap.put(EMR_CREATEPEN,new emfRecordType()); 
		typeObjectmap.put(EMR_CREATEBRUSHINDIRECT,new emfRecordType()); 
		typeObjectmap.put(EMR_DELETEOBJECT,new emfRecordType()); 
		typeObjectmap.put(EMR_ANGLEARC,new emfRecordType()); 
		typeObjectmap.put(EMR_ELLIPSE,new emfRecordType()); 
		typeObjectmap.put(EMR_RECTANGLE,new emfRecordType()); 
		typeObjectmap.put(EMR_ROUNDRECT,new emfRecordType()); 
		typeObjectmap.put(EMR_ARC,new emfRecordType()); 
		typeObjectmap.put(EMR_CHORD,new emfRecordType()); 
		typeObjectmap.put(EMR_PIE,new emfRecordType()); 
		typeObjectmap.put(EMR_SELECTPALETTE,new emfRecordType());
		typeObjectmap.put(EMR_CREATEPALETTE,new emfRecordType());
		typeObjectmap.put(EMR_SETPALETTEENTRIES,new emfRecordType());
		typeObjectmap.put(EMR_RESIZEPALETTE,new emfRecordType());
		typeObjectmap.put(EMR_REALIZEPALETTE,new emfRecordType());
		typeObjectmap.put(EMR_EXTFLOODFILL ,new emfRecordType());
		typeObjectmap.put(EMR_LINETO,new emfRecordType());
		typeObjectmap.put(EMR_ARCTO,new emfRecordType());
		typeObjectmap.put(EMR_POLYDRAW,new emfRecordType());
		typeObjectmap.put(EMR_SETARCDIRECTION,new emfRecordType());
		typeObjectmap.put(EMR_SETMITERLIMIT,new emfRecordType());
		typeObjectmap.put(EMR_BEGINPATH,new emfRecordType());
		typeObjectmap.put(EMR_ENDPATH,new emfRecordType());
		typeObjectmap.put(EMR_CLOSEFIGURE,new emfRecordType());
		typeObjectmap.put(EMR_FILLPATH,new emfRecordType());
		typeObjectmap.put(EMR_STROKEANDFILLPATH,new emfRecordType());
		typeObjectmap.put(EMR_STROKEPATH,new emfRecordType());
		typeObjectmap.put(EMR_FLATTENPATH,new emfRecordType());
		typeObjectmap.put(EMR_WIDENPATH,new emfRecordType());
		typeObjectmap.put(EMR_SELECTCLIPPATH,new emfRecordType());
		typeObjectmap.put(EMR_ABORTPATH,new emfRecordType());
		typeObjectmap.put(EMR_COMMENT,new emfEMRCOMMENT());
		typeObjectmap.put(EMR_FILLRGN,new emfRecordType());
		typeObjectmap.put(EMR_FRAMERGN,new emfRecordType());
		typeObjectmap.put(EMR_INVERTRGN,new emfRecordType());
		typeObjectmap.put(EMR_PAINTRGN,new emfRecordType());
		typeObjectmap.put(EMR_EXTSELECTCLIPRGN,new emfRecordType());
		typeObjectmap.put(EMR_BITBLT,new emfRecordType());
		typeObjectmap.put(EMR_STRETCHBLT,new emfRecordType());
		typeObjectmap.put(EMR_MASKBLT,new emfRecordType());
		typeObjectmap.put(EMR_PLGBLT,new emfRecordType());
		typeObjectmap.put(EMR_SETDIBITSTODEVICE,new emfRecordType());
		typeObjectmap.put(EMR_STRETCHDIBITS,new emfRecordType());
		typeObjectmap.put(EMR_EXTCREATEFONTINDIRECTW,new emfRecordType());
		typeObjectmap.put(EMR_EXTTEXTOUTA,new emfRecordType());
		typeObjectmap.put(EMR_EXTTEXTOUTW,new emfEMREXTTEXTOUTW());
		typeObjectmap.put(EMR_POLYBEZIER16,new emfRecordType());
		typeObjectmap.put(EMR_POLYGON16,new emfRecordType());
		typeObjectmap.put(EMR_POLYLINE16,new emfRecordType());
		typeObjectmap.put(EMR_POLYBEZIERTO16,new emfRecordType());
		typeObjectmap.put(EMR_POLYLINETO16,new emfRecordType());
		typeObjectmap.put(EMR_POLYPOLYLINE16,new emfRecordType());
		typeObjectmap.put(EMR_POLYPOLYGON16,new emfRecordType());
		typeObjectmap.put(EMR_POLYDRAW16,new emfRecordType());
		typeObjectmap.put(EMR_CREATEMONOBRUSH,new emfRecordType());
		typeObjectmap.put(EMR_CREATEDIBPATTERNBRUSHPT,new emfRecordType());
		typeObjectmap.put(EMR_EXTCREATEPEN,new emfRecordType());
		typeObjectmap.put(EMR_POLYTEXTOUTA,new emfRecordType());
		typeObjectmap.put(EMR_POLYTEXTOUTW,new emfRecordType());
		typeObjectmap.put(EMR_SETICMMODE,new emfRecordType());
		typeObjectmap.put(EMR_CREATECOLORSPACE,new emfRecordType());
		typeObjectmap.put(EMR_SETCOLORSPACE,new emfRecordType());
		typeObjectmap.put(EMR_DELETECOLORSPACE,new emfRecordType());
		typeObjectmap.put(EMR_GLSRECORD,new emfRecordType());
		typeObjectmap.put(EMR_GLSBOUNDEDRECORD,new emfRecordType());
		typeObjectmap.put(EMR_PIXELFORMAT,new emfRecordType());
		typeObjectmap.put(EMR_DRAWESCAPE,new emfRecordType());
		typeObjectmap.put(EMR_EXTESCAPE,new emfRecordType());
		typeObjectmap.put(EMR_SMALLTEXTOUT,new emfRecordType());
		typeObjectmap.put(EMR_FORCEUFIMAPPING,new emfRecordType());
		typeObjectmap.put(EMR_NAMEDESCAPE,new emfRecordType());
		typeObjectmap.put(EMR_COLORCORRECTPALETTE,new emfRecordType());
		typeObjectmap.put(EMR_SETICMPROFILEA,new emfEMRSETICMPROFILEA());
		typeObjectmap.put(EMR_SETICMPROFILEW,new emfRecordType());
		typeObjectmap.put(EMR_ALPHABLEND,new emfRecordType());
		typeObjectmap.put(EMR_SETLAYOUT,new emfRecordType());
		typeObjectmap.put(EMR_TRANSPARENTBLT,new emfRecordType());
		typeObjectmap.put(EMR_GRADIENTFILL,new emfRecordType());
		typeObjectmap.put(EMR_SETLINKEDUFIS,new emfRecordType());
		typeObjectmap.put(EMR_SETTEXTJUSTIFICATION,new emfRecordType());
		typeObjectmap.put(EMR_COLORMATCHTOTARGETW,new emfRecordType()); 
		typeObjectmap.put(EMR_CREATECOLORSPACEW,new emfRecordType());
	}
	

	public void setBytesData(byte[] v_emfbytes){
		emfbytes = v_emfbytes;
	}

	public String getTheTitleString(int v_number){
		String ret="non";
		//todo: when object is class instance, get the name by the class method. 
		if(typeObjectmap.containsKey(v_number)){
			String nameFromclass = typeObjectmap.get(v_number).getName();
			if(nameFromclass.isEmpty()){
				ret = typemap.get(v_number);
			}else{
				ret = nameFromclass;
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param v_number : type 
	 * @param index    : record position
	 * @return         : index of the next record
	 * @throws Exception 
	 */
	public int getStartRead(int index) throws Exception{
		int numType = get4bytesInt(emfbytes, index);
		int ret=0;
		//todo: when object is class instance, get the name by the class method. 
		if(typeObjectmap.containsKey(numType)){
			typeObjectmap.get(numType).setBytesData(emfbytes);
			ret = typeObjectmap.get(numType).read(index);
		}
		return ret;
	}
	

  public emfRecordType getTypeInstance(int v_number) throws Exception{
	  emfRecordType ret; 
	  if(typeObjectmap.containsKey(v_number)){
			String nameFromclass = typeObjectmap.get(v_number).getName();
			if(nameFromclass.isEmpty()){
				ret = typeObjectmap.get(v_number);
			}else{
				ret = typeObjectmap.get(v_number);
			}
		}else{
			throw new emfFileException("No record type :");
		}
	  return ret;
  }	
	
	
	//getter setter
	public Map<Integer,String> getTypeMap(){
		return typemap;
	}
	
	public void setTypeMap(Map<Integer,String> map){
		typemap = map;
	}
}

