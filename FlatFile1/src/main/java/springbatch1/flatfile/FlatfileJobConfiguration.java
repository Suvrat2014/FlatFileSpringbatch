package springbatch1.flatfile;

import java.io.FileNotFoundException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * @author Tobias Flohre
 */
@Configuration
@EnableBatchProcessing
public class FlatfileJobConfiguration {	
	@Autowired
	private JobBuilderFactory jobBuilders;
	
	@Autowired
	private StepBuilderFactory stepBuilders;
	
	@Autowired
	private FileWriter fileWriter;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Bean
	public Job flatfileToDbWithParametersJob() throws FileNotFoundException{
		return jobBuilders.get("flatfileToDbWithParametersJob")
				.start(step())
				.build();
	}
	
	@Bean
	public Step step() throws FileNotFoundException{
		return stepBuilders.get("step")
				.<FileType01,FileType01>chunk(1)
    			.reader(reader())
				.writer(fileWriter)
				.build();
	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<FileType01> reader() throws FileNotFoundException{
		FlatFileItemReader<FileType01> itemReader = new FlatFileItemReader<FileType01>();
		CustomLineMapper customLineMapper = new CustomLineMapper();
		CustomBufferedReaderFactory customBufferedReaderFactory = new CustomBufferedReaderFactory();
		itemReader.setLineMapper(customLineMapper);
		itemReader.setResource(new FileSystemResource("src/main/resources/FileType01.zip"));
		//itemReader.setResource(new InputStreamResource(new FileInputStream(new File("src/main/resources/FileType01.zip"))));
		itemReader.setBufferedReaderFactory(customBufferedReaderFactory);
	//	itemReader.open(new ExecutionContext());
		return itemReader;
	}
	
/*	@Bean
	public FlatFileItemWriter<FileType01> writer(){
		FlatFileItemWriter<FileType01> itemWriter = new FlatFileItemWriter<FileType01>();
		itemWriter.toString();
		return itemWriter;
	}
	*/
	
}
