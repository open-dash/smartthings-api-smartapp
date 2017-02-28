/**
*  Copyright 2017 Open-Dash.com
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

//all API endpoints are defined here
mappings {
    // location
    path("/locations") 							{	action: [	GET: "listLocation"        														]}
    // contacts
    path("/contacts") 							{	action: [	GET: "listContacts"        														]}
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
    path("/devices/:id/capabilities") 			{	action: [	GET: "listDeviceCapabilities"        												]}   
    path("/devices/:id/:command")				{   action: [	GET: "sendDeviceCommand"          												]}    
    path("/devices/:id/:command/:secondary")	{   action: [   GET: "sendDeviceCommandSecondary"           									]}    
    path("/devices/commands")					{   action: [	POST: "sendDevicesCommands"         											]}     
    // routines
    path("/routines") 							{   action: [   GET: "listRoutines"        														]}
    path("/routines/:id") 						{   action: [   GET: "listRoutines",            	POST: "executeRoutine"        				]}
    // generic
    path("/updates") 							{   action: [   GET: "updates"        															]}
    path("/allDevices") 						{   action: [   GET: "allDevices"        														]}
    path("/deviceTypes")						{	action: [ 	GET: "listDeviceTypes" 															]}
    path("/weather")							{	action: [ 	GET: "getWeather" 																]}
    path("/webhook/:option")					{	action: [ 	GET: "getWebhook" 																]}
}

// our capabilities list
private def getCapabilities() {
    [   //Capability Prefrence Reference			Display Name					Subscribed Name						Subscribe Attribute(s)        
        ["capability.accelerationSensor",			"Accelaration Sensor",			"accelerations",					"acceleration"],
        ["capability.actuator",						"Actuator",						"actuators",						""],
        ["capability.alarm",						"Alarm",						"alarms",							"alarm"],
        ["capability.audioNotification",			"Audio Notification",			"audioNotifications",				""],
        ["capability.battery",						"Battery",						"batteries",						"battery"],
        ["capability.beacon",						"Beacon",						"beacons",							"presence"],
        ["capability.button",						"Button",						"buttons",							"button"],
        ["capability.carbonDioxideMeasurement",		"Carbon Dioxide Measurement",	"carbonDioxideMeasurements",		"carbonDioxide"],
        ["capability.carbonMonoxideDetector",		"Carbon Monoxide Detector",		"carbonMonoxideDetectors",			"carbonMonoxide"],
        ["capability.colorControl",					"Color Control",				"colorControls",					["color","hue","saturation"] ],
        ["capability.colorTemperature",				"Color Temperature",			"colorTemperatures",				"colorTemperature"],
        ["capability.consumable",					"Consumable",					"consumables",						"consumable"],
        ["capability.contactSensor",	  			"Contact",						"contactSensors",					"contact"],
        ["capability.doorControl",	  				"Door Control",					"doorControls",						"door"],
        ["capability.energyMeter",					"Energy Meter",					"energyMeters",						"energy"],
        ["capability.estimatedTimeOfArrival",		"ETA",							"estimatedTimeOfArrivals",			"eta"],
        ["capability.garageDoorControl",			"Garage Door Control",			"garageDoorControls",				"door"],
        ["capability.illuminanceMeasurement",		"Illuminance",					"illuminanceMeasurements",			"illuminance"],
        ["capability.imageCapture",					"Image Capture",				"imageCaptures",					"image"],
        ["capability.indicator",					"Indicator",					"indicators",						"indicatorStatus"],
        ["capability.lock" ,						"Lock",							"locks",							"lock"],
        ["capability.mediaController" ,				"Media Controller",				"mediaControllers",					["activities", "currentActivity"] ],
        ["capability.momentary" ,					"Momentary",					"momentaries",						""],
        ["capability.motionSensor",					"Motion",						"motionSensors",					"motion"],
        ["capability.musicPlayer",					"Music Player",					"musicPlayer",						["level", "mute", "status", "trackData", "trackDescription"] ],
        ["capability.pHMeasurement",				"pH Measurement",				"pHMeasurements",					"pH"],
        ["capability.powerMeter",					"Power Meter",					"powerMeters",						"power"],
        ["capability.power",						"Power",						"powers",							"powerSource"],
        ["capability.presenceSensor",				"Presence",						"presenceSensors",					"presence"],
        ["capability.relativeHumidityMeasurement",	"Humidity",						"relativeHumidityMeasurements",		"humidity"],
        ["capability.relaySwitch",					"Relay Switch",					"relaySwitches",					"switch"],
        ["capability.sensor",						"Sensor",						"sensors",							""],
        ["capability.shockSensor",					"Shock Sensor",					"shockSensors",						"shock"],
        ["capability.signalStrength",				"Signal Strength",				"signalStrengths",					""],
        ["capability.sleepSensor",					"Sleep Sensor",					"sleepSensors",						"sleeping"],
        ["capability.smokeDetector",				"Smoke Detector",				"smokeDetectors",					["smoke","carbonMonoxide"] ],
        ["capability.soundSensor",					"Sound Sensor",					"soundSensors",						"sound"],
        ["capability.speechRecognition",			"Speech Recognition",			"speechRecognitions",				"phraseSpoken"],
        ["capability.stepSensor",					"Step Sensor",					"stepSensors",						["goal","steps"] ],
        ["capability.switch",						"Switches", 					"switches",							"switch"],
        ["capability.switchLevel",					"Level",						"switchLevels",						"level"],
        ["capability.soundPressureLevel",			"Sound Pressure Level",			"soundPressureLevels",				"soundPressureLevel"],
        ["capability.tamperAlert",					"Tamper Alert",					"tamperAlert",						"tamper"],
        ["capability.temperatureMeasurement" , 		"Temperature", 					"temperatureMeasurements",			"temperature"],
        ["capability.thermostat" , 					"Thermostat", 					"thermostats",						["coolingSetpoint","heatingSetpoint","thermostatFanMode","thermostatMode","thermostatOperatingState","thermostatSetpoint"] ],
        ["capability.thermostatCoolingSetpoint" , 	"Thermostat Cooling Setpoint", 	"thermostatCoolingSetpoints",		"coolingSetpoint"],
        ["capability.thermostatFanMode" , 			"Thermostat Fan Mode", 			"thermostatFanModes",				"thermostatFanMode"],
        ["capability.thermostatHeatingSetpoint" , 	"Thermostat Heating Setpoint", 	"thermostatHeatingSetpoints",		"heatingSetpoint"],
        ["capability.thermostatMode" , 				"Thermostat Mode", 				"thermostatModes",					"thermostatMode"],
        ["capability.thermostatOperatingState",		"Thermostat Operating State",	"thermostatOperatingStates",		"thermostatOperatingState"],
        ["capability.thermostatSetpoint",			"Thermostat Setpoint",			"thermostatSetpoints",				"thermostatSetpoint"],
        ["capability.threeAxis",					"Three Axis",					"threeAxises",						"threeAxis"],
        ["capability.tone",							"Tone",							"tones",							""],
        ["capability.touchSensor",					"Touch Sensor",					"touchSensors",						"touch"],
        ["capability.trackingMusicPlayer",			"Tracking Music Player",		"trackingMusicPlayers",				""],
        ["capability.ultravioletIndex",				"Ultraviolet Index",			"ultravioletIndexes",				"ultravioletIndex"],
        ["capability.valve",						"Valve",						"valves",							["contact", "valve"] ],
        ["capability.voltageMeasurement",			"Voltage Measurement",			"voltageMeasurements",				"voltage"],
        ["capability.waterSensor",					"Water Sensor",					"waterSensors",						"water"],
        ["capability.windowShade",					"Window Shade",					"windowShades",						"windowShade"],
    ]  
}

// Approved Commands for device functions, if it's not in this list, it will not get called, regardless of what is sent.
private def getApprovedCommands() {
    ["on","off","toggle","setLevel","setColor","setHue","setSaturation","setColorTemperature","open","close","windowShade.open","windowShade.close","windowShade.presetPosition","lock","unlock","take","alarm.off","alarm.strobe","alarm.siren","alarm.both","thermostat.off","thermostat.heat","thermostat.cool","thermostat.auto","thermostat.emergencyHeat","thermostat.quickSetHeat","thermostat.quickSetCool","thermostat.setHeatingSetpoint","thermostat.setCoolingSetpoint","thermostat.setThermostatMode","fanOn","fanCirculate","fanAuto","setThermostatFanMode","play","pause","stop","nextTrack","previousTrack","mute","unmute","musicPlayer.setLevel","playText","playTextAndRestore","playTextAndResume","playTrack","playTrackAtVolume","playTrackAndRestore","playTrackAndResume","setTrack","setLocalLevel","resumeTrack","restoreTrack","speak","startActivity","getCurrentActivity","getAllActivities","push","beep","refresh","poll","low","med","high","left","right","up","down","home","presetOne","presetTwo","presetThree","presetFour","presetFive","presetSix","presetSeven","presetEight","presetCommand","startLoop","stopLoop","setLoopTime","setDirection","alert", "setAdjustedColor","allOn","allOff","deviceNotification", "setSchedule", "setTimeRemaining"]
}

// Map of commands and the data type expected to conform input values to.
private def getSecondaryType() {
    ["setLevel": Integer, "playText": String, "playTextAndResume": String, "playTextAndRestore": String, "playTrack" : String, "playTrackAndResume" : String, "playTrackAndRestore": String, "setColor": Map, "setHue": Integer, "setSaturation": Integer, "setColorTemperature": Integer, "startActivity": String, "restoreTrack" :String, "resumeTrack": String, "setTrack": String, "deviceNotification": String, "speak" : String, "setCoolingSetpoint": Integer, "setHeatingSetpoint": Integer, "setSchedule": JSON, "setThermostatFanMode": String, "setThermostatMode": String, "setTimeRemaining": Integer ]
}

preferences {
    section("About Open-Dash") {
        href(name: "hrefNotRequired",
             title: "About Open-Dash",
             required: false,
             style: "external",
             url: "https://open-dash.com/about/",
             description: "Tap to view the Open-Dash website in mobile browser")
    }
    section("Send Notifications?") {
        input("recipients", "contact", title: "Send notifications to", required:false) {
            input "phone", "phone", title: "Warn with text message (optional)",
                description: "Phone Number", required: false
        }
    }
    section("Enable Logging") {
        input("logging", "bool", title: "Enable Logging for debugging", required: false, default:false) 
    }
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

// Called on installed or updated from mobile app or oauth flow. 
def initialize() {
    debug("Initialize called")
    //init updates state var if null
    if (!state.updates) state.updates = []
    if (!state.webhook) state.webhook = false
    //loop through our capabilities list and subscribe to all devices if capability has something to subscribe to and route to eventHandler
    for (cap in capabilities) {
        if(cap[3] != "") {
            if(settings[cap[2]]) {
            	//if single attribute
                if (cap[3] instanceof String) {
                	subscribe(settings[cap[2]], cap[3], eventHandler)
                } else { //assume a map of attributes
            		cap[3].each {
            			subscribe(settings[cap[2]], it, eventHandler)
                	}	
                }
            }
        }
    }
    //subscribe to SHM location status changes and route to alarmHandler
    subscribe(location, "alarmSystemStatus", alarmHandler)
    
    //TODO Implement purging Updates state var on a schedule for events older than X days
    
    //TODO Remove before publication Testing Use Only
    try {
        if (!state.accessToken) {
            createAccessToken()
        }    
    def url = "Testing URL is " + getApiServerUrl() + "/api/smartapps/installations/${app.id}?access_token=${state.accessToken}"
    debug(url)
    }
    catch (e) {
    log.error "Error generating access token, make sure oauth is enabled in IDE, My SmartApps, Open-Dash, App Settings oauth section."
    }
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
	debug("alarmHandler called")
    if (!state.updates) state.updates = []
    def shm = eventJson(evt)
    shm.id = "shm"
    //update updates state variable with SHM status
    state.updates << shm
}

/**
* Gets the current state of the SHM object
*
* @return renders json
*/
def getSHMStatus() {
	debug("getSHMStatus called")
    def alarmSystemStatus = "${location?.currentState("alarmSystemStatus").stringValue}"
    debug("SHM Status is " + alarmSystemStatus)
    render contentType: "text/json", data: new JsonBuilder(alarmSystemStatus).toPrettyString()
}

