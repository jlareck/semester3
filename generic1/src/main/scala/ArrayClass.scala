
//TODO change in sbt file version on 13.0
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.Date
import scala.reflect._
import scala.math.Ordering
object ArrayClass extends App{
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

  def insertElement[T](elm: T, sorted: List[T])(implicit org: Ordering[T]): List[T] =
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



  //implicit val order = Ordering.by[FileClass, Int]
    val a = new FileClass("aa", 2, "ww", new SimpleDateFormat("d-M-y"))
    val c = new FileClass("bb", 3, "qq", new SimpleDateFormat("d-M-y"))
  val d = new FileClass("bb", 1, "qq", new SimpleDateFormat("d-M-y"))
  val q = new FileClass("bb", 2, "qe", new SimpleDateFormat("d-M-y"))
  val someVariable = new Folder("bb", 2, "qe")

    var b = Array(a,c,d, q, someVariable)
  var list = b.toList
  list = mergeSort(list)
  list.foreach(println)
   // implicit val org = Ordering[FileClass]
 // val clsTag = classTag[FileClass]
 // b = quickSort(b)(clsTag, FileClass.ordering)
    //b.foreach(println)
  //  println("b">"a")


    //var l = b.toList
 // l.foreach(print)
   // l = mergeSort(l)
 // l = insertionSort(l)

  //val r = l.toArray

  //r.foreach(println)
  //var a = Array(1,2,2,51,2,3,64,4)
//  a = sort(a)(clsTag, FileClass.ordering)

}
