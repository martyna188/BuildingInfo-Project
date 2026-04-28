# Building Info
For building administrators who want to minimise the costs of building management, our Building Info application will enable obtaining information about building parameters at the level of rooms, floors and entire buildings. The application will be available via GUI and also as a remote API, thanks to which it can be integrated with existing tools. 

## Data structure
- A location is a building, level, or room
- The building may consist of levels and those of rooms
- Each location is characterized by:
    - o id – unique identifier
    - o name – optional name of the location
- The room is additionally characterised by:
   - area = area in m^2
   - cube = room volume in m^3
   - heating = level of heating energy consumption (float)
   - light – total lighting power
