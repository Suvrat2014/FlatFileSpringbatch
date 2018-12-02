package springbatch1.flatfile;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class FileWriter implements ItemWriter<FileType01> {

	@Override
	public void write(List<? extends FileType01> fileList) throws Exception {
		for(FileType01 filetype01 : fileList) {
			System.out.println(filetype01.getId());
			System.out.println(filetype01.getName());
			System.out.println(filetype01.getAddress());
			System.out.println(filetype01.getId());
		}
	}

}
