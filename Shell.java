//import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
//import java.util.Base64;

public class Shell {
    private static String user = "root";
    private static String machineName = "op-sys";
    private static String OS = "op sys 4.20.1";
    private static boolean loggedIn = false;
    private static boolean terminate = false;
    //private static File[] binaries = { new Mkdir() };

    public static void main(String[] args) throws IOException {
        //boolean terminate = false;
        Scanner input = new Scanner(System.in);

        Folder tempRoot = new Folder("root");
        tempRoot = setupENV(tempRoot);
        while(!terminate) {
            logIn(input, tempRoot);

                String command;
                while(loggedIn) {
                    System.out.printf("<%s@%s><%s>: ", user, machineName, tempRoot.getPath());
                    command = input.next();
                    tempRoot = progs(command, tempRoot, input);
                }
        }

        System.out.println("[op sys] terminated.");
    }

    private static void logIn(Scanner input, Folder temp) throws IOException {
        String pw;
        int attempts = 0;

        System.out.printf(
                        "          __________________________________________________\n" +
                        "         /                                                 /\n" +
                        "        /                                                 /\n" +
                        "       /         ____  ____  _______  _______            /\n" +
                        "      /         / __ \\/ __ \\/ ___/ / / / ___/           /\n" +
                        "     /         / /_/ / /_/ (__  ) /_/ (__  )           /\n" +
                        "    /          \\____/ .___/____/\\__, /____/           /\n" +
                        "   /               /_/         /____/                /\n" +
                        "  /                                                 /\n" +
                        " /                                                 /\n" +
                        "/_________________________________________________/\n" +
                                "%50s\n\n" +
                        "┌─────────────────────────────────────────────────┐\n", OS);

        System.out.printf(" ┌login: %s\n", user);
        Folder etc = (Folder) temp.getParent();
        etc = newDir(etc, "etc");
        File shadow = etc.getFile("shadow", etc);
        //BufferedReader readPW = new BufferedReader(new FileReader("pw.txt"));
        //String basedPW = readPW.readLine();
        do {
            System.out.print(" └→pw: ");
            pw = input.nextLine();
            // Compares the inputted password with the encryption of the actual password.
            // If the base64 strings match, then the user logs in.
            // Otherwise, the system locks itself and shuts down after 5 attempts.
            try {
                if(Crypt.crypt(pw).equals(shadow.getFirstData())) {
                    loggedIn = true;
                    System.out.println("└─────────────────────────────────────────────────┘");
                    System.out.println();
                }
            }
            catch (Exception e) {
                System.out.println("[crypt] failed.");
            }

            attempts++;
            if(attempts == 5) {
                System.out.println(" [\uD83D\uDC80] locked out.");
                System.out.println("└─────────────────────────────────────────────────┘");
                terminate = true;
            }
        } while(!loggedIn && attempts < 5);
    }

    private static Folder progs(String command, Folder tempRoot, Scanner input) {
        // The list of commands / programs that can execute.
        String fileName;
        File tempFile;
        switch(command) {
            case "cat":
                fileName = checkARGS(input);
                tempFile = tempRoot.getFile(fileName, tempRoot);
                if(tempFile != null) {
                    tempFile.getFileData();
                }
                break;
            case "cd":
                String cdChoice = checkARGS(input);

                if(cdChoice.equalsIgnoreCase("..") && tempRoot.hasParent()) {
                    tempRoot = (Folder) tempRoot.getParent();
                }
                else if(!cdChoice.isEmpty()) {
                    tempRoot = newDir(tempRoot, cdChoice);
                }
                else {
                    System.out.print("");
                }
                break;
            case "mkdir":
                String folderName = checkARGS(input);
                //System.out.println(folderName);
                Mkdir mkdir = new Mkdir();

                tempRoot = mkdir.execute(folderName, tempRoot);
                break;
            case "mkfile":
                //String fileStuff = checkARGS(input);
                String newFileName = input.next();
                System.out.println(newFileName);
                String fileData = checkARGS(input);
                tempFile = new File(newFileName, fileData);
                tempRoot.addItem(tempFile);
                break;
            case "mv":
                String toMove = input.next();
                String newPath = checkARGS(input);
                tempFile = tempRoot.getFile(toMove, tempRoot);
                boolean moved = mvFile(tempRoot, newPath, tempFile);
                if(moved) {
                    tempRoot.delItem(toMove);
                }

                break;
            case "dir":
            case "ls":
                printDir(tempRoot);
                break;
            case "pwd":
                System.out.println(tempRoot.getPath());
                break;
            case "rm":
                //FileSys temp;
                String toDelete = checkARGS(input);
                tempRoot.delItem(toDelete);
                break;
            case "whoami":
                System.out.println(user);
                break;
            case "help":
                printCommands();
                break;
            case "logout":
                System.out.printf("[%s] logged out.\n", user);
                loggedIn = false;
                break;
            case "exit":
                System.out.printf("<%s@%s><bye!>\n", user, machineName);
                loggedIn = false;
                terminate = true;
                break;
            default:
                printError(command);
                break;
        }

        return tempRoot;
    }

