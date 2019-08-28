

import scala.annotation.tailrec


import scala.math.Ordering



sealed abstract class LinkedList[+E] {

  def size : Int

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

  def reverse(): LinkedList[E] = {
    foldLeft(LinkedList[E]()) {
      (acc, item) =>
        Node(item, acc)
    }
  }

  def foldRight[B](accumulator: B)(f: (E, B) => B): B = {
    reverse().foldLeft(accumulator)((acc, item) => f(item, acc))
  }

  def filter(f: (E) => Boolean): LinkedList[E] = {
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
}

final case class ::[E<:Ordering[E]](private var hd: E, private var tl: LinkedList[E]) extends LinkedList[E] {
  def head : E = hd
  def tail : LinkedList[E] = tl
  def isEmpty: Boolean = false
  val size = 1

}
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

  def split[E](n: Int, l: LinkedList[E]):(LinkedList[E], LinkedList[E]) = {
    def _split[E](c: Int, res: LinkedList[E], rem: LinkedList[E]):(LinkedList[E],LinkedList[E]) = (c, rem) match {
      case (_, Empty) => (res, Empty)
      case (0, rem) => (res, rem)
      case (c, h :: tail) => _split(c - 1, res:::LinkedList(h), tail)
    }
    _split(n, LinkedList(), l)
  }
  def merge[E](left: LinkedList[E], right: LinkedList[E]) (implicit org: Ordering[E]): LinkedList[E] =
    (left, right) match {
      case(left, Empty) => left
      case(Empty, right) => right
      case(leftHead :: leftTail, rightHead :: rightTail) =>
        if (org.compare(leftHead, rightHead)<0) leftHead::merge(leftTail, right)
        else rightHead :: merge(left, rightTail)
    }
//
  def mergeSort[E](list: LinkedList[E])(implicit org: Ordering[E]): LinkedList[E] = {
    val n = list.size / 2
    if (n == 0) list // i.e. if list is empty or single value, no sorting needed
    else {
      val (left, right) = split(n, list)
      merge(mergeSort(left), mergeSort(right))
    }
  }
  var a = LinkedList(3,25,5)
  a = a.::(2)
  var b = List(2,4,5)
  mergeSort(a)
}

