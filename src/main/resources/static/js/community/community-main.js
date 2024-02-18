window.onload = () => {
    let addArticleBtn = document.getElementById("add-article-btn");
    addArticleBtn.addEventListener("click", () => window.location.href = "/community/article/new");

    document.getElementById('search-form').addEventListener('submit', searchPage)
}


function gotoPage(pageNum, maxPageNum) {
    let urlParams = new URLSearchParams(window.location.search);
    if (pageNum < 1 || pageNum > maxPageNum) return;
    urlParams.set("page", pageNum);
    window.location.href = "/community?" + urlParams.toString();
}

function searchPage(event) {
    event.preventDefault();

    let query = document.getElementById('search-input').value;
    let urlParams = new URLSearchParams(window.location.search);
    urlParams.set("search", query);
    urlParams.set("page", 1);

    window.location.href = "/community?" + urlParams.toString(); // Redirect to the new URL with the query parameter
}