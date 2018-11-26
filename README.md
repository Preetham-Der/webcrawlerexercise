# webcrawlerexercise
Exercise Web Crawler
1.Install the required software.
2. Clone the repo into local directory
3. Change to (cd to) the local repo directory
The following commands/tasks are supported for gradle
gradle
gradle eclipse
gradle jar

gradle jar will build one fat jar that has all the dependencies called webcrawlerown.jar in the build/libs folder in the local repo

Copy the jar into any directory where you want to run/execute the program
Execute the following command
Java <Domain for crawling> <seed url> <no of parallel threads> <output file for the program>
For eg:

java https://wiprodigital.com https://wiprodigital.com 64 sitemapout.json

Requirements
[Gradle 4.8.1] (http://www.gradle.org)
Java 1.8
