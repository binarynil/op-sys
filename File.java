public class File extends FileSys {
    private String[] fileData;
    private String fileType;
    private int MAX_DATA = 50;
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

    public File(String name, String data) {
        super(name, '-');
        this.fileData = new String[MAX_DATA];
        addData(data);
        super.setReadable('r');
        super.setWriteable('-');
        super.setExecutable('-');
        fileType = "txt";
    }

    public void addData(String data) {
        //System.out.println(currentfileDataNum);
        this.fileData[currentfileDataNum] = data;
        currentfileDataNum++;
    }

    public void getFileData() {
        for(int i = 0; i < MAX_DATA; i++) {
            if(fileData[i] != null) {
                System.out.println(fileData[i]);
            }
        }
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
