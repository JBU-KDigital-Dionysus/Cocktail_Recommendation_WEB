const searchList = document.getElementById("searchList");
async function readSearchList(ctName, ctKindEng, page=0)   {
    let url = "/cocktail/search.do?ctName=" + ctName + "&ctKindEng=" + ctKindEng
    + "&page=" + page;
    let resp = await fetch(url);
    if (resp.status == 200) {
        let textHtml = await resp.text();
        searchList.innerHTML = textHtml;
    } else{
        alert("칵테일이 없습니다.")
    }
}