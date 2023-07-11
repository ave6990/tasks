import pug from 'pug'
import { toString } from '../lib/date.js'
import { tsk, ptsk } from '../models/tasks.js'
import { jsonToTable } from '../lib/html-gen.js'

const getTasks = async (req, res) => {
    let content = jsonToTable(await tsk.active(), 'tasks_table')
    let main = pug.renderFile('views/tasks.pug',
        {
            content: content,
        }
    )
    res.render('index',
        {
            title: 'taskManager',
            header: toString(new Date()),
            content: main,
        }
    )
}

export { getTasks }
