package com.github.exobite.CharsToArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class Utils {
	
	public static InputStream getResource(String filename) {
		if (filename == null) {
		  throw new IllegalArgumentException("Filename cannot be null");
		}
		try {
		  URL url = Utils.class.getClassLoader().getResource(filename);
		  if (url == null) {
			return null;
		  }
		  URLConnection connection = url.openConnection();
		  connection.setUseCaches(false);
		  return connection.getInputStream();
		} catch (IOException ex) {}
		return null;
	}
	
	public static void saveResource(String resourcePath, boolean replace) {
		if ((resourcePath == null) || (resourcePath.equals(""))) {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}
		
		resourcePath = resourcePath.replace('\\', '/');
		InputStream in = getResource(resourcePath);
		if (in == null) {
			throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found.");
		}
		
		String execPath = getExecutionPath();
		
		//System.out.println(execPath);
		
		File outFile = new File(execPath, resourcePath);
		int lastIndex = resourcePath.lastIndexOf('/');
		File outDir = new File(execPath, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
		
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		try {
			if ((!outFile.exists()) || (replace)) {
				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) { 
					out.write(buf, 0, len);
				}
			out.close();
			in.close();
		} else {
			System.out.println("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
		}
		System.out.println("Saved "+outFile+".");
	} catch (IOException ex) {
		System.out.println("Could not save " + outFile.getName() + " to " + outFile + ",\n"+ex);
		}
	}
	
	public static String getExecutionPath() {
		try {
			String r = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getCanonicalPath().toString();
			//System.out.println(r);
			return r;
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
