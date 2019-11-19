Approach to The Driving History Problem

Usage:
`./gradlew clean build`  
then
`cat input.txt | java -jar build/libs/driving-history-0.0.1-SNAPSHOT.jar`

I started with 3 main thoughts when i saw the problem statement.

1) The file of raw driving history data seems like a data feed to me, and in my experience data 
feeds will always have lots of unexpected errors, so I wanted to make sure there were good tests 
around validation of the input.
2)  I kind of only considered the Trip lines to be real data.  The driver names, to me, are just 
filters.  I think we should keep all the (valid) trip data, and we can decide later which data
we want to actually analyze and see.
3) I think a "Command Pattern" works here, where each line of the file/input is a command, and the 
command goes to all the parsers.  The parsers, since they already should have good validation tests,
should be able to ignore commands (lines) that dont apply to them.

I started with the parsers, and just validated the inputs.  I "saved" the driver names when I get
the "Driver" line, and "saved" the trip data when I got a "Trip" line.  

Next, i moved the "save" to
a repository, in case this ever gets saved to a database.  I made Trip into an object, because
I think it makes sense.  Trips have a lot of elements, and even a calculated field (calculated 
speed).  And Trips would want to be saved in a database someday.  i didnt make drivers into an
object, because its just a name, but also because i didnt think it was realistic.  In real life
we'd probably ignore all driver lines, parse and save all trips, then expose a UI to search/
aggregate/ analyze by driver.  Thats when you'd decide what drivers you wanted to look at.  I
just didnt think a data feed should ever decide which drivers are important.  So i didnt think
it was worth spending time to make an object around driver.

Last was the report writer, which just gets all the "saved" drivers' names, then looks each one up
to get all their Trips, then filters the slow/fast trips, adds them all up, and then prints the
output lines in sorted order.