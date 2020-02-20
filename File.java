public class File extends FileSys {
    private String[] fileData;
    private String fileType;
    private int MAX_DATA = 100;
    private int currentfileDataNum = 0;

    public File() {
        super();
        super.setType('-');
        super.setReadable('r');
        super.setWriteable('-');
        super.setExecutable('-');
        fileType = "txt";
        fileData = new String[MAX_DATA];
    }

    public File(String name, String fileData) {
        super(name, '-');
        addData(fileData);
        super.setReadable('r');
        super.setWriteable('-');
        super.setExecutable('-');
        fileType = "txt";
    }

    public void addData(String fileData) {
        this.fileData[currentfileDataNum] = fileData;
        currentfileDataNum++;
    }

    public String getFileData() {
        return fileData.toString();
    }

    public String getFirstData() {
        return fileData[0];
    }

    /*
    private void calcFileSize() {
        setFileSize(fileData.length() * Character.BYTES);
    }
     */

}
