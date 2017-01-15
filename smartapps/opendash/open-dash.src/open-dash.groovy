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
    name: "Open-Dash",
    namespace: "opendash",
    author: "Open-Dash",
    description: "Open-Dash",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
)

mappings {
    // location
    path("/locations") 							{	action: [	GET: "listLocation"        														]}
    // modes    
    path("/modes") 								{   action: [   GET: "listModes"        														]}
    path("/modes/:id") 							{	action: [   GET: "switchMode"        														]}
    // hub
    path("/hubs") 								{   action: [   GET: "listHubs"		       														]}
    path("/hubs/:id") 							{   action: [   GET: "getHubDetail"        														]}
    // shm
    path("/shm") 								{   action: [   GET: "getSHMStatus"        														]}
    path("/shm/:mode") 							{   action: [   GET: "setSHMMode"        														]}
    path("/notification") 						{   action: [   POST: "sendNotification"     													]}
    // devices  
    path("/devices") 							{   action: [   GET: "listDevices"        														]}
    path("/devices/:id") 						{  	action: [   GET: "listDevices"        														]}
    path("/devices/:id/events") 				{   action: [   GET: "listDeviceEvents"        													]}
    path("/devices/:id/commands") 				{	action: [	GET: "listDeviceCommands"        												]}    
    path("/devices/:id/:command")				{   action: [	GET: "sendDeviceCommand"          												]}    
    path("/devices/:id/:command/:secondary")	{   action: [   GET: "sendDeviceCommandSecondary"           									]}    
    path("/devices/:command")					{   action: [	POST: "sendDevicesCommand"          											]}     
    // routines
    path("/routines") 							{   action: [   GET: "listRoutines"        														]}
    path("/routines/:id") 						{   action: [   GET: "listRoutines",            	POST: "executeRoutine"        				]}
    // generic
    path("/updates") 							{   action: [   GET: "updates"        															]}
    path("/allDevices") 						{   action: [   GET: "allDevices"        														]}
    path("/deviceTypes")						{	action: [ 	GET: "listDeviceTypes" 															]}
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
        ["capability.switch",						"Switches", 						"switches",							"switch"					],
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

private def getApprovedCommands() {
    ["on","off","toggle","setLevel","setColor","setHue","setSaturation","setColorTemperature","open","close","windowShade.open","windowShade.close","windowShade.presetPosition","lock","unlock","take","alarm.off","alarm.strobe","alarm.siren","alarm.both","thermostat.off","thermostat.heat","thermostat.cool","thermostat.auto","thermostat.emergencyHeat","thermostat.quickSetHeat","thermostat.quickSetCool","thermostat.setHeatingSetpoint","thermostat.setCoolingSetpoint","thermostat.setThermostatMode","fanOn","fanCirculate","fanAuto","setThermostatFanMode","play","pause","stop","nextTrack","previousTrack","mute","unmute","musicPlayer.setLevel","playText","playTextAndRestore","playTextAndResume","playTrack","playTrackAtVolume","playTrackAndRestore","playTrackAndResume","setTrack","setLocalLevel","resumeTrack","restoreTrack","speak","startActivity","getCurrentActivity","getAllActivities","push","beep","refresh","poll","low","med","high","left","right","up","down","home","presetOne","presetTwo","presetThree","presetFour","presetFive","presetSix","presetSeven","presetEight","presetCommand","startLoop","stopLoop","setLoopTime","setDirection","alert","setAdjustedColor","allOn","allOff"]
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
    log.debug "Initialize called"
    if (!state.updates) state.updates = []

    for (cap in capabilities) {
        if(cap[3] != "") {
            subscribe(settings[cap[2]], cap[3], handleEvent)
        }
    }
    subscribe(location, "alarmSystemStatus", alarmHandler)  
    //TODO Remove before publication Testing Use Only
    if (!state.accessToken) {
        createAccessToken()
    }
    def url = "Testing URL is " + getApiServerUrl() + "/api/smartapps/installations/${app.id}?access_token=${state.accessToken}"
    log.debug url
    //TODO End removal area
}

/****************************
* Alarm Methods
****************************/

/**
* Handles the subscribed event from a change in SHM status and stores that in updates state variable
*
* @param evt from location object.
*/
def alarmHandler(evt) {
    if (!state.updates) state.updates = []
    //evt.value = ["stay","away","off"]
    def shm = eventJson(evt)
    shm.id = "shm"
    state.updates << shm
}

/**
* Gets the current state of the SHM object
*
* @return renders json
*/
def getSHMStatus() {
    def alarmSystemStatus = "${location?.currentState("alarmSystemStatus").stringValue}"
    log.debug "SHM Status is " + alarmSystemStatus
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[alarmSystemStatus]]).toPrettyString()
}

