# op-sys
Simulates the Unix command line and other things.

The main driver is Shell.

The default password is "toor" because reasons.

## Commands / Programs
* **cat**: outputs the contents of a file in the current directory.
```cat filename```
* **cd**: traverses into a folder within the current directory or traverses into its parent directory. 
```cd ..``` , ```cd folder```
* **mkdir**: creates a folder in the current directory.
```mkdir folder```
* **mkfile**: creates a text file in the current directory.
```mkfile filename sometext``` , ```mkfile filename```
* **mv**: moves a file in the current directory into a different folder.
```mv filename /full/path/to/new/folder```
* **dir / ls**: lists the contents of the current directory.
```dir```, ```ls```
* **pwd**: gets the full path of the current directory. ```pwd```
* **rm**: deletes the item in the current directory. ```rm filename```
* **whoami**: prints the current user logged into the system. ```whoami```
* **logout**: logs the user out of the system. ```logout```
* **exit**: logs out and terminates the system. ```exit```
