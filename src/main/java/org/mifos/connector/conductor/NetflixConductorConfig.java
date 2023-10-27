package org.mifos.connector.conductor;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.client.worker.Worker;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.mifos.connector.conductor.workers.BlockFunds;
import org.mifos.connector.conductor.workers.BookFunds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NetflixConductorConfig {

    @Value("${conductor.server.host}")
    private String uri;

    @Autowired
    BlockFunds blockFunds;

    @PostConstruct
    public void netflixConfig() {
        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI(uri);

        // ideally the thread count should be number of workers
        int threadCount = 2;

        blockFunds.setTaskDefName("block_funds");

        Worker worker2 = new BookFunds("book_funds");

        TaskRunnerConfigurer configurer = new TaskRunnerConfigurer.Builder(taskClient, Arrays.asList(blockFunds, worker2))
                .withThreadCount(threadCount).build();

        configurer.init();
    }

    @Bean
    WorkflowClient workflowClient() {
        return new WorkflowClient();
    }
}