/**
* Sets the state of the SHM object
*
* @return renders json
*/
def setSHMMode() {
    def validmodes = ["off", "away", "stay"]
    def status = params.mode
    def mode = validmodes?.find{it == status}
    if(mode) {
        log.debug "Setting SHM to $status in location: $location.name"
        sendLocationEvent(name: "alarmSystemStatus", value: status)
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[status]]).toPrettyString()
    } else {
        httpError(404, "mode not found")
    }
}

/****************************
* Location Methods
****************************/

/**
* Gets the location object
*
* @return renders json
*/
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
    //log.debug "Returning LOCATION: $result"
    //result
    render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[result]]).toPrettyString()
}

/****************************
* Hubs Methods
****************************/

/**
* Gets the hubs object
*
* @return renders json
*/
def listHubs() {
    def result = []
    location.hubs?.each {
        result << getHub(it)
    }
    log.debug "Returning HUBS: $result"
    render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[result]]).toPrettyString()    
}

/**
* Gets the hub detail
*
* @param params.id is the hub id
* @return renders json
*/
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
        ["firmwareVersionString", "localIP", "localSrvPortTCP", "zigbeeEui", "zigbeeId", "type"].each {
            result << [(it) : hub."$it"]
        }
        result << ["type" : hub.type as String]

        log.debug "Returning HUB: $result"
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[result]]).toPrettyString()
    }
}

/**
* Sends Notification
*
* @param notification details
* @return renders json
*/
def sendNotification() {
    //TODO Implement Notification Endpoint
    def id = request.JSON?.id
    log.debug "In Notifications " + id
    render contentType: "text/json", data: new JsonBuilder([status: "ok", data:["not implemented"]]).toPrettyString()
}

/****************************
* Modes API Commands
****************************/

/**
* Gets Modes for location, if params.id is provided, get details for that mode
*
* @param params.id is the mode id
* @return renders json
*/
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
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[result]]).toPrettyString()
    }
}

/**
* Sets Mode for location
*
* @param params.id is the mode id
* @return renders json
*/
def switchMode() {
    def id = params.id
    def mode = location.modes?.find{it.id == id}
    if(mode) {
        log.debug "Setting mode to $mode.name in location: $location.name"
        location.setMode(mode.name)
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[mode.name]]).toPrettyString()
    } else {
        httpError(404, "mode not found")
    }
}

/****************************
* Routine API Commands
****************************/

/**
* Gets Routines for location, if params.id is provided, get details for that Routine
*
* @param params.id is the routine id
* @return renders json
*/
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
       	render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[getRoutine(routine)]]).toPrettyString()            
        }
    } else {
        location.helloHome?.getPhrases().each { routine ->
            results << getRoutine(routine)
        }
        log.debug "Returning ROUTINES: $results"
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[results]]).toPrettyString()
    }
}

/**
* Executes Routine for location
*
* @param params.id is the routine id
* @return renders json
*/
def executeRoutine() {
    def id = params.id
    def routine = location.helloHome?.getPhrases().find{it.id == id}
    if(!routine) {
        httpError(404, "Routine not found")
    } else {
        log.debug "Executing Routine: $routine.label in location: $location.name"
        location.helloHome?.execute(routine.label)
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[routine]]).toPrettyString()
    }
}

/****************************
* Device API Commands
****************************/

