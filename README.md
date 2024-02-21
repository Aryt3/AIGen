# AIGen

> [!NOTE]
> AIGen is an Android App which can leverage [AUTOMATIC1111s](https://github.com/AUTOMATIC1111/stable-diffusion-webui) stable-diffussion API endpoint to generate images and fetch them to your app. <br/>

## Homescreen

> [!NOTE]
> To enable the backend Application as an API we can launch it by using the command `python3 launch.py --nowebui`.<br>

The App offers the most basic settings at moment to generate an image. <br/>
`Properties:`
 - `prompt`: Text used to generate the image.
 - `steps`: Determines the intensity of image generation. Higher values increase processing time but generally yield better results.
 - `width`: Width of the generated image.
 - `height`: Height of the generated image.

### Examples

<div style="display: flex;">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/324a723a-c288-4ac0-afe0-28f9e85585a8" width="33%">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/bb6e231b-a592-48a5-bc5a-b6367b0ef459" width="33%">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/4b956621-84a6-4695-bd1d-7318931eb84a" width="33%">
</div>

You can use the green button located in the top-right corner to download the displayed image.

## Settings
In the settings page we can change the model we are using and the URL of our API.

<img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/9bad8ae9-59b0-4b60-9caa-e1af1ce578aa" width="33%">

The button located in the top-right corner is used to reload and retrieve all available models, which are then instantly displayed in the dropdown menu. The URL input field is provided for changing the endpoint to which the app sends its requests. The format for the IP address is as follows: `PROTOCOL://IP:PORT`. For example: `http://192.168.1.55:7861`. <br/>

After selecting the desired model from the dropdown menu and entering the correct IP address, simply press the button at the bottom to save the model change. You can now switch back to the homescreen and use the newly selected model.
