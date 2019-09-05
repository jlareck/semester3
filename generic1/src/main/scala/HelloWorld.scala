import LinkedList.quickSort

object HelloWorld extends App {

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