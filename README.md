# dff.Cordova-IP-Plugin

Plugin to get device ip address.

## Supported platforms
 * Android

## Installation

```bash
$ cordova plugin add https://github.com/dff-solutions/dff.Cordova-IP-Plugin.git
```

## Usage

Plugin is available via global `IpAddress`.

### Actions

### get

```js
/**
 * Resolves a list of all devices` network interfaces and thier ip addresses.
 * @name get
 * @param {function} success Success callback.
 * @param {function} error Error callback.
 */
IpAddress
    .get(function (interface) {

    }, function (reason) {
        console.error(reason);
    });

```

## Documentation
- <a href="https://dff-solutions.github.io/dff.Cordova-IP-Plugin/" target="_blank" >JAVA DOC</a>
