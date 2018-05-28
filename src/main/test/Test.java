import java.util.concurrent.*;

/**
 * Created by test on 2017/7/22.
 */
public class Test {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(500000);
                return "zhangsan";
            }
        });
        try {
            String s = null;
            s = future.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    synchronized static void get(){

    }
    synchronized void test(){

    }


}
