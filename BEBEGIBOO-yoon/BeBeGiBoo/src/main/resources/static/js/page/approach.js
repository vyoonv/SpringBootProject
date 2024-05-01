const container1 = document.querySelector("#container1");
const container2 = document.querySelector("#container2");
const container3 = document.querySelector("#container3");
const container4 = document.querySelector("#container4");


document.querySelector("#top-button").addEventListener("click", () => {

    window.scrollTo({top:0, behavior: 'smooth'});
});

container1.addEventListener("click", () => {

    window.scrollTo({top:900, behavior: 'smooth'});
});

container2.addEventListener("click", () => {

    window.scrollTo({top:1400, behavior: 'smooth'});
});

container3.addEventListener("click", () => {

    window.scrollTo({top:2100, behavior: 'smooth'});
});

container4.addEventListener("click", () => {

    window.scrollTo({top:3200, behavior: 'smooth'});
});