# Local setup

Set the VM option `-Dspring.profiles.active="dev"` to use application-dev.properties

# Deployment on heroku

The api can be reached under https://enigmatic-gorge-65663.herokuapp.com/.

Trigger deployment by pushing to master branch of heroku remote (https://git.heroku.com/enigmatic-gorge-65663.git).

Check logs via `heroku logs --tail`
