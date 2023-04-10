
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
  introAreaBtn = document.getElementsByClassName("muove-img")[0];
  
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
  introAreaTxt02.style.transform = "translate("+ -(mx/10)+"px, "+ -(my/10)+"px"+")";
  introAreaBtn.style.transform = "translate("+ (mx/30)+"px, "+ (my/30)+"px"+")";

  window.requestAnimationFrame(loop);
}