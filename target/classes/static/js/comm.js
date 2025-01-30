//  ---> start Theme Change Function

// set theme on local store
function setTheme(theme, oldTheme) {
  localStorage.setItem("theme", theme);
  // remove class dark and light
  document.querySelector("html").classList.remove(oldTheme);
  // add class dark and light
  document.querySelector("html").classList.add(theme);
}

// get Theme on local store
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}

function changeTheme(currentTheme) {
  document.querySelector("html").classList.add(currentTheme);

  const changeThemeButton = document.querySelector("#theme_change");

  changeThemeButton.addEventListener("click", (e) => {
    const oldTheme = currentTheme;
    if (currentTheme === "dark") {
      currentTheme = "light";
    } else {
      currentTheme = "dark";
    }

    // chane the inner text dark and light
    changeThemeButton.querySelector("span").textContent = currentTheme;
    // set cuurent theme in localstore
    setTheme(currentTheme, oldTheme);
  });
}

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
  changeTheme(currentTheme);
});

//  end Theme Change Function  <---
