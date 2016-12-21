/**
*  Copyright 2016 Open-Dash.com
*
*  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License. You may obtain a copy of the License at:
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
*  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
*  for the specific language governing permissions and limitations under the License.
*
*  Open-Dash API SmartApp
*
*  Author: Open-Dash
*  based on https://github.com/jodyalbritton/apismartapp/blob/master/endpoint.groovy
*  weather code from https://github.com/Dianoga/my-smartthings/blob/master/devicetypes/dianoga/weather-station.src/weather-station.groovy
*  
*  To Donate to this project please visit https://open-dash.com/donate/
*/

import groovy.json.JsonBuilder

definition(
    name: "Open-Dash ${appVersion()}",
    namespace: "opendash",
    author: "Open-Dash",
    description: "Open-Dash",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
)
def appVersion() {"0.0.4"}

mappings {
    // location
    path("/locations") 							{	action: [	GET: "listLocation"        														]}
    // modes    
    path("/modes") 								{   action: [   GET: "listModes"        														]}
    path("/modes/:id") 							{	action: [   GET: "switchMode"        														]}
    // hub
    path("/hubs") 								{   action: [   GET: "listHubs"		       														]}
    path("/hubs/:id") 							{   action: [   GET: "getHubDetail"        														]}
    // devices  
    path("/devices") 							{   action: [   GET: "listDevices"        														]}
    path("/devices/:id") 						{  	action: [   GET: "listDevices"        														]}
    path("/devices/:id/events") 				{   action: [   GET: "listDeviceEvents"        													]}
    path("/devices/:id/commands") 				{	action: [	GET: "listDeviceCommands"        												]}    
    path("/devices/:id/:command")				{   action: [	GET: "sendDeviceCommand"          												]}    
    path("/devices/:id/:command/:secondary")	{   action: [   GET: "sendDeviceCommandSecondary"           									]}   

    // Routines
    path("/routines") 							{   action: [   GET: "listRoutines"        														]}
    path("/routines/:id") 						{   action: [   GET: "listRoutines",            	POST: "executeRoutine"        				]}
    path("/updates") 							{   action: [   GET: "updates"        															]}
    path("/allDevices") 						{   action: [   GET: "allDevices"        														]}
    path("/devicetypes")						{	action: [ 	GET: "listDeviceTypes" 															]}
    path("/version")							{	action: [ 	GET: "getVersion" 																]}
    path("/weather")							{	action: [ 	GET: "getWeather" 																]}
}

