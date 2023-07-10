import pug from 'pug'
import { toString } from '../lib/date.js'

const getTasks = async (req, res) => {
    let content = pug.renderFile('./views/index.pug')
    res.render('index',
        {
            title: 'taskManager',
            header: 'Текущие задачи',
            date: toString(new Date()),
            content: 'Some info...'
        }
    )
}

export { getTasks }
