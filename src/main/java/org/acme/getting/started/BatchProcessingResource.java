package org.acme.getting.started;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.batch.api.BatchProperty;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

@Path("/batch")
public class BatchProcessingResource {

    @Inject
    @BatchProperty(name = "job.config.name")
    String batchConfig;

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
//        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobParameters = new Properties();
        long executionId = jobOperator.start("odds", jobParameters);
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);

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