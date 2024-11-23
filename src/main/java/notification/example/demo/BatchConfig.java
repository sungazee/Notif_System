package notification.example.demo;


import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import notification.example.demo.Log.NotificationLogService;
import notification.example.demo.Log.NotificationStepExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.text.SimpleDateFormat;
import java.util.Optional;



@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final NotificationRepository repository;


    //Suivi
    private final NotificationLogService notificationLogService;



    // Depuis DB

    @Autowired
    public EntityManagerFactory entityManagerFactory;
    @Bean
    public JpaPagingItemReader<Notification> reader1(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Notification>()
                .name("notifReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT * FROM Notification  ")
                .pageSize(10)
                .build();
    }


    // Envoi bulk mail
    @Bean
    public NotificationProcessor processor() {
        return new NotificationProcessor();
    }


    //Envoie mail pour des personnes precis
    @Bean
    public NotifProcess1 process1(){return new NotifProcess1();}

    @Bean
    public prtest process2(){return new prtest();}

    @Bean
    public RepositoryItemWriter<Notification> writer() {
        RepositoryItemWriter<Notification> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() {
        return new StepBuilder("TzzzzT", jobRepository)
                .<Notification, Notification>chunk(150, platformTransactionManager)
                .reader(reader1(entityManagerFactory))
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    public Step step2() {
        return new StepBuilder("izretzz", jobRepository)
                .<Notification, Notification>chunk(150, platformTransactionManager)
                .reader(reader1(entityManagerFactory))
                .processor(processor())
                .writer(writer())
                .listener(Optional.of(new NotificationStepExecutionListener(notificationLogService)))
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob() {return new JobBuilder("nuyytzgN", jobRepository)
                .start(step2())
                .build();

    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }



    //pour le fichier csv
    @Bean
    public FlatFileItemReader<Notification> reader() {
        FlatFileItemReader<Notification> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/Notification.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }




    private LineMapper<Notification> lineMapper() {
        DefaultLineMapper<Notification> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "canal_envoi" , "date_envoi", "date_notif", "date_op", "est_envoye", "est_notifie", "file_name", "id_liaison", "message" , "montant_notif", "n_client" , "num_tel", "email", "type_notif", "valide"  );

        BeanWrapperFieldSetMapper<Notification> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Notification.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }


}
