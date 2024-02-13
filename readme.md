# Quark

Quark is a secure messaging application for Android, featuring  [quantum-resistant encryption](https://openquantumsafe.org/).

## Installation

For a compiled APK, see [releases](https://github.com/IUS-CS/c346-sp24-34658-project-sexton-s-savants/releases). For development setup, scroll on.

## Building from source

Using [Android Studio](https://developer.android.com/studio) is highly recommended for any Android development.

Use 

    ./gradlew assembleDebug 
or 

    .\gradlew.bat assembleDebug

in the root of the project to build an APK from the command line. You can also deploy to an Android device or emulator from the command line, using

    ./gradlew installDebug
See [here](https://developer.android.com/build/building-cmdline) for more info.

## Testing

Again, tests are best ran in Android Studio, but it is possible using the command line.

If using the commandline, there are two commands to use.

For on-device-testing, use:

	./gradlew connectedAndroidTest
For developer-side testing, use:

    ./gradlew test

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[GPL-3.0](./LICENSE)