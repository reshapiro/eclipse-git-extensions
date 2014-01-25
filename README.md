This repository defines a simple Eclipse pluging designed to support
some Git operations that are not yet supported by JGit/Egit.  The
implementation is trivial: a process is launched that will run
command-line Git.  Users must therefore tell Eclipse where  the
command-line Git executable lives. Do this with a StringSubstitution
variable in the Run/Debug settings.

This is an experiment in a very early stage - use with caution!


