# librobot
A FRC robotics library aiming to simply vendor libraries and provide a simple API for interacting all of them with minimal effort.

## Installation
Add the following to your `build.gradle` file:
```gradle
repositories {
    maven {
        url 'https://jitpack.io'
    }
}

dependencies {
    implementation 'com.github.frc5183:librobot:VERSION'
}
```

## Versioning
This project uses [Semantic Versioning](https://semver.org/) prefixed by the year of the FRC season.

For example, the 2020 version of the library would be `2020-1.0.0`