import express from 'express'
import bodyParser from 'body-parser'
import { getTasks } from './routes/tasks.js'

const port = 3300
const app = express()
app.set('view engine', 'pug')
app.use(express.static('../public'))
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.text())
app.use(bodyParser.json({ type: 'application/json' }))

app.route('/')
    .get(getTasks)

app.listen(port, () => {
    console.log(`Server started at port: ${port}`)
})

export { app }
