import scala.math.Ordering

trait Item{
    var name:String
    var size: Int
    var localType: String
}
object Item extends Ordering[Item]{
    def compare(x: Item, y: Item): Int = {
        if (x.size != y.size) if (x.size < y.size) return 1
        else return -1

        if (x.name != y.name) if (x.name.compareTo(y.name) < 0) return -1
        else return 1


        x.localType.compareTo(y.localType)


    }
    implicit def ordering[A <: Item]: Ordering[A] = Ordering.by(e => (e.size, e.name, e.localType))
}