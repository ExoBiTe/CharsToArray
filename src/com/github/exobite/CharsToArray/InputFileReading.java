package com.github.exobite.CharsToArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFileReading {
	
	static BufferedReader rd;
	static long RowsSearched, RowsChanged;
	
	public static void readFile(File in, File out) {
		if(!in.exists()) {
			System.out.println("Couldn´t finde the File "+in.getAbsolutePath());
			return;
		}
		System.out.println("Working on File "+in.getAbsolutePath());
		try {
			
			if(!out.exists()) {
				out.createNewFile();
			}
			FileWriter fw = new FileWriter(out);
			rd = new BufferedReader(new FileReader(in));
			String l = rd.readLine();
			while(l!=null) {
				String r = check(l);
				l = rd.readLine();
				RowsSearched++;
				fw.write(r);
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String check(String s) {
		String r = s;
		Pattern p = Pattern.compile("([\\S]\\w+) := '(.*?)'");	//([\S]\w+) := '(.*?)'
		Matcher match = p.matcher(s);
		if(match.find()) {
			String g1 = match.group(1);
			String g2 = match.group(2);
			if((g2.length()>MainClass.cfg.MAXSIZE && MainClass.cfg.MAXSIZE>0) || g2.length()<=1 || g1.equals("S7_Optimized_Access")) return "\n"+r;
			RowsChanged++;
			if(MainClass.cfg.DEBUG_MSG) {
				System.out.println(RowsSearched+": "+s);
				System.out.println("G1: "+g1+"\nG2: "+g2);
			}
			String n = s.replace(g1, "%G1").replace(g2, "%G2");
			r = change(n, g1, g2);
		}else {
			r = "\n"+r;
		}
		return r;
	}
	
	private static String change(String src, String G1, String G2) {
		StringBuilder r = new StringBuilder("");
		int length = G2.length();
		for(int i=0;i<length;i++) {
			String row = src;
			row = row.replaceAll("%G1", G1+"["+i+"]").replaceAll("%G2", ""+G2.charAt(i));
			r.append("\n").append(row);
		}
		return r.toString();
	}

}
