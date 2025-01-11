"use strict";

console.log("Script loaded");
var currentTheme = getTheme(); //initial -->

document.addEventListener("DOMContentLoaded", function () {
  changeTheme();
}); //TODO:

function changeTheme() {
  //set to web page
  changePageTheme(currentTheme, ""); //set the listener to change theme button

  var changeThemeButton = document.querySelector("#theme_change_button");
  changeThemeButton.addEventListener("click", function (event) {
    var oldTheme = currentTheme;
    console.log("change theme button clicked");

    if (currentTheme === "dark") {
      //theme ko light
      currentTheme = "light";
    } else {
      //theme ko dark
      currentTheme = "dark";
    }

    console.log(currentTheme);
    changePageTheme(currentTheme, oldTheme);
  });
} //set theme to localstorage


function setTheme(theme) {
  localStorage.setItem("theme", theme);
} //get theme from localstorage


function getTheme() {
  var theme = localStorage.getItem("theme");
  return theme ? theme : "light";
} //change current page theme


function changePageTheme(theme, oldTheme) {
  //localstorage mein update karenge
  setTheme(currentTheme); //remove the current theme

  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  } //set the current theme


  document.querySelector("html").classList.add(theme); // change the text of button

  document.querySelector("#theme_change_button").querySelector("#Light").textContent = theme == "light" ? "" : "";
}

var toggleButton = document.getElementById('menu-toggle');
var menuContent = document.getElementById('menu-content');
toggleButton.addEventListener('click', function () {
  menuContent.classList.toggle('hidden');
});

function startLoading() {
  // Show loading message and hide the button
  document.getElementById('myButton').classList.add('hidden');
  document.getElementById('loader').classList.remove('hidden'); // Simulate a delay (e.g., 3 seconds)

  setTimeout(function () {
    // After delay, show the redirecting message
    document.getElementById('loader').textContent = "Redirecting..."; // Redirect after another 2 seconds

    setTimeout(function () {
      // Redirect to the next HTML page (change the URL to your target HTML file)
      window.open("/user/eventFormApplication", "_blank"); // Replace with the actual file name or URL
    }, 1000); // 2-second delay before redirection
  }, 2000); // 3-second delay for loading
}

setTimeout(function () {
  var toast = document.getElementById('toast-success');

  if (toast) {
    toast.classList.add('opacity-0');
    toast.classList.remove('opacity-100');
    setTimeout(function () {
      toast.style.display = 'none';
    }, 500); // Wait for the transition to finish
  }
}, 1000);
var dropdown = FlowbiteInstances.getInstance('Dropdown', 'dropdownTimepicker');
var $saveTimeButton = document.getElementById('saveTimeButton');
$saveTimeButton.addEventListener('click', function () {
  // save time code and then hide the dropdown
  dropdown.hide();
});