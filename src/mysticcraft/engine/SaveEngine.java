package mysticcraft.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import mysticcraft.main.Reference;

public class SaveEngine {

	public int nextInt(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	
	public String nextString(StringTokenizer st) {
		return st.nextToken().replace("_", "");
	}

	@SuppressWarnings("deprecation")
	public void load(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			StringTokenizer st = new StringTokenizer(reader.readLine());
			loadFrom(st);
			reader.close();
		} catch (IOException e) {
			try {
				FileUtils.write(new File(Reference.EXCEPTION_RESOURCE_DIRECTORY + "/load_error.exception"), e.getMessage());
			} catch (Exception e1) {

			}
		}
	}

	@SuppressWarnings("deprecation")
	public void save(String filename) {
		try {
			FileOutputStream writer = new FileOutputStream(filename);
			StringBuffer str = new StringBuffer();
			saveTo(str);
			writer.write(str.toString().getBytes());
			writer.close();
		} catch (IOException e) {
			try {
				FileUtils.write(new File(Reference.EXCEPTION_RESOURCE_DIRECTORY + "/save_error.exception"), e.getMessage());
			} catch (Exception e1) {

			}
		}
	}

	public void loadFrom(StringTokenizer st) {
	}

	public void saveTo(StringBuffer str) {
	}
}
