package ass1;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestString {
  static Random rand=new Random(0);
  static public String[][] dataset={
    {"Wellington","Auckland","Taupo","Napier","Dunedin","Hamilton","Palmerston North"},
    {"Network","Engineering","Computer","Science","Software","VUW"},
    {"Spiderman","IronMan","Hulk","Thor","Batman","Superman","Black Panther","Thanos","Mysterio","Flash"},
    {},
    manyOrdered(10000),
    manyReverse(10000),
    manyRandom(10000)
  };

  static String randomstring() {
    StringBuilder builder=new StringBuilder();
    int len=rand.nextInt()%200;
    for(int i=0;i<len;i++) {
      builder.append((char) rand.nextInt()%256);
    }
    return builder.toString();
  }


  static private String[] manyRandom(int size) {
    Random r=new Random(0);
    String[] result=new String[size];
    for(int i=0;i<size;i++){result[i]=randomstring();}
    return result;
  }
  static private String[] manyReverse(int size) {
    String[] result=new String[size];
    for(int i=0;i<size;i++){result[i]=Integer.toString((size-i)*1000+ rand.nextInt()%1000);}
    return result;
  }
  static private String[] manyOrdered(int size) {
    String[] result=new String[size];
    for(int i=0;i<size;i++){result[i]=Integer.toString(i*1000+ rand.nextInt()%1000);}
    return result;
  }

  @Test
  public void testISequentialSorter() {
    Sorter s=new ISequentialSorter();
    for(String[]l:dataset){TestHelper.testData(l,s);}
  }
  @Test
  public void testMSequentialSorter() {
    Sorter s=new MSequentialSorter();
    for(String[]l:dataset){TestHelper.testData(l,s);}
  }
  @Test
  public void testMParallelSorter1() {
    Sorter s=new MParallelSorter1();
    for(String[]l:dataset){TestHelper.testData(l,s);}
  }
  @Test
  public void testMParallelSorter2() {
    Sorter s=new MParallelSorter2();
    for(String[]l:dataset){TestHelper.testData(l,s);}
  }
  @Test
  public void testMParallelSorter3() {
    Sorter s=new MParallelSorter3();
    for(String[]l:dataset){TestHelper.testData(l,s);}
  }
}
