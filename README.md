# SmartThings Open-Dash API Documentation

Endpoints are accessable via:
`https://graph.api.smartthings.com:443/api/smartapps/installations/[smartapp installed uuid]/[endpoint]/`

You muse have the header:
`authorization: Bearer [token] ` 
where the `[token]` is the completed oauth2 authentication flow to the Smartapp.

NOTE:  Almost all endpoints right now only respond to a GET, this will be fixed later.

## Endpoints
**/locations**

Get all locations attached to to authenticated account

returns json

example:
```{
	"contactBookEnabled": true,
	"name": "Home",
	"temperatureScale": "F",
	"zipCode": "55446",
	"latitude": "44.07619000",
	"longitude": "-97.50923000",
	"timeZone": "Central Standard Time",
	"currentMode": {
		"id": "[uuid]",
		"name": "Home"
	},
	"hubs": [{
			"id": "[uuid]",
			"name": "Home Hub"
		}
	]
}
```

**/modes**

Get all modes attached to this account

returns json

example:
```[{
		"id": "[uuid]",
		"name": "Home"
	}, {
		"id": "[uuid]",
		"name": "Night"
	}, {
		"id": "[uuid]",
		"name": "Away"
	}
]
```
**/modes/:id**

Set the mode via its UUID from /modes

returns html  (no content)

**/hubs**

Get all hubs attached to this account

returns json

example:
```[{
		"id": "[uuid]",
		"name": "Home Hub",
		"firmwareVersionString": "000.016.00009",
		"localIP": "[redacted]",
		"localSrvPortTCP": "39500",
		"zigbeeEui": "[redacted]",
		"zigbeeId": "[redacted]",
		"type": "PHYSICAL"
	}
]
```

**/hubs/:id**

Get hub information based on id

returns json

example:
```[{
		"id": "[uuid]",
		"name": "Home Hub",
		"firmwareVersionString": "000.016.00009",
		"localIP": "[redacted]",
		"localSrvPortTCP": "39500",
		"zigbeeEui": "[redacted]",
		"zigbeeId": "[redacted]",
		"type": "PHYSICAL"
	}
]
```

**/routines**

Get all routines associated with Account

returns json

example:
```[{
		"id": "[uuid]",
		"label": "I'm Back!"
	},  {
		"id": "[uuid]",
		"label": "Good Night!"
	}, {
		"id": "[uuid]",
		"label": "Goodbye!"
	}
]
```

**/routines/:id**

GET
Get routine information

returns json

example:
```
{"id":"[uuid]","label":"I'm Back!"}
```

POST
Execute routine

returns html

```
success: 204
error: 404
```

**/devices**

Get list of devices

returns json

example:
```
[{
		"id": "[uuid]",
		"name": "SmartSense Multi",
		"displayName": "Theater SmartSense Multi"
	}, {
		"id": "[uuid]",
		"name": "SmartSense Open/Closed Sensor",
		"displayName": "Front Door SmartSense Open/Closed Sensor"
	}
]
```

**/devices/:id**

Get device info

returns json

example:
```
{
	"id": "[uuid]",
	"name": "SmartSense Multi",
	"displayName": "Theater SmartSense Multi",
	"attributes": {
		"temperature": 68,
		"battery": 1,
		"contact": "closed",
		"threeAxis": {
			"x": -9,
			"y": 65,
			"z": -1020
		},
		"acceleration": "inactive",
		"lqi": 100,
		"rssi": -46,
		"status": "closed"
	}
}
```

**/devices/:id/commands**

Get device commands

returns json

example:
```
[{
		"command": "on",
		"params": {}
	}, {
		"command": "off",
		"params": {}
	}, {
		"command": "setLevel",
		"params": {}
	}, {
		"command": "refresh",
		"params": {}
	}, {
		"command": "ping",
		"params": {}
	}, {
		"command": "refresh",
		"params": {}
	}
]
```

**/devices/:id/:command**

Sends command to device id

**/devices/:id/:command/:secondary**

Sends Secondary command to device id

**/devices/:id/events**

Get Device Events

returns json

example:
```
[{
		"device_id": "[uuid]",
		"label": "server room bulb",
		"name": "switch",
		"value": "off",
		"date": "2016-12-14T23:33:04Z",
		"stateChange": true,
		"eventSource": "DEVICE"
	}, {
		"device_id": "[uuid]",
		"label": "server room bulb",
		"name": "switch",
		"value": "on",
		"date": "2016-12-14T23:32:25Z",
		"stateChange": true,
		"eventSource": "DEVICE"
	}, {
		"device_id": "[uuid]",
		"label": "server room bulb",
		"name": "switch",
		"value": "off",
		"date": "2016-12-14T21:16:14Z",
		"stateChange": true,
		"eventSource": "DEVICE"
	}
]
```

**/updates**

Get last update for each device that has been queued up by the API

returns json

example:

```
[{
		"id": "[uuid]",
		"name": "Deck Door Lock",
		"value": "locked",
		"date": "2016-12-04T18:41:15.770Z"
	}, {
		"id": "[uuid]",
		"name": "Hue Lamp 1",
		"value": "1",
		"date": "2016-12-12T02:41:09.906Z"
	}
]
```

**/allDevices**

Get all devices subscribed to, with full details

returns json

