public class Folder extends FileSys {
    private FileSys[] folderContent;

    public Folder() {
        super();
        folderContent = new FileSys[10];
        //super
    }

    public Folder(String name) {
        super(name, 'd');
        folderContent = new FileSys[10];
    }

    public void addItem(FileSys item) {
        int i = nextNull();
        if(i < 0) {
            System.out.println("cannot add item");
        }
        else {
            //System.out.println(i);
            folderContent[i] = item;
        }
    }

    public void delItem(String name) {
        int index = findItem(name);

        if(index < 0) {
            System.out.printf("[%s] doesn't exist.\n", name);
        }
        else {
            folderContent[index] = null;
            //folderContent[index].e
            System.out.printf("[%s] deleted.\n", name);
        }
    }

    public FileSys[] getFolderContent() {
        return folderContent;
    }

    public boolean isEmpty() {
        for(int i = 0; i < folderContent.length; i++) {
            if(folderContent[i] != null) {
                return false;
            }
        }

        return true;
    }

    public File getFile(String name, Folder rootdir) {
        File item = null;
        int index = findFile(name);
        if(index < 0) {
            System.out.printf("[%s] file doesn't exist.\n", name);
        }
        else {
            item = (File) folderContent[index];
        }

        return item;
    }

    public int findFile(String name) {
        //System.out.println(isEmpty());
        if(isEmpty()) {
            return -1;
        }
        //System.out.println(name);

        for(int i = 0; i < folderContent.length; i++) {
            if(folderContent[i].getType() == '-' && folderContent[i].getName().equalsIgnoreCase(name)) {
                return i;
            }
            else if(folderContent[i].getType() == 'd' && folderContent[i].getName().equalsIgnoreCase(name)) {
                return -1;
            }
        }
        return -1;
    }

    public int findItem(String name) {
        //System.out.println(isEmpty());
        if(isEmpty()) {
            return -1;
        }

        for(int i = 0; i < folderContent.length; i++) {
            if(folderContent[i] != null && folderContent[i].getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    private int nextNull() {
        for(int i = 0; i < folderContent.length; i++) {
            if(folderContent[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public boolean findPath(Folder tempRoot, String path) {
        //get num of folders, boolean array mark true num times
        // if true times = num, path exists return true;
        // otherwise, false;
        while(tempRoot.getParent() != null) {
            tempRoot = (Folder) tempRoot.getParent();
        }

        String[] arrayStr = path.split(" ");
        //System.out.println(arrayStr.length);
        Boolean[] folderExists = new Boolean[arrayStr.length];
        for(int i = 0; i < arrayStr.length; i++) {
            int index = tempRoot.findItem(arrayStr[i]);
            //System.out.printf(" '%s' \n", arrayStr[i]);
            if(index >= 0) {
                folderExists[i] = true;
                //System.out.printf(" '%s' \n", arrayStr[i]);
                tempRoot = (Folder) tempRoot.getFolderContent()[index];
            }
            else {
                folderExists[i] = false;
            }
        }

        for(int i = 0; i < folderExists.length; i++) {
            //System.out.println(folderExists[i]);
            if(!folderExists[i]) {
                return false;
            }
        }

        return true;
    }
}
