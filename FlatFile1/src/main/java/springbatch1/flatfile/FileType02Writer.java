package springbatch1.flatfile;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class FileType02Writer implements ItemWriter<FileType02> {

	@Override
	public void write(List<? extends FileType02> fileList) throws Exception {
		for(FileType02 filetype02 : fileList) {
			System.out.println(filetype02.getId());
			System.out.println(filetype02.getName());
			System.out.println(filetype02.getAddress());
			System.out.println(filetype02.getId());
		}
	}
	
}


