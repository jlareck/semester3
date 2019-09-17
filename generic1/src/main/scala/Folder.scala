import scala.math.Ordering

class Folder(var name:String, var size: Int, var localType: String) extends Item
//object Folder extends Ordering[Folder]{
//  def compare(x: Folder, y: Folder): Int = {
//    if (x.size != y.size) if (x.size < y.size) return 1
//    else return -1
//
//    if (x.name != y.name) if (x.name.compareTo(y.name) < 0) return -1
//    else return 1
//
//
//    x.localType.compareTo(y.localType)
//
//
//  }
//  implicit def ordering[A <: Folder]: Ordering[A] = Ordering.by(e => (e.size, e.name, e.localType))
//
//}
