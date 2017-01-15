# SmartThings Open-Dash API Documentation

Endpoints are accessable via:
`https://graph.api.smartthings.com:443/api/smartapps/installations/[smartapp installed uuid]/[endpoint]/`

You muse have the header:
`authorization: Bearer [token] ` 
where the `[token]` is the completed oauth2 authentication flow to the Smartapp.

NOTE:  Almost all endpoints right now only respond to a GET, this will be fixed later.

## Endpoints
/locations

/contacts

/modes

/modes/:id

/hubs

/hubs/:id

/shm

/shm/:mode

/notification  (PUT)

/devices

/devices/:id

/devices/:id/events

/devices/:id/commands

/devices/:id/command

/devices/:id/command/:secondary

/devices/commands

/routines

/routines/:id  (GET/POST)

/updates

/allDevices

/devicetypes

/weather



**/locations**

Get all locations attached to to authenticated account

returns json

example:

```
{
    "status": "ok",
    "data": [
        {
            "contactBookEnabled": true,
            "name": "Home",
            "temperatureScale": "F",
            "zipCode": "55446",
            "latitude": "43.07619000",
            "longitude": "-97.50923000",
            "timeZone": "Central Standard Time",
            "currentMode": {
                "id": "[UUID]",
                "name": "Home"
            },
            "hubs": [
                {
                    "id": "[UUID]",
                    "name": "Home Hub"
                }
            ]
        }
    ]
}
```

**/contacts**

Get all subscribed to contacts or phones in smartapp

return json

example:

```
{
    "status": "ok",
    "data": [
        [
            {
                "deliveryType": "PUSH",
                "id": "[UUID]",
                "label": "Patrick Stuart - PUSH",
                "name": "Push",
                "contact": {
                    "hasSMS": true,
                    "id": "[UUID]",
                    "title": "Patrick Stuart",
                    "pushProfile": "Patrick Stuart - PUSH",
                    "middleInitial": null,
                    "firstName": "Patrick",
                    "image": null,
                    "initials": "PS",
                    "hasPush": true,
                    "lastName": "Stuart",
                    "fullName": "Patrick Stuart",
                    "hasEmail": true
                }
            },
            {
                "deliveryType": "SMS",
                "id": "[UUID]",
                "label": "Patrick Stuart - SMS",
                "name": "cell",
                "contact": {
                    "hasSMS": true,
                    "id": "[UUID]e",
                    "title": "Patrick Stuart",
                    "pushProfile": "Patrick Stuart - PUSH",
                    "middleInitial": null,
                    "firstName": "Patrick",
                    "image": null,
                    "initials": "PS",
                    "hasPush": true,
                    "lastName": "Stuart",
                    "fullName": "Patrick Stuart",
                    "hasEmail": true
                }
            }
        ]
    ]
}
```
**/modes**

Get all modes attached to this account

returns json

example:

```
{
    "status": "ok",
    "data": [
        [
            {
                "id": "[UUID]",
                "name": "Home"
            },
            {
                "id": "[UUID]",
                "name": "Night"
            },
            {
                "id": "[UUID]",
                "name": "Away"
            }
        ]
    ]
}
```

**/modes/:id**

Set the mode via its UUID from /modes

returns json

example:

```
{
    "status": "ok",
    "data": [
        "Home"
    ]
}
```

**/hubs**

Get all hubs attached to this account

returns json

example:

```
{
    "status": "ok",
    "data": [
        [
            {
                "id": "[UUID]",
                "name": "Home Hub"
            }
        ]
    ]
}
```


**/hubs/:id**

Get hub information based on id

returns json

example:

```
{
    "status": "ok",
    "data": [
        {
            "id": "[UUID]",
            "name": "Home Hub",
            "firmwareVersionString": "000.016.00009",
            "localIP": "[redacted]",
            "localSrvPortTCP": "39500",
            "zigbeeEui": "[redacted]",
            "zigbeeId": "[redacted]",
            "type": "PHYSICAL"
        }
    ]
}
```
**/shm**

GET current state of Smart Home Monitor (SHM)

returns json

example:
```
{
    "status": "ok",
    "data": [
        "off"
    ]
}
```

**/shm/:mode**

GET to change current state of Smart Home Monitor (SHM)

valid :mode are "away", "home", "off"

returns json

example:
```
{
    "status": "ok",
    "data": [
        "off"
    ]
}
```

**/notification**

PUT Sends notification to a contact if address book is enabled

Send as json: 

id is from endpoint contacts
method is only valid if address book is not enabled

returns json

example:

```
{ 
    id: "[uuid]",
    message: "This is a test",
    method: "push"
}
```


