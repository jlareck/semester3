import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}

object HelloWorld extends App {
  val fs = FileSystem.get(new Configuration())
  val dir: String = "/Users/mykolamedynsky/Desktop/personal documents"
  val input_files = fs.listStatus(new Path(dir))

  def recursiveInitializing(files : Array[FileStatus]): List[Item] ={
    val details  = files.map(m =>

      if (m.isDirectory)
        new Folder(m.getPath.getName, m.getLen.toInt, "Folder", m.getPath.toString, recursiveInitializing(fs.listStatus(new Path(m.getPath.toString))))
      else
        new FileClass(m.getPath.getName, m.getLen.toInt, "File", m.getModificationTime.toString, m.getPath.toString)
    ).toList
    details

  }

  val details = recursiveInitializing(input_files)
  println(details)
//  val details  = input_files.map(m =>
//    if(m.isFile)
//      new FileClass(m.getPath.getName, m.getLen.toInt, "File", m.getModificationTime.toString, m.getPath.toString)
//    else if (m.isDirectory)
//      new Folder(m.getPath.getName, m.getLen.toInt, "Folder", m.getPath.toString,fs.listStatus(new Path(m.getPath.toString))
//
//
//      ).toList
 // details.foreach(println)
}

    //  var a = new FileClass("aa", 1, "ww")
    //  va b = LinkedList(a)
    //   val c = new FileClass("bb", 2, "qq")
    //  b = b.::c
   // var a = LinkedList(3,25,5,8,0,-1,8)
   // a = a.::(2)
    // //print(a.head1)
    //  //a.someTest
    //  var b = List(2,4,5)
    //  //print(a)
    // print(mergeSort(a))

     // val a = new FileClass("aa", 2, "ww")
//      val c = new FileClass("bb", 3, "qq")
//      var b = LinkedList(a,c)
//
//      b = b.::(new FileClass("bb", 2, "qq"))
//    val l = LinkedList.insertionSort(b)
//    l.foreach(print)
   // print(quickSort(a))
  //  print(a);


    //println("a"<"b")