private def getCapabilities() {
    [   //Capability Prefrence Reference			Display Name					Subscribed Name						Subscribe Attribute        
        ["capability.accelerationSensor",			"Accelaration Sensor",			"accelerations",					"acceleration"				],
        ["capability.actuator",						"Actuator",						"actuators",						""							],
        ["capability.alarm",						"Alarm",						"alarms",							"alarm"						],
        ["capability.audioNotification",			"Audio Notification",			"audioNotifications",				""							],
        ["capability.battery",						"Battery",						"batteries",						"battery"					],
        ["capability.beacon",						"Beacon",						"beacons",							"presence"					],
        ["capability.button",						"Button",						"buttons",							"button"					],
        ["capability.carbonDioxideMeasurement",		"Carbon Dioxide Measurement",	"carbonDioxideMeasurements",		"carbonDioxide"				],
        ["capability.carbonMonoxideDetector",		"Carbon Monoxide Detector",		"carbonMonoxideDetectors",			"carbonMonoxide"			],
        ["capability.colorControl",					"Color Control",				"colorControls",					""							],
        ["capability.colorTemperature",				"Color Temperature",			"colorTemperatures",				"colorTemperature"			],
        ["capability.consumable",					"Consumable",					"consumables",						"consumable"				],
        ["capability.contactSensor",	  			"Contact",						"contactSensors",					"contact"					],
        ["capability.doorControl",	  				"Door Control",					"doorControls",						"door"						],
        ["capability.energyMeter",					"Energy Meter",					"energyMeters",						"energy"					],
        ["capability.estimatedTimeOfArrival",		"ETA",							"estimatedTimeOfArrivals",			"eta"						],
        ["capability.garageDoorControl",			"Garage Door Control",			"garageDoorControls",				"door"						],
        ["capability.illuminanceMeasurement",		"Illuminance",					"illuminanceMeasurements",			"illuminance"				],
        ["capability.imageCapture",					"Image Capture",				"imageCaptures",					"image"						],
        ["capability.indicator",					"Indicator",					"indicators",						"indicatorStatus"			],
        ["capability.lock" ,						"Lock",							"locks",							"lock"						],
        ["capability.mediaController" ,				"Media Controller",				"mediaControllers",					""							],
        ["capability.momentary" ,					"Momentary",					"momentaries",						""							],
        ["capability.motionSensor",					"Motion",						"motionSensors",					"motion"					],
        ["capability.musicPlayer",					"Music Player",					"musicPlayer",						""							],
        ["capability.pHMeasurement",				"pH Measurement",				"pHMeasurements",					"pH"						],
        ["capability.motionSensor",					"Motion",						"motionSensors",					""							],
        ["capability.powerMeter",					"Power Meter",					"powerMeters",						"power"						],
        ["capability.power",						"Power",						"powers",							"powerSource"				],
        ["capability.presenceSensor",				"Presence",						"presenceSensors",					"presence"					],
        ["capability.relativeHumidityMeasurement",	"Humidity",						"relativeHumidityMeasurements",		"humidity"					],
        ["capability.relaySwitch",					"Relay Switch",					"relaySwitches",					"switch"					],
        ["capability.sensor",						"Sensor",						"sensors",							""							],
        ["capability.shockSensor",					"Shock Sensor",					"shockSensors",						"shock"						],
        ["capability.signalStrength",				"Signal Strength",				"signalStrengths",					""							],
        ["capability.sleepSensor",					"Sleep Sensor",					"sleepSensors",						"sleeping"					],
        ["capability.smokeDetector",				"Smoke Detector",				"smokeDetectors",					"smoke"						],
        ["capability.soundSensor",					"Sound Sensor",					"soundSensors",						"sound"						],
        ["capability.speechRecognition",			"Speech Recognition",			"speechRecognitions",				"phraseSpoken"				],
        ["capability.stepSensor",					"Step Sensor",					"stepSensors",						"steps"						],
        ["capability.switch",						"Switchs", 						"switches",							"switch"					],
        ["capability.switchLevel",					"Level",						"switchLevels",						"level"						],
        ["capability.soundPressureLevel",			"Sound Pressure Level",			"soundPressureLevels",				"soundPressureLevel"		],
        ["capability.tamperAlert",					"Tamper Alert",					"tamperAlert",						"tamper"					],
        ["capability.temperatureMeasurement" , 		"Temperature", 					"temperatureMeasurements",			"temperature"				],
        ["capability.thermostat" , 					"Thermostat", 					"thermostats",						""							],
        ["capability.thermostatCoolingSetpoint" , 	"Thermostat Cooling Setpoint", 	"thermostatCoolingSetpoints",		"coolingSetpoint"			],
        ["capability.thermostatFanMode" , 			"Thermostat Fan Mode", 			"thermostatFanModes",				"thermostatFanMode"			],
        ["capability.thermostatHeatingSetpoint" , 	"Thermostat Heating Setpoint", 	"thermostatHeatingSetpoints",		"heatingSetpoint"			],
        ["capability.thermostatMode" , 				"Thermostat Mode", 				"thermostatModes",					"thermostatMode"			],
        ["capability.thermostatOperatingState",		"Thermostat Operating State",	"thermostatOperatingStates",		"thermostatOperatingState"	],
        ["capability.thermostatSetpoint",			"Thermostat Setpoint",			"thermostatSetpoints",				"thermostatSetpoint"		],
        ["capability.threeAxis",					"Three Axis",					"threeAxises",						"threeAxis"					],
        ["capability.tone",							"Tone",							"tones",							""							],
        ["capability.touchSensor",					"Touch Sensor",					"touchSensors",						"touch"						],
        ["capability.trackingMusicPlayer",			"Tracking Music Player",		"trackingMusicPlayers",				""							],
        ["capability.ultravioletIndex",				"Ultraviolet Index",			"ultravioletIndexes",				"ultravioletIndex"			],
        ["capability.soundPressureLevel",			"Sound Pressure Level",			"soundPressureLevels",				""							],
        ["capability.valve",						"Valve",						"valves",							"contact"					],
        ["capability.voltageMeasurement",			"Voltage Measurement",			"voltageMeasurements",				"voltage"					],
        ["capability.waterSensor",					"Water Sensor",					"waterSensors",						"water"						],
        ["capability.windowShade",					"Window Shade",					"windowShades",						"windowShade"				],
    ]  
}

