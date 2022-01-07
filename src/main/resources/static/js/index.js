const API_URL = 'http://localhost:8080/api/messages';

function findElements() {
    const sendForm = document.querySelector('[data-test="send-form"]')
    //const clapForm = document.querySelector('[data-test="message-clap-form"]')

    const messageList = document.querySelector('#message-list')
    const messageTemplate = document.querySelector('#message-template')

    const sendAlert = sendForm.querySelector('[data-test="send-alert"]')
    const alert = sendAlert.querySelector('.d-none')

    //const successAlert = sendAlert.querySelector('.alert-success')
    //const loadingAlert = sendAlert.getElementsByClassName('alert-warning')
    //successAlert.style.display = "none"

    const authorInput = sendForm.querySelector('[name="sender"]')
    const textInput = sendForm.querySelector('[name="message"]')

    return {
        sendForm,
        messages: {messageList, messageTemplate},
        alert,
        inputs: {authorInput, textInput}
    }
}

function fetchMessages(elements) {
    fetch(`${API_URL}`).then(
        response => {
            if (response.ok)
                return response.json()
            else throw Error(response.statusText)
        }
    ).then(
        messages => {
            //console.log(messages)
            const messageNodes = []
            for (const message of messages) {
                const messageNode = elements.messages.messageTemplate.content.cloneNode(true)

                const authorNode = messageNode.querySelector('[data-test="message-author"]')
                if (authorNode)
                    authorNode.innerText = message.author

                const textNode = messageNode.querySelector('[data-test="message-text"]')
                if (textNode)
                    textNode.innerText = message.text

                const openLink = messageNode.querySelector('[data-test="message-open"]')
                if (openLink) {
                    openLink.href = `${API_URL}/${message.id}`
                }

                const clapNode = messageNode.querySelector('[data-test="clap-count"]')
                if (clapNode) {
                    clapNode.innerText = message.clap
                    const clapButton = messageNode.querySelector('button');

                    clapButton.addEventListener('click', event => {
                        event.preventDefault()
                        clapButton.disabled = true

                        const currClapsCount = parseInt(clapNode.innerText)
                        clapNode.innerText = (currClapsCount + 1).toString()

                        clapMessage(message.id)
                            .then(() => fetchMessages(elements))
                            .catch(error => console.error(error))
                            .finally(() => clapButton.disabled = false)
                    })
                }
                messageNodes.push(messageNode)
            }
            elements.messages.messageList.replaceChildren(...messageNodes)
        }
    ).catch(
        error => console.error(error)
    )
}

async function clapMessage(id) {
    return await fetch(`${API_URL}/${id}/clap`,
        {
            method: 'POST'
        })
}

async function postMessage(message) {
    return await fetch(`${API_URL}`,
        {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(message)
        })
}

async function handlePostMessageSubmit(event) {
    event.preventDefault()
    const sendButton = elements.sendForm.querySelector('button')
    sendButton.disabled = true
    elements.alert.className = alertClasses.warning
    elements.alert.innerText = alertMessages.loading

    const message = {
        author: elements.inputs.authorInput.value,
        text: elements.inputs.textInput.value
    }
    postMessage(message).then(
        response => {
            if (response.ok) {
                response.json()
                elements.alert.className = alertClasses.success
                elements.alert.innerText = alertMessages.messageSent
                fetchMessages(elements)
            } else {
                elements.alert.className = alertClasses.danger
                response.json().then(errors => {
                    let result = ''
                    for (const error of errors) {
                        result += error.message + '\n'
                    }
                    elements.alert.innerText = result
                })
            }
        }
    ).catch(
        () => {
            elements.alert.className = alertClasses.danger
            elements.alert.innerText = 'Произошла ошибка во время отправки запроса'
        }
    ).finally(
        () => sendButton.disabled = false
    )
}

const alertClasses = {
    success: "alert alert-success",
    warning: "alert alert-warning",
    danger: "alert alert-danger"
}

const alertMessages = {
    loading: 'Загрузка...',
    messageSent: 'Сообщение отправлено'
}

const elements = findElements()

function init() {
    fetchMessages(elements)
    elements.sendForm.addEventListener('submit', handlePostMessageSubmit);
}

init();
