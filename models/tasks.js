import sqlite3 from 'sqlite3'
import { open } from 'sqlite'
import { tasksQueries } from '../lib/sql.js'

class MYdb {
    constructor(dbPath='data/midb.db') {
        this.connect(dbPath)
        this.q = []
    }

    async connect(filename) {
        try {
            this._db = await open({
                filename: filename,
                driver: sqlite3.Database
            })
        } catch (err) {
            console.log('Connection Error!\n', err)
        }
    }

    setQueries(queries) {
        this.q = queries
    }

    /** Execute a single query and return the resulting data. */
    async sql(query) {
        try {
            return await this._db.all(query)
        } catch (err) {
            console.log(err)
        }
    }

    async getRecord(table, id) {
        const rec = await this.sql(`select * from ${table} where id =${id}`)
        return rec[0]
    }

    run(query) {
        try {
            this._db.run(query)
        } catch (err) {
            console.log(err)
        }
    }

    /** Show the data base tables. */
    async tables() {
        const query = 'select type, name from sqlite_master where type = "table" or type = "view" order by type'
        try {
            return await this._db.all(query)
        } catch (err) {
            console.log(err)
        }
    }

    async record(table) {
        try {
            const obj = await this._db.get(`select * from ${table} limit 1`)
            
            for (const field of await Object.keys(await obj)) {
                obj[await field] = null
            }

            return await obj
        } catch (err) {
            console.log(err)
        }
    }

    insert(table, data) {
        if (!Array.isArray(data)) {
            data = [ data ]
        }

        data = this.validRecord(data)

        let values = data.map((record) => `(${Object.values(record).join(', ')})`).join(', ')
        let query = `insert into ${table} values ${values}`

        this.run(query)
    }

    validRecord(data) {
        for (const [i, rec] of Object.entries(data)) {
            for (const [key, value] of Object.entries(rec)) {
                if (typeof value == 'string') {
                    data[i][key] = `"${value}"`
                } else if (value === null) {
                    data[i][key] = 'null'
                }
                console.log(key, value)
            }
        }

        return data
    }

    update(table, data) {
        if (!Array.isArray(data)) {
            data = [ data ]
        }

        data = this.validRecord(data)

        for (const [i, rec] of Object.entries(data)) {
            let values = [] 

            for (const [key, value] of Object.entries(rec)) {
                values.push(`${key} = ${value}`)
            }

            let query = `update ${table} set ${values.join(', ')} where id = ${rec.id}`
            this.run(query)
        }
    }
}

class TasksDb extends MYdb {
    constructor(dbPath='data/tasks.db') {
        super(dbPath)
        this.setQueries(tasksQueries)
    }

    active() {
        this.view(this.q.active_tasks)
    }

    task(id) {
        this.view(this.q.tasks_by_id.replace('#{}', id))
    }

    info() {
        this.view([
            'select * from categories',
            'select * from statuses',
            'select * from priority',
        ])
    }
}

const tsk = new TasksDb()
const ptsk = new TasksDb('data/personal.db')

export { tsk, ptsk }
