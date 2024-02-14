# Current Issues
### Integrating Quark with liboqs

Initially it seemed as though integrating the [liboqs C-library](https://github.com/open-quantum-safe/liboqs?tab=readme-ov-file#documentation) would not be a difficult process, as
the android developer docs have some great articles on how to link C and C++ code to an android project (and build it).

https://developer.android.com/studio/projects/add-native-code#new-project
https://developer.android.com/studio/projects/gradle-external-native-builds#specify-abi
https://developer.android.com/build/dependencies#dependency_configurations

Unfortunately, it does not seem to be as straightforward to build all of the dependencies that liboqs requires, and will need further research/tinkering

Then, we discovered that liboqs has a [java implementation](https://github.com/open-quantum-safe/liboqs-java), but through
more [research](https://stackoverflow.com/questions/65794862/android-no-implementation-found-for-native-method-even-after-calling-loadlibr) it is unclear whether 
the library even works at all..

As of now, more research needs to be done to decide if using liboqs is possible or not. Additionally, other [alternatives](https://frodokem.org/)
are being considered.
