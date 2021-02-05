window.onload = function(){
    document.querySelector(".btn-next").addEventListener("click",getNext);
    document.querySelector(".btn-prev").addEventListener("click",getPrevious);
    autoPage();
}

function getNext() {
    let current = $('.img-body:visible');
    let next = (current.next().length) ? current.next() : $('.img-body').first();
    current.css('display', 'none');
    next.css('display', 'block');
}

function getPrevious(){
    let current = $('.img-body:visible');
    let next = (current.prev().length) ? current.prev() : $('.img-body').last();
    current.css('display', 'none');
    next.css('display', 'block');
}

function autoPage(){
    setInterval(function(){getNext()} ,5000);
}