/**
* Gets Subscribed Devices for location, if params.id is provided, get details for that device
*
* @param params.id is the device id
* @return renders json
*/
def listDevices() {
    def id = params.id
    // if there is an id parameter, list only that device. Otherwise list all devices in location
    if(id) {
        def device = findDevice(id)
        log.debug device      
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[deviceItem(device, true)]]).toPrettyString()
    } else {
        def result = []
        result << allSubscribed.collect{deviceItem(it, false)}                
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[result[0]]]).toPrettyString()
    }
}

/**
* Gets Subscribed Device Events for location
*
* @param params.id is the device id
* @return renders json
*/
def listDeviceEvents() {
    def numEvents = 20
    def id = params.id
    def device = findDevice(id)

    if (!device) {
        httpError(404, "Device not found")
    } else {
        def events = device.events(max: numEvents)
        def result = events.collect{item(device, it)}
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[result]]).toPrettyString()
    }
}

/**
* Gets Subscribed Device Commands for location
*
* @param params.id is the device id
* @return renders json
*/
def listDeviceCommands() {
    def id = params.id
    def device = findDevice(id) 
    def result = []
    if(!device) {
        httpError(404, "Device not found")
    } else {
        device.supportedCommands?.each {
            result << ["command" : it.name, "params"  : [:]]
        }
    }
    render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[result]]).toPrettyString()
}

/**
* Executes Command for list of Device Ids for location
*
* @param params.ids is a list of the device ids
* @return renders json
*/
def sendDevicesCommands() {
    //TODO get list of device id's from POST
    //LOOP through device ids
    //if (approvedCommands.contains(command)) {
    //device."$command"()  
    //}
    //END LOOP
    //RETURN list of ids and succsess / fail for each
}

/**
* Executes Supported Command for a Device
*
* @param params.ids is the device id, params.command is the command to send
* @return renders json
*/
def sendDeviceCommand() {
    def id = params.id
    def device = findDevice(id) 
    def command = params.command
    def secondary_command = params.level
    if (approvedCommands.contains(command)) {
        device."$command"()  
    } else  {
        httpError(404, "Command not found")
    }
    if(!command) {
        httpError(404, "Device not found")
    }
    if(!device) {
        httpError(404, "Device not found")
    } else {
        log.debug "Executing command: $command on device: $device.displayName"
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[deviceItem(device, true)]]).toPrettyString()
    }
}

/**
* Executes Supported Command with secondary parameter for a Device
*
* @param params.ids is the device id, params.command is the command to send, params.command is the value for secondary command
* @return renders json
*/
def sendDeviceCommandSecondary() {
    def id = params.id
    def device = findDevice(id) 
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
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[deviceItem(device, true)]]).toPrettyString()
    }
}

/**
* Get the updates from state variable and returns them
*
* @return renders json
*/
def updates() {
    //render out json of all updates since last html loaded
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[state.updates]]).toPrettyString()
}

/**
* Builds a map of all unique devices
*
* @return renders json
*/
def allDevices() {
    def allAttributes = []

    allSubscribed.each {
        it.collect{ i ->
            def deviceData = [:]

            deviceData << [name: i?.displayName, label: i?.name, type: i?.typeName, id: i?.id, date: i?.events()[0]?.date, model: i?.modelName, manufacturer: i?.manufacturerName ]
            def attributes = [:]
            i.supportedAttributes.each {
                attributes << [(it.toString()) : i.currentState(it.toString())?.value]
            }
            deviceData << [ "attributes" : attributes ]
            def cmds = []
            i.supportedCommands?.each {
                cmds << ["command" : it.name ]
            }
            deviceData << [ "commands" : cmds ] //i.supportedCommands.toString() ]  //TODO fix this to parse to an object
            allAttributes << deviceData
        }
    }
    render contentType: "text/json", data: new JsonBuilder(allAttributes).toPrettyString()
}

/**
* Builds a map of all unique devicesTypes
*
* @return renders json
*/
def listDeviceTypes() {
    def deviceData = []
    allSubscribed?.each {
        it.collect{ i ->    
            if (!deviceData.contains(i?.typeName)) {
                deviceData << i?.typeName  
            }
        } 
    }
    render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[deviceData]]).toPrettyString()
}

