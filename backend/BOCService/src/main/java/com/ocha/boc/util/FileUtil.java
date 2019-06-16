/**
 * 
 */
package com.ocha.boc.util;

import java.io.File;
import java.io.InputStream;

/**
 * @author robert
 *
 */
public class FileUtil {
	public static InputStream getFile(String path) {
		FileUtil.class.getClassLoader();
		InputStream file = ClassLoader.getSystemResourceAsStream(path);
		return file;
	}
	public static boolean deleteFile(String fileName) {
		File localFile = new File(fileName);
			return localFile.delete();
	}
}
