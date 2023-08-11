function getAPIArray(callback) {
    let url = "/bookings/getbookings";
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            console.log(data)
            checkIfDateExists(data)
            timeslots = [];
            addTimeslots();
            //data will be the response from the server (the api's)
            // If it's not already in a list (array) then you have to build it up
        },
        error: function (err) {
            alert('error!');
            console.log(err); //prints error object to console
        }

    });
}

let timeslots = []

function checkIfDateExists(data) {
    document.getElementById("date").addEventListener("change", function () {
        let warning = document.getElementById("warningtime")
        warning.innerText = "";
        if (!checkIfDateOK(this.value)) {
            warning.innerText = "Kan geen datum in verleden gebruiken"
            warning.style.color = "red";
        }

        console.log("You selected: " + this.value)
        let element = {date: this.value}
        let check = data.some(d => d.date === element.date)
        console.log(timeslots)
        console.log(data.some(d => d.date === element.date))
        if (check) {
            let foundObj = data.find(item => item.date === element.date)
            console.log(foundObj.time + " FOUND TIME")
            for (let i = 0; i < timeslots.length; i++) {
                if (timeslots[i] === foundObj.time) {
                    let index = timeslots.indexOf(foundObj.time);
                    let timeslotLength = timeslots.length;
                    timeslots.splice(i, 1)
                    /*for (let i = index; i < timeslotLength; i++) {
                        timeslots.push(i + ":15:00")
                    }*/
                    removeOptions(document.getElementById('time'));
                    addOptions()
                }
            }
            console.log(timeslots + " NEW TIMESLOT")
        } else {
            removeOptions(document.getElementById('time'));
            timeslots = []
            addTimeslots()
            addOptions()
            console.log(timeslots + " REFRESHED TIMESLOT")
        }
    })
}

function addTimeslots() {
    for (let i = 9; i <= 18; i++) {
        timeslots.push(i + ":00:00");
    }
}

function removeOptions(selectElement) {
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

function checkIfDateOK(date) {
    let today = new Date()
    let currentDay= String(today.getDate()).padStart(2, '0');
    let currentMonth = String(today.getMonth()+1).padStart(2,"0");
    let currentYear = today.getFullYear();
    let currentDate = currentYear+currentMonth+currentDay;
    let time = today.getTime();
    return date < currentDate;
}

function getsCalledWhenFinished(apiArray) {
    for (let i = 0; i < apiArray.length; i++) {
        console.log(i.date)
    }
}


getAPIArray(getsCalledWhenFinished); //invokes the ajax call