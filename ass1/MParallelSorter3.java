package ass1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * The Fork/Join algorithm is (on my computer) consistently faster than the other implemented algorithms.
 *The Fork Join algorithm is a form of ExecutorService. The result of the algorithm is a Future.
 * While implementing this algorithm I learnt more about the ForkJoin pool and the RecursiveTask
 * such as when to call invoke/invokeAll.
 */
public class MParallelSorter3 extends MSequentialSorter implements Sorter {


  class Fork <T extends Comparable<? super T>> extends RecursiveTask{

    List<T> list;
    public Fork (List<T> l) {
      list = l;
    }

    @Override
    protected List<T> compute() {
      if (list.size()<20) {
        return new MSequentialSorter().sort(list);
      }
      int divider = list.size()/2;
      Fork<T> fork1 = new Fork(list.subList(0,divider));
      Fork<T> fork2 = new Fork(list.subList(divider,list.size()));
      invokeAll(fork1,fork2);
      return MParallelSorter3.super.merge((List<T>)fork1.join(),(List<T>) fork2.join());
    }
  }


  static ForkJoinPool pool = ForkJoinPool.commonPool();

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
    Fork f = new Fork(list);
   return (List<T>) pool.invoke(f);
  }


}