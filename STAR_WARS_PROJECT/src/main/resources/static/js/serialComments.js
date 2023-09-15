const backEndLocation = 'http://localhost:8080'
let serialId = document.getElementById('serialId').getAttribute("value")
let commentSerialSection = document.getElementById('commentSerialSpanTest')

fetch(`${backEndLocation}/api/series/comments/${serialId}`)
    .then((response) => response.json())
    .then((body) => {
        for (const bodyElement of body) {
            addCommentAsHtml(bodyElement)
        }
    })

function addCommentAsHtml(bodyElement) {
    let commentHtml =
                    `<div class="comments" id="comment${bodyElement.id}">
                          <img class="shadow-1-strong me-3" src="/images/commentProfilePicture.webp" alt="avatar" width="60" height="60"/>
                                 <div>
                                        <h6 class="fw-bold mb-1">${bodyElement.authorName}</h6>
                                        <div class="d-flex align-items-center mb-3">
                                            <p class="mb-0">${bodyElement.created}</p>
                                        </div>
                                        <p class="mb-0">${bodyElement.postContent}</p>
                                        <div class="d-flex justify-content-between mt-3">
                                            <button type="submit" class="btn btn-danger" onclick="(deleteSerialComment(${bodyElement.id}))">Delete</button>
                                       </div>
                                 </div>
                          <hr id="my0Test" class="my-0"/>
                   </div>`
    commentSerialSection.innerHTML += commentHtml;
}
///api/serial/{serialId}/comments/{commentId}
function deleteSerialComment(commentId) {
    fetch(`${backEndLocation}/api/serial/${serialId}/comments/${commentId}`, {
        method: 'DELETE',
        headers: {
            [csrfHeaderName]: csrfHeaderValue
        }
    })
        .then(() => {
                document.getElementById("comment" + commentId).remove();
            }
        );
}

let csrfHeaderName = document.getElementById('csrf').getAttribute('name');
let csrfHeaderValue = document.getElementById('csrf').getAttribute('value');

let commentSerialForm = document.getElementById("commentSerialForm");


commentSerialForm.addEventListener("submit", (event) => {
    event.preventDefault();

    let text = document.getElementById("textAreaExample1").value;


    fetch(`${backEndLocation}/api/serial/${serialId}/comments`, {
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
        document.getElementById("textAreaExample1").value = '';
        let location = res.headers.get("Location");
        fetch(`${backEndLocation}${location}`)
            .then(res => res.json())
            .then(body => addCommentAsHtml(body))
    });

});
