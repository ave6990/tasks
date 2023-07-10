const jsonToTable = (data, table_id = 'tasks', header = true) => {
    let rows = ''
    let thead = ''
    let tbody = ''

    if (header) {
        console.log(data, data[0])
        for (const field of Object.keys(data[0])) {
            rows = `${rows}\n<th>${field}</th>`
        }
        thead = `<thead>\n<tr>${rows}\n</tr>\n</thead>`
        rows = ''
    }
    for (const [i, record] of data.entries()){
        let cols = ''
        for (const [j, field] of Object.keys(data[0]).entries()) {
            cols = `${cols}\n<td id='cell_${i}_${j}'>${record[field]}</td>`
        }
        rows = `${rows}\n<tr id='row_${i}'>${cols}\n</tr>`
    }

    const table = `<table class='tasks'>\n${thead}\n<tbody>${rows}\n</tbody>\n</table>`
    return table
}

export { jsonToTable }
