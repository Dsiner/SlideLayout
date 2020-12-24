# SlideLayout for Android

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![API](https://img.shields.io/badge/API-9%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=9)
[![Download](https://api.bintray.com/packages/dsiner/maven/slidelayout/images/download.svg) ](https://bintray.com/dsiner/maven/slidelayout/_latestVersion)

<a href="https://github.com/Dsiner/SlideLayout" target="_blank"><p align="center"><img src="https://github.com/Dsiner/SlideLayout/blob/master/logo/SL_Logotype.png" alt="SlideLayout" height="150px"></p></a>

## Demo
<img src="https://github.com/Dsiner/Resouce/blob/master/lib/SlideLayout/slidelayout.gif" width="320" alt="Screenshot"/>
<img src="https://github.com/Dsiner/Resouce/blob/master/lib/SlideLayout/slidelayout01.gif" width="320" alt="Screenshot"/>

## Setup
Maven:
```xml
<dependency>
  <groupId>com.dsiner.lib</groupId>
  <artifactId>slidelayout</artifactId>
  <version>1.0.4</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.dsiner.lib:slidelayout:1.0.4'
```

## How do I use it?

### Via XML ###
```xml
    <!-- Just contain two view -->
    <com.d.lib.slidelayout.SlideLayout
        android:layout_width="match_parent"
        android:layout_height="65dp">

        <!-- Content view -->
        <View
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

        <!-- Slide view -->
        <View
            android:layout_width="wrap_content"
            android:layout_height="match_parent" />
    </com.d.lib.slidelayout.SlideLayout>
```

### Operation ###
```java
        boolean isEnable();
        void setEnable(Boolean isEnable);

        boolean isOpen();
        void open();
        void close();
        void setOpen(boolean open, boolean withAnim);
```

### State change callback ###
Just implement `SlideLayout.OnStateChangeListener`:

```java
        .setOnStateChangeListener(new SlideLayout.OnStateChangeListener() {

            @Override
            public boolean onInterceptTouchEvent(SlideLayout layout) {
                return false;
            }

            @Override
            public void onStateChanged(SlideLayout layout, boolean open) {
                ...
            }
        });
```

### Parameter ###
| Attrs        | Type           | Function  |
| ------------- |:-------------:| -----:|
| sl_enable      | Boolean      | Enable   |
| sl_slideSlop   | Dimension    | Slop     |
| sl_duration    | Integer      | Duration |


More usage see [Demo](app/src/main/java/com/d/slidelayout/MainActivity.java)

## Latest Changes
- [Changelog.md](CHANGELOG.md)

## Contributors
- [Tebriz](https://github.com/tebriz159)  - Logo design contribution

## Licence

```txt
Copyright 2017 D

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
