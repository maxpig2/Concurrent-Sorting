package ass1;

import java.util.List;
import java.util.concurrent.*;

/**Using Futures means that we are able to divide the tasks in half with.
 * Instead of sorting the first half and then sorting the second half we are
 * able to do this simultaneously. You are also able to specify the pool type,
 * in this case we are using a Work Stealing pool. Though others such as fixed or
 * cached work pools are also available.
 *While implementing this algorithm I learnt/became more comfortable with the basics of futures
 * and the impact on performance that different pools can have.
 */
public class MParallelSorter1 extends MSequentialSorter implements Sorter {

  static ExecutorService executor = Executors.newWorkStealingPool();

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
    if (list.size()<20) {
      return super.sort(list);
    }
    int divider = list.size()/2;
    List<T> sectionOne = list.subList(0,divider);
    List<T> sectionTwo = list.subList(divider,list.size());
    Future<List <T>> taskOne = executor.submit(()->sort(sectionOne));
    Future<List <T>> taskTwo = executor.submit(()->sort(sectionTwo));
    try{
      return super.merge(taskOne.get(),taskTwo.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    
    return null;
  }

}