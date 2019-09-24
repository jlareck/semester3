import scala.math.Ordering
import scala.reflect.ClassTag

object SortedAlgorithms {


  def quickSort[E: ClassTag](xs: Array[E])(implicit org: Ordering[E]): Array[E] = {
    if (xs.length <= 1) xs
    else {
      val pivot = xs(xs.length / 2)
      Array.concat(
        quickSort(xs.filter(org.compare(pivot,_)>0)),
        xs.filter(org.compare(pivot,_)==0),
        quickSort(xs.filter(org.compare(pivot,_)<0)))
    }
  }


  def mergeSort[E](xs: List[E])(implicit org: Ordering[E]): List[E] = {
    def merge(xs1: List[E], xs2: List[E]): List[E] = (xs1, xs2) match {
      case(_, Nil) => xs1
      case(Nil,_) => xs2
      case (x1 :: xtl1, x2 :: xtl2) =>  if (org.compare(x1, x2)<0)  x1  :: merge(xtl1, xs2) else x2 :: merge(xs1, xtl2)

    }
    val n = xs.length/2
    if (n == 0) xs
    else merge(mergeSort(xs take n), mergeSort(xs drop n))

  }

  def mergeSort[E](xs: LinkedList[E])(implicit org: Ordering[E]): LinkedList[E] = {
    def merge(xs1: LinkedList[E], xs2: LinkedList[E]): LinkedList[E] = {
      if (xs1.size == 0) xs2
      else if (xs2.size == 0) xs1
      else if (org.compare(xs1.head, xs2.head)<0) xs1.head :: merge(xs1.tail, xs2)
      else xs2.head :: merge(xs1, xs2.tail)
    }

    val n = xs.size/2
    if (n == 0) xs
    else merge(mergeSort(xs take n), mergeSort(xs drop n))
  }
  def insertionSort[E](xs: LinkedList[E])(implicit org: Ordering[E]): LinkedList[E] = {
    if(xs.size == 0) LinkedList()
    else  insert(xs.head, insertionSort(xs.tail))
  }

 private def insert[E](x: E, xs: LinkedList[E])(implicit org: Ordering[E]): LinkedList[E] =  {
    if(xs.size==0)  LinkedList(x)
    else{
      if (org.compare(x, xs.head)<0 ) x :: xs else xs.head :: insert(x, xs.tail)
    }
  }
 private def insertElement[T](elm: T, sorted: List[T])(implicit org: Ordering[T]): List[T] =
    sorted match {
      case Nil => elm :: sorted
      case head :: tail if org.compare(head, elm) <0 => head :: insertElement(elm, tail)
      case _ => elm :: sorted
    }

  def insertionSort[T](list: List[T])(implicit org: Ordering[T]): List[T] =
    list match {
      case Nil => list
      case head :: tail =>
        val sorted = insertionSort(tail)
        insertElement(head, sorted)
    }
  def quickSort[E](list: LinkedList[E])(implicit org: Ordering[E]): LinkedList[E] = {

    if (list.size==0)  Empty
    else if (list.size == 1)  LinkedList(list.head)
    else  quickSort(list.tail.filter(x=> org.compare(x, list.head)<=0 )) ::: LinkedList(list.head) ::: quickSort(list.tail.filter(x => org.compare(x, list.head)>0))

  }



}
