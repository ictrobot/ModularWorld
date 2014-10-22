package ethanjones.modularworld.core.system;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class Threads {

  private static ScheduledThreadPoolExecutor executor;

  public static void init() {
    executor = new ScheduledThreadPoolExecutor(8, new ThreadFactory() {
      int threads = 0;

      public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName("Executor-" + threads++);
        t.setDaemon(true);
        return t;
      }
    });
  }

  public static synchronized Future execute(Callable<?> task) {
    return executor.submit(task);
  }

  public static synchronized void disposeExecutor() {
    executor.shutdownNow();
  }
}