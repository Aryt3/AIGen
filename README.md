# AIGen
AIGen is an Android App which can generate AI images via stable-diffussion.<br><br>

## Homescreen
The App makes calls to the backend Image Generation Application from https://github.com/AUTOMATIC1111/stable-diffusion-webui.<br>
To enable the backend Application as an API we can launch it by using the command `python3 launch.py --nowebui`.<br>

<img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/67bb03a6-bebe-40e8-9923-c31069b84162)" width="25%">

After launching the App you are greeted by this screen.<br>
This is a simple GUI which only forwards the most basic properties, but it is sufficient for most cases.<br><br>
`Properties:`
 - `prompt`
 - `steps`
 - `width`
 - `height`

With just a Prompt you can already generate an Image which will be displayed in the GUI.

<div style="display: flex;">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/324a723a-c288-4ac0-afe0-28f9e85585a8" width="25%">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/bb6e231b-a592-48a5-bc5a-b6367b0ef459" width="25%">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/4b956621-84a6-4695-bd1d-7318931eb84a" width="25%">
</div>

The green Button in the Top Right Corner will download the image to your android device and instantly pop up in the phones gallery.<br><br>

## Settings
In the settings page we can change the model we are using and the URL of our API.

<img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/9bad8ae9-59b0-4b60-9caa-e1af1ce578aa" width="25%">

The Top Right Button is used to reload and pull all available models which will than instantly be displayed in the dropdown menu.<br>
The URL input field is here to change the endpoint the app is sending its requests to. The schema for the IP is as following `PROTOCOL://IP:PORT`. An example for this would be `http://192.168.1.55:7861`.<br>
After selecting the correct model from the spinner and input the correct IP we can just press the button at the bottom which will save our model change.<br>
You can now simply switch back to the Homescreen and use the new model.
