# Keycloak + [TrustPath.io](https://trustpath.io) Integration

This repository demonstrates how to integrate Keycloak with [TrustPath.io](https://trustpath.io) for advanced fraud detection and prevention. The integration helps protect your application from malicious account registrations and account takeover attempts.

## Overview

This integration leverages Keycloak's SPI system to evaluate user registration using [TrustPath.io](https://trustpath.io)'s risk evaluation API. The workflow automatically blocks suspicious activities before they can impact your application.

## Features

- Real-time fraud detection during user authentication
- Protection against account takeover attempts
- Malicious account registration prevention
- Automated risk scoring and decision making
- Seamless integration with Keycloak Auth workflows

## More detail about integration can be found at

[https://docs.trustpath.io/docs/integration-guide/keycloak-integration](https://docs.trustpath.io/docs/integration-guide/keycloak-integration)

## License

MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
