public class FileSys {
    private String name;
    private FileSys parent;
    //private int fileSize;
    private char readable;
    private char writeable;
    private char executable;
    private char type;
    private String permissions;


    public FileSys() {
        name = "";
        type = 'd';
        parent = null;
        //fileSize = 0;
        readable = 'r';
        writeable ='w';
        executable = 'x';
        permissions = "" + type + readable + writeable + executable;
    }

    public FileSys(String name, char type) {
        this.name = name;
        this.type = type;
        if(type == 'd') {
            readable = 'r';
            writeable ='w';
            executable = 'x';
        }
        else {
            readable = 'r';
            writeable ='-';
            executable = '-';
        }

        parent = null;
        //fileSize = 0;
        permissions = "" + type + readable + writeable + executable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReadable(char readable) {
        this.readable = readable;
    }

    public void setWriteable(char writeable) {
        this.writeable = writeable;
    }

    public void setExecutable(char executable) {
        this.executable = executable;
    }

    public String getPermissions() {
        return permissions = "" + type + readable + writeable + executable;
    }

    /*
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileSize() {
        return fileSize;
    }

     */

    public void setType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }


    public void setParent(FileSys parent) {
        this.parent = parent;
    }

    public FileSys getParent() {
        return parent;
    }

    public boolean hasParent() {
        if(parent == null) {
            return false;
        }
        return true;
    }

    public String getPath() {
        FileSys temp = parent;
        StringBuilder pathstr = new StringBuilder("/" + name);

        while(temp != null) {
            pathstr.insert(0, "/" + temp.getName());
            temp = temp.parent;
        }
        //path = parent.getName() + "/" + name;
        return pathstr.toString();
    }
}
