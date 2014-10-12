This repository defines an Eclipse plugin designed to support
a range of Git operations that are not yet supported by JGit/Egit. It does
this by launching command-line Git.

This is not a replacement for EGit, it's more like a set of extensions to it.

The major supported operations are as follows:

- SVN bridging: pull commits from SVN; push commits to SVN; fetch commits from SVN; SVN info; ref -> SVN rev; SVN rev -> SHA

- Bisect: start (optional bad & good commits); mark good; mark bad; mark from run; skip; log (optional file); replay; reset

- Merge with strategy options

- Merge abort

- Basic bundle support: create bundle file; fetch from bundle file; pull from bundle file.

- More reset variants: resetting a directory, reset with --merge or --keep

- Pruning obsolete remote references

- Creating Lightweight tags

- Listing orphan commits

- Trivial command line to run any other Git operation you want without leaving Eclipse.

- Also includes command-line Git versions of push, pull,fetch and checkout, as these can be significantly more efficient vis-a-vis JGit.

For more info please read notes/notes.txt.


You can easily build the plugin yourself, or install a a prebuilt version from http://home.comcast.net/~reshapiro/org.res.gitx/

