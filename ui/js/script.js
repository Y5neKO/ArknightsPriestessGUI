const body=document.querySelector('body')
const sidebar=body.querySelector('nav')
const toggle=body.querySelector('.toggle')
// const searchBtn=body.querySelector('.search-box')
const modeSwitch=body.querySelector('.toggle-switch')
const modeText=body.querySelector('.mode-text')
const content_box=body.querySelector('.log_content_box')
const setting_box=body.querySelector('.log_setting_box')
const setting_button=body.querySelector('.log_setting_button_2')
const task_box=body.querySelector('.current_task_box')


toggle.addEventListener('click',()=>{
    sidebar.classList.toggle('close')
})
// searchBtn.addEventListener('click',()=>{
//     sidebar.classList.remove('close')
// })
modeSwitch.addEventListener('click',()=>{

    body.classList.toggle('dark');
    content_box.classList.toggle('dark');
    setting_box.classList.toggle('dark');
    task_box.classList.toggle('dark');

    if(body.classList.contains('dark')){
        modeText.innerText="白"
    }else{
        modeText.innerText="夜"
    }
});

setting_button.addEventListener('click',()=>{
    setting_button.classList.toggle('off');
    if(setting_button.classList.contains('off')){
        isAjaxEnabled = false;
        setting_button.innerText="自动滚动: 关"
    }else{
        isAjaxEnabled = true;
        setting_button.innerText="自动滚动: 开"
    }
});