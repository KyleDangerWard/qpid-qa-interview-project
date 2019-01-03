QPID QA DEPARTMENT INTERVIEW EXAM
Github Instructions for Remote Exam Takers

If you are taking this exam remotely, there are some extra steps that must be taken to pull the code from git and push your work up to QPID at the end.


The repo exists on Kyle's personal Github, at https://github.com/KyleDangerWard/qpid-qa-interview-project. 

1. Open a Terminal window (or Gitbash/Windows Console on a Windows machine).

2. Clone the project in a useful local directory.

    $ git clone https://github.com/KyleDangerWard/qpid-qa-interview-project.git

3. Create and point at a new branch for your work. Please include your name in the branch name.

    $ git checkout -b kyle_ward_exam

4. When you're finished, push your branch to the code repo so we can check it out.
   You are welcome to delete your local files once you're sure that your code has been uploaded.

   Add your code into local commit staging.
    $ git add --all

   You should see in your local Git Status that all of the listed file changes have been staged.
    $ git status

   Create your commit with a message.
    $ git commit -m "Some commit message like... Added two unit tests and one system test"

   Push your commit to QPID, so we can see your fabulous work.
    $ git push --set-upstream origin kyle_ward_exam
