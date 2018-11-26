# webcrawlerexercise
Exercise Web Crawler

1. Install the required software.

2. Clone the repo into local directory.

3. Change to (cd to) the local repo directory.

The following commands/tasks are supported for gradle.
    gradle
    gradle eclipse
    gradle jar

gradle jar will build one fat jar that has all the dependencies called webcrawlerown.jar in the build/libs folder in the local repo.

Copy the jar into any directory where you want to run/execute the program.

Execute the following command.

java [Domain for crawling] [seed url] [no of parallel threads] [output file for the program]

For eg:

java -jar webcrawlerown.jar https://wiprodigital.com https://wiprodigital.com 64 sitemapout.json

Requirements.

    [Gradle 4.8.1] (http://www.gradle.org)

    Java 1.8

More things to do
  
  1.Security
    Support for authentication for the application.
    Crawl using proxy.
    Leverage SSL based crawling.

  2.Runtime Control
    Pause/Stop/Restart support.

  3.External Configuration
    More configuration parameters such as seed, thread etc should be supported at the external configuration level.

  4.Functionality support
    Support for HTTP POST/REDIRECT.
    Multiple seed url.
    Subdomain aware.

  5.Testing 
    Dont have time for implementing and test contracts. Needs more dev for pure unit testing.

