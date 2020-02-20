public class Mkdir extends File {

    public Mkdir() {
        super();
        super.setType('-');
        super.setReadable('r');
        super.setWriteable('-');
        super.setExecutable('x');
        super.setName("mkdir");
    }

    public Folder execute(String folderName, Folder root) {
        if(!folderName.equalsIgnoreCase("")) {
            Folder newFolder = new Folder(folderName);
            newFolder.setParent(root);
            root.addItem(newFolder);
        }

        return root;
    }
}
