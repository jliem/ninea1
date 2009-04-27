package T9A1.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * A class to update the server's files.
 * @author Chase
 *
 */
public class ServerUpdater {

	public File dir = new File("directoryName");
	public Hashtable<String, Long> files;
	public ServerUpdater(){

	}

	/**
	 * Retrieve the list of files requiring an update.
	 * @return a list of files requiring an update
	 */
	public List<File> getUpdateList(){
		File[] fileList = dir.listFiles();
		ArrayList<File> updateList = new ArrayList<File>();
		for(int i=0;i<dir.length();i++){
			if(files.contains(fileList[i].toString())){
				if(fileList[i].lastModified() != files.get(fileList[i].toString())){
					files.put(fileList[i].toString(), fileList[i].lastModified());
					updateList.add(fileList[i]);
				}
			}else{
				files.put(fileList[i].toString(), fileList[i].lastModified());
				updateList.add(fileList[i]);
			}
		}
		return updateList;
	}

}
