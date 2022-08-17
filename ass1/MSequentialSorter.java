package ass1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *MSequentialSorter goes through and splits a list in half.
 * It then sorts one half of the list before sorting the second half.
 * It is then able to take the first element of each list and check
 * which element is smaller (or larger) and merge the elements into one list.
 * A benefit of this is that it intentionally wont use additional processors when in use.
 * Also due to the nature of the merge fuction, the merge function cannot be made concurrent
 * This means that the other algorithms still rely on this to some degree.
 *While implementing this, I learnt about when using concurrency would be appropriate.
 */
public class MSequentialSorter implements Sorter {

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
    return mergeSort(list);
  }


  public <T extends Comparable<? super T>> List<T> mergeSort(List<T> list){
    if(list.size() <= 1) {
      return list;
    }
    int divider = list.size()/2;
    List<T> sectionOne = list.subList(0,divider);
    List<T> sectionTwo = list.subList(divider,list.size());
    sectionOne= mergeSort(sectionOne);
    sectionTwo = mergeSort(sectionTwo);

    return merge(sectionOne,sectionTwo);
  }


  public static <T extends Comparable<? super T>>  List<T> merge(List<T> list1, List<T> list2){
    if(list1.get(list1.size()-1).compareTo(list2.get(0) ) <= 0) {
      List<T> newerList = new ArrayList<>();
      newerList.addAll(list1);
      newerList.addAll(list2);
      return newerList;
    }
    if(list1.get(0).compareTo(list2.get(list2.size()-1) ) >= 0) {
      List<T> newerList = new ArrayList<>();
      newerList.addAll(list2);
      newerList.addAll(list1);
      return newerList;
    }

    List<T> newList = new ArrayList<>();
    int index1 =0;
    int index2 = 0;
    while(newList.size() < list1.size() + list2.size()){
      T elem1 = list1.get(index1);
      T elem2 = list2.get(index2);
      if(elem1.compareTo(elem2) <= 0) {
        newList.add(elem1);
        index1++;
        if (index1==list1.size()) {
          while(index2 < list2.size()){
            elem2 = list2.get(index2);
            newList.add(elem2);
            index2++;
          }
        }
      } else{
        newList.add(elem2);
        index2++;
        if (index2==list2.size()) {
          while(index1 < list1.size()){
            elem1 = list1.get(index1);
            newList.add(elem1);
            index1++;
          }
        }
      }
    }
    return newList;
  }




}