package mysticcraft.io;

import java.io.File;

import mysticcraft.main.Reference;

public class MysticcraftFile extends File {

	private static final long serialVersionUID = 1L;

	public MysticcraftFile(String filename) {
		super(Reference.RESOURCE_DIRECTORY + "/" + filename + ".mysticcraft");
	}
	
	public MysticcraftFile(String filename, String extension) {
		super(Reference.RESOURCE_DIRECTORY + "/" + filename + "." + extension);
	}
	
	public MysticcraftFile(String dir, String filename, String extension) {
		super(dir + "/" + filename + "." + extension);
	}

}
