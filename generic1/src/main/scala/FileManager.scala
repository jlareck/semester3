import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}

object FileManager{
  private val fs = FileSystem.get(new Configuration())
  private val dir: String = "/Users/mykolamedynsky/Desktop/personal documents"
  val input_files = fs.listStatus(new Path(dir))

  def recursiveInitializing(files : Array[FileStatus]): List[Item] ={
    val details  = files.map(m =>

      if (m.isDirectory)
         Folder(m.getPath.getName, m.getLen.toInt, "Folder", m.getPath.toString, recursiveInitializing(fs.listStatus(new Path(m.getPath.toString))))
      else
        FileClass(m.getPath.getName, m.getLen.toInt, "File", m.getModificationTime.toString, m.getPath.toString)
    ).toList
    details

  }

  def find(files: List[Item], size1: Int, size2: Int): List[Item]={
      files.filter( m => m.size < size1 && m.size > size2)
  }

 // var details = recursiveInitializing(input_files)

 // details = ArrayClass.insertionSort(details)
  //details.foreach(println)
//  details = ArrayClass.mergeSort(details)

  //println("t" > "m")
//  val files = details.toArray


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

