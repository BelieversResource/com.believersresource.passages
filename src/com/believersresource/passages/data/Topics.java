package com.believersresource.passages.data;

import java.util.ArrayList;
import org.json.JSONArray;

public class Topics extends ArrayList<Topic> {

	private static final long serialVersionUID = 1L;

	public static Topics loadFromJSON(JSONArray array)
	{
		Topics result=new Topics();
		try {
			for (int i=0;i<array.length();i++)
			{
				result.add(Topic.jsonDecode(array.getJSONObject(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String[] getNames()
	{
		String[] result=new String[this.size()];
		for (int i=0;i<this.size();i++) result[i]=this.get(i).Name;
		return result;
	}
	
}
