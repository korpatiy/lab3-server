const API_URL = 'http://localhost:8080/api';

function fetchMessages() {
    const request = new XMLHttpRequest();
    request.open("GET", `${API_URL}/messages`, true);
    request.responseType = "json";
    request.send();

    request.onload = function () {
        const body = document.getElementById("message-list")
        if (this.status === 200) {
            for (let message in request.response) {
            }
        }
    }
}

async function postMessage(message) {
    const response = await fetch(`${API_URL}/messages`,
        {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(message)
        })
    if (response.ok) {
        return await response.json()
    } else {
        //todo
    }
}

async function handlePostMessageSubmit(event) {
    event.preventDefault()
    const form = event.target
    const message = {
        author: form.querySelector('[name="sender"]').value,
        text: form.querySelector('[name="message"]').value
    }
    await postMessage(message)
}

function init() {
    const sendForm = document.querySelector('[data-test="send-form"]')
    if (!sendForm) {
        throw new Error('Send form is null')
    }
    sendForm.addEventListener('submit', handlePostMessageSubmit);
}


init();