import java.util.concurrent.LinkedBlockingDeque

import org.scalatest.FunSuite

import scala.reflect.ClassTag
class FileSystemTest extends FunSuite {


  /*
    I used 4 kinds of polymorphism. For standard types like int and string I used parametric polymorphism,
    for FileClass and Folder - subtypes of class Item, for data structures linked list, array and list - ad-hoc (overloading)
    and coercion polymorphism (implicit conversions). So if you want to sort array with merge sort or insertion sort
    it will convert array to list and sort it as list. Also if you want to sort list with quicksort
    it will implicitly convert it to array. But there is a problem that when I use for example function,
    that uses implicit conversion, I need to return back it to first state(condition), so there is solution,
    to handle it with boolean variable, but in unit tests I only checked if functions work. And I also used
    overloaded sorting functions because there is linked list - the structure that is not library and
    I can't use toList or toArray for it. You will also ask where is logic in parameter of type array in quicksort
    and list in others, the answer is - I don't know ¯\_(ツ)_/¯.
  */

  test("ArrayClass.sort") {
    //var flag: Boolean = false

    implicit def arrayToList[A](a: Array[A]) = a.toList

    implicit def listToArray[A: ClassTag](a: List[A]) = a.toArray

    val list1 = List(1, 221, 331, 35, 6, 21, 5)
    assert(ArrayClass.mergeSort(list1) === List(1, 5, 6, 21, 35, 221, 331))
    assert(ArrayClass.insertionSort(list1) === List(1, 5, 6, 21, 35, 221, 331))
    assert(ArrayClass.quickSort(list1.toArray) === Array(1, 5, 6, 21, 35, 221, 331))
    val list2 = Array("a", "s", "b", "r", "c","k") // можна було var

    assert(SortedAlgorithms.quickSort(list2)===Array("a", "b","c", "k", "r", "s" ))
//    if (flag){
//      list2 = list2.toArray
//    }
//    assert(ArrayClass.mergeSort(list2)===List("a", "b","c", "k", "r", "s" ))
//    assert(ArrayClass.quickSort(list2.toArray)===Array("a", "b","c", "k", "r", "s" ))

//    val objList1 = HelloWorld.recursiveInitializing(HelloWorld.input_files)
//
//    val objList2 = objList1
//    assert(ArrayClass.insertionSort(objList1)=== ArrayClass.quickSort(objList2.toArray).toList)
  }
  test("LinkedList"){
    val linkedList = LinkedList(1,5,6,21,35,221,331)
    assert(LinkedList.mergeSort(linkedList) === LinkedList(1,5,6,21,35,221,331))
    assert(LinkedList.insertionSort(linkedList) === LinkedList(1,5,6,21,35,221,331))
    assert(LinkedList.quickSort(linkedList) === LinkedList(1,5,6,21,35,221,331))



  }
}
