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
    I can't use toList or toArray for it.

    You will also ask where is logic in parameter of data structure array in quicksort
    and list in others, the answer is - I don't know ¯\_(ツ)_/¯.
  */

  test("Array and list sorting") {
    //var flag: Boolean = false

    implicit def arrayToList[A](a: Array[A]) = a.toList

    implicit def listToArray[A: ClassTag](l: List[A]) = l.toArray

    val array = Array(1, 221, 331, 35, 6, 21, 5)
    assert(SortedAlgorithms.mergeSort(array) === Array(1, 5, 6, 21, 35, 221, 331))//implicit conversion firstly from array to list and then if expected data structure is not list - from list to array
    assert(SortedAlgorithms.insertionSort(array) === List(1, 5, 6, 21, 35, 221, 331))
    assert(SortedAlgorithms.quickSort(array) === Array(1, 5, 6, 21, 35, 221, 331))

    val list = List("a", "s", "b", "r", "c","k")
    assert(SortedAlgorithms.quickSort(list)===List("a", "b","c", "k", "r", "s" ))
    assert(SortedAlgorithms.mergeSort(list)===List("a", "b","c", "k", "r", "s" ))
    assert(SortedAlgorithms.insertionSort(list)===List("a", "b","c", "k", "r", "s" ))

    val obj1 = FileManager.init("/Users/mykolamedynsky/Desktop/personal documents") // there you need to write a path to the directory where you want get data
    val obj2 = obj1
    assert(SortedAlgorithms.insertionSort(obj1.files) === SortedAlgorithms.quickSort(obj2.files))
    //if you also use function findByMask in debuger you will see that it works, but I don't know how to check it
    //on unit tests
    //val a = FileManager.findByMask(obj1, "sal*")
  }
  test("LinkedList"){
    val linkedList = LinkedList(1,5,6,21,35,221,331)
    assert(SortedAlgorithms.mergeSort(linkedList) === LinkedList(1,5,6,21,35,221,331))
    assert(SortedAlgorithms.insertionSort(linkedList) === LinkedList(1,5,6,21,35,221,331))
    assert(SortedAlgorithms.quickSort(linkedList) === LinkedList(1,5,6,21,35,221,331))
    val linkedList2 = LinkedList("a", "s", "b", "r", "c","k")
    assert(SortedAlgorithms.quickSort(linkedList2)===LinkedList("a", "b","c", "k", "r", "s" ))
    assert(SortedAlgorithms.mergeSort(linkedList2)===LinkedList("a", "b","c", "k", "r", "s" ))
    assert(SortedAlgorithms.insertionSort(linkedList2)===LinkedList("a", "b","c", "k", "r", "s" ))


  }
}
