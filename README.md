Introproject_GIT android

If you get an error about modules, it's probably because Android studio (or just me because i'm a noob)
uses absolute paths for modules instead of relative.
Fix by manually removing and adding jtds and mssql jars and dependencies to the project.
I hope you don't need this.