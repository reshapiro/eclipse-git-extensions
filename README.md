This repository defines a simple Eclipse plugin designed to support
some Git operations that are not yet supported by JGit/Egit. It does
this by launching command-line Git.

The currently supported operations are as follows:

-SVN bridging: pull commits from SVN; push commits to SVN; fetch commits from SVN; SVN info; ref -> SVN rev; SVN rev -> SHA

- Bisect: start (optional bad & good commits); mark good; mark bad; mark from run; skip; log (optional file); replay; reset

- Pruning obsolete remote references

- Creating Lightweight tags

- Listing orphan commits

- Basic bundle support: create bundle file; fetch from bundle file; pull from bundle file.

For more info please read notes/notes.txt.
