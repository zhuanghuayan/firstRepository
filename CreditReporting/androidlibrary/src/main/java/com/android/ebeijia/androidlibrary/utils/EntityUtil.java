package com.android.ebeijia.androidlibrary.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class EntityUtil {
	@Deprecated
	public static <T> List<T> parseObject(JSONArray data, Class<T> clazz) {
		List<T> list = new ArrayList<T>();

		JSONArray array = data;

		Field[] field = clazz.getDeclaredFields();
		for (int j = 0; j < array.length(); j++) {
			JSONObject json = null;
			try {
				json = array.getJSONObject(j);
			} catch (JSONException e) {

				Log.e(e);
			}
			T t = null;
			try {
				t = clazz.newInstance();
			} catch (Exception e) {
			}

			for (int i = 0; i < field.length; i++) {

				field[i].setAccessible(true);
				Object o = null;
				try {
					o = json.get(field[i].getName());
				} catch (JSONException e) {
					Log.i(field[i].getName(),field[i].getName());
					continue;
				}
				try {
					field[i].set(t, o);
				} catch (Exception e) {
					Log.i(field[i].getName(),field[i].getName());
					Log.e(e);
				}

			}
			list.add(t);
		}

		return list;

	}
	/**
	 * array 中必须是jsonObject
	 *
	 * @param clazz
	 * @return
	 */
public static  <T extends Object> List<T> parseJSONArray(Object a,Class<T> clazz){
	List<T> list=new ArrayList<T>();
	if(a==null){
		return list;
	}
	JSONArray array=null;

	if(a instanceof JSONArray){
		array=(JSONArray)a;
	}
	else {
		try {
			array = new JSONArray((String) a);
		} catch (Exception e) {
			return list;
		}
	}
	if(array!=null){
	for(int i=0;i<array.length();i++){
	
	 JSONObject o=null;
      try {
		
		 o = array.getJSONObject(i);
	 } catch (Exception e) {
		Log.e(e);
		 continue;
	 } 
    list.add(parseJSONObject(o, clazz));
	}
	}
	return list;
	
}
	
public static <T> T parseJSONObject(Object d, Class<T> clazz) {


	T t = null;
	JSONObject data=null;
	try {
		t = clazz.newInstance();

	} catch (Exception e) {
		Log.e(e);
	}
	if(d==null){
		return t;
	}
	if(d instanceof JSONObject){
		data=(JSONObject)d;
	}
	else {
		try {
			data = new JSONObject((String) d);
		} catch (Exception e) {
			return t;
		}
	}
	Method[] methods = clazz.getDeclaredMethods();//不包含继承的方法

	for (Method m : methods) {
		if(m.getName().length()<4){
			continue;
		}

		if(Modifier.isStatic(m.getModifiers())){//判断是否是静态的
			continue;
		}
		String temp3 = m.getName().substring(0, 3);
		if(!"set".equals(temp3)){
			continue;
		}

		String temp2 = m.getName().substring(4);
		String temp1 = m.getName().substring(3, 4).toLowerCase();
		StringBuffer sb = new StringBuffer();
		sb.append(temp1);
		sb.append(temp2);
		Object value = null;
		try {
			value = data.get(sb.toString());
			m.invoke(t, value);
		} catch (Exception e) {
			Log.e(e);
			continue;
		}
		

	}

	return t;

}
	public static<T> JSONObject parseObject(T t){

		JSONObject o=new JSONObject();
		Field[]fields=t.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field f=fields[i];
			f.setAccessible(true);
			if(Modifier.isStatic(f.getModifiers())){
				continue;
			}
			try {
				o.put(f.getName(), f.get(t));
			}  catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return o;

	}
}