/**
* Builds a map of useful weather data
*
* @return renders json
*/
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
    if (obs) {
        render contentType: "text/json", data: new JsonBuilder([status: "ok", data:[obs]]).toPrettyString()
    }
}

/****************************
* Private Methods
****************************/

/**
* Builds a map of hub details
*
* @param hub id (optional), explodedView to show details
* @return a map of hub
*/
private getHub(hub, explodedView = false) {
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

/**
* Handles the subscribed event and updates state variable
*
* @param evt is the event object
*/
private handleEvent(evt) {

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

/**
* WebHook API Call on Subscribed Change 
*
* @param evt is the event object, c is a Closure
*/
private logField(evt, Closure c) {
    //log.debug "The souce of this event is ${evt.source} and it was ${evt.id}"
    //TODO Use ASYNCHTTP Model instead
    //httpPostJson(uri: "#####SEND EVENTS TO YOUR ENDPOINT######",   body:[source: "smart_things", device: evt.deviceId, eventType: evt.name, value: evt.value, event_date: evt.isoDate, units: evt.unit, event_source: evt.source, state_changed: evt.isStateChange()]) {
    //    log.debug evt.name+" Event data successfully posted"
    //}
}

/**
* Builds a map of all subscribed devices and returns a unique list of devices
*
* @return returns a unique list of devices
*/
private getAllSubscribed() {
    def dev_list = []
    capabilities.each { 
        dev_list << settings[it[2]] 
    }
    return dev_list?.findAll()?.flatten().unique { it.id }
}

/**
* finds a device by id in subscribed capabilities
*
* @param id is a device uuid
* @return device object
*/
def findDevice(id) {
	def device = null
    capabilities.find { 
        settings[it[2]].find { d ->
        	//log.debug id + " : " + d.id
            if (d.id == id) {
            	device = d
                return true
            }
            
        }
    }
    return device
}

/**
* Builds a map of device items
*
* @param device object and s true/false
* @return a map of device details
*/
private item(device, s) {
    device && s ? [device_id: device.id, 
                   label: device.displayName, 
                   name: s.name, value: s.value, 
                   date: s.date, stateChange: s.stateChange, 
                   eventSource: s.eventSource] : null
}

/**
* gets Routine information
*
* @param routine object
* @return a map of routine information
*/
private getRoutine(routine) {
    def result = [:]
    ["id", "label"].each {
        result << [(it) : routine."$it"]
    }
    result
}

/**
* gets mode information
*
* @param mode object
* @return a map of mode information
*/
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
    result
}

/**
* Builds a map of device details including attributes
*
* @param device is the device object, explodedView is true/false
* @return device details
*/
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
    results
}

/**
* Builds a map of event details based on event
*
* @param evt is the event object
* @return a map of event details
*/
private eventJson(evt) {
    def update = [:]
    update.id = evt.deviceId
    update.name = evt.name
    update.value = evt.value
    update.name = evt.displayName
    update.date = evt.isoDate
    return update
}

/**
* Gets the weather feature based on location / zipcode
*
* @param feature is the weather parameter to get
* @return weather information
*/
private get(feature) {
    getWeatherFeature(feature, zipCode)
}

/**
* Gets local Date based on TimeZone
*
* @param timeZone
* @return date
*/
private localDate(timeZone) {
    def df = new java.text.SimpleDateFormat("yyyy-MM-dd")
    df.setTimeZone(TimeZone.getTimeZone(timeZone))
    df.format(new Date())
}

/**
* Estimates current light level (LUX) based on weather info
*
* @param sunriseDate is day of sunrise, sunsetDate is day of sunset, weatherIcon is a string
* @return estimated lux value
*/
private estimateLux(sunriseDate, sunsetDate, weatherIcon) {
    def lux = 0
    def now = new Date().time
    if (now > sunriseDate.time && now < sunsetDate.time) {
        //day
        switch(weatherIcon) {
            case 'tstorms':
            lux = 200
            break
            case ['cloudy', 'fog', 'rain', 'sleet', 'snow', 'flurries', 'chanceflurries', 'chancerain', 'chancesleet', 'chancesnow', 'chancetstorms']:
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