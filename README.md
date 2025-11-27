
# WegFinderApplikation

Brief description
- Java / Eclipse project for indoor navigation in a hospital ("Way Finder").
- Purpose: Help visitors and staff locate rooms, floors and routes inside the hospital.

Key features (short)
- Loads room, floor and user data from plain text files (files/*.txt, wegfinderApp/bin/*.txt).
- Computes and displays routes between locations.
- Eclipse-style Java project with code in `src/com`.

Repository layout (important folders)
- wegfinderApp/src/ — Java source code (package `com`).
- wegfinderApp/bin/ — built classes and sample text resources.
- files/ — example input files (floor.txt, Maparray.txt, room.txt, user.txt).
- wegfinder/.metadata/ — Eclipse workspace metadata (do not commit).

Quick start — Eclipse
1. Open Eclipse → File → Import → "Existing Projects into Workspace".
2. Select project folder `wegfinderApp/` and import.
3. Create a Run Configuration and select the project Main class to run.
4. Make sure text resource files are available on the classpath or provided as external files.

