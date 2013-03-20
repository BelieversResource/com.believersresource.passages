package com.believersresource.passages.data;

import java.util.ArrayList;
import org.json.JSONArray;

public class Passages extends ArrayList<Passage> {

	private static final long serialVersionUID = 1L;

	public static Passages loadFromJSON(JSONArray array)
	{
		Passages result=new Passages();
		try {
			for (int i=0;i<array.length();i++)
			{
				result.add(Passage.jsonDecode(array.getJSONObject(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String[] getDisplayNames()
	{
		String[] result=new String[this.size()];
		for (int i=0;i<this.size();i++) result[i]=this.get(i).DisplayName;
		return result;
	}
	
	public String[][] getGroupedBodies()
	{
		String[][] result=new String[this.size()][1];
		for (int i=0;i<this.size();i++) result[i][0]=this.get(i).Body;
		return result;
	}
	
}
