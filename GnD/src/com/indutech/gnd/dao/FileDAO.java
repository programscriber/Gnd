package com.indutech.gnd.dao;

import java.util.List;
import java.util.Map;

import com.indutech.gnd.dto.CoreFiles;

public interface FileDAO {
	
	Long saveFile(CoreFiles file);

	List<CoreFiles> getFiles(Map<String,String> parametermap );

	List<CoreFiles> getAUFList();

}