/**
* Sets the state of the SHM object
*
* @return renders json
*/
def setSHMMode() {
	debug("setSHMMode called")
    def validmodes = ["off", "away", "stay"]
    def status = params?.mode
    def mode = validmodes?.find{it == status}
    if(mode) {
        debug("Setting SHM to $status in location: $location.name")
        sendLocationEvent(name: "alarmSystemStatus", value: status)
        render contentType: "text/json", data: new JsonBuilder(status).toPrettyString()
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
	debug("listLocation called")
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
    debug("Returning LOCATION: $result")
    //result
    render contentType: "text/json", data: new JsonBuilder(result).toPrettyString()
}

/****************************
* Contact Methods
****************************/

/**
* Gets the contact object
*
* @return renders json
*/
def listContacts() {
	debug("listContacts called")
    def results = []
    recipients?.each { 
        def result = [:]
        def contact = [ "deliveryType": it.deliveryType, "id": it.id, "label" : it.label, "name": it.name]
        def contactDetails = [ "hasSMS" : it.contact.hasSMS, "id": it.contact.id, "title": it.contact.title, pushProfile : it.contact.pushProfile as String, middleInitial: it.contact.middleInitial, firstName : it.contact.firstName, image: it.contact.image, initials: it.contact.initials, hasPush: it.contact.hasPush, lastName: it.contact.lastName, fullName : it.contact.fullName, hasEmail: it.contact.hasEmail]
        contact << [contact: contactDetails]
        results << contact
    }
    render contentType: "text/json", data: new JsonBuilder(results).toPrettyString()    
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
	debug("listHubs called")
    def result = []
    location.hubs?.each {
        result << getHub(it)
    }
    debug("Returning HUBS: $result")
    render contentType: "text/json", data: new JsonBuilder(result).toPrettyString()    
}

/**
* Gets the hub detail
*
* @param params.id is the hub id
* @return renders json
*/
def getHubDetail() {
	debug("getHubDetail called")
    def id = params?.id
    debug("getting hub detail for id: " + id)
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

        debug("Returning HUB: $result")
        render contentType: "text/json", data: new JsonBuilder(result).toPrettyString()
    }
}

