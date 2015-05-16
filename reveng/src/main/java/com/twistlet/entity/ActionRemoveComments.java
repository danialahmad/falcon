package com.twistlet.entity;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ActionRemoveComments {
	public static void main(String[] args) throws IOException {
		String directory = args[0];
		Collection<File> files = FileUtils.listFiles(new File(directory), null, false);
		for (File file : files) {
			List<String> lines = FileUtils.readLines(file);
			if (lines.size() > 2 && lines.get(1).startsWith("// Generated")) {
				lines.remove(1);
			}
			FileUtils.writeLines(file, lines);
		}
	}
}