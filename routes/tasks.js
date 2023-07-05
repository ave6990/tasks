import pug from 'pug'

const getTasks = async (req, res) => {
    let content = pug.renderFile('./views/index.pug')
    res.render('index', { title: 'taskManager',
        header: 'Текущие задачи',
        content: 'Some info...'})
}

export { getTasks }