example:
```
[{
		"name": "Theater SmartSense Multi",
		"label": "SmartSense Multi",
		"type": "SmartSense Multi",
		"id": "[uuid]",
		"date": "2016-12-15T15:00:48+0000",
		"model": null,
		"manufacturer": null,
		"attributes": {
			"temperature": "68",
			"battery": "1",
			"contact": "closed",
			"threeAxis": "-9,65,-1020",
			"acceleration": "inactive",
			"lqi": "100",
			"rssi": "-46",
			"status": "closed"
		},
		"commands": "[]"
	}, {
		"name": "Front Door SmartSense Open/Closed Sensor",
		"label": "SmartSense Open/Closed Sensor",
		"type": "SmartSense Multi Sensor",
		"id": "[uuid]",
		"date": "2016-12-15T15:08:51+0000",
		"model": "3300",
		"manufacturer": "CentraLite",
		"attributes": {
			"temperature": "58",
			"battery": "67",
			"contact": "closed",
			"threeAxis": null,
			"acceleration": null,
			"checkInterval": "720",
			"status": "closed"
		},
		"commands": "[configure, refresh, ping, enrollResponse]"
	}, 
]
```

**/devicetypes**

Get devicetype names for all subscribed devices

returns json

example:
```
[ "SmartSense Multi", "SmartSense Multi Sensor", "Hue Lux Bulb", "SmartPower Outlet", "zZ-Wave Schlage Touchscreen Lock", "Hue Bulb", "Z-Wave Plus Window Shade", "Dropcam", "Z-Wave Remote", "Aeon Minimote", "Z-Wave Lock Reporting", "zps_Control4_Dimmer_ZigbeeHA", "Z-Wave Metering Switch", "zIris Motion/Temp Sensor", "SmartSense Moisture Sensor", "SmartSense Motion Sensor", "zIris Open/Closed Sensor", "Aeon Multisensor", "zCentralite Keypad", "SmartSense Open/Closed Sensor", "zLCF Control4 Controller", "zSmartWeather Station Tile HTML", "Generic Video Camera", "Samsung SmartCam" ]
```
**/weather**

Get current conditions for subscribed location

returns json
example:
```{
	"wind_gust_mph": 0,
	"precip_1hr_metric": " 0",
	"precip_today_metric": "0",
	"pressure_trend": "-",
	"forecast_url": "http://www.wunderground.com/US/MN/Plymouth.html",
	"history_url": "http://www.wunderground.com/weatherstation/WXDailyHistory.asp?ID=KMNMAPLE23",
	"alertString": "Winter Storm Warning, Wind Chill Advisory",
	"estimated": {},
	"weather": "Mostly Cloudy",
	"windchill_string": "-11 F (-24 C)",
	"station_id": "KMNMAPLE23",
	"aleryKeys": "[\"WIN1481794620\"]",
	"UV": "0.0",
	"observation_epoch": "1481812776",
	"wind_gust_kph": 0,
	"precip_1hr_in": "0.00",
	"observation_time": "Last Updated on December 15, 8:39 AM CST",
	"feelslike_string": "-11 F (-24 C)",
	"temp_f": -10.7,
	"local_tz_long": "America/Chicago",
	"relative_humidity": "49%",
	"temp_c": -23.7,
	"image": {
		"title": "Weather Underground",
		"link": "http://www.wunderground.com",
		"url": "http://icons.wxug.com/graphics/wu2/logo_130x80.png"
	},
	"solarradiation": "22",
	"visibility_mi": "10.0",
	"observation_location": {
		"full": "Maple Grove, Minnesota",
		"elevation": "965 ft",
		"state": "Minnesota",
		"longitude": "-93.475601",
		"latitude": "45.067692",
		"country_iso3166": "US",
		"country": "US",
		"city": "Maple Grove"
	},
	"illuminance": 9408,
	"wind_mph": 0.0,
	"heat_index_c": "NA",
	"precip_today_string": "0.00 in (0 mm)",
	"observation_time_rfc822": "Thu, 15 Dec 2016 08:39:36 -0600",
	"feelslike_f": "-11",
	"heat_index_f": "NA",
	"feelslike_c": "-24",
	"heat_index_string": "NA",
	"forecastIcon": "mostlycloudy",
	"ob_url": "http://www.wunderground.com/cgi-bin/findweather/getForecast?query=44.067692,-95.475601",
	"dewpoint_string": "-25 F (-32 C)",
	"local_tz_offset": "-0600",
	"wind_kph": 0,
	"windchill_f": "-11",
	"windchill_c": "-24",
	"wind_degrees": 359,
	"pressure_in": "30.48",
	"percentPrecip": "10",
	"dewpoint_c": -32,
	"pressure_mb": "1032",
	"icon": "mostlycloudy",
	"local_time_rfc822": "Thu, 15 Dec 2016 08:39:51 -0600",
	"precip_1hr_string": "0.00 in ( 0 mm)",
	"icon_url": "http://icons.wxug.com/i/c/k/mostlycloudy.gif",
	"wind_dir": "North",
	"dewpoint_f": -25,
	"nowcast": "",
	"display_location": {
		"zip": "55446",
		"magic": "1",
		"full": "Plymouth, MN",
		"elevation": "303.9",
		"state": "MN",
		"wmo": "99999",
		"longitude": "-93.500000",
		"latitude": "45.070000",
		"state_name": "Minnesota",
		"country_iso3166": "US",
		"country": "US",
		"city": "Plymouth"
	},
	"visibility_km": "16.1",
	"sunset": "4:32 PM",
	"temperature_string": "-10.7 F (-23.7 C)",
	"local_tz_short": "CST",
	"sunrise": "7:46 AM",
	"local_epoch": "1481812791",
	"wind_string": "Calm",
	"precip_today_in": "0.00"
}
```
