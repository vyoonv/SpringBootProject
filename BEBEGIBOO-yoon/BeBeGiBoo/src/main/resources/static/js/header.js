const hamburger = document.querySelector("#hamburger");
const navContainer = document.querySelector("#nav-container");

hamburger.addEventListener("click", () => {

    if(navContainer.style.visibility != "visible") {
        navContainer.style.visibility = "visible";
        navContainer.classList.add("act-down");
    } else {
        navContainer.style.visibility = "hidden";
        navContainer.classList.remove("act-down");
    }

});