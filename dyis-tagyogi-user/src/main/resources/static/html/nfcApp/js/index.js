document.addEventListener("DOMContentLoaded", function () {
    
    //menu click: dialog
    const menu_items = document.querySelectorAll("main .menu-item");
    const menu_closes = document.querySelectorAll(".dialog .close");
    const dialog = document.getElementById("dialog");

    menu_items.forEach((menu_item) => menu_item.addEventListener("click", ()=>{
        dialog.classList.add("on");
    }));
    menu_closes.forEach((menu_close) => menu_close.addEventListener("click", ()=>{
        menu_close.closest(".dialog").classList.remove("on");
    }));

    //menu-list scroll: menu-btn(class on) control
    const menu_lists = document.querySelectorAll("main .menu-list > li"); 
    const menu_btn = document.querySelectorAll(".menu-btn");
    const menu_offsetTop = [];

    menu_lists.forEach((li)=>{
        menu_offsetTop.push(li.offsetTop);
    })
    const scrollMenuBtn = () => {
        if(window.scrollY > menu_offsetTop[0] && window.scrollY < menu_offsetTop[menu_offsetTop.length-1]){
            const target = menu_offsetTop.filter((li) => li > window.scrollY);
            const target_index = menu_offsetTop.indexOf(target[0]);
            const btn = menu_btn.item(target_index);
            menu_btn.forEach(btn => btn.classList.remove("on"));
            btn.classList.add("on");
        }else{
            menu_btn.forEach(btn => btn.classList.remove("on"));
            menu_btn.item(0).classList.add("on");
        }
    }
    
    window.addEventListener("scroll", scrollMenuBtn);

    //menu-btn click: menu-btn(class on), menu-list scroll control
    menu_btn.forEach((btn, index) => {
        btn.setAttribute("data-index", index);
        btn.addEventListener('click',  () =>  {
            const btn_index= +btn.getAttribute("data-index") + 1;
            const btn_target= document.querySelector(`.menu-list > li:nth-child(${btn_index})`);
            const scrollPosition = btn_target.offsetTop;

            window.scrollTo({ top: scrollPosition -150, behavior: 'smooth' });
            //menu_btn.forEach((btn) => btn.classList.remove("on"))
            //btn.classList.add("on");
        })
    });
    
});//document==

const aprojlassOn = (target) => {
    target.classList.add("on");
} 
const removeClassOn = (target) => {
    target.classList.remove("on");
} 

const scrollFix = (trigger, target, scroll_target) => {
    scroll_trigger = trigger.offsetTop - 60;

    if (window.scrollY > scroll_trigger) {
        target.classList.add("fix");
    }else {
        target.classList.remove("fix");
    }
    
    if(scroll_target){
        if (scroll_target.scrollTop > scroll_trigger) {
            target.classList.add("fix");
        }else {
            target.classList.remove("fix");
        }
    }
}