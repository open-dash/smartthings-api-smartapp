Open-Dash SmartApp API Install Instructions
===================

Copy and Paste Method
* Copy the SmartApp Raw code from this repo
* Open IDE go to My SmartApps and create new SmartApp -> From Code and Paste code into box, Save

GitHub Integration Method
(coming soon)
C
If Using Open-Dash Core Server (meteor)
* Open My Smartapps and the Open-Dash SmartApp in IDE 
* Click on App Settings in upper right
* Enable OAUTH and note Client ID and Client Secret
* Enter "http://localhost:3000/auth/smartthings" in the Redirect URL box
* Start Open-Dash Meteor Server, visit localhost:3000 and login
* Go to Settings page, enter Client ID and Client Secret for SmartApp
* Go to Devices and start the oauth connection to SmartThings
* Once completed, you should see a list of devices and ability to view details of any device

If Just Testing Endpoint
* Install SmartApp via mobile app in Marketplace under myApps
* Select at least one device, no need to select the same device in multiple capabilities, but no worries if you do.
* Enable logging
* Open IDE live logging before saving, updating app in mobile
* Save / Update SmartApp
* In IDE live logging you should now see a testing URL, grab that for testing the endpoints
* Test each endpoint per the documentation adding the endpoint path before the "?access_token" in the URL via POSTMAN or other methods

NOTE: Do NOT share your testing URL, this grants irrevocable access to your smartapp install.  The only way to revoke this token is to uninstall the smartapp.
