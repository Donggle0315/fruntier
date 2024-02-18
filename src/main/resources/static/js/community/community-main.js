window.onload = () => {
    let addArticleBtn = document.getElementById("add-article-btn");

    addArticleBtn.addEventListener("click", () => window.location.href = "/community/article/new");
}


function gotoPage(pageNum, maxPageNum) {
    let urlParams = new URLSearchParams(window.location.search);
    if (pageNum < 1 || pageNum > maxPageNum) return;
    urlParams.set("page", pageNum);
    window.location.href = "/community?" + urlParams.toString();
}