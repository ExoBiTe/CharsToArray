package com.github.exobite.CharsToArray;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainClass {
	
	static Gson gs;
	static GSONConfig cfg;
	
	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();
		System.out.println("Initializing...");
		gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		loadConfig();
		if(args.length>0) loadStartparams(args);
		File f = new File(Utils.getExecutionPath()+File.separator+cfg.INFILE);
		File out = new File(Utils.getExecutionPath()+File.separator+cfg.OUTFILE);
		InputFileReading.readFile(f, out);
		System.out.println("Searched "+InputFileReading.RowsSearched+" Rows, changed "+InputFileReading.RowsChanged+" of them.");
		System.out.println("Done! (Took "+(System.currentTimeMillis()-time1)+" ms)");
	}
	
	private static void loadStartparams(String[] arg) {
		for(String s:arg) {
			if(s.toLowerCase().startsWith("infile=")) {
				s = s.replace("infile=", "");
				cfg.INFILE = s;
				System.out.println("[STARTPARAM]Recognized Startparameter, changed the INFILE to "+s+".");
			}else if(s.toLowerCase().startsWith("outfile=")) {
				s = s.replace("outfile=", "");
				cfg.OUTFILE = s;
				System.out.println("[STARTPARAM]Recognized Startparameter, changed the OUTFILE to "+s+".");
			}
		}
	}
	
	private static void loadConfig() {
		File cfg = new File(Utils.getExecutionPath()+File.separator+"config.json");
		if(!cfg.exists()) {
			System.out.println(cfg.getAbsolutePath()+" doesnt exist.");
			Utils.saveResource("config.json", true);
		}else {
			System.out.println(cfg.getAbsolutePath()+" exists!");
		}
		try(Reader rd = new FileReader(cfg)){
			MainClass.cfg = gs.fromJson(rd, GSONConfig.class);
		} catch (IOException e) {
			e.printStackTrace();
			//Exit.
		}
		/*for(GSONSearchword gw:MainClass.cfg.SEARCH) {
			gw.escapeChars();
		}*/
	}

}
