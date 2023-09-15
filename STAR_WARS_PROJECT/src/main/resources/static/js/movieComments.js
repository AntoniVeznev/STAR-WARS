const backEndLocation = 'http://localhost:8080';
let movieId = document.getElementById('movieId').getAttribute("value");
let commentSection = document.getElementById('commentSpanTest');

fetch(`${backEndLocation}/api/movies/comments/${movieId}`)
    .then((response) => response.json())
    .then((body) => {
        for (const bodyElement of body) {
            addCommentAsHtml(bodyElement);
        }
    });

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
                                            <button type="submit" class="btn btn-danger" onclick="(deleteMovieComment(${bodyElement.id}))">Delete</button>
                                       </div>
                                 </div>
                          <hr id="my0Test" class="my-0"/>
                   </div>`
    commentSection.innerHTML += commentHtml;
}

function deleteMovieComment(commentId) {
    fetch(`${backEndLocation}/api/movie/${movieId}/comments/${commentId}`, {
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
let commentForm = document.getElementById("commentForm");

commentForm.addEventListener("submit", (event) => {
    event.preventDefault();
    let text = document.getElementById("textAreaExample").value;

    fetch(`${backEndLocation}/api/movie/${movieId}/comments`, {
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
