import express from 'express'
import tasks from 'routes/tasks.js'

const app = express()

app.route('/')
    .get(

app.listen(3000, () => {
    console.log('Server started at port: 3000')
})

export { app }
