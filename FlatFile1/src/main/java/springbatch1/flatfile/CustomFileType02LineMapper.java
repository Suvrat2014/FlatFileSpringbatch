package springbatch1.flatfile;

import org.springframework.batch.item.file.LineMapper;

public class CustomFileType02LineMapper implements LineMapper<FileType02>{

	@Override
	public FileType02 mapLine(String line, int arg1) throws Exception {
		FileType02 fileType02 = new FileType02();
		fileType02.setId(line.substring(2, 3));
		fileType02.setName(line.substring(4, 5));
		fileType02.setAddress(line.substring(5, 6));
	    return fileType02;
	} 

}
