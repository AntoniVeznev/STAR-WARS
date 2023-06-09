const backEndLocation = 'http://localhost:8080'

let movieId = document.getElementById('movieId').getAttribute("value")
let commentSection = document.getElementById('commentSpanTest')

fetch(`${backEndLocation}/api/${movieId}/comments`)
    .then((response) => response.json())
    .then((body) => {
        for (const bodyElement of body) {
            addCommentAsHtml(bodyElement)
        }
    })

function addCommentAsHtml(bodyElement) {
    let commentHtml = `<img class="shadow-1-strong me-3" src="/images/commentProfilePicture.webp" alt="avatar" width="60" height="60"/>
                       <div>
                           <h6 class="fw-bold mb-1">${bodyElement.authorName}</h6>
                           <div class="d-flex align-items-center mb-3">
                                 <p class="mb-0">${bodyElement.created}</p>
                           </div>
                           <p class="mb-0">${bodyElement.postContent}</p>
                       </div>
                       <hr id="my0Test" class="my-0"/>`

    commentSection.innerHTML += commentHtml;
}

let csrfHeaderName = document.getElementById('csrf').getAttribute('name');
let csrfHeaderValue = document.getElementById('csrf').getAttribute('value');

let commentForm = document.getElementById("commentForm");


commentForm.addEventListener("submit", (event) => {
    event.preventDefault();

    let text = document.getElementById("textAreaExample").value;


    fetch(`${backEndLocation}/api/${movieId}/comments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            postContent: text
        })

    }).then((res) => {
        document.getElementById("textAreaExample").value = '';
        let location = res.headers.get("Location");
        fetch(`${backEndLocation}${location}`)
            .then(res => res.json())
            .then(body => addCommentAsHtml(body))
    });

});
