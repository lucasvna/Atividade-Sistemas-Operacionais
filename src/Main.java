import java.util.Random;

class ThreadSum implements Runnable {
    private final int[] array;
    private final int start;
    private final int end;
    private final int threadId;

    public ThreadSum(int[] array, int start, int end, int threadId) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        System.out.println("Thread " + threadId + " - Sum: " + sum);
    }

    public static void main(String[] args) {
        int NUM_THREADS = 4;
        int ARRAY_SIZE = 20;

        int[] array = new int[ARRAY_SIZE];
        Random random = new Random();

        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(100);
        }

        Thread[] threads = new Thread[NUM_THREADS];
        int chunkSize = ARRAY_SIZE / NUM_THREADS;
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * chunkSize;
            int end = (i == NUM_THREADS - 1) ? ARRAY_SIZE : (i + 1) * chunkSize;
            threads[i] = new Thread(new ThreadSum(array, start, end, i));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