preferences {
    section("Allow Endpoint to Control These Things by Their Capabilities (You only need to choose one capability to get access to full device, however, selecting all capabilities will not create duplicate devices...") {
        for (cap in capabilities) {
            input cap[2], cap[0], title: "Select ${cap[1]} Devices", multiple:true, required: false
        }
    }
}

def installed() {
    initialize()
}

def updated() {
    unsubscribe()
    initialize()
}

def initialize() {
    for (cap in capabilities) {
        if(cap[3] != "") {
            log.debug cap
            subscribe(settings[cap[2]], cap[3], handleEvent)
        }
    }
}

/****************************
* Location Methods
****************************/
def listLocation() {
    def result = [:]
    ["contactBookEnabled", "name", "temperatureScale", "zipCode"].each {
        result << [(it) : location."$it"]
    }
    result << ["latitude" : location.latitude as String]
    result << ["longitude" : location.longitude as String]
    result << ["timeZone" : location.timeZone?.getDisplayName()]
    result << ["currentMode" : getMode(location.currentMode)]

    // add hubs for this location to the result
    def hubs = []
    location.hubs?.each {
        hubs << getHub(it)
    }
    result << ["hubs" : hubs]
    log.debug "Returning LOCATION: $result"
    result
}

/****************************
* Hubs Methods
****************************/
def listHubs() {
    def id = params.id
    // if there is an id parameter, list only that hub. Otherwise list all hubs in location
    if(id) {
        def hub = location.hubs?.find{it.id == id}
        if(hub) {
            getHub(hub, true)
        } else {
            httpError(404, "hub not found")
        }
    } else {
        def result = []
        location.hubs?.each {
            result << getHub(it)
        }
        log.debug "Returning HUBS: $result"
        result
    }
}

def getHub(hub, explodedView = false) {
    def result = [:]
    //put the id and name into the result
    ["id", "name"].each {
        result << [(it) : hub."$it"]
    }

    // if we want detailed information about this hub
    if(explodedView) {
        ["firmwareVersionString", "localIP", "localSrvPortTCP", "zigbeeEui", "zigbeeId"].each {
            result << [(it) : hub."$it"]
        }
        result << ["type" : hub.type as String]
    }
    log.debug "Returning HUB: $result"
    result
}

def getHubDetail() {
    def id = params.id
    log.debug "getting hub detail for id: " + id
    if(id) {
        def hub = location.hubs?.find{it.id == id}
        def result = [:]
        //put the id and name into the result
        ["id", "name"].each {
            result << [(it) : hub."$it"]
        }
        ["firmwareVersionString", "localIP", "localSrvPortTCP", "zigbeeEui", "zigbeeId"].each {
            result << [(it) : hub."$it"]
        }
        result << ["type" : hub.type as String]

        log.debug "Returning HUB: $result"
        result
    }
}

/****************************
* Modes API Commands
****************************/
def listModes() {
    def id = params.id
    // if there is an id parameter, list only that mode. Otherwise list all modes in location
    if(id) {
        def themode = location.modes?.find{it.id == id}
        if(themode) {
            getMode(themode, true)
        } else {
            httpError(404, "mode not found")
        }
    } else {
        def result = []
        location.modes?.each {
            result << getMode(it)
        }
        log.debug "Returning MODES: $result"
        result
    }
}

def switchMode() {
    def id = params.id
    def mode = location.modes?.find{it.id == id}
    if(mode) {
        log.debug "Setting mode to $mode.name in location: $location.name"
        location.setMode(mode.name)
        render contentType: "text/html", status: 201, data: "No Content"
    } else {
        httpError(404, "mode not found")
    }
}

