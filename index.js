import express from 'express'
import { getTasks } from './routes/tasks.js'

const port = 3300
const app = express()
app.set('view engine', 'pug')

app.route('/')
    .get(getTasks)

app.listen(port, () => {
    console.log(`Server started at port: ${port}`)
})


export { app }
