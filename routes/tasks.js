import fs from 'fs'

const async getTasks = (req, res) => {
    let content = await fs.readFile('views/index.html', { encoding: 'utf8' })
    res.send(content)
}

export { getTasks }
