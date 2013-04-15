## Clojure in the Clouds, it's a sunny day

### Did we say ? 5 minutes to deploy your clojure application on Heroku

#### Generate a new project

    lein new laminus test-project

Make sure the resulting ring project is working fine, by running 

    lein ring server

and then checking the local URL:

    http://localhost:3000/


#### Prepare your SSH keys and access

The short version is simply to add your existing ssh key to your heroku account. Copy your public key at the following place:

[https://dashboard.heroku.com/account](https://dashboard.heroku.com/account)

Now the long version, in case you:

* do not have existing SSH keys
* run into troubles later

Indeed, while you can use your existing keys, we suggest you generate a new set of SSH key with: 

     ssh-keygen -t rsa -f ~/.ssh/id_rsa_heroku

Add it to your local ssh agent:

    ssh-add ~/.ssh/id_rsa_heroku

Make sure that the new key is showing up in the ssh-agent with: 

     ssh-add -l

Remove unnecessary keys with the -d flag. (This will not delete the key, only remove it from the ssh-agent.)

    ssh-add -d /Users/Niko/.ssh/id_rsa_hellonico

Add the ssh key to your account:

[https://dashboard.heroku.com/account](https://dashboard.heroku.com/account)

#### setup heroku 

Prepare for creating a new application on Heroku. Let's head to our account page again, and create a new app through the following steps:

![../images/chap06/heroku1.png]

![../images/chap06/heroku2.png]

![../images/chap06/heroku3.png]

Then back to our application, we can add the necessary git remote.

    git remote add heroku git@heroku.com:glacial-tundra-7926.git

Once this is done, we just have to perform a git push:

    git push heroku master 

And our application is up and running at:

[http://glacial-tundra-7926.herokuapp.com/](http://glacial-tundra-7926.herokuapp.com/)

#### Some explanations

Heroku runs on top of Amazon AWS, and performs all the dirty tasks for you. 

Do a diagram of Heroku running

[Deploying Application on Heroku](https://devcenter.heroku.com/articles/clojure-web-application)
[Debugging clojure on Heroku](https://devcenter.heroku.com/articles/debugging-clojure)

### Monitor your amazon ec2 instance directly from Clojure
[http://architects.dzone.com/articles/how-monitoring-ec2-clojure-and](http://architects.dzone.com/articles/how-monitoring-ec2-clojure-and)

Mixpanel allows you to track any kind of event from Within your Application.
[https://github.com/pingles/clj-mixpanel](https://github.com/pingles/clj-mixpanel)
[https://mixpanel.com/account/](https://mixpanel.com/account/)

### Define clusters and tasks for system administration or code deployment, then execute them on one or many remote machines
[https://github.com/killme2008/clojure-control/](https://github.com/killme2008/clojure-control/)

### Monitor your clojure application using clojure-control
[https://github.com/killme2008/clj.monitor](https://github.com/killme2008/clj.monitor)

### Parallel SSH
[http://blog.rjmetrics.com/Parallel-SSH-and-system-monitoring-in-Clojure/](http://blog.rjmetrics.com/Parallel-SSH-and-system-monitoring-in-Clojure/)
[https://github.com/RJMetrics/Parallel-SSH](https://github.com/RJMetrics/Parallel-SSH)
[Monitoring EC2 with clojure](http://paulosuzart.github.com/blog/2012/04/17/monitoring-ec2-with-clojure-and-server-stats/)

### robin: a RRDtool or system graphing and monitoring in Clojure
[http://www.jrobin.org/index.php/Main_Page](http://www.jrobin.org/index.php/Main_Page)

### Your cloud infrastructure right at your fingertips
[http://palletops.com/](http://palletops.com/)

Pallet is the mother of them all of cloud infrastructure tool. See the [list of providers](http://www.jclouds.org/documentation/reference/supported-providers/) it supports! 
They are actually doing this through [jclouds](http://www.jclouds.org/)


Lein Plugin for deployment on heroku
[Lein Heroku](https://github.com/technomancy/lein-heroku)

### Easy VirtualBox wrapper for easy cloud management
[https://github.com/tbatchelli/vmfest](https://github.com/tbatchelli/vmfest)

@@@ ruby 14_vmfest.clj @@@

Make sure you also look at the [playground](https://github.com/pallet/vmfest-playground) and have a look at the [tutorial](https://github.com/pallet/vmfest-playground/blob/master/src/play.clj)

### Lemur, your friend to launch hadoop jobs locally or on EMR
[Lemur is a tool to launch Hadoop jobs locally or on EMR](https://github.com/TheClimateCorporation/lemur)

### Ever wanted to deploy on Redhat's openshift plateform ?
[Deploy on Redhat's openshift](http://sisciatech.tumblr.com/post/29614188595/webnoir-in-openshift)

### Google App Engine deployment
[AppEngine](https://github.com/gcv/appengine-magic)

And a ready to be used CMS to deploy on the app engine:
[Sample Webapp](https://github.com/thurn/ackbar)

### Deploying clojure app in the cloud with jetlastic. it's super easy !
[Jelastic](http://jelastic.com/ja/docs/clojure) and [sample app](https://github.com/cemerick/clojure-web-deploy-conj)