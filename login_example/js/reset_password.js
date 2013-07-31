function onload(){
  var param = window.location.search.replace( "?", "" );
  if (!param) {
    return;
  }
  var backUrlContainer = document.getElementById("back-url-container");
  var url = param.split("=")[1];
  backUrlContainer.innerHTML = "<a href='" + url + "'>Back</a>";
}
