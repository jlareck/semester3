
import java.util.Date;
public class File implements Comparable<File>  {
    public String name;
    public int size;
    public Date createTime;
    public Date modificationTime;
    public String type;

    @Override
    public int compareTo(File value2) {
        if (this.size!=value2.size){
            if (this.size<value2.size) return 1;
            else
                return -1;
        }

        if (this.name!=value2.name){
            if (this.name.compareTo(value2.name)<0) return -1;
            else
                return 1;
        }

        return this.type.compareTo(value2.type);
//        if (!value1.createTime.equals(value2.createTime))
//            return value2.createTime.after(value1.createTime);
//        if (!value1.modificationTime.equals(value2.modificationTime))
//            return value2.modificationTime.after(value1.modificationTime);
//        return comparator(value1.type, value2.type);
    }
}