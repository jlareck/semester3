import java.util.concurrent.LinkedBlockingDeque

import org.scalatest.FunSuite

import scala.reflect.ClassTag
class FileSystemTest extends FunSuite {


  /*
    I used 4 kinds of polymorphism. For standard types like int and string I used parametric polymorphism,
    for FileClass and Folder - subtypes of class Item, for data structures linked list, array and list - ad-hoc (overloading)
    and coercion polymorphism (implicit conversions). So if you want to sort array with merge sort or insertion sort
    it will convert array to list and sort it as list. Also if you want to sort list with quicksort
    it will implicitly convert it to array. And I also used
    overloaded sorting functions because there is linked list - the structure that is not library and
    I can't use toList or toArray for it. You will also ask where is logic in parameter of type array in quicksort
    and list in others, the answer is - I don't know ¯\_(ツ)_/¯.
  */

  test("Array and list sorting") {
    //var flag: Boolean = false

    implicit def arrayToList[A](a: Array[A]) = a.toList

    implicit def listToArray[A: ClassTag](a: List[A]) = a.toArray

    val array = Array(1, 221, 331, 35, 6, 21, 5)
    assert(SortedAlgorithms.mergeSort(array) === List(1, 5, 6, 21, 35, 221, 331))
    assert(SortedAlgorithms.insertionSort(array) === List(1, 5, 6, 21, 35, 221, 331))
    assert(SortedAlgorithms.quickSort(array) === Array(1, 5, 6, 21, 35, 221, 331))

    val list = List("a", "s", "b", "r", "c","k")
    assert(SortedAlgorithms.quickSort(list)===List("a", "b","c", "k", "r", "s" ))
    assert(SortedAlgorithms.mergeSort(list)===List("a", "b","c", "k", "r", "s" ))
    assert(SortedAlgorithms.insertionSort(list)===List("a", "b","c", "k", "r", "s" ))


    val objList1 = FileManager.recursiveInitializing(FileManager.input_files)
    val objList2 = objList1
    assert(SortedAlgorithms.mergeSort(objList1) === SortedAlgorithms.insertionSort(objList2))
    assert(SortedAlgorithms.quickSort(objList1) === SortedAlgorithms.insertionSort(objList2))


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
    assert(SortedAlgorithms.mergeSort(linkedList) === LinkedList(1,5,6,21,35,221,331))
    assert(SortedAlgorithms.insertionSort(linkedList) === LinkedList(1,5,6,21,35,221,331))
    assert(SortedAlgorithms.quickSort(linkedList) === LinkedList(1,5,6,21,35,221,331))



  }
}
