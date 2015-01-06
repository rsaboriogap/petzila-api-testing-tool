Petzila API Testing Tool
========================

Petzila API Testing Tool (known as patt), is a command-line stress-testing tool for Petzila API. It works in Linux,
Windows, and MAC OS. In order to run patt you need JDK 8+ installed in your system.

Building patt
=============
To build patt run the following command on a terminal:

    you@yourmachine:/path/to/patt$ ./gradlew clean shadow

Running patt
============
To show patt's help execute the following command on a terminal:

    you@yourmachine:/path/to/patt$ java -jar build/libs/patt.jar -h

It should show you something like this:

    usage: patt
     -d <arg>   Time delay before making a call. Random delay between 1 sec and N secs. Default = 1 sec.
     -e <arg>   Environment (local, dev, qa, qa2, qa3, qa4, pre, stg). Default = local.
     -f <arg>   Flow to run.
     -h         Show this help.
     -l         Show all registered flows.
     -t <arg>   Duration of test (s, m, h) eg. 1h = one hour test. Default = 1m.
     -u <arg>   Number of concurrent users. Default = 10.

If you want 20 concurrent users to call during 1 minute the flow that lists all posts in QA's environment run:

    you@yourmachine:/path/to/patt$ java -jar build/libs/patt.jar -e qa -t 1m -u 20 -f post-get-flow

Flows
=====
What is a Flow? It's basically a set of operations you want to perform on Petzila API. For instance, this could be a flow:

  * Login using John Doe credentials
  * Create a new pet
  * Create a new post with that pet
  * Create a new comment on that post

Flows are under the /com/petzila/api/flow package. You need to know Java programming to create new flows but don't worry.
They are pretty straightforward to implement.


Listing Flows
=============
To list all registered Flows run:

    you@yourmachine:/path/to/patt$ java -jar build/libs/patt.jar -l


If you think this tool is useful to you buy Randy Saborio a beer.