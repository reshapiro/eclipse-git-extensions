This repository defines a simple Eclipse plugin designed to support
some Git operations that are not yet supported by JGit/Egit.
The currently supported operations are as follows:

- Basic SVN bridging: svn rebase, dcommit, fetch, rebase --local, info, mapping SHAs to SVN revs and vice-versa

- Basic Bisect: start, good, bad, reset

- Pruning obsolete remote references

- Creating simple tags

- Basic bundle support: create bundle file, fetch from bundle file, pull from bundle file.

Requires Java 7 and an installation of command-line Git.
Also requires the workspace to include a definition of the String Substitution variable 'git_exec'
which should point at the absolute path of Git. 