/****************************
* Routine API Commands
****************************/
def listRoutines() {
    def id = params.id
    def results = []
    // if there is an id parameter, list only that routine. Otherwise list all routines in location
    if(id) {
        def routine = location.helloHome?.getPhrases().find{it.id == id}
        def myRoutine = [:]
        if(!routine) {
            httpError(404, "Routine not found")
        } else {
            getRoutine(routine)
        }
    } else {
        location.helloHome?.getPhrases().each { routine ->
            results << getRoutine(routine)
        }
        log.debug "Returning ROUTINES: $results"
        results
    }
}

def executeRoutine() {
    def id = params.id
    def routine = location.helloHome?.getPhrases().find{it.id == id}
    if(!routine) {
        httpError(404, "Routine not found")
    } else {
        log.debug "Executing Routine: $routine.label in location: $location.name"
        location.helloHome?.execute(routine.label)
        render contentType: "text/html", status: 204, data: "No Content"
    }
}

/****************************
* Device API Commands
****************************/
def listDevices() {
    def id = params.id
    // if there is an id parameter, list only that device. Otherwise list all devices in location
    if(id) {
        def device = allSubscribed?.find{it.id == id}
        deviceItem(device, true)
    } else {
        def result = []
        result << allSubscribed.collect{deviceItem(it, false)}
        log.debug "Returning DEVICES: $result"
        result[0]
    }
}

private deviceItem(device, explodedView) {
    if (!device) return null
    def results = [:]
    ["id", "name", "displayName"].each {
        results << [(it) : device."$it"]
    }

    if(explodedView) {
        def attrsAndVals = [:]
        device.supportedAttributes?.each {
            attrsAndVals << [(it.name) : device.currentValue(it.name)]
        }

        results << ["attributes" : attrsAndVals]
    }
    log.debug "Returning DEVICE: $results"
    results
}

def listDeviceEvents() {
    def numEvents = 20
    def id = params.id
    log.debug "In listDeviceEvents for device " + id
    def device = allSubscribed?.find{it.id == id}

    if (!device) {
        httpError(404, "Device not found")
    } else {
        log.debug "Retrieving last $numEvents events"
        def events = device.events(max: numEvents)
        def result = events.collect{item(device, it)}
        log.debug "Returnings EVENTS: $result"
        result
    }
}

def listDeviceCommands() {
    def id = params.id
    def device = allSubscribed?.find{it.id == id}
    def result = []
    if(!device) {
        httpError(404, "Device not found")
    } else {
        device.supportedCommands?.each {
            result << ["command" : it.name, "params"  : [:]]
        }
    }
    log.debug "Returning COMMANDS: $result"
    result
}

def sendDeviceCommand() {
    def id = params.id
    def device = allSubscribed?.find{it.id == id}
    def command = params.command
    def secondary_command = params.level

    device."$command"()
    if(!command) {
        httpError(404, "Device not found")
    }
    if(!device) {
        httpError(404, "Device not found")
    } else {
        log.debug "Executing command: $command on device: $device.displayName"
    }
}

def sendDeviceCommandSecondary() {
    def id = params.id
    def device = allSubscribed?.find{it.id == id}
    def command = params.command
    def secondary = params.secondary.toInteger()

    device."$command"(secondary)
    if(!command) {
        httpError(404, "Device not found")
    }
    if(!device) {
        httpError(404, "Device not found")
    } else {
        log.debug "Executing with secondary command: $command $secondary on device: $device.displayName"
    }
}

def updates() {
    //render out json of all updates since last html loaded
    render contentType: "text/json", data: new JsonBuilder(state.updates).toPrettyString()
}

def allDevices() {
    def allAttributes = []
    log.debug "${allSubscribed.size()} Unique Devices"

    allSubscribed.each {
        it.collect{ i ->
            def deviceData = [:]

            deviceData << [name: i?.displayName, label: i?.name, type: i?.typeName, id: i?.id, date: i?.events()[0]?.date, model: i?.modelName, manufacturer: i?.manufacturerName ]
            def attributes = [:]
            i.supportedAttributes.each {
                attributes << [(it.toString()) : i.currentState(it.toString())?.value]
            }
            deviceData << [ "attributes" : attributes ]
            deviceData << [ "commands" : i.supportedCommands.toString() ]
            allAttributes << deviceData
        }
    }
    render contentType: "text/json", data: new JsonBuilder(allAttributes).toPrettyString()
}

