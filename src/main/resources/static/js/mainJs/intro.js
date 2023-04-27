
let x = 0;
let y = 0;
let mx = 0;
let my = 0;
let speed = 0.03;

let introAreaTxt01;
let introAreaTxt02;
let introAreaBtn;

window.onload = function(){

  introAreaTxt01 = document.getElementsByClassName("intro-Area--txt01")[0];
  introAreaTxt02 = document.getElementsByClassName("intro-Area--txt02")[0];
  // introAreaBtn = document.getElementsByClassName("musicMo")[0];
  
  window.addEventListener("mousemove", mouseFunc, false);

  function mouseFunc(e){
    x = (e.clientX - window.innerWidth / 2);
    y = (e.clientY - window.innerHeight / 2);
  }

  loop();
}

function loop() {
  mx += (x - mx) * speed;
  my += (y - my) * speed;

  introAreaTxt01.style.transform = "translate("+ -(mx/10)+"px, "+ -(my/10)+"px"+")";
  introAreaTxt02.style.transform = "translate("+ (mx/10)+"px, "+ (my/10)+"px"+")";
  // introAreaBtn.style.transform = "translate("+ (mx/30)+"px, "+ (my/30)+"px"+")";

  window.requestAnimationFrame(loop);
}




const docStyle = document.documentElement.style;
const aElem = document.querySelector(".musicMo");
const boundingClientRect = aElem.getBoundingClientRect();

aElem.onmousemove = function (e) {

  const x = e.clientX - boundingClientRect.left;
  const y = e.clientY - boundingClientRect.top;

  const xc = boundingClientRect.width / 2;
  const yc = boundingClientRect.height / 2;

  const dx = x - xc;
  const dy = y - yc;

  docStyle.setProperty('--rx', `${dy / -1}deg`);
  docStyle.setProperty('--ry', `${dx / 10}deg`);
};

aElem.onmouseleave = function (e) {

  docStyle.setProperty('--ty', '0');
  docStyle.setProperty('--rx', '0');
  docStyle.setProperty('--ry', '0');

};

aElem.onmousedown = function (e) {

  docStyle.setProperty('--tz', '-25px');

};

document.body.onmouseup = function (e) {

  docStyle.setProperty('--tz', '-12px');

};