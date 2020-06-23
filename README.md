# ThermalMonJTM05
ThermalMonJTM05 FaceRecognition Forehead Thermal IR Camera Module
We aim to more engineer to develop the forehead thermal IR camera module with faster time with JTM05 module.
JTM05 camera module has been factory well calibrated accuracy and stability. Only few command can connect to android system board.
Simpify the design compare with standard Thermal Camera.

Procedure to get one person forehead temperature:
1) Face recognization program at android board, find out the fore head X, Y position and fore head size.
2) Send the X Y position and fore head size to the camera module
3) Camera module reply environment temperature and the fore head averaged temperature value to the android board
4) Android according 2 value, fore head temperature, environment temperature and using the fore head temperature equation to find out the fore head core temperature.

The procedure is simple, as a result, we can use a moblie CPU android to get 5 fore head temperature within 0.15s. 0.15s is the face recognization time in android. 
At JTM05 it is running up to 15fps and handle the command and interior calculation in few ms. 

Compare with the thermal camera in the market, we can speed up the warm up time to within 5 minutes. Actually, during powerup to 5 minutes later, the difference is only 0.5degree Celsius.
And each thermal sensor pixel we have well calibrated in factory controlled environment.  A patented blackbody is embedded inside JTM05 to compensate the thermal camera affected by a tiny environment temperature changing effect. 

Finally we can use the camera to monitoring people forehead temperature without user to stop and wait before the Thermal Camera. 
User can just walk as usual without standing and waiting in a queue.