    private static Folder setupENV(Folder root) {
        // Initializes the op-sys environment.
        Mkdir mkdir = new Mkdir();
        Folder etc = new Folder("etc");
        etc.setParent(root);
        File shadow = new File();
        shadow.setName("shadow");
        // The base64 string of the encrypted default password "toor".
        shadow.addData("hM7Bog8utjmPK6oYkpuNhS7cbF1l2N4UaXQ9gEv+V8EzTBYWBMbVFpc6jxheyc4HwjAJ5vEn6DeSBrYx09XzFPpbMW4Uldp7mT5UW0uOrI/kT6g4MEhcYSufBOmDRgPnkmHbDFazVSg4aArECT15E949UD+yrmb2axNfQI9NgshnwywiUdiweRIBmvYrhOdV4IBrCQtFmLYKPAw9IVz/1mBVzLzmAI3s9vT1qekQ3kH8xUzDjQjNtxTLNRdG8IcHlQyvk7m50xk=");
        //System.out.println("adding shadow");
        etc.addItem(shadow);
        Folder bin = new Folder("bin");
        bin.setParent(root);
        mkdir.setParent(bin);
        bin.addItem(mkdir);
        Folder disks = new Folder("disks");
        disks.setParent(root);
        Folder hd1 = new Folder("hd1");
        hd1.setParent(disks);
        Folder tmp = new Folder("tmp");
        tmp.setParent(root);
        Folder home = new Folder("home");
        home.setParent(root);
        //System.out.println("adding etc");
        root.addItem(etc);
        root.addItem(bin);
        root.addItem(disks);
        root.addItem(tmp);
        root.addItem(home);
        disks.addItem(hd1);

        return home;
    }

    private static void printError(String command) {
        System.out.printf("[%s] is an unrecognizable command\n", command);
    }

    private static void printCommands() {

    }

    private static void printDir(Folder rootdir) {
        //Folder tempdir = (Folder) rootdir;
        if(rootdir.getFolderContent() == null) {
            System.out.println();
        }
        else {
            for(int i = 0; i < rootdir.getFolderContent().length; i++) {
                if(rootdir.getFolderContent()[i] != null) {
                    System.out.println(rootdir.getFolderContent()[i].getPermissions() + " " + user + " " +rootdir.getFolderContent()[i].getName());
                }
            }
        }
    }

    private static Folder newDir(Folder rootdir, String dirName) {
        Folder tempdir = rootdir;

        for(int i = 0; i < tempdir.getFolderContent().length; i++) {
            if(tempdir.getFolderContent()[i] != null && tempdir.getFolderContent()[i].getName().equalsIgnoreCase(dirName)
                    && tempdir.getFolderContent()[i].getType() == 'd') {
                return (Folder) tempdir.getFolderContent()[i];
            }
        }

        System.out.printf("[%s] doesn't exist or it's not a folder.\n", dirName);

        return tempdir;
    }

    private static boolean mvFile(Folder rootdir, String path, File toMove) {
        // An inefficient way of moving one file to another directory.
        // Traverses back to root, then to the destination, if it exists,
        // and moves the file to the new directory.

        Folder temp = rootdir;

        while(temp.getParent() != null) {
            temp = (Folder) temp.getParent();
        }
        path = path.replaceAll("/", " ");
        path = path.substring(1);

        if(!temp.findPath(temp, path)) {
            System.out.println("Path not found");
            return false;
        }

        //Scanner pathScan = new Scanner(path);
        //System.out.println(path);
        //String tempName = pathScan.next();

        //int index = -1;
        String[] arrayStr = path.split(" ");
        //System.out.println(arrayStr.length);
        for(int i = 0; i < arrayStr.length; i++) {
            int index = temp.findItem(arrayStr[i]);
            if(index >= 0) {
                temp = (Folder) temp.getFolderContent()[index];
            }
        }

        temp.addItem(toMove);
        return true;
    }

    private static String checkARGS(Scanner input) {
        String arg = "";

        if(input.hasNextLine()) {
            arg = input.nextLine();
            if(arg.length() > 1) {
                arg = arg.substring(1);
                //System.out.println(arg);
            }
        }

        return arg;
    }
}
