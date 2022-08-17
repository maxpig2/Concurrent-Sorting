package ass1;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Discussion about Pros and Cons of this this method of testing performance.
 * While it doesn't greatly improve the testing, if we didnt want to use System.currentTimeMillis() to get the performance
 * of the algorithms, it would be more accurate to use System.nanoTime().
 * Furthermore issues arrise with this method due to how unreliable it is on different computers/environments.
 * While it is able to give a rough idea of which algorithm is faster, it doesnt give a true representation on its performance.
 *This means that while testing and trying to make a method faster, it is difficult to gauge wether a program has increased the
 * performance or if it just executed faster than normal.
 */
public class TestPerformance {
  /**
   * This method performs the warmUp and calls r.run that many times in order to properly initiate JIT.
   * The method then gets the current time in milliseconds, it runs the test then gets the current time in milliseconds
   * again and finds the difference between the two times recorded inorder to naively calculate how long it took to run the test.
   * @param r
   * @param warmUp
   * @param runs
   * @return
   */
  long timeOf(Runnable r,int warmUp,int runs) {
    System.gc();
    for(int i=0;i<warmUp;i++) {r.run();}
    long time0=System.currentTimeMillis();
    for(int i=0;i<runs;i++) {r.run();}
    long time1=System.currentTimeMillis();
    return time1-time0;
  }

  /**
   *This method is given a Sorter in its parameter, e.g MSequentialSorter, MparallelSorter1/2/3.
   * It then calls sort on that sorter giving it an array of data converted into a list.
   * It finally prints out the name of the sorter which is also provided in the parameter and the amount of time it takes in
   * seconds to took to run. It converts the given time from milliseconds into seconds.
   * @param s
   * @param name
   * @param dataset
   * @param <T>
   */
  <T extends Comparable<? super T>>void msg(Sorter s,String name,T[][] dataset) {
    long time=timeOf(()->{
      for(T[]l:dataset){s.sort(Arrays.asList(l));}
      },20000,200);//realistically 20.000 to make the JIT do his job.. //Warmup originally 20000
      System.out.println(name+" sort takes "+time/1000d+" seconds");
    }

  /**
   *This method calls the above method, 'msg' 4 times, passing it the required parameters.
   * Including the Sorter, the name of the sorter and the dataset.
   * @param dataset
   * @param <T>
   */
  <T extends Comparable<? super T>>void msgAll(T[][] dataset) {
    //msg(new ISequentialSorter(),"Sequential insertion",TestBigInteger.dataset);//so slow
    //uncomment the former line to include performance of ISequentialSorter
    msg(new MSequentialSorter(),"Sequential merge sort",dataset);
    msg(new MParallelSorter1(),"Parallel merge sort (futures)",dataset);
    msg(new MParallelSorter2(),"Parallel merge sort (completablefutures)",dataset);
    msg(new MParallelSorter3(),"Parallel merge sort (forkJoin)",dataset);
    }
  @Test
  void testBigInteger() {
    System.out.println("On the data type BigInteger");
    msgAll(TestBigInteger.dataset);
    }
  @Test
  void testFloat() {
    System.out.println("On the data type Float");
    msgAll(TestFloat.dataset);
    }
  @Test
  void testPoint() {
    System.out.println("On the data type Point");
    msgAll(TestPoint.dataset);
    }
  @Test
  void testString() {
    System.out.println("On the data type String");
    msgAll(TestString.dataset);
  }
  }
/*
With the model solutions, on a lab machine (2019) we may get those results:
On the data type Float
Sequential merge sort sort takes 1.178 seconds
Parallel merge sort (futures) sort takes 0.609 seconds
Parallel merge sort (completablefutures) sort takes 0.403 seconds
Parallel merge sort (forkJoin) sort takes 0.363 seconds
On the data type Point
Sequential merge sort sort takes 1.373 seconds
Parallel merge sort (futures) sort takes 0.754 seconds
Parallel merge sort (completablefutures) sort takes 0.541 seconds
Parallel merge sort (forkJoin) sort takes 0.48 seconds
On the data type BigInteger
Sequential merge sort sort takes 1.339 seconds
Parallel merge sort (futures) sort takes 0.702 seconds
Parallel merge sort (completablefutures) sort takes 0.452 seconds
Parallel merge sort (forkJoin) sort takes 0.492 seconds

On another lab machine in 2021 we get those results:
//they must have optimized sequential execution quite a bit!
On the data type Float
Sequential merge sort sort takes 0.635 seconds
Parallel merge sort (futures) sort takes 0.475 seconds //with a smart trick we may get 0.241 instead
Parallel merge sort (completablefutures) sort takes 0.25 seconds
Parallel merge sort (forkJoin) sort takes 0.253 seconds
On the data type Point
Sequential merge sort sort takes 0.76 seconds //with a smart trick we may get 0.296 instead
Parallel merge sort (futures) sort takes 0.505 seconds
Parallel merge sort (completablefutures) sort takes 0.279 seconds
Parallel merge sort (forkJoin) sort takes 0.296 seconds
On the data type BigInteger
Sequential merge sort sort takes 0.871 seconds
Parallel merge sort (futures) sort takes 0.574 seconds //with a smart trick we may get 0.372 instead
Parallel merge sort (completablefutures) sort takes 0.354 seconds
Parallel merge sort (forkJoin) sort takes 0.338 seconds
 
*/