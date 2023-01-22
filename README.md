# Audio-Playback-Detector

A small Android TV utility service that detects audio playback on the device and turns on a Yamaha MusicCast enabled receiver. My use case is an Android TV device connected via an HDMI -> TOSLINK adapter that adds a Chromecast capability to my Yamaha R-N602 receiver.

The app uses a sticky service that checks for the `AudioManager::isMusicActive` every 2 seconds, upon which it wakes up, sets the input and volume on the network audio receiver.

## Usage

Clone the repository, import to Android Studio and open `local.properties`. Add `receiver.ip.address="{IP_ADDRESS}"`. Open `DetectorService.kt` and set the `SELECTED_INPUT` constant to the desired input then run the app on your device. Then initiate any playback (e.g. via casting) and the receiver APIs will get called.

> Note: if you ran the app from AS, the process might get killed when you close the IDE. Make sure you run `adb shell am start -n pl.manfredmaniek.audioplaybackdetector/pl.manfredmaniek.audioplaybackdetector.MainActivity` after closing.

### Additional optional config

To ensure that the receiver has a static IP address, use its remote and go to `Setup` -> `Network` -> `IP Address`. Turn DHCP off and set a static IP address.

In order to control the output volume from the Android TV device with a casting sender (or via its remote), go to `Settings` -> `Device Preferences` -> `Volume control` and turn off `HDMI fixed volume`.

If you wish to control your Yamaha MusicCast receiver in a different way, search for `Yamaha Extended Control API Specification`.