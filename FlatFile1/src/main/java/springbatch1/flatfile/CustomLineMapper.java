package springbatch1.flatfile;

import org.springframework.batch.item.file.LineMapper;

public class CustomLineMapper implements LineMapper<FileType01>{

	@Override
	public FileType01 mapLine(String line, int arg1) throws Exception {
		FileType01 fileType01 = new FileType01();
		fileType01.setId(line.substring(2, 3));
		fileType01.setName(line.substring(4, 5));
		fileType01.setAddress(line.substring(5, 6));
	    return fileType01;
	}

}
