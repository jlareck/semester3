
import scala.annotation.tailrec
import scala.annotation.unchecked.uncheckedVariance
import scala.collection.immutable.List
import scala.math.Ordering
import scala.runtime.Statics.releaseFence
import scala.sys.process.processInternal


sealed abstract class LinkedList[+E] {

  def size : Int
  def head: E
  def tail: LinkedList[E]
  def foreach(f : (E) => Unit) {
    @tailrec def loop( items : LinkedList[E] ) {
      items match {
        case Node(head,tail) => {
          f(head)
          loop(tail)
        }
        case Empty => {}
      }
    }

    loop(this)
  }

  def ::[B >: E](element : B) : LinkedList[B] = Node(element, this)

  def :::[B >: E](prefix : LinkedList[B]) : LinkedList[B] = {

    @tailrec def helper(acc : LinkedList[B], other : LinkedList[B]) : LinkedList[B] = {
      other match {
        case Node(head,tail) => helper(head :: acc, tail)
        case Empty => acc
      }
    }

    helper(this, prefix.reverse())
  }

  def map[R](f: E => R): LinkedList[R] = foldRight(LinkedList[R]()) {
    (item, acc) =>
      Node(f(item), acc)
  }



  def take(n: Int): LinkedList[E] =
    if (n == 0 || size==0) Empty else head :: tail.take(n-1)
  def drop(n: Int): LinkedList[E] =
    if (n == 0 || size == 0) this else tail.drop(n-1)



  def reverse(): LinkedList[E] = {
    foldLeft(LinkedList[E]()) {
      (acc, item) =>
        Node(item, acc)
    }
  }

  def foldRight[B](accumulator: B)(f: (E, B) => B): B = {
    reverse().foldLeft(accumulator)((acc, item) => f(item, acc))
  }

  def filter(f: E => Boolean): LinkedList[E] = {
    foldRight(LinkedList[E]()) {
      (item, acc) =>
        if (f(item)) {
          Node(item, acc)
        } else {
          acc
        }
    }
  }

  @tailrec final def foldLeft[B](accumulator: B)(f: (B, E) => B): B = {
    this match {
      case Node(head, tail) => {
        val current = f(accumulator, head)
        tail.foldLeft(current)(f)
      }
      case Empty => accumulator
    }
  }

  @tailrec final def find( p : (E) => Boolean  ) : Option[E] = {
    this match {
      case Node( head, tail ) => {
        if ( p(head) ) {
          Some(head)
        } else {
          tail.find(p)
        }
      }
      case Empty => None
    }
  }

}
case class Node[E](head: E, tail: LinkedList[E]) extends LinkedList[E]{
  val size = 1 + tail.size
}

case object Empty extends LinkedList[Nothing]{
  val size = 0
  override def head: Nothing = throw new NoSuchElementException("head of empty list")
  override def tail: LinkedList[Nothing] = throw new UnsupportedOperationException("tail of empty list")
}

//final case class :: [E]( private var hd: E, private var tl: LinkedList[E]) extends LinkedList[E] {
//  releaseFence()
//
//  override def size = 1
//
//  override def head : E = hd
//  override def tail : LinkedList[E] = tl
//}



//object :: {
//  def unapply[E](ls: LinkedList[E]): Option[(E, E)] = {
//    if (ls.size == 0) None
//    else Some(ls.head1, ls.tail1)
//  }
//}
//final case class ::[E<:Ordering[E]](private var hd: E, private var tl: LinkedList[E]) extends LinkedList[E] {
//  def head : E = hd
//  def tail : LinkedList[E] = tl
//  def isEmpty: Boolean = false
//  val size = 1
//  releaseFence()
//
//}
object LinkedList extends App{

  def apply[E](items: E*): LinkedList[E] = {
    if (items.isEmpty) {
      Empty
    } else {
      Node(items.head, apply(items.tail: _*))
    }
  }

  def sum(numbers: LinkedList[Int]): Int = {
    numbers match {
      case Node(head, tail) => head + sum(tail)
      case Empty => 0
    }
  }

  def join(numbers: LinkedList[String]): String = {
    numbers match {
      case Node(head, tail) => head + join(tail)
      case Empty => ""
    }
  }

//  def split[A](n: Int, l: List[A]):(List[A], List[A]) = {
//    def _split[A](c: Int, res: List[A], rem: List[A]):(List[A],List[A]) = (c, rem) match {
//      case (_, Nil) => (res, Nil)
//      case (0, rem) => (res, rem)
//      case (c, h::tail) => _split(c - 1, res:::List(h), tail)
//    }
//    _split(n, List(), l)
//  }
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
    else  insert(xs.head, insertionSort(xs.tail))(org)
  }

  def insert[E](x: E, xs: LinkedList[E])(implicit org: Ordering[E]): LinkedList[E] =  {
    if(xs.size==0)  LinkedList(x)
  else{
      if (org.compare(x, xs.head)<0 ) x :: xs else xs.head :: insert(x, xs.tail)
    }
  }

  def quickSort[E](list: LinkedList[E])(implicit org: Ordering[E]): LinkedList[E] = {

      if (list.size==0)  Empty
      else if (list.size == 1)  LinkedList(list.head)
      else  quickSort(list.tail.filter(x=> org.compare(x, list.head)<=0 )) ::: LinkedList(list.head) ::: quickSort(list.tail.filter(x => org.compare(x, list.head)>0))

  }

  //  def split[E](n: Int, l: LinkedList[E]):(LinkedList[E], LinkedList[E]) = {
//    def _split[E](c: Int, res: LinkedList[E], rem: LinkedList[E]):(LinkedList[E],LinkedList[E]) = (c, rem) match {
//      case (_, Empty) => (res, Empty)
//      case (0, rem) => (res, rem)
//      case (c, h :: tail) => _split(c - 1, res:::LinkedList(h), tail)
//    }
//    _split(n, LinkedList(), l)
//  }
//  def merge[E](left: LinkedList[E], right: LinkedList[E]) (implicit org: Ordering[E]): LinkedList[E] =
//    (left, right) match {
//      case(left, Empty) => left
//      case(Empty, right) => right
//      case(leftHead :: leftTail, rightHead :: rightTail) =>
//        if (org.compare(leftHead, rightHead)<0) leftHead::merge(leftTail, right)
//        else rightHead :: merge(left, rightTail)
//    }
////
//  def mergeSort[E](list: LinkedList[E])(implicit org: Ordering[E]): LinkedList[E] = {
//    val n = list.size / 2
//    if (n == 0) list // i.e. if list is empty or single value, no sorting needed
//    else {
//      val (left, right) = split(n, list)
//      merge(mergeSort(left), mergeSort(right))
//    }
//  }
//  var a = new FileClass("aa", 1, "ww")
//  va b = LinkedList(a)
//   val c = new FileClass("bb", 2, "qq")
//  b = b.::c
  var a = LinkedList(3,25,5,8,0,-1,8)
  a = a.::(2)
// //print(a.head1)
//  //a.someTest
//  var b = List(2,4,5)
//  //print(a)
// print(mergeSort(a))

//  val a = new FileClass("aa", 2, "ww")
//  val c = new FileClass("bb", 3, "qq")
//  var b = LinkedList(a,c)
//
//  b = b.::(new FileClass("bb", 2, "qq"))
//  print(insertionSort(b)(FileClass))
  print(quickSort(a))

  //println("a"<"b")
}

