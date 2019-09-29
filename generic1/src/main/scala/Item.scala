import scala.math.Ordering

trait Item{
    var name:String
    var modificationTime: String
    var localType: String
    var path: String
}
object Item extends Ordering[Item]{
    def compare(x: Item, y: Item): Int = {
        if (x.name != y.name) if (x.name.compareTo(y.name) < 0) return -1
        else return 1

        if (x.modificationTime != y.modificationTime) if (x.modificationTime < y.modificationTime) return 1
        else return -1

        x.localType.compareTo(y.localType)
    }
    implicit def ordering[A <: Item]: Ordering[A] = Ordering.by(e => (e.name, e.modificationTime, e.localType))
}