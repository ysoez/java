package server.cluster;

import server.model.WorkerTask;
import server.model.WorkerTaskResult;

public interface WorkerTaskExecutor {

    WorkerTaskResult execute(WorkerTask task);

}
