This repository defines a simple Eclipse plugin designed to support
some Git operations that are not yet supported by JGit/Egit.
The currently supported operations are as follows:

-SVN bridging: pull commits from SVN; push commits to SVN; fetch commits from SVN; SVN info; ref -> SVN rev; SVN rev -> SHA

- Bisect: start (optional bad & good commits); mark good; mark bad; mark from run; skip; log (optional file); replay; reset

- Pruning obsolete remote references

- Creating Lightweight tags

- Listing orphan commits

- Basic bundle support: create bundle file; fetch from bundle file; pull from bundle file.

Requires Java 7 and an installation of command-line Git.
Also requires the workspace to include a definition of the String Substitution variable 'git_exec'
which should point at the absolute path of Git executable. 
