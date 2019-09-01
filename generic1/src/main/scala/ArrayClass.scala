object ArrayClass extends App{
  def sort[E](xs: Array[E])(implicit org:Ordering[E]): Array[E] = {
    if (xs.length <= 1) xs
    else {
      val pivot = xs(xs.length / 2)
      Array.concat(
        sort(xs filter (org.compare(_, pivot)<0)),
        xs filter (pivot ==),
        sort(xs filter( org.compare(_, pivot)>0)))
    }
    }
    val a = Array(1,2,4,5)
    print(sort(a))
}
