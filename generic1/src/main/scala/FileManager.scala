import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

import scala.annotation.tailrec
//import scala.util.matching.Regex
import scala.collection.mutable.Queue
object FileManager extends App{
  private val fs = FileSystem.get(new Configuration())
  private val dir = new Path("/Users/mykolamedynsky/Desktop/personal documents") //TODO change directory for finding exe file/ user
  val input_files = fs.listStatus(dir)

  def init(): Folder ={
    val formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
    val currentDate = formatDate.format(Calendar.getInstance().getTime())
    var listOfFiles = List[MyFile]()
    var listOfFolders = List[Folder]()
    val folder: Folder = new Folder(dir.getName, "Folder", dir.toString, currentDate, listOfFolders, listOfFiles)
     def recursiveInitializingFolders(files: Array[FileStatus], folders: Folder): List[Folder] ={
      files.foreach(m =>

        if (m.isDirectory){
          val formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
          val date: Date = new Date(m.getModificationTime)
          val dateInFormat = formatDate.format(date)
        //  var listOfFile = List[MyFile]()
        val listOfFiles1 = List[MyFile]()
          val listOfFolders1 = List[Folder]()
        val folder1: Folder = new Folder(m.getPath.getName, "Folder", m.getPath.toString, dateInFormat, listOfFolders1, listOfFiles1)
           val a: Folder = new Folder(m.getPath.getName, "Folder", m.getPath.toString, dateInFormat,
            recursiveInitializingFolders(fs.listStatus(new Path(m.getPath.toString)), folder1),  recursiveInitializingFiles(fs.listStatus(new Path(m.getPath.toString)), folder1))
          folders.folders = a :: folders.folders

        }
      )
      folders.folders
    }
    def recursiveInitializingFiles(files: Array[FileStatus], folders: Folder): List[MyFile] = {
      files.foreach(m=>

      if (!m.isDirectory) {

        val formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date: Date = new Date(m.getModificationTime)
        val dateInFormat = formatDate.format(date)

      // folders.folders
        val a = new MyFile(m.getPath.getName, m.getLen.toInt, "File",dateInFormat, m.getPath.toString)
        folders.files = a::folders.files
      }
      )
      folders.files
    }

     val newFolder: Folder = new Folder(dir.getName, "Folder", dir.toString, currentDate, recursiveInitializingFolders(input_files, folder), recursiveInitializingFiles(input_files,folder))
      newFolder
  }
  def isMaskEqualToString(str: String, mask: String): Boolean = {
    if (mask.size == 0) return str.isEmpty
    var i = 0
    var j = 0
    var count1 = -1
    var count2 = -1
    while (i < str.size) {
    if (j < mask.size &&  str(i) == mask(j)) {
      i += 1
      j += 1
    }
    else if (j < mask.size && (mask(j) == '?')) {
      i += 1
      j += 1
    }
    else if (j < mask.size && (mask(j) == '*')) {
      count1 = i
      count2 = j
      j += 1
    }
    else if (count2 != -1) {
      j = count2 + 1
      i = count1 + 1
      count1 += 1
    }
    else
      return false
  }
    while (j < mask.size && (mask(j) == '*'))
      j += 1

    if (j == mask.size) return true

    return false
  }
//  def findByMask(files: List[Item], mask: String): List[Item]={
//      var list = List[Item]()
//      var queue: Queue[Folder] = Queue()
//      do{
//        files.foreach(m => if(m.localType == "File" && isMaskEqualToString(m.name, mask)) list = m :: list  else if (m.localType == "Folder") queue += m )
//        files
//      }while(queue.isEmpty)
//
//  }
//  def findByMask(files: LinkedList[Item], name: String): LinkedList[Item]={
//    files.filter(m => isMaskEqualToString(m.name, name))
//  }



 // var details = recursiveInitializing(input_files)

 // details = ArrayClass.insertionSort(details)
//  details.foreach(println)
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

