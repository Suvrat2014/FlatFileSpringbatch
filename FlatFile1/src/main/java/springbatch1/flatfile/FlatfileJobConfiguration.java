package springbatch1.flatfile;

import java.io.FileNotFoundException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

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
	private FileType02Writer fileType02Writer;
	
	
	@Bean
	public Job flatfileToDbWithParametersJob() throws FileNotFoundException{
	
		 Flow flowJob1 = (Flow) new FlowBuilder("flow1").start(step()).build();
			
	        Flow flowJob2 = (Flow) new FlowBuilder("flow2").start(step1()).build();
	
	 //       Flow flowJob3 = new FlowBuilder("flow3").start(taskletStep("step4")).build();
	  
	  //      Flow flowJob4 = new FlowBuilder("flow3").
	 
	
	        Flow slaveFlow = (Flow) new FlowBuilder("slaveFlow")
	
	                .split(new SimpleAsyncTaskExecutor()).add(flowJob1, flowJob2).build();
	
	        return jobBuilders.get("flatfileToDbWithParametersJob")
	
	                .incrementer(new RunIdIncrementer())
	
	                .start(slaveFlow)
	
	                .build().build(); 
		
		
		
		/*	return jobBuilders.get("flatfileToDbWithParametersJob")
				.start(step())
				.build();
				*/
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
	public Step step1() throws FileNotFoundException{
		return stepBuilders.get("step")
				.<FileType02,FileType02>chunk(1)
    			.reader(readerType02())
				.writer(fileType02Writer)
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
	
	@Bean
	@StepScope
	public FlatFileItemReader<FileType02> readerType02() throws FileNotFoundException{
		FlatFileItemReader<FileType02> itemReader = new FlatFileItemReader<FileType02>();
		CustomFileType02LineMapper fileTypeLineMapper = new CustomFileType02LineMapper();
		CustomBufferedReaderFactory customBufferedReaderFactory = new CustomBufferedReaderFactory();
		itemReader.setLineMapper(fileTypeLineMapper);
		itemReader.setResource(new FileSystemResource("src/main/resources/FileType02.zip"));
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
