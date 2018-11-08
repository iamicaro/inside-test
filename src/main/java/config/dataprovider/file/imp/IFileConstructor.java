package config.dataprovider.file.imp;

import java.io.File;

import com.google.gson.JsonArray;

import config.dataprovider.file.FileConstructor;

public class IFileConstructor implements FileConstructor {
	
	public File read(final String path) {
		File file = new File(path);
		return file;
	}
	
	public JsonArray getJsonArray() {
		return new JsonArray();
	}
	
}