/**
* Sends Notification
*
* @param notification details
* @return renders json
*/
def sendNotification() {
	debug("sendNotification called")
    def id = request.JSON?.id  //id of recipients
    debug("recipients configured: $recipients")
    def message = request.JSON?.message
    def method = request.JSON?.method
    if (location.contactBookEnabled && recipients) {
        debug("contact book enabled!")
        def recp = recipients.find{ it.id == id }
        debug(recp)
        if (recp) {
            sendNotificationToContacts(message, [recp])
        } else {
            sendNotificationToContacts(message, recipients)
        }
    } else {
        debug("contact book not enabled")
        if(method) {
            if(method == "sms") {
                if (phone) {
                    sendSms(phone, message)
                }
            } else if (method == "push") {
                sendPush(message)
            }
        }         
    }
    debug("In Notifications " + id)
    render contentType: "text/json", data: new JsonBuilder("message sent").toPrettyString()
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
	debug("listModes called")
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
        debug("Returning MODES: $result")
        render contentType: "text/json", data: new JsonBuilder(result).toPrettyString()
    }
}

/**
* Sets Mode for location
*
* @param params.id is the mode id
* @return renders json
*/
def switchMode() {
	debug("switchMode called")
    def id = params?.id
    def mode = location.modes?.find{it.id == id}
    if(mode) {
        debug("Setting mode to $mode.name in location: $location.name")
        location.setMode(mode.name)
        render contentType: "text/json", data: new JsonBuilder(mode.name).toPrettyString()
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
	debug("listRoutines called")
    def id = params?.id
    def results = []
    // if there is an id parameter, list only that routine. Otherwise list all routines in location
    if(id) {
        def routine = location.helloHome?.getPhrases().find{it.id == id}
        def myRoutine = [:]
        if(!routine) {
            httpError(404, "Routine not found")
        } else {
            render contentType: "text/json", data: new JsonBuilder(getRoutine(routine)).toPrettyString()            
        }
    } else {
        location.helloHome?.getPhrases().each { routine ->
            results << getRoutine(routine)
        }
        debug("Returning ROUTINES: $results")
        render contentType: "text/json", data: new JsonBuilder(results).toPrettyString()
    }
}

/**
* Executes Routine for location
*
* @param params.id is the routine id
* @return renders json
*/
def executeRoutine() {
	debug("executeRoutine called")
    def id = params?.id
    def routine = location.helloHome?.getPhrases().find{it.id == id}
    if(!routine) {
        httpError(404, "Routine not found")
    } else {
        debug("Executing Routine: $routine.label in location: $location.name")
        location.helloHome?.execute(routine.label)
        render contentType: "text/json", data: new JsonBuilder(routine).toPrettyString()
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
	debug("listDevices called")
    def id = params?.id
    // if there is an id parameter, list only that device. Otherwise list all devices in location
    if(id) {
        def device = findDevice(id)    
        render contentType: "text/json", data: new JsonBuilder(deviceItem(device, true)).toPrettyString()
    } else {
        def result = []
        result << allSubscribed.collect{deviceItem(it, false)}                
        render contentType: "text/json", data: new JsonBuilder(result[0]).toPrettyString()
    }
}

/**
* Gets Subscribed Device Events for location
*
* @param params.id is the device id
* @return renders json
*/
def listDeviceEvents() {
	debug("listDeviceEvents called")
    def numEvents = 20
    def id = params?.id
    def device = findDevice(id)

    if (!device) {
        httpError(404, "Device not found")
    } else {
        def events = device.events(max: numEvents)
        def result = events.collect{item(device, it)}
        render contentType: "text/json", data: new JsonBuilder(result).toPrettyString()
    }
}

/**
* Gets Subscribed Device Commands for location
*
* @param params.id is the device id
* @return renders json
*/
def listDeviceCommands() {
	debug("listDeviceCommands called")
    def id = params?.id
    def device = findDevice(id) 
    def result = []
    if(!device) {
        httpError(404, "Device not found")
    } else {
        device.supportedCommands?.each {
            result << ["command" : it.name ]
        }
    }
    render contentType: "text/json", data: new JsonBuilder(result).toPrettyString()
}

/**
* Gets Subscribed Device Capabilities for location
*
* @param params.id is the device id
* @return renders json
*/
def listDeviceCapabilities() {
	debug("listDeviceCapabilities called")
    def id = params?.id
    def device = findDevice(id) 
    def result = []
    if(!device) {
        httpError(404, "Device not found")
    } else {
        //device.capabilities?.each {
        //    result << ["capability" : it.name ]
        //}
        def caps = []
        device.capabilities?.each {
            caps << it.name 
            def attribs = []
            it.attributes?.each { i -> 
                attribs << [ "name": i.name, "dataType" : i.dataType ]
                if(i.values) {
                	def vals = []
                    i.values.each { v ->
                    	vals << v
                    }
                    attribs << [ "values" : vals]
                }
            }
            if (attribs) {
            	caps << ["attributes" : attribs ]
            }
        }
        result << ["capabilities" : caps] 
    }
    render contentType: "text/json", data: new JsonBuilder(result).toPrettyString()
}

/**
* Executes Command for list of Device Ids for location
*
* @param params.ids is a list of the device ids
* @return renders json
*/
def sendDevicesCommands() {
	debug("sendDevicesCommands called")
    def group = request.JSON?.group
    def results = []
    group.each {
        def device = findDevice(it?.id) 
        if(device) {
            if(!it.value) {
                if (approvedCommands.contains(it.command)) {
                    debug("Sending command ${it.command} to Device id ${it.id}")
                    log.debug(it.command)
                    if (it.command == "toggle") {
                    	it.command = "off"
                    	if (device.currentValue("switch") == "off") { it.command = "on" }
                    }
                    device."$it.command"()  
                    results << [ id : it.id, status : "success", command : it.command, state: [deviceItem(device, true)] ]
                }
            } else {                
                def commandType = secondaryType.find { i -> i.key == it.command.toString()}?.value
                debug(commandType)
                def secondary = it.value.asType(commandType) //TODO need to test all possible commandTypes and see if it converts properly
                debug("Sending command ${it.command} to Device id ${it.id} with value ${it.value}")
                device."$it.command"(secondary)
                results << [ id : it.id, status : "success", command : it.command, value : it.value, state: [deviceItem(device, true)] ]
            }
        } else {
            results << [ id : it.id, status : "not found" ]
        }
    }
    render contentType: "text/json", data: new JsonBuilder(results).toPrettyString()
}
/**
* Executes Supported Command for a Device
*
* @param params.ids is the device id, params.command is the command to send
* @return renders json
*/
def sendDeviceCommand() {
	debug("sendDeviceCommand called")
    def id = params?.id
    def device = findDevice(id) 
    def command = params.command
    def secondary_command = params.level
    if (approvedCommands.contains(command)) 
    {
        if (command == "toggle") {
            command = "off"
            if (device.currentValue("switch") == "off") { command = "on" }
        }
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
        debug("Executing command: $command on device: $device.displayName")
        render contentType: "text/json", data: new JsonBuilder(deviceItem(device, true)).toPrettyString()
    }
}

/**
* Executes Supported Command with secondary parameter for a Device
*
* @param params.ids is the device id, params.command is the command to send, params.command is the value for secondary command
* @return renders json
*/
def sendDeviceCommandSecondary() {
	debug("sendDeviceCommandSecondary called")
    def id = params?.id
    def device = findDevice(id) 
    def command = params?.command
    def commandType = secondaryType.find { it.key == command.toString()}?.value
    debug(commandType)
    def secondary = params?.secondary?.asType(commandType) //TODO need to test all possible commandTypes and see if it converts properly

    device."$command"(secondary)
    if(!command) {
        httpError(404, "Device not found")
    }
    if(!device) {
        httpError(404, "Device not found")
    } else {
        debug("Executing with secondary command: $command $secondary on device: $device.displayName")
        render contentType: "text/json", data: new JsonBuilder(deviceItem(device, true)).toPrettyString()
    }
}

/**
* Get the updates from state variable and returns them
*
* @return renders json
*/
def updates() {
	debug("updates called")
    //render out json of all updates since last html loaded
    render contentType: "text/json", data: new JsonBuilder(state.updates).toPrettyString()
}

/**
* Builds a map of all unique devices
*
* @return renders json
*/
def allDevices() {
	debug("allDevices called")
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
	debug("listDeviceTypes called")
    def deviceData = []
    allSubscribed?.each {
        it.collect{ i ->    
            if (!deviceData.contains(i?.typeName)) {
                deviceData << i?.typeName  
            }
        } 
    }
    render contentType: "text/json", data: new JsonBuilder(deviceData).toPrettyString()
}

/**
* Builds a map of useful weather data
*
* @return renders json
*/
def getWeather() {
	debug("getWeather called")
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
    def oldKeys = state.alertKeys?.jsonValue

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
    debug(obs)
    if (obs) {
        render contentType: "text/json", data: new JsonBuilder(obs).toPrettyString()
    }
}

/**
* Gets webhook on/off and updates state var
*
* @param params.id is the device id
* @return renders json
*/
def getWebhook() {
	debug("listDeviceEvents called")
    def option = params?.option
    if (option == "on") {
    	state.webhook = true
    } else if (option == "off") {
    	state.webhook = false
    } else {
        httpError(404, "Option not found")
    }
    render contentType: "text/json", data: new JsonBuilder(option).toPrettyString()
}

/**
* Handles the subscribed event and updates state variable
*
* @param evt is the event object
*/
def eventHandler(evt) {
	debug("eventHandler called")
    //send to webhook api
    if(state.webhook) {
        logField(evt) { it.toString() }
    }
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
	debug("getHub called")
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
    debug("Returning HUB: $result")
    result
}

/**
* WebHook API Call on Subscribed Change 
*
* @param evt is the event object, c is a Closure
*/
private logField(evt, Closure c) {
	debug("logField called")
    debug("The souce of this event is ${evt.source} and it was ${evt.id}")
    //TODO Use ASYNCHTTP Model instead
    //httpPostJson(uri: "#####SEND EVENTS TO YOUR ENDPOINT######",   body:[source: "smart_things", device: evt.deviceId, eventType: evt.name, value: evt.value, event_date: evt.isoDate, units: evt.unit, event_source: evt.source, state_changed: evt.isStateChange()]) {
    //    debug(evt.name+" Event data successfully posted")
    //}
}

/**
* Builds a map of all subscribed devices and returns a unique list of devices
*
* @return returns a unique list of devices
*/
private getAllSubscribed() {
	debug("getAllSubscribed called")
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
	debug("findDevice called")
    def device = null
    capabilities.find { 
        settings[it[2]].find { d ->
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
	debug("item called")
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
	debug("getRoutine called")
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
	debug("getMode called")
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
	debug("deviceItem called")
    if (!device) return null
    def results = [:]
    ["id", "name", "displayName"].each {
        results << [(it) : device."$it"]
    }

    if(explodedView) {
        def attrsAndVals = []
        device.supportedAttributes?.each {
        	def attribs = ["name" : (it.name), "currentValue" : device.currentValue(it.name), "dataType" : it.dataType]
            
            if(it.values) {
                	def vals = []
                    it.values.each { v ->
                    	vals << v
                    }
                    attribs << [ "values" : vals]
                }
                attrsAndVals << attribs
        }
        results << ["attributes" : attrsAndVals]
        
        def caps = []
        device.capabilities?.each {
            caps << it.name 
            def attribs = []
            it.attributes.each { i -> 
                attribs << [ "name": i.name, "dataType" : i.dataType ]
                if(i.values) {
                	def vals = []
                    i.values.each { v ->
                    	vals << v
                    }
                    attribs << [ "values" : vals]
                }
            }
            if (attribs) {
            	caps << ["attributes" : attribs ]
            }
        }
        results << ["capabilities" : caps] 
        
        def cmds = []
        device.supportedCommands?.each {
            cmds << it.name
        }
        results << ["commands" : cmds] 
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
	debug("eventJson called")
    def update = [:]
    update.id = evt.deviceId
    update.name = evt.name
    //find device by id
    def device = findDevice(evt.deviceId)
    def attrsAndVals = []
        device.supportedAttributes?.each {
        	def attribs = ["name" : (it.name), "currentValue" : device.currentValue(it.name), "dataType" : it.dataType]
            attrsAndVals << attribs
        }
    update.attributes =   attrsAndVals
    //update.value = evt.value
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
	debug("get called")
    getWeatherFeature(feature, zipCode)
}

/**
* Gets local Date based on TimeZone
*
* @param timeZone
* @return date
*/
private localDate(timeZone) {
	debug("localDate called")
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
	debug("estimateLux called")
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

//Debug Router to log events if logging is turned on
def debug(evt) {
	if (logging) {
    	log.debug evt
    }
}