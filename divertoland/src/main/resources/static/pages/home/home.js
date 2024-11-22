const ready = fn => document.readyState !== 'loading' ? fn() : document.addEventListener('DOMContentLoaded', fn);

ready(() => {
    "use strict";

    console.log(document.getElementsByClassName("atracao-card")[0])
    console.log(getComputedStyle(document.getElementsByClassName("atracao-card")[2]).getPropertyValue('--position'));

});