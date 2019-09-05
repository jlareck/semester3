import java.util.Date
import  scala.math.Ordering
case class FileClass(var name:String, var size: Int, var localType: String)
object FileClass extends Ordering[FileClass]{
    def compare(x: FileClass, y: FileClass): Int = {
    if (x.size != y.size) if (x.size < y.size) return 1
    else return -1

    if (x.name != y.name) if (x.name.compareTo(y.name) < 0) return -1
    else return 1


    x.localType.compareTo(y.localType)


  }
  implicit def ordering[A <: FileClass]: Ordering[A] = Ordering.by(e => (e.size, e.name, e.localType))

}