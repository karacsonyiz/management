window.onload = function(){
console.log("asd")
    if (window.location.href.indexOf("error") != -1){
    console.log("fgh")
        let errormsg = document.querySelector("#errormsg");
        errormsg.setAttribute("style","display : block;color : red;");
    } else {
       console.log("jks")
    }
}