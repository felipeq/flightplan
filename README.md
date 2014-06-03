areusmart.flightplan
====================

Finding alternate airports in given range from flypath

Run command:
java pl.areusmart.flightplan.Main [SOURCE] [DESTINATION] [RANGE] [VELOCITY] [TIME]
Where:
[SOURCE] is IATA code of start airport
[DESTINATION] is IATA code of ending airport
[RANGE] is max distance between flypath and alternate airport
[VELOCITY] is airplane's velocity, constant on whole flight, I ignored takeoffs and landings
[TIME] it's start time in [HH:mm:ss] format

Database's source is external file, lotniska.dat. Fill free to replace it, but keep data format

Example input command:
java pl.areusmart.flightplan.Main KRK GDN 300 800 09:15:00 
Flight from Cracov to Gdansk, 800 km/h, takeoff at 9:15:00, maximum range for nearby airports to be clasified as "alternate" is 300 km

Output consists of list of airports with time, when airplane will be closest to flypath

!! The code is not protected against incorrect input !!
