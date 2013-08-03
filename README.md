deco2800-2013
=============

DECO2800 project for semester 2 2013.

## Installation

    git clone git@github.com:UQdeco2800/deco2800-2013.git
    cd deco2800-2013
    gradle build
    
##### Dependencies

[Gradle Installation Instructions](https://github.com/UQdeco2800/deco2800-2013/wiki/Installing-Gradle)
    
## Running

Start up the server:

    gradle runServer
    
Then start up the client by running the following in a separate terminal:

    gradle runArcade
    
To see a list of all available gradle commands:

    gradle tasks
    
Detailed run instructions: [Running The Arcade](https://github.com/UQdeco2800/deco2800-2013/wiki/Running-the-arcade)
    
## Testing

To run unit tests:

    gradle test

## Frequently Asked Questions

##### Pong is boring.

That's a lie, not a question.

## Contributing

Git best practices advise working on your own fork of a repository and utilising [Pull Requests](https://help.github.com/articles/using-pull-requests) in order to minimise clashes.

If you'd rather work directly on this repository please follow these steps to make everyone's life easier:

1. Create your own branch to work on (`git checkout -b <branch_name>`). Choose a short but descriptive branch name.
    * For a long lived branch of your changes, your student number would make a good, unique name.
    * Remember you can have as many branches as you like
1. Make changes and commit them as usual.
1. To update your branch to reflect the latest changes made by the group (on the `master` branch) run the following:

        git checkout master
        git pull
        git checkout <your_branch>
        git merge master
    * This will also work with other branches, just substitute either `master` or `<your_branch>` depending on what you want to do.
    * It is likely that this will result in merge conflicts at some point during the semester. [Google is your friend here](https://www.google.com.au/search?q=git+resolving+conflicts).
1. If you aren't ready to merge your changes to `master` but you want to backup your changes to github just push your branch:
    * `git push origin <your_branch>`
    * This will not affect anyone working on any other branch
1. When ready to share your changes with the rest of the group, merge your branch with `master`:

        git checkout master
        git pull
        git merge <your_branch>
        git push
1. If you're finished with your branch you can also delete it:
    * `git branch -d <your_branch>`