def listDeviceTypes() {
    def deviceData = []
    //def uniqueDevices = settings.collect { k, devices -> devices.findAll{k != "capability"} }.flatten().unique { it.id }
    log.debug "${allSubscribed.size()} Unique Devices" // is $uniqueDevices"

    allSubscribed.each {
        it.collect{ i ->    
            if (!deviceData.contains(i?.typeName)) {
                deviceData << i?.typeName  
            }
        } //.flatten().unique { it }
    }
    render contentType: "text/json", data: new JsonBuilder(deviceData).toPrettyString()
}

/* General API Functions */
def getVersion() {
    render contentType: "text/json", data: appVersion();
}

/* WebHook API Call on Subscribed Change */
private logField(evt, Closure c) {
    //log.debug "The souce of this event is ${evt.source} and it was ${evt.id}"

    //httpPostJson(uri: "#####SEND EVENTS TO YOUR ENDPOINT######",   body:[source: "smart_things", device: evt.deviceId, eventType: evt.name, value: evt.value, event_date: evt.isoDate, units: evt.unit, event_source: evt.source, state_changed: evt.isStateChange()]) {
    //    log.debug evt.name+" Event data successfully posted"
    //}
}

def getWeather() {
    // Current conditions
    def obs = get("conditions")?.current_observation

    // Sunrise / sunset
    def a = get("astronomy")?.moon_phase
    def today = localDate("GMT${obs.local_tz_offset}")
    def ltf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
    ltf.setTimeZone(TimeZone.getTimeZone("GMT${obs.local_tz_offset}"))
    def utf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    utf.setTimeZone(TimeZone.getTimeZone("GMT"))

    def sunriseDate = ltf.parse("${today} ${a.sunrise.hour}:${a.sunrise.minute}")
    def sunsetDate = ltf.parse("${today} ${a.sunset.hour}:${a.sunset.minute}")

    def tf = new java.text.SimpleDateFormat("h:mm a")
    tf.setTimeZone(TimeZone.getTimeZone("GMT${obs.local_tz_offset}"))
    def localSunrise = "${tf.format(sunriseDate)}"
    def localSunset = "${tf.format(sunsetDate)}"
    obs << [ sunrise : localSunrise ]
    obs << [ sunset : localSunset ]

    // Forecast
    def f = get("forecast")
    def f1= f?.forecast?.simpleforecast?.forecastday
    if (f1) {
        def icon = f1[0].icon_url.split("/")[-1].split("\\.")[0]
        def value = f1[0].pop as String // as String because of bug in determining state change of 0 numbers
        obs << [ percentPrecip : value ]
        obs << [ forecastIcon : icon ]
    }
    else {
        log.warn "Forecast not found"
    }
    obs << [ illuminance : estimateLux(sunriseDate, sunsetDate, weatherIcon) ]
    // Alerts
    def alerts = get("alerts")?.alerts
    def newKeys = alerts?.collect{it.type + it.date_epoch} ?: []
    log.debug "WUSTATION: newKeys: $newKeys"
    def oldKeys = state.alertKeys?.jsonValue
    log.debug "WUSTATION: oldKeys: $oldKeys"

    def noneString = "no current weather alerts"
    if (!newKeys && oldKeys == null) {
        obs << [alertKeys : newKeys.encodeAsJSON()]
        obs << [alertString : noneString]
    }
    else if (newKeys != oldKeys) {
        if (oldKeys == null) {
            oldKeys = []
        }
        //send(name: "alertKeys", value: newKeys.encodeAsJSON(), displayed: false)
        obs << [aleryKeys : newKeys.encodeAsJSON() ]
        def newAlerts = false
        alerts.each {alert ->
            if (!oldKeys.contains(alert.type + alert.date_epoch)) {
                def msg = "${alert.description} from ${alert.date} until ${alert.expires}"
                obs << [ alertString : alert.description ]
                newAlerts = true
            }
        }

        if (!newAlerts && device.currentValue("alert") != noneString) {
            obs << [ alertString : noneString ]
        }
    }

    log.debug obs
    log.debug obs.alertString
    if (obs) {
       return obs  
    }
}

