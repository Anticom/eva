package eu.anticom.eva.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsynchronousBufferedInputStreamWrapper extends InputStream {
    volatile BufferedInputStream wrappedInputStream;
    volatile ConcurrentLinkedQueue<byte[]> dataQueue;
    volatile int overallBytesConsumed;
    volatile int overallBytesBuffered;
    volatile int bufferSizeInBytes;

    byte[] currentByteBuffer;
    int currentBufferPosition;
    ExecutorService executorService;

    volatile boolean eof = false;

    Runnable buffering = new Runnable() {
        public void run() {

            int maxDataQueueSize = 4;


            int currentBytesBuffered = overallBytesBuffered
                    - overallBytesConsumed;

            while (!eof) {
                try {
                    if (currentBytesBuffered < bufferSizeInBytes
                            || dataQueue.size() < maxDataQueueSize) {

                        System.out.println("Buffering...");

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                        while (currentBytesBuffered < bufferSizeInBytes) {
                            byte[] buffer = new byte[bufferSizeInBytes];

                            int bytesRead = wrappedInputStream.read(buffer);

                            if (bytesRead == -1) { // EOF
                                System.out.println("EOF");
                                eof = true;
                                break;
                            }

                            byteArrayOutputStream.write(buffer, 0,
                                    bytesRead);

                            overallBytesBuffered += bytesRead;

                            currentBytesBuffered = overallBytesBuffered
                                    - overallBytesConsumed;

                            System.out.println("Buffered: "
                                    + currentBytesBuffered + " Current bytes buffered: " + currentBytesBuffered);
                        }

                        if (!eof) {
                            dataQueue.add(byteArrayOutputStream
                                    .toByteArray());
                            currentBytesBuffered = 0;
                        }

                    } else {
                        TimeUnit.MILLISECONDS.sleep(5L);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Finished Buffering");
        }
    };

    public AsynchronousBufferedInputStreamWrapper(InputStream inputStream,
                                                  int bufferSizeInBytes) {
        this.wrappedInputStream = new BufferedInputStream(inputStream,
                bufferSizeInBytes);
        this.bufferSizeInBytes = bufferSizeInBytes;
        this.dataQueue = new ConcurrentLinkedQueue<byte[]>();

        this.executorService = Executors.newSingleThreadExecutor();
        executorService.execute(buffering);
    }

    @Override
    public int read() throws IOException {
        waitForCurrentByteBuffer();
        if (reachedEndOfStream()) {
            return -1;
        }
        byte b = currentByteBuffer[currentBufferPosition];
        currentBufferPosition++;
        overallBytesConsumed++;
        return b & 0xFF;
    }

    private boolean reachedEndOfStream() {
        return overallBytesConsumed == overallBytesBuffered;
    }

    private void waitForCurrentByteBuffer() {
        if (currentByteBuffer == null
                || currentBufferPosition > currentByteBuffer.length - 1) {
            currentByteBuffer = null;
            while (currentByteBuffer == null && !reachedEndOfStream()) {
                currentByteBuffer = dataQueue.poll();
                currentBufferPosition = 0;
                try {
                    TimeUnit.MILLISECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.executorService.shutdownNow();
    }
}
