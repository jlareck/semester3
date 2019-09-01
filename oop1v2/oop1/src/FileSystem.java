public class FileSystem {
    public static void main(String argv[]) {
        var fileSystem = new Node<File>();

        File file1 = new File();
        file1.type = "fda";
        file1.size = 12;
        file1.name = "fsa";

        System.out.println(file1);

        File file2 = new File();
        file2.type = "fga";
        file2.size = 12;
        file2.name = "fsaaa";

        System.out.println(file2);

        File file3 = new File();
        file3.type = "fqda";
        file3.size = 12;
        file3.name = "xcaa";

        System.out.println(file3);

        fileSystem.push(file1);
        fileSystem.push(file2);
        fileSystem.push(file3);
//        fileSystem.push(10.5);
//        fileSystem.push(1.3);
//        fileSystem.push(1220.0);
//        fileSystem.push(140.3);
//        fileSystem.push(105.1);
        fileSystem.head = fileSystem.mergeSort(fileSystem.head);
        fileSystem.printList(fileSystem.head);
    }
}
