
import scala.annotation.tailrec

import scala.math.Ordering



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

object LinkedList{

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



}