/* Common Functions */
def handleEvent(evt) {
    //Find what we know about evt
    /*
log.debug evt
log.debug evt.date // Sun Mar 01 22:43:37 UTC 2015
log.debug evt.name // motion (capability type)
log.debug evt.displayName // name of the device in ST "Office aeon multi"
log.debug evt.value // the value of the capability type, open close inactive, active, etc.
log.debug evt.descriptionText // ex. Master Bath 1 switch is on
log.debug evt.description // zigbee or zwave raw data
log.debug evt.unit // could F or others
log.debug evt.type // null?
log.debug evt.user // null?
*/
    //log.debug evt.jsonValue

    //send to webhook api
    logField(evt) { it.toString() }

    def js = eventJson(evt) //.inspect().toString()
    if (!state.updates) state.updates = []
    def x = state.updates.findAll { js.id == it.id }

    if(x) {
        for(i in x) {
            state.updates.remove(i) 
        }
    }
    state.updates << js
}

private getAllSubscribed() {
    def dev_list = []
    capabilities.each { 
        dev_list << settings[it[2]] 
    }
    return dev_list?.findAll()?.flatten().unique { it.id }
}

private item(device, s) {
    device && s ? [device_id: device.id, 
                   label: device.displayName, 
                   name: s.name, value: s.value, 
                   date: s.date, stateChange: s.stateChange, 
                   eventSource: s.eventSource] : null
}

private getRoutine(routine) {
    def result = [:]
    ["id", "label"].each {
        result << [(it) : routine."$it"]
    }
    log.debug "Returning ROUTINE: $result"
    result
}

private getMode(mode, explodedView = false) {
    def result = [:]
    ["id", "name"].each {
        result << [(it) : mode."$it"]
    }

    if(explodedView) {
        ["locationId"].each {
            result << [(it) : mode."$it"]
        }
    }
    log.debug "Returning MODE: $result"
    result
}

private eventJson(evt) {
    def update = [:]
    update.id = evt.deviceId
    update.name = evt.name
    update.value = evt.value
    update.name = evt.displayName
    update.date = evt.isoDate
    //log.debug update
    return update
}

private get(feature) {
	getWeatherFeature(feature, zipCode)
}

private localDate(timeZone) {
	def df = new java.text.SimpleDateFormat("yyyy-MM-dd")
	df.setTimeZone(TimeZone.getTimeZone(timeZone))
	df.format(new Date())
}

private estimateLux(sunriseDate, sunsetDate, weatherIcon) {
	def lux = 0
	def now = new Date().time
	if (now > sunriseDate.time && now < sunsetDate.time) {
		//day
		switch(weatherIcon) {
			case 'tstorms':
				lux = 200
				break
			case ['cloudy', 'fog', 'rain', 'sleet', 'snow', 'flurries',
				'chanceflurries', 'chancerain', 'chancesleet',
				'chancesnow', 'chancetstorms']:
				lux = 1000
				break
			case 'mostlycloudy':
				lux = 2500
				break
			case ['partlysunny', 'partlycloudy', 'hazy']:
				lux = 7500
				break
			default:
				//sunny, clear
				lux = 10000
		}

		//adjust for dusk/dawn
		def afterSunrise = now - sunriseDate.time
		def beforeSunset = sunsetDate.time - now
		def oneHour = 1000 * 60 * 60

		if(afterSunrise < oneHour) {
			//dawn
			lux = (long)(lux * (afterSunrise/oneHour))
		} else if (beforeSunset < oneHour) {
			//dusk
			lux = (long)(lux * (beforeSunset/oneHour))
		}
	}
	else {
		//night - always set to 10 for now
		//could do calculations for dusk/dawn too
		lux = 10
	}

	lux
}


//TODO  Add location subscription subscribe(location, "alarmSystemStatus", alarmStatus) for alarmStatus function to store alarm state
//TODO get SHM status String alarmSystemStatus = "${location?.currentState("alarmSystemStatus").stringValue}"
//TODO create alarmStatus function for processing changes to alarm state aka SHM
//TODO update alarm state with this sendLocationEvent(name: "alarmSystemStatus", value: status)  values = off,away,stay
//TODO add commands and attributes defn, maybe liberate from https://github.com/ady624/webCoRE/blob/5f41cdcaf08616fb021021f7a4a4b3ccb1b7e239/smartapps/ady624/webcore.src/webcore.groovy
//TODO add function and endpoint for sending notifications
//TODO add endpoint for controlling a group of deviceids in JSON (for group off commands)
