class Folder(var name: String,var localType: String, var modificationTime: String, var path: String, var folders: List[Folder], var files: List[MyFile]) extends Item

