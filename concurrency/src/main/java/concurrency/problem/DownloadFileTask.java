package concurrency.problem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DownloadFileTask implements Runnable {

    private final DownloadStatus status;
    private final int fileSizeInBytes;

    @Override
    public void run() {
        for (var i = 0; i < fileSizeInBytes; i++) {
            if (Thread.currentThread().isInterrupted())
                return;
            status.incrementTotalBytes();
        }
    }

}