returns json
**/routines**

Get all routines associated with Account

returns json

example:
```
{
    "status": "ok",
    "data": 
       [{
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
}
```

**/routines/:id**

GET
Get routine information

returns json

example:

```
{
    "status": "ok",
    "data": [
        {
            "id": "[UUID]",
            "label": "Good Morning!"
        }
    ]
}
```

POST
Execute routine

returns json

example: 

```
{
    "status": "ok",
    "data": [
        {
            "id": "[UUID]",
            "label": "Good Morning!",
            "hasSecureActions": false,
            "action": "/api/smartapps/installations/[UUID]/action/execute"
        }
    ]
}
```

**/devices**

Get list of devices

returns json

example:

```
{
    "status": "ok",
    "data": [{
		"id": "[uuid]",
		"name": "SmartSense Multi",
		"displayName": "Theater SmartSense Multi"
	}, {
		"id": "[uuid]",
		"name": "SmartSense Open/Closed Sensor",
		"displayName": "Front Door SmartSense Open/Closed Sensor"
	}
	]
}
```

**/devices/:id**

Get device info

returns json

example:

```
{
    "status": "ok",
    "data": [
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
	]
}
```

**/devices/:id/commands**

Get device commands

returns json

example:
```
{
    "status": "ok",
    "data": [{
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
}
```

**/devices/:id/:command**

Sends command to device id

returns json

example:

```
{
    "status": "ok",
    "data": [
        {
            "id": "[UUID]",
            "name": "ps_Control4_Dimmer_ZigbeeHA",
            "displayName": "Patrick Office Dimmer",
            "attributes": {
                "switch": "off",
                "level": 0
            }
        }
    ]
}
```

**/devices/:id/:command/:secondary**

Sends Secondary command to device id

returns json

example:

```
{
    "status": "ok",
    "data": [
        {
            "id": "[UUID]",
            "name": "ps_Control4_Dimmer_ZigbeeHA",
            "displayName": "Patrick Office Dimmer",
            "attributes": {
                "switch": "off",
                "level": 0
            }
        }
    ]
}
```

**/devices/:id/events**

Get Device Events

returns json

example:
```
{
    "status": "ok",
    "data": [{
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
}
```

**/devices/commands**

POST a list of device ids, commands and option value for batch Control 

```
{   
	group: [
    { id:"[UUID]",command:on },
    { id:"[UUID]",command:off },
    {id:"[UUID]",command:setLevel,value:100}
]}
```

returns json

example:

```
{
    "status": "ok",
    "data": [
        [
            {
                "id": "[UUID]",
                "status": "success",
                "command": "on",
                "state": [
                    {
                        "id": "[UUID]",
                        "name": "CentraLite Switch",
                        "displayName": "Patrick Office CentraLite Switch",
                        "attributes": {
                            "switch": "on",
                            "power": 0,
                            "checkInterval": 720
                        }
                    }
                ]
            },
            {
                "id": "[UUID]",
                "status": "not found"
            },
            {
                "id": "[UUID]",
                "status": "success",
                "command": "setLevel",
                "value": 100,
                "state": [
                    {
                        "id": "[UUID]",
                        "name": "ps_Control4_Dimmer_ZigbeeHA",
                        "displayName": "Patrick Office Dimmer",
                        "attributes": {
                            "switch": "on",
                            "level": 100
                        }
                    }
                ]
            }
        ]
    ]
}
```

**/updates**

Get last update for each device that has been queued up by the API

returns json

example:

```
{
    "status": "ok",
    "data": [{
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
}
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
{
    "status": "ok",
    "data": [
        [
            "SmartSense Multi",
            "SmartSense Multi Sensor",
            "Hue Bulb",
            "Hue Lux Bulb",
            "SmartPower Outlet",
            "zZ-Wave Schlage Touchscreen Lock",
            "Z-Wave Plus Window Shade",
            "Z-Wave Remote",
            "Aeon Minimote",
            "Z-Wave Lock Reporting",
            "zps_Control4_Dimmer_ZigbeeHA",
            "Z-Wave Metering Switch",
            "zIris Motion/Temp Sensor",
            "SmartSense Moisture Sensor",
            "SmartSense Motion Sensor",
            "zIris Open/Closed Sensor",
            "zCentralite Keypad",
            "SmartSense Open/Closed Sensor",
            "zLCF Control4 Controller",
            "zSmartWeather Station Tile HTML",
            "Samsung SmartCam"
        ]
    ]
}
```
**/weather**

Get current conditions for subscribed location

returns json

example:

```
{
    "status": "ok",
    "data": [
		{
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
	]
}
```
