# IP-Info-Plugin

A simple Minecraft plugin that displays detailed IP information for connecting players.

## Description
This Bukkit/Spigot plugin fetches and displays comprehensive IP information when players connect to your server, including location data, VPN detection, and network details.

## Features
- Fetches detailed IP information for connecting players
- Displays connection type (VPN/Proxy/Mobile/Datacenter)
- Shows geographical location (Country/State/City)
- Provides network information (ASN, Company, Route)
- Automatically logs connection details

## Installation
1. Download the .jar file
2. Place it in your server's `plugins` folder
3. Restart your server

## Output Format
The plugin provides information in the following format:
```
IP Information:

General Info:
IP: xxx.xxx.xxx.xxx
Is VPN: true/false
Is Proxy: true/false
Is Mobile: true/false
Is DataCenter: true/false

Location:
Country: [country]
State: [state]
City: [city]
Timezone: [timezone]

Network Info:
Company: [company_name]
Network: [network]
ASN: [asn_number]
Route: [route]
```

## Dependencies
- Bukkit/Spigot server
- Java 8 or higher
- Internet connection for API calls

## API Usage
This plugin uses the ipapi.is service for IP information. For production use, please ensure you have appropriate API credentials.

## License
MIT License

## Support
For issues and feature requests, please use the GitHub issue tracker.
