class Form {
    constructor() {
        this._form = document.getElementById('input_form')
        this._inputs = Array.from(this._form.elements).filter(item => !!item.name)
        console.log(this._inputs)
    }

    fill(data) {

    }

    clear() {

    }

    getData() {

    }
}

const tab = document.getElementById('tasks_table')
const form = new Form()

tab.addEventListener('click', (ev) => {
    const row_num = ev.target.parentNode.id.split('_')[1]
    const id = document.getElementById(`cell_${row_num}_0`).innerHTML
    console.log(id)
})


