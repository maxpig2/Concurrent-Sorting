package ass1;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * The benefit of this algorithm is that using Completeable Futures means that the chances of blocking threads is
 * reduced. By using completable futures, if a function is taking too long instead of timing out, the completable future is
 * able to finish mid-way something that Futures dont offer. Furthermore Completable futures allow Threads to be combined
 * another thing that Futures dont offer.
 * While the performance is dependent on the environment, my implementation is typically faster than
 * MParallelSorter1 and MSequentialSorter.
 * While implementing this, I learnt more about the differences between Comparable Futures and Futures.
 */


public class MParallelSorter2 extends MSequentialSorter implements Sorter {

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
      if (list.size()<20) {
          return super.sort(list);
      }
      int divider = list.size()/2;
      List<T> sectionOne = list.subList(0,divider);
      List<T> sectionTwo = list.subList(divider,list.size());
      CompletableFuture<List <T>> taskOne = CompletableFuture.supplyAsync(()->sort(sectionOne));
      CompletableFuture<List <T>> taskTwo = CompletableFuture.supplyAsync(()->sort(sectionTwo));
      CompletableFuture<List <T>> taskThree = taskOne.thenCombineAsync(taskTwo,(a,b)->merge(a,b));
      return (taskThree.join());
    }

}