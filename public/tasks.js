const tab = document.getElementById('tasks_table')

tab.addEventListener('click', (ev) => {
    const row_num = ev.target.parentNode.id.split('_')[1]
    const id = document.getElementById(`cell_${row_num}_0`).innerHTML
    console.log(id)
})
