let today = new Date();
let timeslots = [];
let regExTime = /([0-9]?[0-9]):([0-9][0-9]):([0-9][0-9])/;

function getAPIArray(callback) {
    let url = "/bookings/getbookings";
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            changeDate(data)
            addTimeslots();
            blockDateBeforeToday();
            //data will be the response from the server (the api's)
            // If it's not already in a list (array) then you have to build it up
        },
        error: function (err) {
            alert('error!');
            console.log(err); //prints error object to console
        }

    });
}

function changeDate(data) {
    document.getElementById("date").addEventListener("change", function () {
        timeslots = [];
        addTimeslots()
        let element = {date: this.value}
        checkIfDateExists(element.date, data)
        let day = new Date(this.value).getUTCDay();
        if([0].includes(day)){
            removeOptions();
            alert("Sorry, wij zijn gesloten op deze dag!")
        }
    })
}

function checkIfDateExists(elementDate, data) {
    if (elementDate === convertTodayToDate()) {
        timeslots = []
        removeOptions()
        let hour = today.getHours();
        if (hour < 9) {
            hour = 9;
        }
        for (i = hour; i <= 18; i++) {
            if (i === 9) {
                timeslots.push("0" + i + ":00:00")
            } else {
                timeslots.push(i + 1 + ":00:00");
            }
        }
    }
    checkIfTimeExists(elementDate, data)
}

function checkIfTimeExists(checkDate, data) {
    if (checkDate) {
        const result = data.filter(x => x.date === checkDate);
        for (j = 0; j < result.length; j++) {
            for (let i = 0; i < timeslots.length; i++) {
                if (timeslots[i] === result[j].time) {
                    timeslots.splice(i, 1)
                    removeOptions();
                    addOptions()
                    i = -1;
                }
            }
        }
        removeOptions();
        addOptions()
    }
}

function addTimeslots() {
    for (let i = 9; i <= 18; i++) {
        if (i === 9) {
            timeslots.push("0" + i + ":00:00")
        } else {
            timeslots.push(i + ":00:00");
        }
    }
}

function removeOptions() {
    let selectElement = document.getElementById("time")
    var i, L = selectElement.options.length - 1;
    for (i = L; i >= 0; i--) {
        selectElement.remove(i);
    }
}

function addOptions() {
    let select = document.getElementById("time");

    for (let i = 0; i < timeslots.length; i++) {
        let opt = timeslots[i];
        let el = document.createElement("option");
        el.textContent = opt;
        el.value = opt;
        select.appendChild(el);
    }
}

function convertTodayToDate() {
    let month = today.getMonth() + 1;
    let day = today.getDate();
    let year = today.getFullYear();
    if (month < 10)
        month = '0' + month.toString();
    if (day < 10)
        day = '0' + day.toString();

    return year + '-' + month + '-' + day;
}

function blockDateBeforeToday() {
    let month = today.getMonth() + 1;
    let day = today.getDate();
    let year = today.getFullYear();

    if (month < 10)
        month = '0' + month.toString();
    if (day < 10)
        day = '0' + day.toString();

    let maxDate = year + '-' + month + '-' + day;
    $('#date').attr('min', maxDate)
}

function blockClosedDays() {

}

function getsCalledWhenFinished(apiArray) {
    for (let i = 0; i < apiArray.length; i++) {
    }
}

getAPIArray(getsCalledWhenFinished); //invokes the ajax call