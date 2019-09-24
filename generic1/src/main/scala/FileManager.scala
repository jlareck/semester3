import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


import scala.collection.mutable.Queue
object FileManager{


  def init(path: String): Folder ={
    val fs = FileSystem.get(new Configuration())
    val dir = new Path(path)
    val input_files = fs.listStatus(dir)
    val formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
    val currentDate = formatDate.format(Calendar.getInstance().getTime())


    val folder: Folder = new Folder(dir.getName, "Folder", dir.toString, currentDate, List[Folder](),List[MyFile]() )
     def recursiveInitializingFolders(files: Array[FileStatus], folders: Folder): List[Folder] ={
      files.foreach(m =>

        if (m.isDirectory){
          val formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
          val date: Date = new Date(m.getModificationTime)
          val dateInFormat = formatDate.format(date)

          val folder1: Folder = new Folder(m.getPath.getName, "Folder", m.getPath.toString, dateInFormat, List[Folder](), List[MyFile]())
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
  private def isMaskEqualToString(str: String, mask: String): Boolean = {
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
  def findByMask(files: Folder, mask: String): List[MyFile]={
      var list = List[MyFile]()
      var queue: Queue[Folder] = Queue()
      var currentFolder = files
      do{

          currentFolder.files.foreach(m=> if (isMaskEqualToString(m.name, mask)) list = m::list)
          currentFolder.folders.foreach(m => queue += m)
          currentFolder = queue.dequeue


      }while(!queue.isEmpty)
      list
  }

}

