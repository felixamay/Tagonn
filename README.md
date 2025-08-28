# Tagonn - Android WebView App

A modern Android WebView application that wraps tagonn.com into a native mobile app experience. Built with Kotlin and following Material Design 3 guidelines.

## Features

- **WebView Integration**: Seamless browsing of tagonn.com within the app
- **Pull-to-Refresh**: Swipe down to refresh the current page
- **Offline Error Handling**: Graceful error handling when network is unavailable
- **External Link Handling**: Opens external links in the device's default browser
- **Runtime Permissions**: Proper handling of camera, location, and microphone permissions
- **Dark Mode Support**: Automatic theme switching based on system preferences
- **3-Dot Menu**: Clean menu with reload, share, open in browser, and rate app options
- **Splash Screen**: Beautiful splash screen with app branding
- **Back Navigation**: Proper back button handling for WebView navigation

## Technical Stack

- **Language**: Kotlin 1.9.20
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Architecture**: MVVM with ViewBinding
- **UI Framework**: Material Design 3
- **WebView**: Android WebView with custom clients
- **Permissions**: Runtime permission handling
- **Build System**: Gradle 8.7 with Kotlin DSL
- **Android Gradle Plugin**: 8.5.0

## Project Structure

```
app/
├── src/main/
│   ├── java/com/tagonn/app/
│   │   ├── TagonnApplication.kt
│   │   ├── ui/
│   │   │   ├── main/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── TagonnWebViewClient.kt
│   │   │   │   └── TagonnWebChromeClient.kt
│   │   │   └── splash/
│   │   │       └── SplashActivity.kt
│   │   └── utils/
│   │       ├── NetworkUtils.kt
│   │       └── PermissionUtils.kt
│   ├── res/
│   │   ├── layout/
│   │   ├── menu/
│   │   ├── drawable/
│   │   ├── values/
│   │   └── xml/
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Building the Project

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34
- JDK 17 or later
- Gradle 8.7 (automatically managed by wrapper)

### Build Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Tagonn
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the project directory and select it

3. **Sync Gradle**
   - Wait for the initial Gradle sync to complete
   - If prompted, update Gradle wrapper

4. **Build the project**
   - Go to Build → Make Project (Ctrl+F9 / Cmd+F9)
   - Or use the command line: `./gradlew build`

5. **Run on device/emulator**
   - Connect an Android device or start an emulator
   - Click the Run button (Shift+F10) or use `./gradlew installDebug`

**Alternative Build Methods:**
- **Windows**: Run `build.bat` for automated build process
- **Linux/Mac**: Run `./build.sh` for automated build process

## Configuration

### App Configuration

- **Package Name**: `com.tagonn.app`
- **App Name**: Tagonn
- **Version**: 1.0.0
- **Base URL**: https://tagonn.com

### Permissions

The app requests the following permissions:

- **Internet**: Required for web content loading
- **Network State**: For connectivity checking
- **Camera**: For web features that require camera access
- **Microphone**: For web features that require audio input
- **Location**: For web features that require location access
- **Storage**: For caching and file access (optional)

## Customization

### Changing the Base URL

To change the website URL, modify the `base_url` string in `app/src/main/res/values/strings.xml`:

```xml
<string name="base_url">https://your-website.com</string>
```

### Customizing Colors

Edit the color values in `app/src/main/res/values/colors.xml` to match your brand:

```xml
<color name="primary">#YOUR_PRIMARY_COLOR</color>
<color name="primary_dark">#YOUR_PRIMARY_DARK_COLOR</color>
<color name="accent">#YOUR_ACCENT_COLOR</color>
```

### Modifying App Name

Change the app name in `app/src/main/res/values/strings.xml`:

```xml
<string name="app_name">Your App Name</string>
```

### Logo Options

The app includes multiple stylish logo designs:

1. **Main Logo** (`ic_launcher_foreground.xml`): Premium modern design with rounded rectangle background
2. **Alternative Logo** (`ic_launcher_foreground_alt.xml`): Geometric design with square background
3. **Minimalist Logo** (`ic_launcher_foreground_minimal.xml`): Clean circular design
4. **Premium Logo** (`ic_launcher_foreground_premium.xml`): Sophisticated design with corner details

To change the logo, replace the content of `ic_launcher_foreground.xml` with any of the alternative designs.

## Play Store Preparation

The app is configured for Play Store release with:

- **ProGuard**: Code obfuscation enabled for release builds
- **App Signing**: Ready for Play App Signing
- **Target API**: Android 14 (API 34)
- **Permissions**: Properly declared and handled
- **Privacy Policy**: Ready for Play Store requirements

## Troubleshooting

### Common Issues

1. **Build Errors**
   - Ensure you have the correct Android SDK version installed
   - Clean and rebuild the project: Build → Clean Project

2. **Memory Issues (Gradle 8.7)**
   - If you encounter "JVM garbage collector is thrashing" errors, the `gradle.properties` file is already configured with proper memory settings
   - The project uses 4GB heap space and 1GB metaspace
   - If issues persist, increase `org.gradle.jvmargs` in `gradle.properties`

3. **WebView Issues**
   - Check that JavaScript is enabled in WebView settings
   - Verify network permissions are granted

4. **Permission Issues**
   - Ensure runtime permissions are properly requested
   - Check that permission rationale dialogs are shown

### Debug Mode

For debugging, the app includes:
- Detailed error logging
- Network state monitoring
- WebView debugging support

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions, please contact the development team or create an issue in the project repository.
