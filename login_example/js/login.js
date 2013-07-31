DEFAULT_STATE = 0;
INSUFFICIENT_INPUT = 1;
HAS_INPUT = 2;
lastState = DEFAULT_STATE;

function check(){
  var currentState = checkFormState();
  if (currentState == lastState)
    return false;
  lastState = currentState;
  if (currentState == INSUFFICIENT_INPUT) {
    showMessage("Please input your name and password");
  } else {
    showMessage("Did you forget your password? <a href='reset_password.html?back_url=top.html'>Reset password</a>");
  }
  return false;
}

function getElementValueById(id) {
  return document.getElementById(id).value;
}

function checkFormState() {
  var name = getElementValueById("name");
  var password = getElementValueById("password");
  return (name || password) ? HAS_INPUT : INSUFFICIENT_INPUT;
}

function setClass(element, classValue) {
  element.setAttribute("class", classValue);
  element.setAttribute("className", classValue);
}

function showMessage(message) {
  var messageBox = document.getElementById("message");
  messageBox.innerHTML = message;
  if (message) {
    setClass(messageBox, "warning");
  } else {
    setClass(messageBox, "");
  }
}
