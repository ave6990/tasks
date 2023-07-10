const tasksQueries = {

    tasks_by_id:
`with temp as (
    select
        #{} as id
    )
select
    t.id
    , p.name as priority
    , s.name as status
    , s.percentage as progress
    , t.description
    , mt.description as master_task
    , c.name as category
    , t.date_from
    , t.date_to
    , t.comment
from
    tasks as t
inner join
    statuses as s
    on t.status = s.id
inner join
    categories as c
    on t.category = c.id
inner join
    priority as p
    on t.priority = p.id
left join
    tasks as mt
    on t.master_task = mt.id
where
    t.id = (select id from temp) or t.master_task = (select id from temp)
order by
    coalesce(t.master_task, t.id)
    , t.master_task
    , t.date_to
    , t.priority
    , s.percentage desc
    , t.category`,

    active_tasks:
`select * from view_tasks where status != 'done' and status != 'cancel'`,
}

export { tasksQueries }
