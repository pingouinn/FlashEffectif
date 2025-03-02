FlashEffectif
======

FlashEffectif is a JAVA Desktop tool aiming to help French Red Cross executives detect staff shortages in rescue teams on planned operations.

A tool designed to save time and avoid errors
---------------------------------------------

This tool is implementing selenium both as GUI (for authentication) and headless (for API calls), along with swing for a user-friendly visualisation.
Based on the closed API REST JSON of Pegass (the French Red Cross operations webapp), it can analyse every planned mission and execute reports on missions that are low staffed 

Requirements
------------

Currently only supported on Windows with Chrom or Opera browsers.
More OS and Browser are planned for further releases.

Releases
--------

No stable release for the moment. 
You can still download the project in the current state and build it yourself, but it can be unfinished / may not work as intended.

How to build
-------------

Download the package and make sure you have the following dependencies : 

- org.json
- selenium

Execute the build.bat script
