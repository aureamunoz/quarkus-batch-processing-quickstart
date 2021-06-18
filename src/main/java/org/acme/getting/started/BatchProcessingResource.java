package org.acme.getting.started;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.StepBuilder;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.JobExecution;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

@Path("/batch")
public class BatchProcessingResource {

    @Inject
    QuarkusJobOperator quarkusJobOperator;

    @Inject
    JobOperator jobOperator;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/job/execute")
    public Response executeJob() {
        Properties jobParameters = new Properties();
        long executionId = jobOperator.start("odds", jobParameters);
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);

        return Response.ok(new JobData(executionId, jobExecution.getBatchStatus().toString())).build();
    }

    @GET
    @Path("/job/programmatic/execute")
    public Response executeProgrammaticJob() {
        Properties jobParameters = new Properties();

        Job job = new JobBuilder("programmatic")
                .step(new StepBuilder("programmaticStep")
                        .reader("simpleItemReader")
                        .processor("simpleItemProcessor")
                        .writer("simpleItemWriter")
                        .build())
                .build();

        long executionId = quarkusJobOperator.start(job, new Properties());
        JobExecution jobExecution = quarkusJobOperator.getJobExecution(executionId);

        return Response.ok(new JobData(executionId, jobExecution.getBatchStatus().toString())).build();
    }

    @RegisterForReflection
    public static class JobData {
        private Long executionId;
        private String status;

        public JobData() {
        }

        public JobData(final Long executionId, final String status) {
            this.executionId = executionId;
            this.status = status;
        }

        public Long getExecutionId() {
            return executionId;
        }

        public void setExecutionId(final Long executionId) {
            this.executionId = executionId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(final String status) {
            this.status = status;
        }
    }
}