# AIGen
AIGen is an Android App which can generate AI images via stable-diffussion.<br><br>

## Homescreen
The App makes calls to the backend Image Generation Application from https://github.com/AUTOMATIC1111/stable-diffusion-webui.<br>
To enable the backend Application as an API we can launch it by using the command `python3 launch.py --nowebui`.<br>

<img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/9d22c368-ac9c-49ba-a10c-c92c5e512a6d" width="25%">

After launching the App you are greeted by this screen.<br>
This is a simple GUI which only forwards the most basic properties, but it is sufficient for most cases.<br><br>
`Properties:`
 - `prompt`
 - `steps`
 - `width`
 - `height`

With just a Prompt you can already generate an Image which will be displayed in the GUI.

<div style="display: flex;">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/a1e5b6d0-37e2-41c8-a7fe-4e3fbcda5e36" width="25%">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/7205d6b2-7723-46b8-ac1c-ef92b04f591c" width="25%">
  <img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/168cc30e-1ec8-4c98-bb3b-de1409d402f8" width="25%">
</div>

The green Button in the Top Right Corner will download the image to your android device and instantly pop up in the phones gallery.<br><br>

## Settings
In the settings page we can change the model we are using.

<img src="https://github.com/Th1s1s1t/AIGen/assets/110562298/dc03a433-7a0a-4ee1-a5a7-17cc3d9764d7" width="25%">

The Top Right Button is used to reload and pull all available models which will than instantly be displayed in the Dropdownmenu.<br>
After selecting the correct model from the spinner we can just press the button at the bottom which will save our model change.<br>
You can now simply switch back to the Homescreen and use the new model.
