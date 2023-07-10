const toDate = (str, format='%d.%m.%Y') => {
    const res = {}
    const formats = { '%Y': /\d{4}/gi,
        '%m': /\d{2}/gi,
        '%y': /\d{2}/gi,
        '%d': /\d{2}/gi,
        '%H': /\d{2}/gi,
        '%M': /\d{2}/gi }
    const parts = format.matchAll(/%[A-Za-z]/g)

    for (const el of parts) {
        const temp = el[0]
        res[temp] = parseInt(str.match(formats[temp])[0])
        str = str.replace(res[temp], '')
    }

    if (res['%y']) {
        res['%Y'] = 2000 + res['%y']
    }

    console.log(res['%y'])
    console.log(res)

	return new Date(res['%Y'], res['%m'] - 1, res['%d'] + 1)
}

const toString = (date, format='%Y-%m-%d') => {
	const day = firstZero(date.getDate())

	if (isNaN(day)) {
		return undefined
	}

	const month = firstZero(date.getMonth() + 1)
	const year = date.getFullYear()
	return format.replace('%d', day).replace('%m', month).replace('%Y', year)
}

const firstZero = (val) => {
	if (val < 10) {
		return `0${val}`
	} else {
		return `${val}`
	}
}

const now = () => {
    return new Date()
}

const setDate = (date, days) => {
    const d = new Date(date)
    return new Date(d.setDate(d.getDate() + days))
}

const setMonth = (date, months) => {
    const d = new Date(date)
    return new Date(d.setMonth(d.getMonth() + months))
}

const setYear = (date, years) => {
    const d = new Date(date)
    return new Date(d.setYear(d.getYear() + years))
}

export { toDate, toString, now, setDate, setMonth, setYear }

